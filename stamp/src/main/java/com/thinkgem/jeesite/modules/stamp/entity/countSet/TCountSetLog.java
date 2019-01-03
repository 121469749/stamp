/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.entity.countSet;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 历史分配记录Entity
 * @author howen
 * @version 2017-12-26
 */
public class TCountSetLog extends DataEntity<TCountSetLog> {

	private static final long serialVersionUID = 1L;
	private String companyName;		// 单位名称
	private String companyId;		// 单位id
	private String eleCount;		// 电子印章数
	private String eleSumcount;		// 历史电子印章数
	private String phyCount;		// 物理印章数
	private String phySumcount;		// 历史物理印章数
	private Date beginUpdateDate;		// 开始 更新时间
	private Date endUpdateDate;		// 结束 更新时间

	public TCountSetLog() {
		super();
	}

	public TCountSetLog(String id){
		super(id);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getEleCount() {
		return eleCount;
	}

	public void setEleCount(String eleCount) {
		this.eleCount = eleCount;
	}

	public String getEleSumcount() {
		return eleSumcount;
	}

	public void setEleSumcount(String eleSumcount) {
		this.eleSumcount = eleSumcount;
	}

	public String getPhyCount() {
		return phyCount;
	}

	public void setPhyCount(String phyCount) {
		this.phyCount = phyCount;
	}

	public String getPhySumcount() {
		return phySumcount;
	}

	public void setPhySumcount(String phySumcount) {
		this.phySumcount = phySumcount;
	}

	public Date getBeginUpdateDate() {
		return beginUpdateDate;
	}

	public void setBeginUpdateDate(Date beginUpdateDate) {
		this.beginUpdateDate = beginUpdateDate;
	}

	public Date getEndUpdateDate() {
		return endUpdateDate;
	}

	public void setEndUpdateDate(Date endUpdateDate) {
		this.endUpdateDate = endUpdateDate;
	}

}