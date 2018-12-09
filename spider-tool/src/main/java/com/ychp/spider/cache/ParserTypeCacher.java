package com.ychp.spider.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.ychp.spider.model.ParserType;
import com.ychp.spider.repository.ParserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Component
public class ParserTypeCacher {

    @Autowired
    private ParserTypeRepository parserTypeRepository;

    private LoadingCache<Boolean, List<ParserType>> parsersCache;
    private LoadingCache<Long, ParserType> parserCache;

    public ParserTypeCacher() {
        parserCache = CacheBuilder.newBuilder().build(new CacheLoader<Long, ParserType>() {
            @Override
            public ParserType load(Long key) {
                return parserTypeRepository.findById(key);
            }
        });

        parsersCache = CacheBuilder.newBuilder().build(new CacheLoader<Boolean, List<ParserType>>() {
            @Override
            public List<ParserType> load(Boolean key) {
                Map<String, Object> params = Maps.newHashMap();
                if (!key) {
                    params.put("justAdmin", false);
                }
                return parserTypeRepository.list(params);
            }
        });
    }

    public List<ParserType> listBy(Boolean isAdmin) {
        return parsersCache.getUnchecked(isAdmin);
    }

    public ParserType findById(Long id) {
        return parserCache.getUnchecked(id);
    }

    public void invalidate() {
        parserCache.invalidateAll();
        parsersCache.invalidateAll();
    }
}
