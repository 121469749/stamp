package com.thinkgem.jeesite.modules.stamp.dto.stampMake;

import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.TempAgent;

/**
 * Created by Administrator on 2017/6/13.
 */
public class MakeStampBusniessHanlerDTO {

    private Stamp stamp;

    private Company useCompany;

    private StampRecord stampRecord;

    private TempAgent tempAgent;


    public MakeStampBusniessHanlerDTO(Stamp stamp, Company useCompany,StampRecord stampRecord) {
        this.stamp = stamp;
        this.useCompany = useCompany;
        this.stampRecord = stampRecord;
    }

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public Company getUseCompany() {
        return useCompany;
    }

    public void setUseCompany(Company useCompany) {
        this.useCompany = useCompany;
    }

    public StampRecord getStampRecord() {
        return stampRecord;
    }

    public void setStampRecord(StampRecord stampRecord) {
        this.stampRecord = stampRecord;
    }

    public TempAgent getTempAgent() {
        return tempAgent;
    }

    public void setTempAgent(TempAgent tempAgent) {
        this.tempAgent = tempAgent;
    }
}

