package com.thinkgem.jeesite.modules.stamp.dao.stamprecord;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampOperation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 印章操作历史DAO接口
 * Created by sjk on 2017-05-22.
 */
@MyBatisDao
public interface StampOperationDao {

    /**
     * 插入数据
     * @param entity
     * @return
     */
    public int insert(StampOperation entity);

    /**
     * 查询印章使用历史
     * @param entity
     * @return
     */
    public List<StampOperation> findOperation(StampOperation entity);

    /**
     * 根据印章类型（物理、电子）查询盖章列表
     */
    public List<StampOperation> findUseHistoryByShape(StampOperation entity);

}
