package com.ychp.mybatis.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.ychp.common.model.paging.Paging;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-08-08
 */
public class BaseRepository<T, K> {

    @Resource
    private SqlSession sqlSession;
    @Resource
    private ObjectMapper objectMapper;

    private static final String CREATE = "create";
    private static final String DELETE = "delete";
    private static final String UPDATE = "update";
    private static final String FIND_BY_ID = "findById";
    private static final String FIND_BY_IDS = "findByIds";
    protected static final String LIST = "list";
    protected static final String COUNT = "count";
    protected static final String PAGING = "paging";
    private final static TypeReference<Map<String, Object>> TYPE = new TypeReference<Map<String, Object>>(){};

    private final String nameSpace;


    public BaseRepository(){
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            nameSpace = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        } else {
            //解决cglib实现aop时转换异常
            nameSpace = ((Class<T>) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        }
    }

    /**
     * 添加对象
     * @param t 范型对象
     * @return 增加记录数
     */
    public Boolean create(T t){
        return sqlSession.insert(sqlId(CREATE), t) == 1;
    }

    /**
     * 删除
     * @param id 主键
     * @return 删除记录数
     */
    public Boolean delete(Long id){
        return sqlSession.delete(sqlId(DELETE), id) == 1;
    }

    /**
     * 更新对象
     * @param t 范型对象
     * @return 更新记录数
     */
    public Boolean update(T t){
        return sqlSession.update(sqlId(UPDATE), t) == 1;
    }

    /**
     * 查询单个对象
     * @param id 主键
     * @return 对象
     */
    public T findById(K id){
        return sqlSession.selectOne(sqlId(FIND_BY_ID), id);
    }

    /**
     * 批量查询
     * @param ids 主键列表
     * @return
     */
    public List<T> findByIds(List<K> ids){
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return sqlSession.selectList(sqlId(FIND_BY_IDS), ids);
    }

    /**
     * 查询对象列表
     * @param criteria 查询条件
     * @return 查询到的对象列表
     */
    public List<T> list(T criteria){
        Map<String, Object> params = Maps.newHashMap();
        if (criteria != null) {
            Map<String, Object> objMap = objectMapper.convertValue(criteria, TYPE);
            params.putAll(objMap);
        }
        return sqlSession.selectList(sqlId(LIST), params);
    }

    /**
     * 查询对象列表
     * @param criteria 查询条件
     * @return 查询到的对象列表
     */
    public List<T> list(Map<String, Object> criteria){
        return sqlSession.selectList(sqlId(LIST), criteria);
    }

    /**
     * 查询分页对象
     * @param criteria 查询条件
     * @return 查询到的分页对象
     */
    public Paging<T> paging(Map<String, Object> criteria){
        Long total = sqlSession.selectOne(sqlId(COUNT), criteria);
        if (total <= 0){
            return new Paging<>(0L, Collections.<T>emptyList());
        }

        List<T> datas = sqlSession.selectList(sqlId(PAGING), criteria);
        return new Paging<>(total, datas);
    }

    protected String sqlId(String id){
        return nameSpace + "." + id;
    }


    protected SqlSession getSqlSession() {
        return sqlSession;
    }
}
