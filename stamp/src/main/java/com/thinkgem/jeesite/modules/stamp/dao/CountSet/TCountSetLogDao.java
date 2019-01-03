/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.dao.CountSet;


import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.TCountSetLog;

/**
 * 历史分配记录DAO接口
 * @author howen
 * @version 2017-12-26
 */
@MyBatisDao
public interface TCountSetLogDao extends CrudDao<TCountSetLog> {

    String getSumEleCount(Company company);

    String getSumPhyCount(Company company);
}