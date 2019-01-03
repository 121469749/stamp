package com.thinkgem.jeesite.modules.stamp.entity.exchange;

import java.io.Serializable;

/**
 * @Auther: linzhibao
 * @Date: 2018-09-03 14:41
 * @Description:
 */
public class SealEntity implements Serializable {

    private Integer sealType;        // 印章类型
    private Integer sealStyle;        // 印章属性（物理/电子）
    private String sealMaterial;        // 印章材料
    private Integer sealCount;        // 刻制数量


    public Integer getSealType() {
        return sealType;
    }

    public void setSealType(Integer sealType) {
        this.sealType = sealType;
    }

    public Integer getSealStyle() {
        return sealStyle;
    }

    public void setSealStyle(Integer sealStyle) {
        this.sealStyle = sealStyle;
    }

    public String getSealMaterial() {
        return sealMaterial;
    }

    public void setSealMaterial(String sealMaterial) {
        this.sealMaterial = sealMaterial;
    }

    public Integer getSealCount() {
        return sealCount;
    }

    public void setSealCount(Integer sealCount) {
        this.sealCount = sealCount;
    }
}
