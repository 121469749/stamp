package com.thinkgem.jeesite.modules.sign.entity;

/**
 * Created by bb on 2017/9/1.
 */
public class SinatureCerDTO {

	private String undoneRealPath; // 未签章的路径
	private String doneRealPath; // 已签章的路径
	private String cerPath; //证书路径
	private String cerPassword; //证书密码
	private String sealPath; //印模路径
	private int pageSize;//页码
    private String sign_domain;//签名域
    private float llx;//章左下角x
    private float lly;//章左下角y
    private float urx;//章右上角x
    private float ury;//章右上角y


	public String getUndoneRealPath() {
		return undoneRealPath;
	}

	public void setUndoneRealPath(String undoneRealPath) {
		this.undoneRealPath = undoneRealPath;
	}

	public String getDoneRealPath() {
		return doneRealPath;
	}

	public void setDoneRealPath(String doneRealPath) {
		this.doneRealPath = doneRealPath;
	}

	public String getCerPath() {
		return cerPath;
	}

	public void setCerPath(String cerPath) {
		this.cerPath = cerPath;
	}

	public String getCerPassword() {
		return cerPassword;
	}

	public void setCerPassword(String cerPassword) {
		this.cerPassword = cerPassword;
	}

	public String getSealPath() {
		return sealPath;
	}

	public void setSealPath(String sealPath) {
		this.sealPath = sealPath;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSign_domain() {
		return sign_domain;
	}
	public void setSign_domain(String sign_domain) {
		this.sign_domain = sign_domain;
	}
	public float getLlx() {
		return llx;
	}
	public void setLlx(float llx) {
		this.llx = llx;
	}
	public float getLly() {
		return lly;
	}
	public void setLly(float lly) {
		this.lly = lly;
	}
	public float getUrx() {
		return urx;
	}
	public void setUrx(float urx) {
		this.urx = urx;
	}
	public float getUry() {
		return ury;
	}
	public void setUry(float ury) {
		this.ury = ury;
	}


}
