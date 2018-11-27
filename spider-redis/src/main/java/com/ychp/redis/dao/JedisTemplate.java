package com.ychp.redis.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Pool;

/**
 * @author yingchengpeng
 * @date 2018-08-14
 */
public class JedisTemplate {

    private Pool<Jedis> jedisPool;

    private int indexdb;

    public JedisTemplate(Pool<Jedis> jedisPool, int indexdb){
        this.jedisPool = jedisPool;
        this.indexdb = indexdb;
    }

    public <T> T excute(JedisAction<T> action){
        Jedis jedis = null;
        boolean broken = false;
        try{
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return action.action(jedis);
        }catch (JedisConnectionException je){
            broken = true;
            throw je;
        } finally {
            if(jedis != null){
                if(broken){
                    jedisPool.returnBrokenResource(jedis);
                }else {
                    jedisPool.returnResource(jedis);
                }
            }
        }
    }

    public interface JedisAction<T> {
        T action(Jedis jedis);
    }

}
