package com.thinkgem.jeesite.modules.areaattachment.dto;

import com.thinkgem.jeesite.modules.areaattachment.entity.AreaAttachment;

import java.util.List;

/**
 *
 *
 *
 * Created by locker on 2017/6/16.
 */
public class AreaAttachmentDTO {

    private AreaAttachment areaAttachment;

    private List<String> attachList;


    private List<String> requiredList;

    public AreaAttachmentDTO(){

    }

    public AreaAttachmentDTO(AreaAttachment areaAttachment, List<String> attachList,List<String> requiredList) {
        this.areaAttachment = areaAttachment;
        this.attachList = attachList;
        this.requiredList = requiredList;
    }

    public AreaAttachment getAreaAttachment() {
        return areaAttachment;
    }

    public void setAreaAttachment(AreaAttachment areaAttachment) {
        this.areaAttachment = areaAttachment;
    }

    public List<String> getAttachList() {

        return attachList;
    }

    public void setAttachList(List<String> attachList) {
        this.attachList = attachList;
    }

    public List<String> getRequiredList() {
        return requiredList;
    }

    public void setRequiredList(List<String> requiredList) {
        this.requiredList = requiredList;
    }

    @Override
    public String toString() {
        return "AreaAttachmentDTO{" +
                "areaAttachment=" + areaAttachment +
                ", attachList=" + attachList +
                ", requiredList=" + requiredList +
                '}';
    }
}
