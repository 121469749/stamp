package com.thinkgem.jeesite.modules.stamp.dto.police;

import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;

import java.util.List;

/**
 * Created by hjw-pc on 2017/6/2.
 * 公安录入刻章点
 * 数据传输对象
 *
 */
public class MakeComDTO {
    private Company company;

    private List<Attachment> attachmentList;

    public Company getCompany() {
        return company;
    }

    private List<String> fileType;

    public List<String> getFileType() {
        return fileType;
    }

    public void setFileType(List<String> fileType) {
        this.fileType = fileType;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
