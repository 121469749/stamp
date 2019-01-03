/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.log.entity.ModifyCompanyAttachment;
import com.thinkgem.jeesite.modules.log.dao.ModifyCompanyAttachmentDao;

/**
 * 企业信息变更Service
 * @author linzhibao
 * @version 2018-08-222
 */
@Service
@Transactional(readOnly = true)
public class ModifyCompanyAttachmentService extends CrudService<ModifyCompanyAttachmentDao, ModifyCompanyAttachment> {

	@Autowired
	private ModifyCompanyAttachmentDao modifyCompanyAttachmentDao;

	public ModifyCompanyAttachment get(String id) {
		return super.get(id);
	}
	
	public List<ModifyCompanyAttachment> findList(ModifyCompanyAttachment modifyCompanyAttachment) {
		return super.findList(modifyCompanyAttachment);
	}
	
	public Page<ModifyCompanyAttachment> findPage(Page<ModifyCompanyAttachment> page, ModifyCompanyAttachment modifyCompanyAttachment) {
		return super.findPage(page, modifyCompanyAttachment);
	}
	
	@Transactional(readOnly = false)
	public void save(ModifyCompanyAttachment modifyCompanyAttachment) {
		super.save(modifyCompanyAttachment);
	}
	
	@Transactional(readOnly = false)
	public void delete(ModifyCompanyAttachment modifyCompanyAttachment) {
		super.delete(modifyCompanyAttachment);
	}
	@Transactional(readOnly = false)
	public ModifyCompanyAttachment findModifyCompanyAttachment(ModifyCompanyAttachment mca){
		return modifyCompanyAttachmentDao.findModifyCompanyAttachment(mca);
	}
	
}