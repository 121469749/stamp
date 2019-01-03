/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.service.countSet;


import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.stamp.dao.CountSet.TCountSetLogDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.TCountSetLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 历史分配记录Service
 * @author howen
 * @version 2017-12-26
 */
@Service
@Transactional(readOnly = true)
public class TCountSetLogService extends CrudService<TCountSetLogDao, TCountSetLog> {

	@Autowired
	private TCountSetLogDao tCountSetLogDao;

	public TCountSetLog get(String id) {
		return super.get(id);
	}

	public List<TCountSetLog> findList(TCountSetLog tCountSetLog) {
		return super.findList(tCountSetLog);
	}

	public Page<TCountSetLog> findPage(Page<TCountSetLog> page, TCountSetLog tCountSetLog) {
		return super.findPage(page, tCountSetLog);
	}

	@Transactional(readOnly = false)
	public void save(TCountSetLog tCountSetLog) {
		super.save(tCountSetLog);
	}

	@Transactional(readOnly = false)
	public void delete(TCountSetLog tCountSetLog) {
		super.delete(tCountSetLog);
	}

    public String getSumEleCount(Company company) {
    	return tCountSetLogDao.getSumEleCount(company);
	}

	public String getSumPhyCount(Company company) {
		return tCountSetLogDao.getSumPhyCount(company);
	}
}