package com.ychp.spider.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.model.Rule;
import com.ychp.spider.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 16/10/16
 */
@Component
public class RuleCacher {

    @Autowired
    private RuleRepository ruleRepository;

    private LoadingCache<String,List<Rule>> rulesCacher;

    private LoadingCache<Long, Rule> ruleCacher;

    public RuleCacher(){
        ruleCacher = CacheBuilder.newBuilder().build(new CacheLoader<Long, Rule>() {
            @Override
            public Rule load(Long key) {
                return ruleRepository.findById(key);
            }
        });

        rulesCacher = CacheBuilder.newBuilder().build(new CacheLoader<String, List<Rule>>() {
            @Override
            public List<Rule> load(String key) {
                Map<String, Object> params = Maps.newHashMap();
                params.put("offset", 0);
                params.put("limit", Integer.MAX_VALUE);
                Paging<Rule> rulesPaging = ruleRepository.paging(params);
                List<Rule> rules = rulesPaging.getDatas();
                if(Objects.equals("domain", key)){
                    String url;
                    for(Rule rule : rules){
                        url = rule.getUrl();
                        url = url.substring(url.indexOf("://")+3);
                        if(url.contains("/")) {
                            url = url.substring(0, url.indexOf("/"));
                        }
                        rule.setUrl(url);
                    }
                }
                rules.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
                return rules;
            }
        });
    }

    public List<Rule> listAll(){
        return rulesCacher.getUnchecked("all");
    }

    public List<Rule> listAllWithDomain(){
        return rulesCacher.getUnchecked("domain");
    }

    public Rule getRuleById(Long id){
        return ruleCacher.getUnchecked(id);
    }

    public void invalidate(){
        ruleCacher.invalidateAll();
        rulesCacher.invalidateAll();
    }
}
