/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.entity.company;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation.Enumeration;
import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.CountUseCompanyStampDTO;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.vo.count.CompanyCountVO;
import com.thinkgem.jeesite.modules.stamp.vo.moneyCount.CompanyMoneyCountVO;
import com.thinkgem.jeesite.modules.sys.entity.Area;

import java.util.Date;

/**
 * 公司Entity
 *
 * @author Locker
 * @version 2017-05-13
 */
public class Company extends DataEntity<Company> {

    private static final long serialVersionUID = 1L;

    //	private String compType;		// 单位系统类型
    @Enumeration(enumClass = CompanyType.class)
    private CompanyType compType; //单位系统类型
    private Area area;        // 所属区域

    private String soleCode;        // 单位唯一编码（统一社会信用代码）
    private String companyName;        // 单位名称
    private String companyCode;        // 单位编码
    private String type1;        // 单位一级类别
    private String type2;        // 单位二级类别
    private String type3;        // 单位三级类别

    private String legalName;        // 法定代表人
    private String legalPhone;        // 法人手机
    private String legalCertType;        // 法人证件类型
    private String legalCertCode;        // 法人证件号码

    private String policeName;        // 治安负责人
    private String policeIdCode;        // 治安负责人身份证号码
    private String policePhone;        // 治安负责人联系电话
    private String busModel;        // 经营方式
    private String busType;        // 经济性质
    private String headCount;        // 单位总人数
    private String specialCount;        // 单位特业从业人员
    private String compAddress;        // 单位地址
    private String compPhone;        // 单位电话
    private String postcode;        // 邮编
    private String busLicnum;        // 企业营业执照号
    private String busTagnum;        // 企业税务登记证号

    @Enumeration(enumClass = CompanyState.class)
    private CompanyState compState; //企业状态

    private Date compCreatDate;        // 成立日期
    private Date busStartDate;        // 营业期限起始时间
    private Date busEndDate;        // 营业期限截止时间
    private String recordUnit;        // 登记机关
    private String busScope;        // 经营范围
    private String regCap;        // 注册资本

    private Licence licence;  //只有刻章单位才会有证明

    private Stamp stamp;

    @Enumeration(enumClass = OprationState.class)
    private OprationState sysOprState; // 润城科技管理员操作状态

    //上次许可证申请类型
    private WorkType lastLicenceState;

    /**
     * 计算公司的物理、电子印章数目和钱统计
     */
    private CompanyMoneyCountVO companyMoneyCountVO;

    private CountUseCompanyStampDTO countUseCompanyStampDTO;


    private CompanyCountVO companyCountVO;

    private CountSet phyCountSet;

    private CountSet eleCountSet;

    /*设置一个临时存放公司制作公章次数的记录字段(电子)*/
    private int checkEleStampCount;

    /*设置一个临时存放公司制作公章次数的记录字段(物理)*/
    private int checkEngraveCount;

    /*设置多个存放各种物理印章制作次数的记录字段（物理）*/
    private int engrave_02;
    private int engrave_03;
    private int engrave_04;
    private int engrave_05;
    private int engrave_06;
    private int engrave_07;

    // defaultValue = SysOprState.open

    public Company() {
        super();
    }

    public Company(String id) {
        super(id);
    }


    public Company(String id, CompanyType companyType) {
        this.id = id;
        this.compType = companyType;
    }

    public Company(CompanyType companyType, String soleCode) {
        this.compType = companyType;
        this.soleCode = soleCode;
    }

    public Company(Area area, String companyName, CompanyType compType) {

        this.area = area;
        this.companyName = companyName;
        this.compType = compType;
    }

//	public String getCompType() {
//		return compType;
//	}
//
//	public void setCompType(String compType) {
//		this.compType = compType;
//	}
//

    public WorkType getLastLicenceState() {
        return lastLicenceState;
    }

    public void setLastLicenceState(WorkType lastLicenceState) {
        this.lastLicenceState = lastLicenceState;
    }

    public CompanyType getCompType() {
        return compType;
    }

    public void setCompType(CompanyType compType) {
        this.compType = compType;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getType3() {
        return type3;
    }

    public void setType3(String type3) {
        this.type3 = type3;
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

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getPoliceIdCode() {
        return policeIdCode;
    }

    public void setPoliceIdCode(String policeIdCode) {
        this.policeIdCode = policeIdCode;
    }

    public String getPolicePhone() {
        return policePhone;
    }

    public void setPolicePhone(String policePhone) {
        this.policePhone = policePhone;
    }

    public String getBusModel() {
        return busModel;
    }

    public void setBusModel(String busModel) {
        this.busModel = busModel;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getHeadCount() {
        return headCount;
    }

    public void setHeadCount(String headCount) {
        this.headCount = headCount;
    }

    public String getSpecialCount() {
        return specialCount;
    }

    public void setSpecialCount(String specialCount) {
        this.specialCount = specialCount;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getBusLicnum() {
        return busLicnum;
    }

    public void setBusLicnum(String busLicnum) {
        this.busLicnum = busLicnum;
    }

    public String getBusTagnum() {
        return busTagnum;
    }

    public void setBusTagnum(String busTagnum) {
        this.busTagnum = busTagnum;
    }

    public CompanyState getCompState() {
        return compState;
    }

    public void setCompState(CompanyState compState) {
        this.compState = compState;
    }

    public Date getCompCreatDate() {
        return compCreatDate;
    }

    public void setCompCreatDate(Date compCreatDate) {
        this.compCreatDate = compCreatDate;
    }

    public Date getBusStartDate() {
        return busStartDate;
    }

    public void setBusStartDate(Date busStartDate) {
        this.busStartDate = busStartDate;
    }

    public Date getBusEndDate() {
        return busEndDate;
    }

    public void setBusEndDate(Date busEndDate) {
        this.busEndDate = busEndDate;
    }

    public String getRecordUnit() {
        return recordUnit;
    }

    public void setRecordUnit(String recordUnit) {
        this.recordUnit = recordUnit;
    }

    public String getBusScope() {
        return busScope;
    }

    public void setBusScope(String busScope) {
        this.busScope = busScope;
    }

    public String getRegCap() {
        return regCap;
    }

    public void setRegCap(String regCap) {
        this.regCap = regCap;
    }

    public OprationState getSysOprState() {
        return sysOprState;
    }

    public void setSysOprState(OprationState sysOprState) {
        this.sysOprState = sysOprState;
    }

    public CompanyMoneyCountVO getCompanyMoneyCountVO() {
        return companyMoneyCountVO;
    }

    public void setCompanyMoneyCountVO(CompanyMoneyCountVO companyMoneyCountVO) {
        this.companyMoneyCountVO = companyMoneyCountVO;
    }

    public CompanyCountVO getCompanyCountVO() {
        return companyCountVO;
    }

    public void setCompanyCountVO(CompanyCountVO companyCountVO) {
        this.companyCountVO = companyCountVO;
    }

    public CountSet getPhyCountSet() {
        return phyCountSet;
    }

    public void setPhyCountSet(CountSet phyCountSet) {
        this.phyCountSet = phyCountSet;
    }

    public CountSet getEleCountSet() {
        return eleCountSet;
    }

    public void setEleCountSet(CountSet eleCountSet) {
        this.eleCountSet = eleCountSet;
    }

    public CountUseCompanyStampDTO getCountUseCompanyStampDTO() {
        return countUseCompanyStampDTO;
    }

    public void setCountUseCompanyStampDTO(CountUseCompanyStampDTO countUseCompanyStampDTO) {
        this.countUseCompanyStampDTO = countUseCompanyStampDTO;
    }

    public int getCheckEleStampCount() {
        return checkEleStampCount;
    }

    public void setCheckEleStampCount(int checkEleStampCount) {
        this.checkEleStampCount = checkEleStampCount;
    }

    public int getCheckEngraveCount() {
        return checkEngraveCount;
    }

    public void setCheckEngraveCount(int checkEngraveCount) {
        this.checkEngraveCount = checkEngraveCount;
    }

    public int getEngrave_02() {
        return engrave_02;
    }

    public void setEngrave_02(int engrave_02) {
        this.engrave_02 = engrave_02;
    }

    public int getEngrave_03() {
        return engrave_03;
    }

    public void setEngrave_03(int engrave_03) {
        this.engrave_03 = engrave_03;
    }

    public int getEngrave_04() {
        return engrave_04;
    }

    public void setEngrave_04(int engrave_04) {
        this.engrave_04 = engrave_04;
    }

    public int getEngrave_05() {
        return engrave_05;
    }

    public void setEngrave_05(int engrave_05) {
        this.engrave_05 = engrave_05;
    }

    public int getEngrave_06() {
        return engrave_06;
    }

    public void setEngrave_06(int engrave_06) {
        this.engrave_06 = engrave_06;
    }

    public int getEngrave_07() {
        return engrave_07;
    }

    public void setEngrave_07(int engrave_07) {
        this.engrave_07 = engrave_07;
    }

    @Override
    public String toString() {
        return "Company{" +
                "compType=" + compType +
                ", area=" + area +
                ", soleCode='" + soleCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", type3='" + type3 + '\'' +
                ", legalName='" + legalName + '\'' +
                ", legalPhone='" + legalPhone + '\'' +
                ", legalCertType='" + legalCertType + '\'' +
                ", legalCertCode='" + legalCertCode + '\'' +
                ", policeName='" + policeName + '\'' +
                ", policeIdCode='" + policeIdCode + '\'' +
                ", policePhone='" + policePhone + '\'' +
                ", busModel='" + busModel + '\'' +
                ", busType='" + busType + '\'' +
                ", headCount='" + headCount + '\'' +
                ", specialCount='" + specialCount + '\'' +
                ", compAddress='" + compAddress + '\'' +
                ", compPhone='" + compPhone + '\'' +
                ", postcode='" + postcode + '\'' +
                ", busLicnum='" + busLicnum + '\'' +
                ", busTagnum='" + busTagnum + '\'' +
                ", compState='" + compState + '\'' +
                ", compCreatDate=" + compCreatDate +
                ", busStartDate=" + busStartDate +
                ", busEndDate=" + busEndDate +
                ", recordUnit='" + recordUnit + '\'' +
                ", busScope='" + busScope + '\'' +
                ", regCap='" + regCap + '\'' +
                "} " + super.toString();
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    /**
     * 润城经销商 公司信息 Copy
     *
     * @param company
     * @return
     */
    public static Company dealerCompanyCopy(Company company) {

        Company newCompany = new Company();

        newCompany.setCompanyName(company.getCompanyName());

        newCompany.setLegalCertType(company.getLegalCertType());

        newCompany.setLegalCertCode(company.getLegalCertCode());

        newCompany.setSoleCode(company.getSoleCode());

        newCompany.setLegalName(company.getLegalName());

        newCompany.setLegalPhone(company.getLegalPhone());

        newCompany.setCompAddress(company.getCompAddress());

        newCompany.setDelFlag("0");

        newCompany.setCompType(company.getCompType());

        newCompany.setCompState(company.getCompState());

        newCompany.setCompPhone(company.getCompPhone());

        return newCompany;
    }
}