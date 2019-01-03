package com.thinkgem.jeesite.modules.stamp.excelExportDo.money;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.sys.entity.Area;

/**
 *
 * 刻章点收费统计 excel导出对象
 *
 * Created by Locker on 2017/7/29.
 */
public class CompanyMoneyExcelDo {

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

    @ExcelField(title = "物理印章的数目(个)",type = 1)
    private int phyStampCount; // 在当前刻章点上的刻制物理印章的数目

    @ExcelField(title = "物理印章收费(元)",type = 1)
    private double phyStampCountMoney; // 物理印章收费数目

    @ExcelField(title = "电子印章的数目(个)",type = 1)
    private int eleStampCount; // 在当前刻章点上的刻制电子印章的数目

    @ExcelField(title = "电子印章收费(个)",type = 1)
    private double eleStampCountMoney; //电子印章收费


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

    public int getPhyStampCount() {
        return phyStampCount;
    }

    public void setPhyStampCount(int phyStampCount) {
        this.phyStampCount = phyStampCount;
    }

    public double getPhyStampCountMoney() {
        return phyStampCountMoney;
    }

    public void setPhyStampCountMoney(double phyStampCountMoney) {
        this.phyStampCountMoney = phyStampCountMoney;
    }

    public int getEleStampCount() {
        return eleStampCount;
    }

    public void setEleStampCount(int eleStampCount) {
        this.eleStampCount = eleStampCount;
    }

    public double getEleStampCountMoney() {
        return eleStampCountMoney;
    }

    public void setEleStampCountMoney(double eleStampCountMoney) {
        this.eleStampCountMoney = eleStampCountMoney;
    }
}
