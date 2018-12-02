package com.ychp.spider.image.tool;

import com.ychp.spider.image.ImageProperties;
import com.ychp.spider.utils.HttpDownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Component
public class DefaultWriter extends Writer {

    @Autowired
    private ImageProperties imageProperties;

    @Override
    public String writeImage(String url, String subPath, String name) {
        Boolean isSuccess = HttpDownloadUtil.download(url, imageProperties.getPath() + File.separator + subPath, name);
        if(isSuccess){
            return imageProperties.getImageUrl() + "/" + subPath + "/" +name;
        }
        return null;
    }

    @Override
    public Boolean deleteImage(String url) {
        String path = url.replace(imageProperties.getImageUrl(), imageProperties.getPath());
        File file = new File(path);
        if(file.exists()){
            return file.delete();
        }
        return false;
    }
}
