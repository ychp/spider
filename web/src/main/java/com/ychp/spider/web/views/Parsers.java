package com.ychp.spider.web.views;

import com.ychp.common.model.paging.Paging;
import com.ychp.common.util.SessionContextUtils;
import com.ychp.spider.bean.request.ParserCriteria;
import com.ychp.spider.bean.request.ParserTypeCriteria;
import com.ychp.spider.bean.request.TaskCriteria;
import com.ychp.spider.bean.response.ParserDetailInfo;
import com.ychp.spider.bean.response.ParserInfo;
import com.ychp.spider.bean.response.TaskInfo;
import com.ychp.spider.service.ParserReadService;
import com.ychp.spider.service.ParserTypeReadService;
import com.ychp.spider.service.TaskReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Controller
public class Parsers {

    @Resource
    private ParserReadService parserReadService;

    @Resource
    private ParserTypeReadService parserTypeReadService;

    @Resource
    private TaskReadService taskReadService;

    /**
     * 爬虫解析管理
     */
    @GetMapping("parsers")
    public String parsers(Model model, ParserCriteria criteria) {
        criteria.setUserId(SessionContextUtils.getUserId());
        Paging<ParserInfo> parserNodePaging = parserReadService.paging(criteria);
        model.addAttribute("parsers", parserNodePaging);
        model.addAttribute("criteria", criteria);
        return "parsers";
    }

    /**
     * 爬虫详情
     */
    @GetMapping("{id}/parser-detail")
    public String parserDetail(Model model, @PathVariable Long id) {
        ParserDetailInfo detailInfo = parserReadService.findOneById(id);
        model.addAttribute("parser", detailInfo);
        return "parser-detail";
    }

    /**
     * 爬虫创建/编辑
     */
    @GetMapping("/parser")
    public String parser(Model model, @RequestParam(required = false) Long id) {
        if(id != null) {
            ParserDetailInfo detailInfo = parserReadService.findOneById(id);
            model.addAttribute("parser", detailInfo);
        }

        ParserTypeCriteria criteria = new ParserTypeCriteria();
        if(!"admin".equals(SessionContextUtils.currentUser().getName())) {
            criteria.setJustAdmin(false);
        }
        log.info("criteria", criteria.toMap());
        model.addAttribute("types", parserTypeReadService.list(criteria));
        return "parser-edit";
    }

    /**
     * 爬虫任务管理
     */
    @GetMapping("tasks")
    public String tasks(Model model, TaskCriteria criteria) {
        criteria.setUserId(SessionContextUtils.getUserId());
        Paging<TaskInfo> taskInfo = taskReadService.paging(criteria);
        model.addAttribute("tasks", taskInfo);
        model.addAttribute("criteria", criteria);
        return "tasks";
    }
}
