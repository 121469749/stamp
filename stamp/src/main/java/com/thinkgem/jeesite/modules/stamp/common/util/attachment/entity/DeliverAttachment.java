package com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity;

import com.thinkgem.jeesite.common.utils.IdGen;

import java.io.Serializable;

/**
 * Created by bb on 2018/3/22.
 */
public class DeliverAttachment implements Serializable{

    private Integer id;

    private String stampId;

    private String recordId;

    private String fileName;

    private Integer xFlag;

    public DeliverAttachment(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getxFlag() {
        return xFlag;
    }

    public void setxFlag(Integer xFlag) {
        this.xFlag = xFlag;
    }

    @Override
    public String toString() {
        return "DeliverAttachment{" +
                "id=" + id +
                ", stampId='" + stampId + '\'' +
                ", recordId='" + recordId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", xFlag=" + xFlag +
                '}';
    }
}

