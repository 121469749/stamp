/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.areaattachment.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.areaattachment.dto.AreaAttachmentDTO;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentExistException;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.StampMakeException;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.areaattachment.entity.AreaAttachment;
import com.thinkgem.jeesite.modules.areaattachment.dao.AreaAttachmentDao;

/**
 * 区域对应办事附件Service
 * @author Locker
 * @version 2017-06-16
 */
@Service
public class AreaAttachmentService  {

	@Autowired
	private AreaAttachmentDao areaAttachmentDao;

	@Autowired
	private AreaDao areaDao;

	@Autowired
	private DictDao dictDao;


	/**
	 *
	 * 区域附件列表分页查询服务
	 *
	 * @param page
	 * @param areaAttachment
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<AreaAttachment> findPage(Page<AreaAttachment> page,AreaAttachment areaAttachment){

		areaAttachment.setPage(page);

		List<AreaAttachment> list = areaAttachmentDao.findList(areaAttachment);

		page.setList(list);

		return page;
	}

	/**
	 * 保存单挑区域附件信息
	 *
	 * @param areaAttachmentDTO
	 * @return
	 */
	@Transactional(readOnly = false)
	public Condition saveAreaAttachment(AreaAttachmentDTO areaAttachmentDTO){

		try {

			AreaAttachment areaAttachment = areaAttachmentDTO.getAreaAttachment();

			if(StringUtils.isNotBlank(areaAttachment.getId())){

				List<String> attachList = areaAttachmentDTO.getAttachList();

				areaAttachment.setJsonAttachList(JsonMapper.toJsonString(attachList));

				List<String> requiredList = areaAttachmentDTO.getRequiredList();

				areaAttachment.setJsonRequiredList(JsonMapper.toJsonString(requiredList));

				areaAttachment.preUpdate();

				areaAttachmentDao.update(areaAttachment);

			}else{

				//检查该区域附件是否已经存在
				if(areaAttachmentDao.checkAreaAttachmentExist(areaAttachment)==1){

					throw new AreaAttachmentExistException("AreaAttachment already Exist!");

				}

				List<String> attachList = areaAttachmentDTO.getAttachList();

				areaAttachment.setJsonAttachList(JsonMapper.toJsonString(attachList));

				List<String> requiredList = areaAttachmentDTO.getRequiredList();


				areaAttachment.setJsonRequiredList(JsonMapper.toJsonString(requiredList));

				areaAttachment.preInsert();

				areaAttachmentDao.insert(areaAttachment);

			}
			return new Condition(Condition.SUCCESS_CODE,"Success");

		}catch (AreaAttachmentExistException e){

			throw e;

		}catch (AreaAttachmentException e){

			throw e;
		}catch (Exception e){

			e.printStackTrace();
			throw new AreaAttachmentException("System error :" + e.getMessage());
		}
	}

	/**
	 * 获得单个的区域附件信息
	 * @param areaAttachmentId
	 * @return
	 */
	@Transactional(readOnly = true)
	public AreaAttachmentDTO getAreaAttachmentById(String areaAttachmentId){

		AreaAttachment areaAttachment = areaAttachmentDao.get(new AreaAttachment((areaAttachmentId)));

		List<String> attachList = (List<String>) JsonMapper.fromJsonString(areaAttachment.getJsonAttachList(),List.class);

		List<String> requiredList = (List<String>) JsonMapper.fromJsonString(areaAttachment.getJsonRequiredList(),List.class);

		AreaAttachmentDTO areaAttachmentDTO =new AreaAttachmentDTO(areaAttachment,attachList,requiredList);

		return areaAttachmentDTO;
	}

	/**
	 * 删除单挑区域附件信息
	 * @param areaAttachmentId
	 */
	@Transactional(readOnly = false)
	public void deleteAreaAttachment(String areaAttachmentId){

		areaAttachmentDao.delete(areaAttachmentId);

	}

	/**
	 * 检查当前设定区域是否存在
	 * @param areaId
	 * @return
	 */
	@Transactional(readOnly = true)
	public boolean checkAreaIfExist(String areaId){

		if(areaDao.checkAreaExist(areaId)==0){
			return false;
		}
		return true;
	}

	@Transactional(readOnly = true)
	public boolean checkDictValueExist(Dict dict){

		if(dictDao.checkValueExist(dict)==0){

			return false;
		}

		return true;
	}

	/**
	 * @author 练浩文
	 * @TODO (注：将list的内容修改为0或1)
	 * @param listShow
	 * @param listRequired
	 * @DATE: 2018/2/28 11:26
	 */
	private List<String> listChangeTo01(List<String> listShow,List<String> listRequired){

		List<String> requiredList = new ArrayList<String>();
		for(int i=0;i<listShow.size();i++) {
			if (listRequired.contains(listShow.get(i))) {
				requiredList.add("1");
			}else {
				requiredList.add("0");
			}
		}
		return requiredList;
	}

}