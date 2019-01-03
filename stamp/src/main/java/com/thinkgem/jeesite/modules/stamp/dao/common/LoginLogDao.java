package com.thinkgem.jeesite.modules.stamp.dao.common;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.entity.LoginLog;


/**
 * Created by hjw-pc on 2017/7/27.
 */
@MyBatisDao
public interface LoginLogDao extends CrudDao<LoginLog> {
}
