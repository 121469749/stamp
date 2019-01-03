package com.thinkgem.jeesite.modules.stamp.dto.countSet;

import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;

/**
 *
 * 印章数量生产控制DTO
 *
 * Created by Locker on 2017/8/17.
 */
public class CountSetDTO {

    private String companyId;

    private CountSet eleCountSet;

    private CountSet phyCountSet;

    public CountSetDTO(){

    }

    public CountSetDTO(CountSet eleCountSet, CountSet phyCountSet) {
        this.eleCountSet = eleCountSet;
        this.phyCountSet = phyCountSet;
    }

    public CountSet getEleCountSet() {
        return eleCountSet;
    }

    public void setEleCountSet(CountSet eleCountSet) {
        this.eleCountSet = eleCountSet;
    }

    public CountSet getPhyCountSet() {
        return phyCountSet;
    }

    public void setPhyCountSet(CountSet phyCountSet) {
        this.phyCountSet = phyCountSet;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
