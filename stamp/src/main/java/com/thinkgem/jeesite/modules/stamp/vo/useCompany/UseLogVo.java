package com.thinkgem.jeesite.modules.stamp.vo.useCompany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampOperation;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by sjk on 2017-06-13.
 */
public class UseLogVo {

    private Stamp stamp;    //印章信息

    private Page<StampOperation> page;  //印章日志分页

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    @JsonIgnore
    @XmlTransient
    public Page<StampOperation> getPage() {
        if (page == null){
            page = new Page<StampOperation>();
        }
        return page;
    }

    public Page<StampOperation> setPage(Page<StampOperation> page) {
        this.page = page;
        return page;
    }
}
