/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.service.stamp;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.ElectronDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.MoulageDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampAuthDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampRecordDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Electron;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Moulage;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampAuth;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.service.stamprecord.StampLogService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用章单位-印章信息Service
 *
 * @author Locker
 * @version 2017-05-13
 */
@Service
@Transactional(readOnly = true)
public class StampService extends CrudService<StampDao, Stamp> {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private StampRecordDao stampRecordDao;

    @Autowired
    private StampLogService stampLogService;

    @Autowired
    private StampAuthDao stampAuthDao;

    @Autowired
    private ElectronDao electronDao;

    @Autowired
    private MoulageDao moulageDao;

    @Autowired
    private StampDao stampDao;

//	public Stamp get(String id) {
//		return super.get(id);
//	}
//
//	public List<Stamp> findList(Stamp stamp) {
//		return super.findList(stamp);
//	}
//
//	public Page<Stamp> findPage(Page<Stamp> page, Stamp stamp) {
//		return super.findPage(page, stamp);
//	}
//
//	@Transactional(readOnly = false)
//	public void save(Stamp stamp) {
//		super.save(stamp);
//	}
//
//	@Transactional(readOnly = false)
//	public void delete(Stamp stamp) {
//		super.delete(stamp);
//	}

    /**
     * 判断当前刻章单位是否可以进行业务操作
     * 根据公司的 状态,许可证,润城管理员状态
     *
     * @return true - 可以进行业务操作
     * fasle- 不可以进行业务操作
     */
    private boolean judgeService() {

        User user = UserUtils.getUser();

        if (!(user.getUserType() == UserType.MAKE)) {
            //用户类型不对
            return false;
        }

        Company company = user.getCompanyInfo();
        // 公司不对
        if (!(company.getCompType() == CompanyType.MAKE)) {
            return false;
        }

        //如果公司被停用了则无法进行
        if (company.getCompState() == CompanyState.STOP) {

            return false;
        }

        //如果 润城管理员停用了
        if (company.getSysOprState() == OprationState.STOP) {

            return false;
        }

        return false;
    }

    /**
     * 更改印章状态
     *
     * @param stamp
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public boolean updateStampState(Stamp stamp, String state) {

        OprationState oprationState = null;
        if (state.equals("1")) {
            oprationState = OprationState.OPEN;
        } else {
            oprationState = OprationState.STOP;
        }

        stamp.setUseState(oprationState);
        stamp.setStampShape("2");
        stamp.preUpdate();
        int updateResult = dao.updateStampState(stamp);

        if (updateResult > 0) {

            stamp = dao.get(stamp);

            String title = oprationState.getValue() + " " + stamp.getStampName();

            //记录日志
            stampLogService.insertLog(title);

            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询印章详细信息
     *
     * @param stampId
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public Stamp findStampInfo(String stampId, String stampShape) {

        Stamp stamp = new Stamp(stampId);

        // update by linzhibao 2018-8-27
        // 如果只传了一个唯一ID,没有传入物理或电子标识,先查t_stamp_1,如果没找到对应数据,再从电子t_stamp_2中查询
        if(stampShape == null){
             stamp.setStampShape("1");
             stamp = dao.get(stamp);
             if(stamp == null){
                 stamp = new Stamp(stampId);
                 stamp.setStampShape("2");
                 stamp = dao.get(stamp);
             }

        }else{
            stamp.setStampShape(stampShape);
            stamp = dao.get(stamp);
        }

        //获取用章单位信息
        stamp.setUseComp(companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE)));

        //获取制章单位信息
        stamp.setMakeComp(companyDao.get(new Company(stamp.getMakeComp().getId(), CompanyType.MAKE)));

        //获取现在所属制章单位信息
        stamp.setNowMakeComp(companyDao.get(new Company(stamp.getNowMakeComp().getId(), CompanyType.MAKE)));

        //获取最后一个备案记录信息
        StampRecord stampRecord = stamp.getLastRecord();

        if (stampRecord == null) {
            stampRecord = new StampRecord();
        }

        stampRecord.setWorkType(stamp.getRecordState());

        stamp.setLastRecord(stampRecordDao.get(stampRecord));

        return stamp;
    }

    /**
     * 根据印章使用状态查询印章列表
     *
     * @param stamp
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Stamp> listStampByUseStatePage(Page<Stamp> page, Stamp stamp) {

        stamp.setPage(page);

        List<Stamp> stamps = dao.listStampByUseState(stamp);

        page.setList(stamps);

        return page;
    }

    /**
     * 根据印章状态查询印章列表（用章单位查询刻制中印章）
     *
     * @param stamp
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Stamp> listStampByStampStatePage(Page<Stamp> page, Stamp stamp) {

        stamp.setPage(page);

        List<Stamp> stampList = dao.listStampByStampState(stamp);

        page.setList(stampList);

        return page;
    }

    /**
     * 查询用章公司子用户可使用的电子印章列表
     */
    @Transactional(readOnly = true)
    public Page<StampAuth> showUsefulEleStampPage(Page<StampAuth> page, StampAuth stampAuth) {

        stampAuth.setPage(page);

        List<StampAuth> stampAuthList = stampAuthDao.findUsefulByUser(stampAuth);

        page.setList(stampAuthList);

        return page;
    }

    /**
     * 获取印章详细信息
     *
     * @param stamp
     * @return
     */
    @Transactional(readOnly = true)
    public Stamp getStampInfo(Stamp stamp) {

        stamp = dao.get(stamp);

        //判断当前印章是物理印章还是电子印章
        if (stamp.getStampShape().equals(StampShape.ELESTAMP.getKey())) {

            Electron electron = new Electron();

            electron.setId(stamp.getStampShapeId());

            electron = electronDao.get(electron);

            stamp.setElectron(electron);

        } else {

            Moulage moulage = moulageDao.getMoulageById(stamp.getStampShapeId());

            stamp.setMoulage(moulage);


        }

        return stamp;
    }

    @Transactional(readOnly = false)
    public int checkMakeStampCount(Stamp stamp) {
        stamp.setStampState(StampState.ENGRAVE);
        int stampMakeCount = stampDao.checkMakeStampCount(stamp);
        return stampMakeCount;
    }

    /**
     * 根据 印章名称、用章公司 查找印章（模糊查询）
     *
     * @param company
     * @param stamp
     * @return
     */
    public List<Stamp> listStampByName(Company company, Stamp stamp){
        return stampDao.listStampByName(company,stamp);
    }

    /**
     * 功能描述:  根据公司相关信息查找名下物理章和电子章
     * @param:
     * @return: 
     * @auther: linzhibao
     * @date: 2018-08-25 16:20
     */
    public List<Stamp> findStampListByCompanyInfo(Stamp stamp){
        return dao.findStampListByCompanyInfo(stamp);
    }

}