package com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.entity.Area;

import java.util.Date;

/**
 *
 * 刻章点印章数据统计DTO
 *
 * Created by Locker on 2017/10/5.
 */
public class StampCountDTO {

    private String companyName;

    private String nowMakeCompanyName;

    private StampState stampState;

    private Date date;

    private String stampShape;

    private Area area;

    //以下四个字段为查询所用的
    private Date beginMakeDate;

    private Date endMakeDate;

    private Date beginDeliveryDate;

    private Date endDeliveryDate;

    public String getStampType() {
        return StampType;
    }

    public void setStampType(String stampType) {
        StampType = stampType;
    }

    private String StampType;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public StampState getStampState() {
        return stampState;
    }

    public void setStampState(StampState stampState) {
        this.stampState = stampState;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStampShape() {
        return stampShape;
    }

    public void setStampShape(String stampShape) {
        this.stampShape = stampShape;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Date getBeginMakeDate() {
        return beginMakeDate;
    }

    public void setBeginMakeDate(Date beginMakeDate) {
        this.beginMakeDate = beginMakeDate;
    }

    public Date getEndMakeDate() {
        return endMakeDate;
    }

    public void setEndMakeDate(Date endMakeDate) {
        this.endMakeDate = endMakeDate;
    }

    public Date getBeginDeliveryDate() {
        return beginDeliveryDate;
    }

    public void setBeginDeliveryDate(Date beginDeliveryDate) {
        this.beginDeliveryDate = beginDeliveryDate;
    }

    public Date getEndDeliveryDate() {
        return endDeliveryDate;
    }

    public void setEndDeliveryDate(Date endDeliveryDate) {
        this.endDeliveryDate = endDeliveryDate;
    }


    public String getNowMakeCompanyName() {
        return nowMakeCompanyName;
    }

    public void setNowMakeCompanyName(String nowMakeCompanyName) {
        this.nowMakeCompanyName = nowMakeCompanyName;
    }

    public Stamp changeStamp(){
        //设置查询条件
        Stamp stamp = new Stamp();
        //用章单位名称
        if(this.getCompanyName()!=null){
            Company useCompany = new Company();
            useCompany.setCompanyName(this.getCompanyName().trim());
            stamp.setUseComp(useCompany);
        }else{
            stamp.setUseComp(null);
        }

        //当前刻章点单位名称
        if(this.getNowMakeCompanyName()!=null){
            Company makeCompany = new Company();
            makeCompany.setCompanyName(this.getNowMakeCompanyName().trim());
            stamp.setNowMakeComp(makeCompany);
        }else{
            stamp.setNowMakeComp(null);
        }
        //印章状态
        if(this.getStampState() !=null){
            stamp.setStampState(this.getStampState());
        }else{
            stamp.setStampState(null);
        }
        //印章类型
        if(this.getStampShape()!=null){
            stamp.setStampShape(this.getStampShape());
        }else {
            stamp.setStampShape(null);
        }
        //时间
        if(this.getDate()!=null){
            stamp.setCheckDate(this.getDate());
        }else{
            stamp.setCheckDate(null);
        }

        //以下是为印章统计查询所设置
        //制作日期的筛选
        if(this.getBeginMakeDate() !=null){
            stamp.setBeginMakeDate(this.getBeginMakeDate());
        }

        if(this.getEndMakeDate() !=null){
            stamp.setEndMakeDate(this.getEndMakeDate());
        }


        //交付日期的筛选

        if(this.getBeginDeliveryDate() !=null){
            stamp.setBeginDeliveryDate(this.getBeginDeliveryDate());
        }

        if(this.getEndDeliveryDate() !=null){
            stamp.setEndDeliveryDate(this.getEndDeliveryDate());
        }
        // 印章类型
        if(this.getStampType() != null){
            stamp.setStampType(this.getStampType());
        }
        return stamp;
    }
}
