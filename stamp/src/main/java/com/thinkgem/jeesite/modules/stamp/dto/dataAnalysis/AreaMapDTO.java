package com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * Created by Administrator on 2018\11\22 0022.
 */
public class AreaMapDTO {
    private String code;
    private String areaName;
    private String value;

    public AreaMapDTO(String code,String areaName,String value){
        this.code = code;
        this.areaName = areaName;
        this.value = value;
    }
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
