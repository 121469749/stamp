package com.thinkgem.jeesite.modules.sign.dao;



import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sign.entity.Audit;

import java.util.List;

/**
 * 安全审计功能DAO接口
 * @author bb
 * @version 2018-07-16
 */
@MyBatisDao
public interface AuditDao extends CrudDao<Audit>{

}
