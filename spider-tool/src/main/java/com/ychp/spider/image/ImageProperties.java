package com.ychp.spider.image;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
@Component
@ConfigurationProperties(prefix = "image")
public class ImageProperties {

    private String imageUrl;

    private String path;
}
