package com.thinkgem.jeesite.modules.stamp.entity.stamp;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;

/**
 * 印模信息Entity
 * @author Locker
 * @version 2017-05-13
 */
public class Moulage extends DataEntity<Moulage>{

    private String stampType; //印章类型
    private String moulageName;  //印模名称
    private Company useCompany; //用章公司
    private Company makeCompany;// 制章公司


    private String stampText;		// 印章文本
    private String acrossText;		// 横排文字
    private String subText;		// 下标文字
    private String securityCode;		// 防伪码
    private String insideImage;		// 内框图
    private String ringUp;		// 环形文本上下偏移
    private String ringSize;		// 环形字号大小
    private String ringWidth;		// 环形文本宽度
    private String ringAngle;		// 环形文本角度
    private String acrossOffset;		// 横排文本偏移
    private String acrossSize;		// 横排文本大小
    private String acrossWidth;		// 横排文本宽度
    private String codeSize;		// 防伪码字体大小
    private String codeWidth;		// 防伪码字体宽度
    private String codeUp;		// 防伪码上下偏移
    private String codeAngle;		// 防伪码角度


    public Moulage(){
        super();
    }

    public Moulage(String id){
        super(id);
    }

    public String getStampText() {
        return stampText;
    }

    public void setStampText(String stampText) {
        this.stampText = stampText;
    }

    public String getAcrossText() {
        return acrossText;
    }

    public void setAcrossText(String acrossText) {
        this.acrossText = acrossText;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getInsideImage() {
        return insideImage;
    }

    public void setInsideImage(String insideImage) {
        this.insideImage = insideImage;
    }

    public String getRingUp() {
        return ringUp;
    }

    public void setRingUp(String ringUp) {
        this.ringUp = ringUp;
    }

    public String getRingSize() {
        return ringSize;
    }

    public void setRingSize(String ringSize) {
        this.ringSize = ringSize;
    }

    public String getRingWidth() {
        return ringWidth;
    }

    public void setRingWidth(String ringWidth) {
        this.ringWidth = ringWidth;
    }

    public String getRingAngle() {
        return ringAngle;
    }

    public void setRingAngle(String ringAngle) {
        this.ringAngle = ringAngle;
    }

    public String getAcrossOffset() {
        return acrossOffset;
    }

    public void setAcrossOffset(String acrossOffset) {
        this.acrossOffset = acrossOffset;
    }

    public String getAcrossSize() {
        return acrossSize;
    }

    public void setAcrossSize(String acrossSize) {
        this.acrossSize = acrossSize;
    }

    public String getAcrossWidth() {
        return acrossWidth;
    }

    public void setAcrossWidth(String acrossWidth) {
        this.acrossWidth = acrossWidth;
    }

    public String getCodeSize() {
        return codeSize;
    }

    public void setCodeSize(String codeSize) {
        this.codeSize = codeSize;
    }

    public String getCodeWidth() {
        return codeWidth;
    }

    public void setCodeWidth(String codeWidth) {
        this.codeWidth = codeWidth;
    }

    public String getCodeUp() {
        return codeUp;
    }

    public void setCodeUp(String codeUp) {
        this.codeUp = codeUp;
    }

    public String getCodeAngle() {
        return codeAngle;
    }

    public void setCodeAngle(String codeAngle) {
        this.codeAngle = codeAngle;
    }

    public String getStampType() {
        return stampType;
    }

    public void setStampType(String stampType) {
        this.stampType = stampType;
    }

    public String getMoulageName() {
        return moulageName;
    }

    public void setMoulageName(String moulageName) {
        this.moulageName = moulageName;
    }

    public Company getUseCompany() {
        return useCompany;
    }

    public void setUseCompany(Company useCompany) {
        this.useCompany = useCompany;
    }

    public Company getMakeCompany() {
        return makeCompany;
    }

    public void setMakeCompany(Company makeCompany) {
        this.makeCompany = makeCompany;
    }
}
