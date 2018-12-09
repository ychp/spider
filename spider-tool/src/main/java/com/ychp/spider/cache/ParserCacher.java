package com.ychp.spider.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.model.Parser;
import com.ychp.spider.repository.ParserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Component
public class ParserCacher {

    @Autowired
    private ParserRepository parserRepository;

    private LoadingCache<String,List<Parser>> parserCache;

    public ParserCacher(){
        parserCache = CacheBuilder.newBuilder().build(new CacheLoader<String, List<Parser>>() {
            @Override
            public List<Parser> load(String key) {
                Map<String, Object> params = Maps.newHashMap();
                params.put("offset", 0);
                params.put("limit", Integer.MAX_VALUE);
                Paging<Parser> parserNodes = parserRepository.paging(params);
                return parserNodes.getDatas();
            }
        });
    }

    public List<Parser> listAll(){
        return parserCache.getUnchecked("all");
    }

    public void invalidate(){
        parserCache.invalidateAll();
    }
}
