package com.thinkgem.jeesite.modules.stamp.vo.useCompany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampAuth;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by sjk on 2017-06-16.
 */
public class StampAuthVo {

    private Stamp stamp;

    private Page<StampAuth> page;

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    @JsonIgnore
    @XmlTransient
    public Page<StampAuth> getPage() {
        if (page == null){
            page = new Page<StampAuth>();
        }
        return page;
    }

    public Page<StampAuth> setPage(Page<StampAuth> page) {
        this.page = page;
        return page;
    }
}
