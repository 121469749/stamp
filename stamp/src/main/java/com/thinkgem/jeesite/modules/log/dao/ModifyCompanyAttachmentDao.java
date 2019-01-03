/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.log.entity.ModifyCompanyAttachment;

/**
 * 企业信息变更DAO接口
 * @author linzhibao
 * @version 2018-08-22
 */
@MyBatisDao
public interface ModifyCompanyAttachmentDao extends CrudDao<ModifyCompanyAttachment> {

    public ModifyCompanyAttachment findModifyCompanyAttachment(ModifyCompanyAttachment mca);
	
}