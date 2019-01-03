/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.service.exchange;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampRecordDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.StampMakeService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.utils.Object2Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.stamp.entity.exchange.DataExchange;
import com.thinkgem.jeesite.modules.stamp.dao.exchange.DataExchangeDao;

/**
 * 完成数据交换Service
 *
 * @author ADD BY LINZHIBAO
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
public class DataExchangeService extends CrudService<DataExchangeDao, DataExchange> {


    @Autowired
    private DataExchangeDao dataExchangeDao;

    @Autowired
    private StampDao stampDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private StampRecordDao stampRecordDao;

    @Autowired
    private StampMakeService stampMakeService;

    public DataExchange get(String id) {
        return super.get(id);
    }

    public List<DataExchange> findList(DataExchange dataExchange) {
        return super.findList(dataExchange);
    }

    public Page<DataExchange> findPage(Page<DataExchange> page, DataExchange dataExchange) {
        return super.findPage(page, dataExchange);
    }

    @Transactional(readOnly = false)
    public void save(DataExchange dataExchange) {
        super.save(dataExchange);
    }

    @Transactional(readOnly = false)
    public void delete(DataExchange dataExchange) {
        super.delete(dataExchange);
    }

    /**
     * 功能描述:创建用户信息,并绑定权限根据印章商城传递过来的数据
     *
     * @param: [dataExchange, area]
     * @return: com.thinkgem.jeesite.modules.sys.entity.User
     * @auther: linzhibao
     * @date: 2018-09-06 10:45
     */
    @Transactional(readOnly = false)
    public Company createUser(DataExchange dataExchange, Area area, StampRecord stampRecord) {
        Company company = Object2Object.convertPojo(dataExchange, Company.class);
        company.setArea(area);
        company.setCompType(CompanyType.USE);
        company.setCompState(CompanyState.USING);
        company.setCompPhone(dataExchange.getLegalPhone());
        company.setIsNewRecord(false);
        company.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        stampRecord.setUseComp(company);
        User user = stampMakeService.createNewUser(stampRecord, area); // 用户创建与公司创建
        // 将公司与sysuser进行绑定进行绑定
        user.setUserType(UserType.USE);
        user.setUserTypeId(user.getCompany().getId());
        userDao.updateUseTypeIdAndComp(user);
        return company;
    }

    /**
     * 功能描述:保存备案登记信息 t_stamprecord_1
     *
     * @param: [dataExchange]
     * @return: String
     * @auther: linzhibao
     * @date: 2018-09-10 14:47
     */
    @Transactional(readOnly = false)
    public StampRecord insertStampRecord(DataExchange dataExchange) {
        StampRecord stampRecord = Object2Object.convertPojo(dataExchange, StampRecord.class);
        stampRecord.setUseComp(dataExchange.getUseCompany());
        stampRecord.setMakeComp(dataExchange.getMakeCompany());
        stampRecord.setWorkType(StampWorkType.APPLY);
        stampRecord.setType1(String.valueOf(dataExchange.getCompType())); // 企业类型
        stampRecord.setCompPhone(dataExchange.getLegalPhone());
        stampRecord.setRemarks("印章商城订单" + new Date());
        stampRecord.setDelFlag("0");
        stampRecord.beforeInsert();
        stampRecordDao.insert(stampRecord);
        return stampRecord;
    }


    /**
     * 功能描述:保存印章信息 t_stamp_1 or 2
     *
     * @param: [dataExchange, stampRecord]
     * @return: Stamp
     * @auther: linzhibao
     * @date: 2018-09-06 17:14
     */
    @Transactional(readOnly = false)
    public Stamp insertStamp(DataExchange dataExchange, StampRecord stampRecord) {
        String displayName = DictUtils.getDictLabel(String.valueOf(dataExchange.getStampShape()), "stampType", "");
        //  计算该公司 这种印章类型的 数目有多少个 ,例如:珠海润成业务章已经刻制2个
        int stampCount = stampDao.getStampTypeCountByUseCompanyAndStampType(dataExchange.getStampType().toString(), dataExchange.getUseCompany(), dataExchange.getStampShape().toString());
        Stamp stamp = Object2Object.convertPojo(dataExchange, Stamp.class);
        stamp.beforeInsert();
        stamp.setUseComp(dataExchange.getUseCompany());
        stamp.setMakeComp(dataExchange.getMakeCompany());
        stamp.setNowMakeComp(dataExchange.getMakeCompany());
        stamp.setLastRecord(stampRecord);
        stamp.setStampName(displayName + "-" + (stampCount + 1));
        stamp.setStampState(StampState.RECEPT);
        stamp.setUseState(OprationState.OPEN);
        stamp.setSysState(SysState.USABLE);
        stamp.setRecordState(StampWorkType.APPLY);
        stamp.setRecordDate(new Date());
        stamp.setRemarks("印章商城订单数据");
        if ("1".equals(dataExchange.getStampShape())) { // 物理印章生成stampCode,电子印章不生成stampCode
            stamp.setStampCode(stampMakeService.phyStampCode()); // 生成stampCode
        }
        stampDao.insert(stamp);
        Stamp applyInfoStamp = new Stamp();
        applyInfoStamp.setId(stamp.getId());
        applyInfoStamp.setStampName(stamp.getStampName());
        applyInfoStamp.setRecordState(StampWorkType.APPLY);
        applyInfoStamp.setStampShape(stamp.getStampShape());
        applyInfoStamp.setStampType(stamp.getStampType());
        applyInfoStamp.setStampTexture(stamp.getStampTexture());
        applyInfoStamp.setMakeStampCount(dataExchange.getSealCount()); // stamprecord表中applyinfos只需要记录这几个字段,故单独返回
        return applyInfoStamp;
    }

    /**
     * 功能描述: 将stamp与record进行绑定
     *
     * @param: [dataExchange, stamp]
     * @return: void
     * @auther: linzhibao
     * @date: 2018-09-11 9:19
     */
    @Transactional(readOnly = false)
    public void stampBindRecord(DataExchange dataExchange, List<Stamp> stampList, StampRecord stampRecord) {
        Stamp applyInfos = Object2Object.convertPojo(dataExchange, Stamp.class);
        applyInfos.setId(stampList.get(0).getId());
        applyInfos.setStampName(stampList.get(0).getStampName());
        applyInfos.setRecordState(StampWorkType.APPLY);
        applyInfos.setMakeStampCount(dataExchange.getSealCount());
        applyInfos.setNowMakeComp(null);
        String applyInfosJson = JsonMapper.toJsonString(stampList);
        stampRecord.setStamp(applyInfos);
        stampRecord.setUpdateDate(new Date());
        stampRecord.setUpdateBy(UserUtils.getUser());
        stampRecord.setApplyInfos(applyInfosJson);
        stampRecordDao.bindRecord(stampRecord);
    }

    /**
     * 功能描述:检查订单中的印章是否可以进行刻制,公章只能刻制一个,如已经刻制过公章,则不可再刻制公章,要刻制电子章就必须先刻制物理章
     * @param: [dataExchange]
     * @return: boolean
     * @auther: linzhibao
     * @date: 2018-09-12 10:10
     */
    public String validCanMakeStamp(DataExchange dataExchange) {
        // 单独验证公章
        if("1".equals(dataExchange.getStampType())){// 公章
            int phyCount = stampDao.getStampTypeCountByUseCompanyAndStampType(String.valueOf(dataExchange.getStampType()),dataExchange.getUseCompany(),"1");
            if("1".equals(dataExchange.getStampShape())){ //物理章
             if(phyCount > 0 ){
                return "该公司已刻制过物理公章,不能再进行刻制";
             }
            }else if("2".equals(dataExchange.getStampShape())){ // 电子章
                if(phyCount == 0){
                    return "请先刻制物理公章";
                }else{
                   int eleCount =  stampDao.getStampTypeCountByUseCompanyAndStampType(String.valueOf(dataExchange.getStampType()),dataExchange.getUseCompany(),"2");
                   if(eleCount > 0){
                       return "该公司已经存在对应的物理公章和电子公章";
                   }
                }
            }
        }else{ // 验证除了公章外的其他章
            // 物理章没有个数限定
             if("2".equals(dataExchange.getStampShape())){ // 电子章
                int phyCount = stampDao.getStampTypeCountByUseCompanyAndStampType(String.valueOf(dataExchange.getStampType()),dataExchange.getUseCompany(),"1");
                int eleCount = stampDao.getStampTypeCountByUseCompanyAndStampType(String.valueOf(dataExchange.getStampType()),dataExchange.getUseCompany(),"2");
                //已经刻制的物理章减去已经刻制的电子章,则得到还可以刻制的电子章,
                // 假设已经刻制了三个物理业务章,一个电子业务章,则还可以继续刻制两个电子的业务章,同个类型的章下电子章不能超过物理印章个数
                if(phyCount == 0){
                    return "请先刻制对应章的物理印章";
                }
                if(phyCount - eleCount < dataExchange.getSealCount()){
                    return "同个类型的章下电子章不能超过物理印章个数";
                }
            }
        }
        return null;
    }

}