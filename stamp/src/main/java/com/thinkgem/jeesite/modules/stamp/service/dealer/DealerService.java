package com.thinkgem.jeesite.modules.stamp.service.dealer;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.licence.LicenceDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.exception.dealer.DealerSystemOprationException;
import com.thinkgem.jeesite.modules.stamp.exception.dealer.DealerSystemOprationOpenFailException;
import com.thinkgem.jeesite.modules.stamp.exception.dealer.DealerSystemOprationStopFailException;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.MakeCompanyLicenseService;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 经销商 服务
 * <p>
 * Created by Locker on 2017/7/2.
 */
@Service
public class DealerService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private LicenceDao licenceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MakeCompanyLicenseService makeCompanyLicenseService;

    /**
     * 经销商查找公司列表
     *
     * @param company
     * @param page
     */
    @Transactional(readOnly = true)
    public Page<Company> findCompanyPage(Company company, Page<Company> page) {

        User currentUser = UserUtils.getUser();


        Company currentCompany = currentUser.getCompanyInfo();
        Area currentArea;
        //获得当前区域 only if no selected area in company
        if (company.getArea() != null && StringUtils.isNotBlank(company.getArea().getId())) {
            currentArea = company.getArea();
        } else {
            currentArea = areaDao.get(currentCompany.getArea().getId());
        }

        company.setPage(page);

        List<Company> companys = null;

        //区域id 为1 就是中国，说明当前用户是最大的经销商
        //不需要做区域搜索过滤
        if ("1".equals(currentArea.getId())) {

            company.setArea(null);

            company.setDelFlag("0");

            companys = companyDao.multipleConditionsFindList(company);

            System.out.println(companys.toString());

        } else {
            //否则说明当前用户是普通经销商

            //先查找该区域下的子区域

          //  company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(currentArea,"a3"));

            List<Area> subAreas = areaDao.getSubAreaByPareanId(currentArea.getId());

            subAreas.add(currentArea);

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(subAreas));

            companys = companyDao.findCompanyListByAreas(company);

            System.out.println(companys.toString());

        }



        //读取每个公司的上级区域
        for(Company areaCompany:companys){

            Area area = areaCompany.getArea();
            area = areaDao.get(area);

            Area parent = area.getParent();
            parent = areaDao.get(parent);
            area.setParent(parent);

            areaCompany.setArea(area);

        }

        page.setList(companys);

        return page;
    }

    /**
     * 通过公司Id,与类型来获得一个公司的信息
     *
     * @param companyId
     * @param companyType
     */
    @Transactional(readOnly = true)
    public Company getCompany(String companyId, CompanyType companyType) {

        Company company = new Company(companyId, companyType);

        company = companyDao.get(company);

        if (companyType == CompanyType.MAKE) {

//
//            licence.setMakeComp(company);


//            Date lastLicenceDate = null;
//            Date maxDate = null;
//            int maxIndex = 0;
//            for (WorkType workType : WorkType.values()) {
//                licence.setWorkType(workType);
//                Licence licence1 = licenceDao.getNewestLicense(licence);
//
//                if (lastLicenceDate == null ||  DateUtils.pastMinutes(licence1.getUpdateDate()) <=  DateUtils.pastMinutes(lastLicenceDate)) {
//                    maxDate = licence1.getUpdateDate();
//                    licence.setWorkType(workType);
//                    lastLicenceDate = licence1.getUpdateDate();
//                }
//
//
//            }
           Licence licence = makeCompanyLicenseService.checkNewestLicence(company);



            company.setLicence(licence);
        }

        return company;
    }


    /**
     * 系统管控启用停用刻章点或者用章单位
     *
     * @param companyId
     * @param companyType
     * @param oprationState
     */
    @Transactional(readOnly = false)
    public Condition systemOpration(String companyId, CompanyType companyType, OprationState oprationState) throws
            DealerSystemOprationOpenFailException, DealerSystemOprationStopFailException, DealerSystemOprationException {

        Company company = companyDao.get(new Company(companyId, companyType));

        UserType userType = companyType == CompanyType.MAKE ?
                                UserType.MAKE : (companyType == CompanyType.AGENCY ? UserType.AGENY : UserType.USE);

        User checkUser = new User();
        checkUser.setUserTypeId(companyId);
        checkUser.setUserType(userType);
        checkUser.setUpdateBy(UserUtils.getUser());
        checkUser.setUpdateDate(new Date());
        Condition condition = null;

        try {

            //启用
            if (oprationState == OprationState.OPEN) {

                //先启用公司的管控状态
                if (companyDao.systemOprationState(companyId, companyType, oprationState) == 0) {

                    //todo 抛异常
                    throw new DealerSystemOprationOpenFailException("系统管控" + oprationState.getValue() + "-" + company.getCompanyName() + "失败!");
                }


                checkUser.setLoginFlag(Global.YES);
                //公司管控成功
                //就对公司下的帐号进行管控
                if (userDao.systemOprationLoginFlag(checkUser) == 0) {

                    //todo 抛异常
                    throw new DealerSystemOprationOpenFailException("系统管控" + oprationState.getValue() + "-" + company.getCompanyName() + "失败!");
                }

                condition = new Condition(Condition.SUCCESS_CODE, oprationState.getValue() + company.getCompanyName() + "成功!");

            }

            if (oprationState == OprationState.STOP) {

                //先启用公司的管控状态
                if (companyDao.systemOprationState(companyId, companyType, oprationState) == 0) {

                    //todo 抛异常
                    throw new DealerSystemOprationOpenFailException("系统管控" + oprationState.getValue() + "-" + company.getCompanyName() + "失败!");
                }

                checkUser.setLoginFlag(Global.NO);
                //公司管控成功
                //就对公司下的帐号进行管控
                if (userDao.systemOprationLoginFlag(checkUser) == 0) {

                    //todo 抛异常
                    throw new DealerSystemOprationOpenFailException("系统管控" + oprationState.getValue() + "-" + company.getCompanyName() + "失败!");
                }

                condition = new Condition(Condition.SUCCESS_CODE, oprationState.getValue() + company.getCompanyName() + "成功!");

            }

            // 清除该公司下面所有用户缓存
            List<User> list =  userDao.findUsersByUserTypeId(checkUser);
            for(User user : list){
                UserUtils.clearCache(user);
            }

        } catch (DealerSystemOprationOpenFailException e) {

            e.printStackTrace();

            throw e;

        } catch (DealerSystemOprationStopFailException e) {

            e.printStackTrace();

            throw e;

        } catch (DealerSystemOprationException e) {

            e.printStackTrace();

            throw e;

        } catch (Exception e) {

            e.printStackTrace();

            throw new DealerSystemOprationException(e.getMessage());
        }

        return condition;


    }


    public List<Area> findAncestors() {
        Area area = UserUtils.getUser().getCompanyInfo().getArea();
        List<Area> list = new ArrayList<Area>();
        list.add(area);
        //until reach to china
        while (!area.getId().equals("1")) {
            area = areaDao.get(area.getParentId());
            list.add(area);
        }


        return list;
    }
}
