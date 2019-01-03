package com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany;

/**
 * Created by Administrator on 2017/10/6.
 */
public class CountUseCompanyStampDTO {

    private int allCount;//所有的总数

    private StampStateStampCountDTO phyStamps; //物理印章统计DTO

    private StampStateStampCountDTO eleStamps;//电子印章统计DTO

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public StampStateStampCountDTO getPhyStamps() {
        return phyStamps;
    }

    public void setPhyStamps(StampStateStampCountDTO phyStamps) {
        this.phyStamps = phyStamps;
    }

    public StampStateStampCountDTO getEleStamps() {
        return eleStamps;
    }

    public void setEleStamps(StampStateStampCountDTO eleStamps) {
        this.eleStamps = eleStamps;
    }
}
