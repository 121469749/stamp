/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.areaattachment.entity;

import com.thinkgem.jeesite.modules.sys.entity.Area;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.List;

/**
 * 区域对应办事附件Entity
 * @author Locker
 * @version 2017-06-16
 */
public class AreaAttachment extends DataEntity<AreaAttachment> {
	
	private static final long serialVersionUID = 1L;
	private Area area;		// 区域
	private String type;		// 业务办理类型

	private String jsonAttachList;


	private String jsonRequiredList;  //必填列表

	public AreaAttachment() {
		super();
	}

	public AreaAttachment(Area area,String type){
		this.area= area;
		this.type=type;
	}

	public AreaAttachment(String id){
		super(id);
	}

	@NotNull(message="区域不能为空")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJsonAttachList() {
		return jsonAttachList;
	}

	public void setJsonAttachList(String jsonAttachList) {
		this.jsonAttachList = jsonAttachList;
	}

	public String getJsonRequiredList() {
		return jsonRequiredList;
	}

	public void setJsonRequiredList(String jsonRequiredList) {
		this.jsonRequiredList = jsonRequiredList;
	}

	@Override
	public String toString() {
		return "AreaAttachment{" +
				"area=" + area +
				", type='" + type + '\'' +
				", jsonAttachList='" + jsonAttachList + '\'' +
				", jsonRequiredList='" + jsonRequiredList + '\'' +
				"} " + super.toString();
	}
}