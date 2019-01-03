package com.thinkgem.jeesite.modules.stamp.service.police;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.dao.AttachmentDao;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.licence.LicenceDao;
import com.thinkgem.jeesite.modules.stamp.dao.police.PoliceDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by hjw-pc on 2017/5/20.
 * 公安单位对刻章单位的附件操作
 */
@Service
@Transactional(readOnly = true)
public class PoliceLicenseService {
    @Autowired
    private LicenceDao licenceDao;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private PoliceDao policeDao;
    @Autowired
    private SystemService systemService;
    @Autowired
    private UserDao userDao;


    /**
     * @param
     * @return com.thinkgem.jeesite.modules.stamp.info.entity.licence.Licence
     * @author hjw
     * @description 公安录入刻章公司初始化年审
     * @date 2017/5/20
     */
    public Licence newLicense(WorkType workType, Company company) {
        Licence licence = new Licence();
        licence.setMakeComp(company);
        licence.setWorkType(workType);
        //初始状态是待审核
        licence.setCheckState(CheckState.CHECKING);
        licence.setCompName(company.getCompanyName());
        licence.setLegalName(company.getLegalName());
        licence.setLegalPhone(company.getLegalPhone());
        licence.setLegalCertType(company.getLegalCertType());
        licence.setLegalCertCode(company.getLegalCertCode());
        licence.setPoliceName(company.getPoliceName());
        licence.setPoliceIdCode(company.getPoliceIdCode());
        licence.setPolicePhone(company.getPolicePhone());
        licence.setBusType(company.getBusType());
        licence.setHeadCount(company.getHeadCount());
        licence.setSpecialCount(company.getSpecialCount());

        return licence;
    }


    /**
     * @return void
     * @author hjw
     * @description 更新license审核状态
     * @date 2017/5/20
     */
    @Transactional(readOnly = false)
    public void updateCheckState(Condition condition, Licence licence) {

        CheckState checkState = licence.getCheckState();
        Licence l = licenceDao.get(licence);
        l.setCheckState(checkState);

        if (l.getCheckState() == CheckState.CHECKSUCCESS) {
            //更新开办时间和结束时间
            if (l.getWorkType() == WorkType.OPEN || l.getWorkType() == WorkType.ANNUAL) {

                Company company = new Company();
                company.setCompType(CompanyType.MAKE);
                company.setId(l.getMakeComp().getId());
                company.setBusStartDate(new Date());
                company.setBusEndDate(DateUtils.addYears(new Date(), 1));
                company.setCompState(CompanyState.USING);
                systemService.updateLoginFlagByCompany(company);
                companyDao.updateComDateState(company);

            }
            //变更
            else if (l.getWorkType() == WorkType.CHANGE) {
                Company company = new Company();
                company.setCompType(CompanyType.MAKE);
                company.setId(l.getMakeComp().getId());
                company.setBusType(l.getBusType());
                company.setLegalName(l.getLegalName());
                company.setLegalPhone(l.getLegalPhone());
                company.setPoliceName(l.getPoliceName());
                company.setLegalCertCode(l.getLegalCertCode());
                company.setPoliceIdCode(l.getPoliceIdCode());
                company.setPolicePhone(l.getPolicePhone());
                company.setHeadCount(l.getHeadCount());
                company.setSpecialCount(l.getSpecialCount());
                //更新过期时间
                company.setBusStartDate(new Date());
                company.setBusEndDate(DateUtils.addYears(new Date(), 1));
                companyDao.updateByLicense(company);

            }
            //注销
            else if (l.getWorkType() == WorkType.LOGOUT) {
                Company company = new Company();
                company.setCompType(CompanyType.MAKE);
                company.setId(l.getMakeComp().getId());
                company.setCompState(CompanyState.LOGOUT);
                companyDao.updateComState(company);
                //禁止该公司下的用户登录
                userDao.updateLoginFlagByUserTypeId(company.getId(), "0", UserType.MAKE,UserUtils.getUser(),new Date());

                User checkUser = new User();

                checkUser.setUserTypeId(company.getId());
                checkUser.setUserType(UserType.MAKE);

                // 清除该公司下的用户缓存
                List<User> list = userDao.findUsersByUserTypeId(checkUser);
                for (User user : list) {
                    UserUtils.clearCache(user);
                }
            }
        } else if (l.getCheckState() == CheckState.CHECKFAIL && (l.getWorkType() == WorkType.OPEN || l.getWorkType() == WorkType.ANNUAL)) {
            Company company = new Company();
            company.setId(l.getMakeComp().getId());
            company.setBusStartDate(new Date());
            company.setBusEndDate(DateUtils.addYears(new Date(), -1));
            company.setCompState(CompanyState.STOP);
            company.setCompType(CompanyType.MAKE);
            systemService.updateLoginFlagByCompany(company);
            companyDao.updateComDateState(company);
        }

        licence.setWorkType(l.getWorkType());
        licenceDao.updateCheckState(licence);


    }


    /**
     *
     * @param attas
     * @return
     * 根据附件id获取附件列表详情
     */
    @Transactional(readOnly = true)
    public List<Attachment> getListAttachment(String attas) {

        if (attas == null) {
            return null;
        }
        String[] att = attas.split(",");
        List<String> list = new ArrayList<String>(Arrays.asList(att));


        return attachmentDao.findListByIdList(list);
    }


    /**
     *
     * @param licence
     * @return
     * 根据附件中的公司获取该公司最新的附件
     */
    @Transactional(readOnly = true)
    public Licence getNewestLicense(Licence licence) {
        Company c = new Company(licence.getMakeComp().getId());
        c.setCompType(CompanyType.MAKE);
      //  Company company = companyDao.get(c);
        //没有licence
        if (companyDao.getDetails(c).getLastLicenceState() == null) {
            return null;
        }
        licence.setWorkType(companyDao.getDetails(c).getLastLicenceState());

        return licenceDao.getNewestLicense(licence);
    }


    /**
     *
     * @param page
     * @param licence
     * @return
     * 获取附件列表，分页
     */
    @Transactional(readOnly = true)
    public Page<Licence> findLicenseList(Page<Licence> page, Licence licence) {
        //area过滤
        Police police = UserUtils.getUser().getPoliceInfo();

        Area area = police.getArea();

        licence.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(area, "sa"));

        licence.setPage(page);
        List<Licence> licenceList = new ArrayList<Licence>();
        if (licence.getWorkType() == null) {

            licence.setWorkType(WorkType.LOGOUT);

            licenceList = licenceDao.findList(licence);

        } else {
            licenceList = licenceDao.findList(licence);
        }
        page.setList(licenceList);

        return page;
    }


    /**
     *
     * @param licence
     * @return
     * 获取附件
     */
    @Transactional(readOnly = true)
    public Licence get(Licence licence) {
        return licenceDao.get(licence);
    }


    /**
     *
     * @param page
     * @param licence
     * @return
     * 获取历史附件列表，根据公安所在区域过滤
     */
    @Transactional(readOnly = true)
    public Page<Licence> findHistoryList(Page<Licence> page, Licence licence) {
        //area过滤
        Police police = UserUtils.getUser().getPoliceInfo();
        Area area = police.getArea();
        licence.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(area, "sa"));
        licence.setPage(page);
        List<Licence> licenceList = licenceDao.findHistoryList(licence);
//        if (licence.getWorkType() == null) {
//
//            for (WorkType workType : WorkType.values()) {
//                licence.setWorkType(workType);
//                licenceList.addAll(licenceDao.findHistoryList(licence));
//            }
//        } else {
//            licenceList =
//
        page.setList(licenceList);

         return page;
    }


    /**
     * @param
     * @return void
     * @author hjw
     * @description 撤销企业动作后续，删除该企业license
     * @date 2017/5/22
     */
    @Transactional(readOnly = false)
    public void deleteMakeCom(Condition condition, Company company) {

        try {
            for (WorkType workType : WorkType.values()) {
                licenceDao.deleteInMakeCom(company.getId(), workType);
            }


        } catch (Exception e) {
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("系统繁忙");
        }


    }
}
