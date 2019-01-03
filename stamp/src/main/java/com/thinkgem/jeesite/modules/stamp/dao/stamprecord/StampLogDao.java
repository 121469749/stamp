package com.thinkgem.jeesite.modules.stamp.dao.stamprecord;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampLog;

import java.util.List;

/**
 * 印章操作日志DAO接口
 * Created by sjk on 2017-07-03.
 */
@MyBatisDao
public interface StampLogDao {

    /**
     * 插入数据
     */
    public int insert(StampLog entity);

    /**
     * 查询
     */
    public List<StampLog> findList(StampLog entity);
}
