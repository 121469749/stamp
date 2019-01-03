package com.thinkgem.jeesite.modules.stamp.dto.stampMake;

import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;

import java.util.List;

/**
 * Created by hjw-pc on 2017/6/24.
 */
public class LicenseApplyDTO {
    private Licence licence;

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public List<String> getFileType() {
        return fileType;
    }

    public void setFileType(List<String> fileType) {
        this.fileType = fileType;
    }

    private List<String> fileType;
}
