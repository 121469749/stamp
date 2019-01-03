/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.dao.stamp;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 印章信息DAO接口
 * @author Locker
 * @version 2017-05-13
 */
@MyBatisDao
public interface NewStampDao {


    /**
     * 为物理印章绑定电子印章
     * @param stamp
     * @return
     */
    public int bindPhyForEle(Stamp stamp);

    /**
     * 为电子印章绑定物理印章
     * @param stamp
     * @return
     */
    public int bindEleForPhy(Stamp stamp);

    /**
     *
     * 查找没有被绑定的物理印章
     *
     * @param stamp
     * @return
     */
    public List<Stamp> findDependPhyStamps(Stamp stamp);


    public int rollBackStampState(Stamp stamp);
}
