/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.entity.police;

import com.thinkgem.jeesite.modules.sys.entity.Area;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 公安机关Entity
 * @author Locker
 * @version 2017-05-13
 */
public class Police extends DataEntity<Police> {
	
	private static final long serialVersionUID = 1L;
	private String policeCode; // 单位编码
	private String name;
	private Area area;		// 所属区域
	private String principal;		// 负责人
	private String address;		// 单位地址
	private String phone;		// 电话
	private String postCode;		// 邮政编码
	
	public Police() {
		super();
	}

	public Police(String id){
		super(id);
	}

	public String getPoliceCode() {
		return policeCode;
	}

	public void setPoliceCode(String policeCode) {
		this.policeCode = policeCode;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}