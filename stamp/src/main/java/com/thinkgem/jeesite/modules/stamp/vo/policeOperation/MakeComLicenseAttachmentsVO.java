package com.thinkgem.jeesite.modules.stamp.vo.policeOperation;

import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;

import java.util.List;

/**
 * Created by hjw-pc on 2017/6/2.
 */
public class MakeComLicenseAttachmentsVO {
    private Company company;
    private Licence licence;
    private List<Attachment> attachmentList;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
