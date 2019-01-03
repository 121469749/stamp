package com.thinkgem.jeesite.modules.stamp.excelExportDo.count;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.sys.entity.Area;

/**
 * Created by Administrator on 2017/7/29.
 */
public class CompanyCountExcelDo {

    @ExcelField(title = "单位名称",type = 1)
    private String companyName; //公司名

    @ExcelField(title = "公司系统类型",type = 1,value = "companyType.value")
    private CompanyType companyType; // 公司类型

    @ExcelField(title = "公司地址",type = 1)
    private String compAddress; //公司地址

    @ExcelField(title = "公司电话",type = 1)
    private String compPhone; // 公司电话

    @ExcelField(title = "公司所属区域",type = 1,value = "area.name")
    private Area area;  //公司区域

    @ExcelField(title = "公司法人",type = 1)
    private String legalName;        // 法定代表人

    @ExcelField(title = "法人手机",type = 1)
    private String legalPhone;        // 法人手机

    @ExcelField(title = "公司统一码",type = 1)
    private String soleCode;//公司统一码

    @ExcelField(title = "印章备案数量(5种类型结合起来)",type = 1)
    private int stampRecordCount; //印章备案数量(5种类型结合起来)

    @ExcelField(title = "物理印章刻制数量",type = 1)
    private int phyStampCount;//物理印章刻制数量

    @ExcelField(title = "电子印章刻制数量",type = 1)
    private int eleStampCount;// 电子印章刻制数量

    @ExcelField(title = "缴销印章数量",type = 1)
    private int logoutStampCount;//缴销印章数量

    @ExcelField(title = "挂失印章数量",type = 1)
    private int reportStampCount;//挂失印章数量


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    public int getStampRecordCount() {
        return stampRecordCount;
    }

    public void setStampRecordCount(int stampRecordCount) {
        this.stampRecordCount = stampRecordCount;
    }

    public int getPhyStampCount() {
        return phyStampCount;
    }

    public void setPhyStampCount(int phyStampCount) {
        this.phyStampCount = phyStampCount;
    }

    public int getEleStampCount() {
        return eleStampCount;
    }

    public void setEleStampCount(int eleStampCount) {
        this.eleStampCount = eleStampCount;
    }

    public int getLogoutStampCount() {
        return logoutStampCount;
    }

    public void setLogoutStampCount(int logoutStampCount) {
        this.logoutStampCount = logoutStampCount;
    }

    public int getReportStampCount() {
        return reportStampCount;
    }

    public void setReportStampCount(int reportStampCount) {
        this.reportStampCount = reportStampCount;
    }
}
