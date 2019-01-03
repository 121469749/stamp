package com.thinkgem.jeesite.modules.stamp.dto.count.dealer;

import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.StampStateStampCountDTO;

/**
 * Created by Administrator on 2017/10/7.
 */
public class StampTypeCountDTO {

    private int count ;//总数

    private String stampType;//印章类型

    private StampStateStampCountDTO phyStampCount; //物理

    private StampStateStampCountDTO eleStampCount;//电子

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStampType() {
        return stampType;
    }

    public void setStampType(String stampType) {
        this.stampType = stampType;
    }

    public StampStateStampCountDTO getPhyStampCount() {
        return phyStampCount;
    }

    public void setPhyStampCount(StampStateStampCountDTO phyStampCount) {
        this.phyStampCount = phyStampCount;
    }

    public StampStateStampCountDTO getEleStampCount() {
        return eleStampCount;
    }

    public void setEleStampCount(StampStateStampCountDTO eleStampCount) {
        this.eleStampCount = eleStampCount;
    }
}
