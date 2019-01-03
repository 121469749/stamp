package com.thinkgem.jeesite.modules.stamp.dto.stampMake;

import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.TempAgent;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * 印章缴销
 * 数据传输对象
 * Created by Locker on 2017/6/5.
 */
public class StampWorkTypeDTO {

    private Stamp stamp;

    private StampRecord stampRecord;

    private List<String> fileType;

    private Company useCompany;

    private List<String> requiredList;

    private TempAgent tempAgent;

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public StampRecord getStampRecord() {
        return stampRecord;
    }

    public void setStampRecord(StampRecord stampRecord) {
        this.stampRecord = stampRecord;
    }

    public List<String> getFileType() {
        return fileType;
    }

    public void setFileType(List<String> fileType) {
        this.fileType = fileType;
    }

    public Company getUseCompany() {
        return useCompany;
    }

    public void setUseCompany(Company useCompany) {
        this.useCompany = useCompany;
    }

    public List<String> getRequiredList() {
        return requiredList;
    }

    public void setRequiredList(List<String> requiredList) {
        this.requiredList = requiredList;
    }

    public TempAgent getTempAgent() {
        return tempAgent;
    }

    public void setTempAgent(TempAgent tempAgent) {
        this.tempAgent = tempAgent;
    }
}
