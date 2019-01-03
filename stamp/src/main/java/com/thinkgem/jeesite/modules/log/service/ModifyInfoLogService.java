/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.log.dao.ModifyInfoLogDao;
import com.thinkgem.jeesite.modules.log.entity.ModifyCompanyAttachment;
import com.thinkgem.jeesite.modules.log.entity.ModifyInfoLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 操作日志明细Service
 * @author xucaikai
 * @version 2018-08-08
 */
@Service
@Transactional(readOnly = true)
public class ModifyInfoLogService extends CrudService<ModifyInfoLogDao, ModifyInfoLog> {

	public ModifyInfoLog get(String id) {
		return super.get(id);
	}
	
	public List<ModifyInfoLog> findList(ModifyInfoLog tLogDetail) {
		return super.findList(tLogDetail);
	}
	
	public Page<ModifyInfoLog> findPage(Page<ModifyInfoLog> page, ModifyInfoLog tLogDetail) {
		return super.findPage(page, tLogDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(ModifyInfoLog tLogDetail) {
		super.save(tLogDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(ModifyInfoLog tLogDetail) {
		super.delete(tLogDetail);
	}

	@Transactional(readOnly = false)
	public String findColComment(String tableName,String s,String db){
		return dao.findColComment(tableName,s,db);
	}


	/**
	 * @author 许彩开
	 * @TODO (注：从stamp.properties读取数据库名)
	  * @param
	 * @DATE: 2018\8\14 0014 17:01
	 */

	@Transactional(readOnly = false)
	public String returnDbName(){
		String dbUrl = Global.getConfig("jdbc.url");

		for(int i=0;i<3;i++){//用三个字符‘/’

			dbUrl=dbUrl.substring(dbUrl.indexOf("/")+1);

		}
		return dbUrl.substring(0,dbUrl.indexOf("?"));
	}

	public List<ModifyInfoLog> findModifyInfoLog(ModifyInfoLog mil){
		return dao.findModifyInfoLog(mil);
	}
	
}