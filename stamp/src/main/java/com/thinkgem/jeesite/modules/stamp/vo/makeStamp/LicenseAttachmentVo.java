package com.thinkgem.jeesite.modules.stamp.vo.makeStamp;

import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;

import java.util.List;

/**
 * Created by hjw-pc on 2017/6/25.
 */
public class LicenseAttachmentVo {
    private Licence licence;
    private List<Attachment> attachmentList;

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
