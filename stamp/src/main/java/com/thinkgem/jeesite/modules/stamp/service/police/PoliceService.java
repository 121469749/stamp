/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.service.police;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.dao.police.PoliceDao;

/**
 * 公安机关Service
 * @author Locker
 * @version 2017-05-13
 */
@Service
@Transactional(readOnly = true)
public class PoliceService extends CrudService<PoliceDao, Police> {

	/**
	 *
	 * @param id
	 * @return
	 * 获取公安用户
	 */
	public Police get(String id) {
		return super.get(id);
	}

	/**
	 *
	 * @param police
	 * @return
	 * 获取公安用户列表
	 */
	public List<Police> findList(Police police) {
		return super.findList(police);
	}

	/**
	 *
	 * @param page 分页对象
	 * @param police
	 * @return
	 * 获取公安用户列表,分页
	 */
	public Page<Police> findPage(Page<Police> page, Police police) {
		return super.findPage(page, police);
	}


	/**
	 *
	 * @param police
	 * 新增或修改公安用户
	 */
	@Transactional(readOnly = false)
	public void save(Police police) {
		super.save(police);
	}


	/**
	 *
	 * @param police
	 * 删除公安用户
	 */
	@Transactional(readOnly = false)
	public void delete(Police police) {
		super.delete(police);
	}
	
}