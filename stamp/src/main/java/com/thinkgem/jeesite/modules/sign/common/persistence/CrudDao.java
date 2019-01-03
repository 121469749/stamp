package com.thinkgem.jeesite.modules.sign.common.persistence;

import java.util.List;

/**
 * Created by hjw-pc on 2017/7/14.
 */
public interface CrudDao<T> extends BaseDao {


    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    public T get(T entity);

    /**
     * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
     * @param entity
     * @return
     */
    public List<T> findList(T entity);



    /**
     * 插入数据
     * @param entity
     * @return
     */
    public void insert(T entity);

    /**
     * 更新数据
     * @param entity
     * @return
     */
    public void update(T entity);


    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     * @param entity
     * @return
     */
    public void delete(T entity);
}
