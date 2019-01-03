package com.thinkgem.jeesite.modules.stamp.vo.makeStamp;

import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;

import java.util.List;

/**
 *
 * 印章制作回执
 *
 * Created by Locker on 2017/6/2.
 */
public class ReceiptVo {

    private StampRecord stampRecord;

    private List<Stamp> stamps;

    private Company useCompany;

    private Company makeCompany;


    public ReceiptVo() {
    }

    public ReceiptVo(StampRecord stampRecord, List<Stamp> stamps, Company useCompany, Company makeCompany) {
        this.stampRecord = stampRecord;
        this.stamps = stamps;
        this.useCompany = useCompany;
        this.makeCompany = makeCompany;
    }

    public StampRecord getStampRecord() {
        return stampRecord;
    }

    public void setStampRecord(StampRecord stampRecord) {
        this.stampRecord = stampRecord;
    }

    public List<Stamp> getStamps() {
        return stamps;
    }

    public void setStamps(List<Stamp> stamps) {
        this.stamps = stamps;
    }

    public Company getUseCompany() {
        return useCompany;
    }

    public void setUseCompany(Company useCompany) {
        this.useCompany = useCompany;
    }

    public Company getMakeCompany() {
        return makeCompany;
    }

    public void setMakeCompany(Company makeCompany) {
        this.makeCompany = makeCompany;
    }

}
