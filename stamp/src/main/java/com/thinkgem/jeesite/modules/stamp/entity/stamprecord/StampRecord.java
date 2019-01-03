/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.entity.stamprecord;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampWorkType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation.Enumeration;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;

import java.util.Date;
import java.util.List;

/**
 * 印章备案信息Entity
 *
 * @author Locker
 * @version 2017-05-13
 */
public class StampRecord extends DataEntity<StampRecord> implements Cloneable {

    private static final long serialVersionUID = 1L;

    private String sblsh;            //多证合一业务申办流水号
    private String serialNum;        // 业务流水号

    private Company useComp;        // 用章公司
    private Company makeComp;        // 刻章公司

    private Stamp stamp; //印章信息

    @Enumeration(enumClass = StampWorkType.class)
    private StampWorkType workType;        // 办事类型

    private String workRemakrs;        // 办事理由

    private String legalName;        // 法定代表人
    private String legalPhone;        // 法人手机
    private String legalCertType;        // 法人证件类型
    private String legalCertCode;        // 法人证件号码

    private String soleCode;        // 单位唯一编码
    private String isSoleCode;      //是否为统一社会信用代码（1：是；2：不是（其他码））
    private String companyName;        // 单位名称
    private String type1;        // 单位一级类别
    private String compAddress;        // 单位地址
    private String compPhone;        // 单位电话


    private String agentName;        // 经办人姓名
    private String agentCertType;        // 经办人证件类型
    private String agentCertCode;        // 经办人证件号码
    private String agentPhone;        // 经办人电话号码

    private String applyInfos;        // 申请印章信息列表

    private String attachs;        // 附件信息列表
    private String permissionPhoto; // 许可证明文件

    private String getStampName;        // 取章人
    private String getStampIdCode;        // 取章人身份证

    private String finishFile;        // 已盖章材料(文件虚拟地址)
    private String deliveryPhoto;        // 交付现场照片(文件虚拟地址)

    private Date finishDate;        // 刻制完成时间
    private Date deliveryDate;        // 交付时间

    private List<Stamp> stamps;

    private List<String> record_id;

    public StampRecord() {
        super();
    }

    //确保每个印章的备案都有唯一的StampRecord,id
    public StampRecord(String id, StampRecord stampRecord) {

        super(id);

        this.delFlag = stampRecord.getDelFlag();

        this.createBy = stampRecord.getCreateBy();

        this.createDate = stampRecord.getCreateDate();

        this.updateDate = stampRecord.getUpdateDate();

        this.updateBy = stampRecord.getUpdateBy();

        this.stamps = stampRecord.getStamps();

        this.serialNum = stampRecord.getSerialNum();

        this.useComp = stampRecord.getUseComp();
        this.makeComp = stampRecord.getMakeComp();

        this.stamp = stampRecord.getStamp();

        this.workType = stampRecord.getWorkType();
        this.workRemakrs = stampRecord.getWorkRemakrs();

        this.legalName = stampRecord.getLegalName();
        this.legalPhone = stampRecord.getLegalPhone();
        this.legalCertType = stampRecord.getLegalCertType();
        this.legalCertCode = stampRecord.getLegalCertCode();

        this.soleCode = stampRecord.getSoleCode();
        this.companyName = stampRecord.getCompanyName();
        this.type1 = stampRecord.getType1();
        this.compAddress = stampRecord.getCompAddress();
        this.compPhone = stampRecord.getCompPhone();


        this.agentName = stampRecord.getAgentName();
        this.agentCertType = stampRecord.getAgentCertType();
        this.agentCertCode = stampRecord.getAgentCertCode();
        this.agentPhone = stampRecord.getAgentPhone();

        this.applyInfos = stampRecord.getApplyInfos();

        this.attachs = stampRecord.getAttachs();
        this.permissionPhoto = stampRecord.getPermissionPhoto();

        this.getStampName = stampRecord.getGetStampName();
        this.getStampIdCode = stampRecord.getGetStampIdCode();

        this.finishFile = stampRecord.getFinishFile();
        this.deliveryPhoto = stampRecord.getDeliveryPhoto();

        this.finishDate = stampRecord.getFinishDate();
        this.deliveryDate = stampRecord.getDeliveryDate();
    }

    public StampRecord(String serialNum, String agentName, String agentCertType, String agentCertCode, String agentPhone, StampWorkType stampWorkType, String workRemakrs) {

        this.serialNum = serialNum;

        this.agentName = agentName;

        this.agentCertType = agentCertType;

        this.agentCertCode = agentCertCode;

        this.agentPhone = agentPhone;

        this.workType = stampWorkType;

        this.workRemakrs = workRemakrs;
    }

    public StampRecord(String id) {
        super(id);
    }

    public StampRecord(String id, StampWorkType workType) {
        super(id);
        this.workType = workType;
    }

    public StampRecord(String serialNum, StampWorkType workType,String recordNote) {
        this.serialNum = serialNum;
        this.workType = workType;
    }

    public String getSblsh() {
        return sblsh;
    }

    public void setSblsh(String sblsh) {
        this.sblsh = sblsh;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Company getUseComp() {
        return useComp;
    }

    public void setUseComp(Company useComp) {
        this.useComp = useComp;
    }

    public Company getMakeComp() {
        return makeComp;
    }

    public void setMakeComp(Company makeComp) {
        this.makeComp = makeComp;
    }

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public StampWorkType getWorkType() {
        return workType;
    }

    public void setWorkType(StampWorkType workType) {
        this.workType = workType;
    }

    public String getWorkRemakrs() {
        return workRemakrs;
    }

    public void setWorkRemakrs(String workRemakrs) {
        this.workRemakrs = workRemakrs;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentCertType() {
        return agentCertType;
    }

    public void setAgentCertType(String agentCertType) {
        this.agentCertType = agentCertType;
    }

    public String getAgentCertCode() {
        return agentCertCode;
    }

    public void setAgentCertCode(String agentCertCode) {
        this.agentCertCode = agentCertCode;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public String getApplyInfos() {
        return applyInfos;
    }

    public void setApplyInfos(String applyInfos) {
        this.applyInfos = applyInfos;
    }

    public String getAttachs() {
        return attachs;
    }

    public void setAttachs(String attachs) {
        this.attachs = attachs;
    }

    public String getGetStampName() {
        return getStampName;
    }

    public void setGetStampName(String getStampName) {
        this.getStampName = getStampName;
    }

    public String getGetStampIdCode() {
        return getStampIdCode;
    }

    public void setGetStampIdCode(String getStampIdCode) {
        this.getStampIdCode = getStampIdCode;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getFinishFile() {
        return finishFile;
    }

    public void setFinishFile(String finishFile) {
        this.finishFile = finishFile;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPhoto() {
        return deliveryPhoto;
    }

    public void setDeliveryPhoto(String deliveryPhoto) {
        this.deliveryPhoto = deliveryPhoto;
    }

    public List<Stamp> getStamps() {
        return stamps;
    }

    public void setStamps(List<Stamp> stamps) {
        this.stamps = stamps;
    }

    public String getPermissionPhoto() {
        return permissionPhoto;
    }

    public void setPermissionPhoto(String permissionPhoto) {
        this.permissionPhoto = permissionPhoto;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(String legalPhone) {
        this.legalPhone = legalPhone;
    }

    public String getLegalCertType() {
        return legalCertType;
    }

    public void setLegalCertType(String legalCertType) {
        this.legalCertType = legalCertType;
    }

    public String getLegalCertCode() {
        return legalCertCode;
    }

    public void setLegalCertCode(String legalCertCode) {
        this.legalCertCode = legalCertCode;
    }

    public String getSoleCode() {
        return soleCode;
    }

    public void setSoleCode(String soleCode) {
        this.soleCode = soleCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getCompAddress() {
        return compAddress;
    }

    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }

    public String getCompPhone() {
        return compPhone;
    }

    public void setCompPhone(String compPhone) {
        this.compPhone = compPhone;
    }

    public String getIsSoleCode() { return isSoleCode; }
    public void setIsSoleCode(String isSoleCode) {
        this.isSoleCode = isSoleCode;
    }
    public void setRecord_id(List<String> record_id) {
        this.record_id = record_id;
    }

    public List<String> getRecord_id() {
        return record_id;
    }

    //json 格式化 id 才会用到
    public void setUUID() {

        this.setId(IdGen.uuid());

        this.setIsNewRecord(true);
    }

    public Object clone() {
        StampRecord o = null;
        try {
            o = (StampRecord) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }

        return o;
    }
}