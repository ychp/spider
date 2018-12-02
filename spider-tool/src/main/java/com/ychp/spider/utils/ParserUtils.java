package com.ychp.spider.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ychp.spider.dto.SpiderRule;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public class ParserUtils {

    public static SpiderRule getRule(Map<String,String> ruleValues){
        SpiderRule spiderRule = new SpiderRule();
        spiderRule.setUrlRegex(ruleValues.get("url").trim());
        String keyStr = ruleValues.get("keywords");
        spiderRule.setKeyWords(keyStr);
        if(!StringUtils.isEmpty(keyStr)){
            List<String> tagTypes = Lists.newArrayList(keyStr.trim().split(","));
            spiderRule.setKeyWord(tagTypes);
        }
        String videoStr = ruleValues.get("videoTag");
        if(!StringUtils.isEmpty(videoStr)){
            List<String> tagTypes = Lists.newArrayList(videoStr.trim().split(","));
            spiderRule.setVideoTag(tagTypes);
        }
        String imageStr = ruleValues.get("imageTag");
        if(!StringUtils.isEmpty(imageStr)){
            List<String> tagTypes = Lists.newArrayList(imageStr.trim().split(","));
            spiderRule.setImageTag(tagTypes);
        }
        String textStr = ruleValues.get("textTag");
        if(!StringUtils.isEmpty(textStr)){
            List<String> tagTypes = Lists.newArrayList(textStr.trim().split(","));
            spiderRule.setTextTag(tagTypes);
        }
        String subTag = ruleValues.get("subTag");
        if(!StringUtils.isEmpty(subTag)){
            List<String> tagTypes = Lists.newArrayList(subTag.trim().split(","));
            spiderRule.setSubTag(tagTypes);
        }
        Set<String> tags = Sets.newHashSet();
        tags.addAll(spiderRule.getVideoTag());
        tags.addAll(spiderRule.getImageTag());
        tags.addAll(spiderRule.getTextTag());
        tags.addAll(spiderRule.getSubTag());
        spiderRule.getTags().addAll(tags);
        return spiderRule;
    }
}
