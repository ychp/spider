package com.ychp.spider.web.component.spider;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.DataCriteria;
import com.ychp.spider.enums.DataStatus;
import com.ychp.spider.image.core.WriterRegistry;
import com.ychp.spider.image.tool.Writer;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.service.SpiderDataReadService;
import com.ychp.spider.service.SpiderDataWriteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Component
public class Imager {

    @Resource
    private WriterRegistry writerRegistry;

    @Resource
    private SpiderDataWriteService spiderDataWriteService;

    @Resource
    private SpiderDataReadService spiderDataReadService;

    private ExecutorService executor;

    @PostConstruct
    public void syncDataInit() {
        this.executor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(1000),
                new ThreadFactoryBuilder().setNameFormat("download-%d").build(),
                (task, executor) -> {
                    DownloadTask task1 = (DownloadTask) task;
                    log.error("download[file = {}] is rejected", task1.data);
                });

    }


    public void download(Long taskId) {
        log.info("start image task[id={}]", taskId);
        try {
            int pageNo = 1;

            DataCriteria criteria = new DataCriteria();
            criteria.setPageNo(pageNo);
            criteria.setPageSize(200);
            criteria.setTaskId(taskId);
            criteria.setStatus(DataStatus.INIT.getValue());
            Paging<SpiderData> spiderDataPaging;

            List<SpiderData> datas;
            Writer imageWriter = writerRegistry.getWriter("defaultWriter");
            while (true) {
                spiderDataPaging = spiderDataReadService.paging(criteria);

                datas = spiderDataPaging.getDatas();
                if (datas.size() == 0) {
                    log.info("finish save image , pageNo={}", pageNo);
                    break;
                }

                for (SpiderData data : datas) {
                    String url = data.getContent();
                    if (!url.startsWith("http")) {
                        continue;
                    }
                    String subPath = "";
                    String tmpUrl = url.split("\\?")[0];
                    String subffix = tmpUrl.substring(tmpUrl.lastIndexOf("."));
                    String name = data.getId() + subffix;

                    if (name.endsWith(".mp4")) {
                        String fileName = url.split("&filename=")[1];
                        url = url.split("&filename=")[0];
                        DownloadTask task = new DownloadTask(url, subPath, fileName, data, imageWriter);
                        executor.submit(task);
                    } else {
                        download(url, subPath, name, data, imageWriter);
                    }
                }

                pageNo++;
                criteria.setPageNo(pageNo);
            }

        } catch (Exception e) {
            log.error("save image fail, error={}", Throwables.getStackTraceAsString(e));
        }

        log.info("end image rule[id={}]", taskId);
    }


    @AllArgsConstructor
    class DownloadTask implements Callable<Boolean> {

        private String url;
        private String subPath;
        private String name;
        private SpiderData data;
        private Writer imageWriter;

        @Override
        public Boolean call() {
            log.info("download file = {} start", name);
            Boolean result = download(url, subPath, name, data, imageWriter);
            log.info("download file = {} end", name);
            return result;
        }
    }

    private Boolean download(String url, String subPath, String name, SpiderData data, Writer imageWriter) {
        try {

            String path = imageWriter.writeImage(url, subPath, name);

            if (!StringUtils.isEmpty(path)) {
                data.setPath(path);
                data.setStatus(DataStatus.FINISH.getValue());
                spiderDataWriteService.update(data);
            } else {
                data.setStatus(DataStatus.ERROR.getValue());
                spiderDataWriteService.update(data);
            }
            return true;
        } catch (Exception e) {
            log.error("download fail, case {}", Throwables.getStackTraceAsString(e));
            return false;
        }
    }

}
