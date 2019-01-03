package com.thinkgem.jeesite.modules.stamp.dto.stampMake;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampWorkType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;

import java.util.List;

/**
 *
 * 修改附件DTO
 *
 * Created by Locker on 2017/10/3.
 */
public class EditAttachmentDTO {

    private String recordId; //备案id

    private StampWorkType stampWorkType;//备案类型

    private List<String> deleteIds; //要删除的附件id列表

    private List<String> fileType;//要上传的附件类型

    private Stamp stamp;//在修改附件的时候用到

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public StampWorkType getStampWorkType() {
        return stampWorkType;
    }

    public void setStampWorkType(StampWorkType stampWorkType) {
        this.stampWorkType = stampWorkType;
    }

    public List<String> getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(List<String> deleteIds) {
        this.deleteIds = deleteIds;
    }

    public List<String> getFileType() {
        return fileType;
    }

    public void setFileType(List<String> fileType) {
        this.fileType = fileType;
    }

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }
}
