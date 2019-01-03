package com.thinkgem.jeesite.modules.stamp.entity.stamp.ES_;

/**
 * @Auther: bb
 * @Date: 2018-08-27
 * @Description: 个人化信息数据结构
 */
public class EPS_PERSONALIZE_INFO {

    public int dwVersion;					    //结构体版本号
    public String szSealSerial;              //印章编号
    public String szSealName;                //印章名称
    public String szStateCode;                //印章状态代码
    public String szUserCompanyCode;         //使用单位编码
    public String szUserCompanyNameChs;     //使用单位名称（汉字）
    public String szUserCompanyNameMin;     //使用单位名称（少数民族文字）
    public String szUserCompanyNameEng;     //使用单位名称（英文）
    public String szApprovalCompanyCode;      //审批单位编码
    public String szApprovalCompanyNameChs; //审计单位名称（汉字）
    public String szProductCompanyCode;      //制作单位编码
    public String szProductCompanyNameChs;  //制作单位名称（汉字）
    public String szProductCompanyNameMin;  //制作单位名称（少数民族文字）
    public String szProductCompanyNameEng;  //制作单位名称（英文）
    public String szSealTypeCode;             //印章类型代码
    public String szMaterialCode;             //章面材料代码
    public String szInkpadDesc;              //印油说明
    public String szOperator;                //经办人
    public String szOperatorID;              //经办人公民身份号码
    public String szApprover;                //审批人
    public String szApprovalDate;             //审批日期
    public String szUndertakeDate;            //承接日期
    public String szProductDate;              //制作日期
    public String szCompressLabel;            //压缩标记
    public String szPersonName;                //法人代表
    public String szPersonIdentity;            //法人代表身份证号
    public byte[] pbPicture;		           //印章原图像数据（byte）
    public String pbPictureUrl;		           //印章原图像数据（String）
    public int dwPictureLen;						//印章原图像数据长度
    public byte[] pbPicCompress;			   //印章图像压缩数据
    public String pbPicCompressUrl;			   //印章图像压缩数据
    public int dwPicCompressLen;               //印章压缩图像数据长度
    public String szSocialCredit;			//社会信用统一代码
    public String szCompanyType;				//单位类型
    public String szSealSpec;				//印章规格
    public String szDepartment;              //部门
    public String szPosition;                //职位
    public String szRecordStatus;             //备案状态
    public String pbExtension1;					//扩展字段1
    public String pbExtension2;					//扩展字段2
    public String pbExtension3;					//扩展字段3
    public String pbExtension4;					//扩展字段4
    public String pbExtension5;					//扩展字段5
    public String szSharp;                   //形状

    public int getDwVersion() {
        return dwVersion;
    }

    public void setDwVersion(int dwVersion) {
        this.dwVersion = dwVersion;
    }

    public String getSzSealSerial() {
        return szSealSerial;
    }

    public void setSzSealSerial(String szSealSerial) {
        this.szSealSerial = szSealSerial;
    }

    public String getSzSealName() {
        return szSealName;
    }

    public void setSzSealName(String szSealName) {
        this.szSealName = szSealName;
    }

    public String getSzStateCode() {
        return szStateCode;
    }

    public void setSzStateCode(String szStateCode) {
        this.szStateCode = szStateCode;
    }

    public String getSzUserCompanyCode() {
        return szUserCompanyCode;
    }

    public void setSzUserCompanyCode(String szUserCompanyCode) {
        this.szUserCompanyCode = szUserCompanyCode;
    }

    public String getSzUserCompanyNameChs() {
        return szUserCompanyNameChs;
    }

    public void setSzUserCompanyNameChs(String szUserCompanyNameChs) {
        this.szUserCompanyNameChs = szUserCompanyNameChs;
    }

    public String getSzUserCompanyNameMin() {
        return szUserCompanyNameMin;
    }

    public void setSzUserCompanyNameMin(String szUserCompanyNameMin) {
        this.szUserCompanyNameMin = szUserCompanyNameMin;
    }

    public String getSzUserCompanyNameEng() {
        return szUserCompanyNameEng;
    }

    public void setSzUserCompanyNameEng(String szUserCompanyNameEng) {
        this.szUserCompanyNameEng = szUserCompanyNameEng;
    }

    public String getSzApprovalCompanyCode() {
        return szApprovalCompanyCode;
    }

    public void setSzApprovalCompanyCode(String szApprovalCompanyCode) {
        this.szApprovalCompanyCode = szApprovalCompanyCode;
    }

    public String getSzApprovalCompanyNameChs() {
        return szApprovalCompanyNameChs;
    }

    public void setSzApprovalCompanyNameChs(String szApprovalCompanyNameChs) {
        this.szApprovalCompanyNameChs = szApprovalCompanyNameChs;
    }

    public String getSzProductCompanyCode() {
        return szProductCompanyCode;
    }

    public void setSzProductCompanyCode(String szProductCompanyCode) {
        this.szProductCompanyCode = szProductCompanyCode;
    }

    public String getSzProductCompanyNameChs() {
        return szProductCompanyNameChs;
    }

    public void setSzProductCompanyNameChs(String szProductCompanyNameChs) {
        this.szProductCompanyNameChs = szProductCompanyNameChs;
    }

    public String getSzProductCompanyNameMin() {
        return szProductCompanyNameMin;
    }

    public void setSzProductCompanyNameMin(String szProductCompanyNameMin) {
        this.szProductCompanyNameMin = szProductCompanyNameMin;
    }

    public String getSzProductCompanyNameEng() {
        return szProductCompanyNameEng;
    }

    public void setSzProductCompanyNameEng(String szProductCompanyNameEng) {
        this.szProductCompanyNameEng = szProductCompanyNameEng;
    }

    public String getSzSealTypeCode() {
        return szSealTypeCode;
    }

    public void setSzSealTypeCode(String szSealTypeCode) {
        this.szSealTypeCode = szSealTypeCode;
    }

    public String getSzMaterialCode() {
        return szMaterialCode;
    }

    public void setSzMaterialCode(String szMaterialCode) {
        this.szMaterialCode = szMaterialCode;
    }

    public String getSzInkpadDesc() {
        return szInkpadDesc;
    }

    public void setSzInkpadDesc(String szInkpadDesc) {
        this.szInkpadDesc = szInkpadDesc;
    }

    public String getSzOperator() {
        return szOperator;
    }

    public void setSzOperator(String szOperator) {
        this.szOperator = szOperator;
    }

    public String getSzOperatorID() {
        return szOperatorID;
    }

    public void setSzOperatorID(String szOperatorID) {
        this.szOperatorID = szOperatorID;
    }

    public String getSzApprover() {
        return szApprover;
    }

    public void setSzApprover(String szApprover) {
        this.szApprover = szApprover;
    }

    public String getSzApprovalDate() {
        return szApprovalDate;
    }

    public void setSzApprovalDate(String szApprovalDate) {
        this.szApprovalDate = szApprovalDate;
    }

    public String getSzUndertakeDate() {
        return szUndertakeDate;
    }

    public void setSzUndertakeDate(String szUndertakeDate) {
        this.szUndertakeDate = szUndertakeDate;
    }

    public String getSzProductDate() {
        return szProductDate;
    }

    public void setSzProductDate(String szProductDate) {
        this.szProductDate = szProductDate;
    }

    public String getSzCompressLabel() {
        return szCompressLabel;
    }

    public void setSzCompressLabel(String szCompressLabel) {
        this.szCompressLabel = szCompressLabel;
    }

    public String getSzPersonName() {
        return szPersonName;
    }

    public void setSzPersonName(String szPersonName) {
        this.szPersonName = szPersonName;
    }

    public String getSzPersonIdentity() {
        return szPersonIdentity;
    }

    public void setSzPersonIdentity(String szPersonIdentity) {
        this.szPersonIdentity = szPersonIdentity;
    }

    public byte[] getPbPicture() {
        return pbPicture;
    }

    public void setPbPicture(byte[] pbPicture) {
        this.pbPicture = pbPicture;
    }

    public String getPbPictureUrl() {
        return pbPictureUrl;
    }

    public void setPbPictureUrl(String pbPictureUrl) {
        this.pbPictureUrl = pbPictureUrl;
    }

    public int getDwPictureLen() {
        return dwPictureLen;
    }

    public void setDwPictureLen(int dwPictureLen) {
        this.dwPictureLen = dwPictureLen;
    }

    public byte[] getPbPicCompress() {
        return pbPicCompress;
    }

    public void setPbPicCompress(byte[] pbPicCompress) {
        this.pbPicCompress = pbPicCompress;
    }

    public String getPbPicCompressUrl() {
        return pbPicCompressUrl;
    }

    public void setPbPicCompressUrl(String pbPicCompressUrl) {
        this.pbPicCompressUrl = pbPicCompressUrl;
    }

    public int getDwPicCompressLen() {
        return dwPicCompressLen;
    }

    public void setDwPicCompressLen(int dwPicCompressLen) {
        this.dwPicCompressLen = dwPicCompressLen;
    }

    public String getSzSocialCredit() {
        return szSocialCredit;
    }

    public void setSzSocialCredit(String szSocialCredit) {
        this.szSocialCredit = szSocialCredit;
    }

    public String getSzCompanyType() {
        return szCompanyType;
    }

    public void setSzCompanyType(String szCompanyType) {
        this.szCompanyType = szCompanyType;
    }

    public String getSzSealSpec() {
        return szSealSpec;
    }

    public void setSzSealSpec(String szSealSpec) {
        this.szSealSpec = szSealSpec;
    }

    public String getSzDepartment() {
        return szDepartment;
    }

    public void setSzDepartment(String szDepartment) {
        this.szDepartment = szDepartment;
    }

    public String getSzPosition() {
        return szPosition;
    }

    public void setSzPosition(String szPosition) {
        this.szPosition = szPosition;
    }

    public String getSzRecordStatus() {
        return szRecordStatus;
    }

    public void setSzRecordStatus(String szRecordStatus) {
        this.szRecordStatus = szRecordStatus;
    }

    public String getPbExtension1() {
        return pbExtension1;
    }

    public void setPbExtension1(String pbExtension1) {
        this.pbExtension1 = pbExtension1;
    }

    public String getPbExtension2() {
        return pbExtension2;
    }

    public void setPbExtension2(String pbExtension2) {
        this.pbExtension2 = pbExtension2;
    }

    public String getPbExtension3() {
        return pbExtension3;
    }

    public void setPbExtension3(String pbExtension3) {
        this.pbExtension3 = pbExtension3;
    }

    public String getPbExtension4() {
        return pbExtension4;
    }

    public void setPbExtension4(String pbExtension4) {
        this.pbExtension4 = pbExtension4;
    }

    public String getPbExtension5() {
        return pbExtension5;
    }

    public void setPbExtension5(String pbExtension5) {
        this.pbExtension5 = pbExtension5;
    }

    public String getSzSharp() {
        return szSharp;
    }

    public void setSzSharp(String szSharp) {
        this.szSharp = szSharp;
    }

    @Override
    public String toString() {
        return "EPS_PERSONALIZE_INFO{" +
                "dwVersion=" + dwVersion +
                ", szSealSerial='" + szSealSerial + '\'' +
                ", szSealName='" + szSealName + '\'' +
                ", szStateCode='" + szStateCode + '\'' +
                ", szUserCompanyCode='" + szUserCompanyCode + '\'' +
                ", szUserCompanyNameChs='" + szUserCompanyNameChs + '\'' +
                ", szUserCompanyNameMin='" + szUserCompanyNameMin + '\'' +
                ", szUserCompanyNameEng='" + szUserCompanyNameEng + '\'' +
                ", szApprovalCompanyCode='" + szApprovalCompanyCode + '\'' +
                ", szApprovalCompanyNameChs='" + szApprovalCompanyNameChs + '\'' +
                ", szProductCompanyCode='" + szProductCompanyCode + '\'' +
                ", szProductCompanyNameChs='" + szProductCompanyNameChs + '\'' +
                ", szProductCompanyNameMin='" + szProductCompanyNameMin + '\'' +
                ", szProductCompanyNameEng='" + szProductCompanyNameEng + '\'' +
                ", szSealTypeCode='" + szSealTypeCode + '\'' +
                ", szMaterialCode='" + szMaterialCode + '\'' +
                ", szInkpadDesc='" + szInkpadDesc + '\'' +
                ", szOperator='" + szOperator + '\'' +
                ", szOperatorID='" + szOperatorID + '\'' +
                ", szApprover='" + szApprover + '\'' +
                ", szApprovalDate='" + szApprovalDate + '\'' +
                ", szUndertakeDate='" + szUndertakeDate + '\'' +
                ", szProductDate='" + szProductDate + '\'' +
                ", szCompressLabel='" + szCompressLabel + '\'' +
                ", szPersonName='" + szPersonName + '\'' +
                ", szPersonIdentity='" + szPersonIdentity + '\'' +
                ", pbPicture='" + pbPicture + '\'' +
                ", dwPictureLen=" + dwPictureLen +
                ", pbPicCompress='" + pbPicCompress + '\'' +
                ", dwPicCompressLen=" + dwPicCompressLen +
                ", szSocialCredit='" + szSocialCredit + '\'' +
                ", szCompanyType='" + szCompanyType + '\'' +
                ", szSealSpec='" + szSealSpec + '\'' +
                ", szDepartment='" + szDepartment + '\'' +
                ", szPosition='" + szPosition + '\'' +
                ", szRecordStatus='" + szRecordStatus + '\'' +
                ", pbExtension1='" + pbExtension1 + '\'' +
                ", pbExtension2='" + pbExtension2 + '\'' +
                ", pbExtension3='" + pbExtension3 + '\'' +
                ", pbExtension4='" + pbExtension4 + '\'' +
                ", pbExtension5='" + pbExtension5 + '\'' +
                ", szSharp='" + szSharp + '\'' +
                '}';
    }
}
