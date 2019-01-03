package com.thinkgem.jeesite.modules.stamp.dto.count;

/**
 * Created by Administrator on 2018\12\14 0014.
 */
public class PoliceCountDTO {
    private String areaId;
    private String makeComId;
    private String StampCount;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getMakeComId() {
        return makeComId;
    }

    public void setMakeComId(String makeComId) {
        this.makeComId = makeComId;
    }

    public String getStampCount() {
        return StampCount;
    }

    public void setStampCount(String stampCount) {
        StampCount = stampCount;
    }
}
