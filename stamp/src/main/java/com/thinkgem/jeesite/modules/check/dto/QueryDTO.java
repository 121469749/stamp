package com.thinkgem.jeesite.modules.check.dto;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;

/**
 *
 * 检验平台查询数据操作对象
 *
 * Created by Administrator on 2017/11/18.
 */
public class QueryDTO {

    private String companyName="";//用章公司名

    private String stampName="";//印章名称

    private String stampCode="";//印章编码

    private String stampShape="";//印章形式

    private Page<Stamp> page;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStampName() {
        return stampName;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    public String getStampCode() {
        return stampCode;
    }

    public void setStampCode(String stampCode) {
        this.stampCode = stampCode;
    }

    public String getStampShape() {
        return stampShape;
    }

    public void setStampShape(String stampShape) {
        this.stampShape = stampShape;
    }

    public Page<Stamp> getPage() {
        return page;
    }

    public void setPage(Page<Stamp> page) {
        this.page = page;
    }
}

