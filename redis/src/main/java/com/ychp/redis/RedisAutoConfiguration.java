package com.ychp.redis;

import com.ychp.redis.dao.JedisTemplate;
import com.ychp.redis.manager.RedisManager;
import com.ychp.redis.properties.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 16/7/25
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedisAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    @ConditionalOnMissingBean(JedisPoolConfig.class)
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        if(!StringUtils.isEmpty(redisProperties.getPool().getMaxActive())) {
            config.setMaxTotal(redisProperties.getPool().getMaxActive());
        }
        if(!StringUtils.isEmpty(redisProperties.getPool().getMaxIdle())) {
            config.setMaxIdle(redisProperties.getPool().getMaxIdle());
        }
        if(!StringUtils.isEmpty(redisProperties.getPool().getMinIdle())) {
            config.setMinIdle(redisProperties.getPool().getMaxIdle());
        }
        if(!StringUtils.isEmpty(redisProperties.getPool().getMaxWait())) {
            config.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        }
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(JedisPool.class)
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig){
        log(redisProperties);
        Integer port = StringUtils.isEmpty(redisProperties.getPort()) ? 6379 : redisProperties.getPort();
        Integer timeOut = StringUtils.isEmpty(redisProperties.getTimeout()) ? 1000 : redisProperties.getTimeout();
        Integer database = StringUtils.isEmpty(redisProperties.getDatabase()) ? 0 : redisProperties.getDatabase();
        return StringUtils.isEmpty(redisProperties.getPassword()) ?
                new JedisPool(jedisPoolConfig, redisProperties.getHost(), port, timeOut, null, database) :
                new JedisPool(jedisPoolConfig, redisProperties.getHost(), port, timeOut, redisProperties.getPassword(), database);
    }

    private void log(RedisProperties redisProperties) {
        log.info("===========redis============");
        log.info("host ==> {}", redisProperties.getHost());
        log.info("port ==> {}", redisProperties.getPort());
        log.info("auth ==> {}", redisProperties.getPassword());
        log.info("db   ==> {}", redisProperties.getDatabase());
        log.info("===========redis============");
    }

    @Bean
    @ConditionalOnMissingBean(JedisTemplate.class)
    public JedisTemplate jedisTemplate(Pool<Jedis> jedisPool){
        Integer database = StringUtils.isEmpty(redisProperties.getDatabase()) ? 0 : redisProperties.getDatabase();
        return new JedisTemplate(jedisPool, database);
    }

    @Bean
    @ConditionalOnMissingBean(RedisManager.class)
    public RedisManager redisManager() {
        return new RedisManager();
    }

}
