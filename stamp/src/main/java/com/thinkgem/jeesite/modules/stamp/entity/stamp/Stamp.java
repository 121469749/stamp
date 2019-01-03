/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.entity.stamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.log.entity.ModifyInfoLog;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampWorkType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.SysState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation.Enumeration;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 印章信息Entity
 *
 * @author Locker
 * @version 2017-05-13
 */
public class Stamp extends DataEntity<Stamp> implements Cloneable {

    private static final long serialVersionUID = 1L;

    private Company useComp ;        // 用章单位

    private Company makeComp;        // 刻章点

    private Company nowMakeComp;        // 现在所属刻章公司

    private String stampName;            // 印章名称

    private String allowUseNum;            // 印章允许使用次数

    private StampWorkType recordState;      //备案状态

    @Enumeration(enumClass = StampState.class)
    private StampState stampState;        // 印章交接状态

    @Enumeration(enumClass = OprationState.class)
    private OprationState useState;    //使用状态

    @Enumeration(enumClass = SysState.class)
    private SysState sysState;        // 印章系统管控状态

    private String stampShape;// 印章形式 (1、物理 2、电子 单选)

    private List<String> stampShapes = new ArrayList<String>();// 印章形式 用于 界面显示

    private String stampType;            // 印章类型(1.公章;2.财务专用章;3.发票;4.合同;5.业务;6.法人章;7.其他;)

    private String stampTexture;        // 印章材质

    private String stampSubType;        // 印章子类型(目前用来判断印章备案来源)

    private StampRecord lastRecord;        // 最后一次备案id

    private User lastStateBy;        // 印章状态最后修改人
    private Date lastStateDate;        // 印章状态最后修改时间

    private Date recordDate;        // 备案日期
    private Date makeDate;        // 制作日期
    private Date deliveryDate;        // 交付日期
    private Date checkDate; //

    /*虚拟构建一个"制作日期"查询当天的字段*/
    private Date fakemakeDate;


    private String livePhoto;   //交付现场照片

    private String recordPhoto;  //交付备案记录 一个路径

    private String stampCode;  //印章编码

    private String stampShapeId;  //印模id

    private String phyModel;        // 物理印模存储路径

    private String eleModel;        // 电子印模存储路径

    private String esEleModel;      // 公安部电子印模存储路径

    private String scanModel;      // 扫描上传的电子印模

    private String waterEleModel; // 水印电子印模图

    private Moulage moulage;    // 物理印模参数信息

    private Electron electron; // 电子印章参数信息

    private String recordPDF;//备案登记表PDF

    @JsonIgnore
    private int makeMoney;//制章点费用
    @JsonIgnore
    private int cityMoney;//市销商费用
    @JsonIgnore
    private int provinceMoney; //省经销商费用
    @JsonIgnore
    private int rcMoney; //润城费用

    private User useUser;

    private Stamp bindStamp;//物理印章绑定电子印章

    private ModifyInfoLog modifyInfoLog; // 记录修改记录

    //电子印章版定物理印章
    private String bindStampId;//用来存bindStamp对象的id

    public String getBindStampId() {
        return bindStampId;
    }

    public void setBindStampId(String bindStampId) {
        this.bindStampId = bindStampId;
    }

    //电子印章版定物理印章
    private String antiFakeId;//芯片Id

    private String antiFakeWrite;//芯片写入标志

    /*印章制作数量存放字段*/
    private int makeStampCount;

    private String returnCode;  //回执编码

    private StampWorkType stateFlag;    //印章临时使用状态标志

    //以下四个字段为查询所用的
    private Date beginMakeDate;

    private Date endMakeDate;

    private Date beginDeliveryDate;

    private Date endDeliveryDate;


    public Stamp() {
        super();
    }

    public Stamp(String id) {
        super(id);
    }

    public Stamp(String id, StampState stampState, OprationState useState, SysState sysState, String stampShape) {

        this.id = id;

        this.stampState = stampState;

        this.useState = useState;

        this.sysState = sysState;

        this.stampShape = stampShape;

    }

    public Stamp(Company useComp, StampState stampState, OprationState useState, SysState sysState, String stampShape) {

        this.useComp = useComp;

        this.stampState = stampState;

        this.useState = useState;

        this.sysState = sysState;

        this.stampShape = stampShape;
    }

    public Stamp(Company useComp, Company makeComp, Company nowMakeComp,
                 String stampName, String stampSubType, String stampType, String stampTexture,
                 StampState stampState, String stampShape, String stampShapeId) {

        this.useComp = useComp;

        this.makeComp = makeComp;

        this.nowMakeComp = nowMakeComp;

        this.stampName = stampName;

        this.stampSubType = stampSubType;

        this.stampType = stampType;

        this.stampTexture = stampTexture;

        this.stampState = stampState;

        this.stampShape = stampShape;

        this.stampShapeId = stampShapeId;
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

    public Company getNowMakeComp() {
        return nowMakeComp;
    }

    public void setNowMakeComp(Company nowMakeComp) {
        this.nowMakeComp = nowMakeComp;
    }

    public OprationState getUseState() {
        return useState;
    }

    public void setUseState(OprationState useState) {
        this.useState = useState;
    }

    public StampRecord getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(StampRecord lastRecord) {
        this.lastRecord = lastRecord;
    }

    public void setLastStateBy(User lastStateBy) {
        this.lastStateBy = lastStateBy;
    }

    public String getStampName() {
        return stampName;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    public void setStampShape(String stampShape) {
        this.stampShape = stampShape;
    }

    public List<String> getStampShapes() {
        return stampShapes;
    }

    public void setStampShapes(List<String> stampShapes) {
        this.stampShapes = stampShapes;
    }

    public String getAllowUseNum() {
        return allowUseNum;
    }

    public void setAllowUseNum(String allowUseNum) {
        this.allowUseNum = allowUseNum;
    }

    public StampState getStampState() {
        return stampState;
    }

    public void setStampState(StampState stampState) {
        this.stampState = stampState;
    }

    public SysState getSysState() {
        return sysState;
    }

    public void setSysState(SysState sysState) {
        this.sysState = sysState;
    }

    public Date getLastStateDate() {
        return lastStateDate;
    }

    public void setLastStateDate(Date lastStateDate) {
        this.lastStateDate = lastStateDate;
    }

    public String getStampTexture() {
        return stampTexture;
    }

    public void setStampTexture(String stampTexture) {
        this.stampTexture = stampTexture;
    }

    public String getStampSubType() {
        return stampSubType;
    }

    public void setStampSubType(String stampSubType) {
        this.stampSubType = stampSubType;
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


    public User getLastStateBy() {
        return lastStateBy;
    }

    public Moulage getMoulage() {
        return moulage;
    }

    public void setMoulage(Moulage moulage) {
        this.moulage = moulage;
    }

    public Electron getElectron() {
        return electron;
    }

    public void setElectron(Electron electron) {
        this.electron = electron;
    }

    public ModifyInfoLog getModifyInfoLog() {
        return modifyInfoLog;
    }

    public void setModifyInfoLog(ModifyInfoLog modifyInfoLog) {
        this.modifyInfoLog = modifyInfoLog;
    }

    public String getStampType() {
        return stampType;
    }

    public void setStampType(String stampType) {
        this.stampType = stampType;
    }

    public String getStampShape() {
        return stampShape;
    }

    public String getStampShapeId() {
        return stampShapeId;
    }

    public void setStampShapeId(String stampShapeId) {
        this.stampShapeId = stampShapeId;
    }

    public String getLivePhoto() {
        return livePhoto;
    }

    public void setLivePhoto(String livePhoto) {
        this.livePhoto = livePhoto;
    }

    public String getRecordPhoto() {
        return recordPhoto;
    }

    public void setRecordPhoto(String recordPhoto) {
        this.recordPhoto = recordPhoto;
    }

    public String getPhyModel() {
        return phyModel;
    }

    public void setPhyModel(String phyModel) {
        this.phyModel = phyModel;
    }

    public String getEleModel() {
        return eleModel;
    }

    public StampWorkType getRecordState() {
        return recordState;
    }

    public void setRecordState(StampWorkType recordState) {
        this.recordState = recordState;
    }

    public void setEleModel(String eleModel) {
        this.eleModel = eleModel;
    }

    public String getEsEleModel() {
        return esEleModel;
    }

    public void setEsEleModel(String esEleModel) {
        this.esEleModel = esEleModel;
    }

    public String getScanModel() {
        return scanModel;
    }

    public void setScanModel(String scanModel) {
        this.scanModel = scanModel;
    }

    public String getStampCode() {
        return stampCode;
    }

    public void setStampCode(String stampCode) {
        this.stampCode = stampCode;
    }

    public int getMakeMoney() {
        return makeMoney;
    }

    public void setMakeMoney(int makeMoney) {
        this.makeMoney = makeMoney;
    }

    public int getCityMoney() {
        return cityMoney;
    }

    public void setCityMoney(int cityMoney) {
        this.cityMoney = cityMoney;
    }

    public int getProvinceMoney() {
        return provinceMoney;
    }

    public void setProvinceMoney(int provinceMoney) {
        this.provinceMoney = provinceMoney;
    }

    public int getRcMoney() {
        return rcMoney;
    }

    public void setRcMoney(int rcMoney) {
        this.rcMoney = rcMoney;
    }

    public User getUseUser() {
        return useUser;
    }

    public void setUseUser(User useUser) {
        this.useUser = useUser;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Stamp getBindStamp() {
        return bindStamp;
    }

    public void setBindStamp(Stamp bindStamp) {
        this.bindStamp = bindStamp;
    }

    public String getWaterEleModel() {
        return waterEleModel;
    }

    public void setWaterEleModel(String waterEleModel) {
        this.waterEleModel = waterEleModel;
    }

    public String getAntiFakeId() {
        return antiFakeId;
    }

    public void setAntiFakeId(String antiFakeId) {
        this.antiFakeId = antiFakeId;
    }

    public String getAntiFakeWrite() {
        return antiFakeWrite;
    }

    public void setAntiFakeWrite(String antiFakeWrite) {
        this.antiFakeWrite = antiFakeWrite;
    }

    public Date getFakemakeDate() {
        return fakemakeDate;
    }

    public void setFakemakeDate(Date fakemakeDate) {
        this.fakemakeDate = fakemakeDate;
    }

    public int getMakeStampCount(){return makeStampCount;}

    public void setMakeStampCount(int makeStampCount) {
        this.makeStampCount = makeStampCount;
    }
    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public StampWorkType getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(StampWorkType stateFlag) {
        this.stateFlag = stateFlag;
    }

    public String getRecordPDF() {
        return recordPDF;
    }

    public void setRecordPDF(String recordPDF) {
        this.recordPDF = recordPDF;
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

    //json 格式化 id 才会用到
    public void setUUID() {
        setId(IdGen.uuid());
        setIsNewRecord(true);
    }

    //对象克隆
    public Object clone() {
        Stamp o = null;
        try {

            o = (Stamp) super.clone();

        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }

        return o;
    }

    /**
     * 当需要json化单个对象时候把，单个对象转化为list Json格式存储
     * 目的是为了统一json的格式
     *
     * @return
     */
    public List<Stamp> toArrayList() {
        List<Stamp> stamps = new ArrayList<Stamp>();

        stamps.add(this);
        return stamps;
    }


}