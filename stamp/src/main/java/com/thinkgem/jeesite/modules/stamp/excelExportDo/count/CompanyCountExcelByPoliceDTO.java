package com.thinkgem.jeesite.modules.stamp.excelExportDo.count;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;

import java.util.Date;

/**
 * @author 许彩开
 * @TODO (注：公安登录，企业信息登录)
 * @DATE: 2018\6\1 0001 15:22
 */

public class CompanyCountExcelByPoliceDTO {

    @ExcelField(title = "企业名称",type = 1, align = 2)
    private String useCompanyName;

    @ExcelField(title = "企业联系电话",type = 1, align = 2)
    private String companyPhone;

    @ExcelField(title = "企业法人",type = 1, align = 2)
    private String legalName;

    @ExcelField(title = "企业法人联系电话",type = 1, align = 2)
    private String legalPhone;

    @ExcelField(title = "统一社会信用代码",type = 1, align = 2)
    private String soleCode;

    @ExcelField(title = "印章备案数量(个)",type = 1, align = 2)
    private String stampCount;


    //物理印章刻制数量(个)
    @ExcelField(title = "总数(物理)",type = 1, align = 2)
    private String physicsCount;        // 总数(物理)

    @ExcelField(title = "启用(物理)",type = 1, align = 2)
    private String physicsUseCount;        // 启用(物理)

    @ExcelField(title = "停用(物理)",type = 1, align = 2)
    private String physicsStopCount;        // 制作日期

    @ExcelField(title = "挂失(物理)",type = 1, align = 2)
    private String physicsReportCount;        // 停用(物理)

    @ExcelField(title = "缴销(物理)",type = 1, align = 2)
    private String physicsLogoutCount;        // 缴销(物理)

    //电子印章刻制数量(个)
    @ExcelField(title = "总数(电子)",type = 1, align = 2)
    private String eleCount;        // 总数(电子)

    @ExcelField(title = "启用(电子)",type = 1, align = 2)
    private String eleUseCount;        // 启用(电子)

    @ExcelField(title = "停用(电子)",type = 1, align = 2)
    private String eleStopCount;        // 停用(电子)

    @ExcelField(title = "挂失(电子)",type = 1, align = 2)
    private String eleReportCount;        // 挂失(电子)

    @ExcelField(title = "缴销(电子)",type = 1, align = 2)
    private String eleLogoutCount;        // 缴销(电子)


    public String getUseCompanyName() {
        return useCompanyName;
    }

    public void setUseCompanyName(String useCompanyName) {
        this.useCompanyName = useCompanyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
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

    public String getSoleCode() {
        return soleCode;
    }

    public void setSoleCode(String soleCode) {
        this.soleCode = soleCode;
    }

    public String getStampCount() {
        return stampCount;
    }

    public void setStampCount(String stampCount) {
        this.stampCount = stampCount;
    }

    public String getPhysicsCount() {
        return physicsCount;
    }

    public void setPhysicsCount(String physicsCount) {
        this.physicsCount = physicsCount;
    }

    public String getPhysicsUseCount() {
        return physicsUseCount;
    }

    public void setPhysicsUseCount(String physicsUseCount) {
        this.physicsUseCount = physicsUseCount;
    }

    public String getPhysicsStopCount() {
        return physicsStopCount;
    }

    public void setPhysicsStopCount(String physicsStopCount) {
        this.physicsStopCount = physicsStopCount;
    }

    public String getPhysicsReportCount() {
        return physicsReportCount;
    }

    public void setPhysicsReportCount(String physicsReportCount) {
        this.physicsReportCount = physicsReportCount;
    }

    public String getPhysicsLogoutCount() {
        return physicsLogoutCount;
    }

    public void setPhysicsLogoutCount(String physicsLogoutCount) {
        this.physicsLogoutCount = physicsLogoutCount;
    }

    public String getEleCount() {
        return eleCount;
    }

    public void setEleCount(String eleCount) {
        this.eleCount = eleCount;
    }

    public String getEleUseCount() {
        return eleUseCount;
    }

    public void setEleUseCount(String eleUseCount) {
        this.eleUseCount = eleUseCount;
    }

    public String getEleStopCount() {
        return eleStopCount;
    }

    public void setEleStopCount(String eleStopCount) {
        this.eleStopCount = eleStopCount;
    }

    public String getEleReportCount() {
        return eleReportCount;
    }

    public void setEleReportCount(String eleReportCount) {
        this.eleReportCount = eleReportCount;
    }

    public String getEleLogoutCount() {
        return eleLogoutCount;
    }

    public void setEleLogoutCount(String eleLogoutCount) {
        this.eleLogoutCount = eleLogoutCount;
    }
}
