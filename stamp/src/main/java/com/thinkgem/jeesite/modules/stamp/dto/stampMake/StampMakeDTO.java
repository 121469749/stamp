package com.thinkgem.jeesite.modules.stamp.dto.stampMake;

import com.drew.lang.annotations.NotNull;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * 刻章点刻制印章
 * 数据传输对象
 *
 * Created by Locker on 2017/5/31.
 */
public class StampMakeDTO {

    private StampRecord stampRecord;

    private Company useComp;

    private List<Stamp> stamps;

    private List<String> fileType;

    private List<String> requiredList;

    private List<String> fileList;

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

    public List<String> getFileType() {
        return fileType;
    }

    public void setFileType(List<String> fileType) {
        this.fileType = fileType;
    }

    public Company getUseComp() {
        return useComp;
    }

    public void setUseComp(Company useCompany) {
        this.useComp = useCompany;
    }

    public List<String> getRequiredList() {
        return requiredList;
    }

    public void setRequiredList(List<String> requiredList) {
        this.requiredList = requiredList;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }
}
