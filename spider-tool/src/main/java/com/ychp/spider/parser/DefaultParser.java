package com.ychp.spider.parser;

import com.ychp.spider.model.Rule;
import com.ychp.spider.dto.SpiderWebData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Component(value = "default")
public class DefaultParser extends Parser<SpiderWebData> {


    private void printDatas(List<SpiderWebData> datas){
        for(SpiderWebData data : datas) {
            System.out.println(data);
        }
    }

    @Override
    protected void initConfigPre() {
        setConfigPre("default");
    }

    @Override
    public List<SpiderWebData> spider(Map<String, String> ruleValues, Rule rule) {
        return parseContext(ruleValues);
    }

    public static void main(String[] args){
        DefaultParser parser = new DefaultParser();
        List<SpiderWebData> datas = parser.parseContext();
        parser.printDatas(datas);
        System.out.println();
    }
}
