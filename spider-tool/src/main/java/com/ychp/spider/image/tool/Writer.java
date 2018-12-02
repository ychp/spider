package com.ychp.spider.image.tool;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public abstract class Writer {

    public abstract String writeImage(String url, String subPath, String name);

    public abstract Boolean deleteImage(String url);
}
