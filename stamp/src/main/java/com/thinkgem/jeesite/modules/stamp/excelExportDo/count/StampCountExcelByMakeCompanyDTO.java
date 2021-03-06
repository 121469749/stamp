package com.thinkgem.jeesite.modules.stamp.excelExportDo.count;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/7.
 */
public class StampCountExcelByMakeCompanyDTO {

    @ExcelField(title = "单位名称",type = 1)
    private String companyName;

    @ExcelField(title = "印章编码",type = 1)
    private String stampCode;

    @ExcelField(title = "印章类型",type = 1,dictType = "stampType")
    private String stampType;

    @ExcelField(title = "印章状态",type = 1,value = "stampState.value")
    private StampState stampState;

    @ExcelField(title = "印章形式",type = 1,dictType = "stampShape")
    private String stampShape;

    @ExcelField(title = "备案日期",type = 1)
    private Date recordDate;        // 备案日期

    @ExcelField(title = "制作日期",type = 1)
    private Date makeDate;        // 制作日期

    @ExcelField(title = "交付日期",type = 1)
    private Date deliveryDate;        // 交付日期


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStampCode() {
        return stampCode;
    }

    public void setStampCode(String stampCode) {
        this.stampCode = stampCode;
    }

    public String getStampType() {
        return stampType;
    }

    public void setStampType(String stampType) {
        this.stampType = stampType;
    }

    public StampState getStampState() {
        return stampState;
    }

    public void setStampState(StampState stampState) {
        this.stampState = stampState;
    }

    public String getStampShape() {
        return stampShape;
    }

    public void setStampShape(String stampShape) {
        this.stampShape = stampShape;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Date getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(Date makeDate) {
        this.makeDate = makeDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
