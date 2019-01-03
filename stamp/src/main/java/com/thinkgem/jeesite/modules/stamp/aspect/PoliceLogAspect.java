package com.thinkgem.jeesite.modules.stamp.aspect;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.licence.LicenceDao;
import com.thinkgem.jeesite.modules.stamp.dao.police.PoliceLogDao;
import com.thinkgem.jeesite.modules.stamp.dto.police.MakeComDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.police.PoliceLog;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by hjw-pc on 2017/6/22.
 */
@Component
@Aspect
public class PoliceLogAspect {

    @Autowired
    private PoliceLogDao policeLogDao;
    @Autowired
    private LicenceDao licenceDao;
    @Autowired
    private CompanyDao companyDao;
    /*
     *@author hjw
     *@description 记录更新公司状态操作
     *@param [joinPoint]
     *@return void
     *@date 2017/6/22
     */
    @After("execution(*  com.thinkgem.jeesite.modules.stamp.service.police.PoliceCompanyService.updateComState(..))")
    public void logAfterUpdateComState(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();

        for(Object arg : args){
            if(arg.getClass() == Company.class){
                Company company = (Company)arg;
                if (company.getCompanyName() == "" || company.getCompanyName() == null) {
                    company = companyDao.get(company.getId());
                }
                PoliceLog policeLog = new PoliceLog();
                policeLog.setContent(company.getCompState().getValue() + company.getCompanyName());
                bindPoliceLog(company,policeLog);

            }
        }
    }

    /*
     *@author hjw
     *@description 记录更新公司状态操作
     *@param [joinPoint]
     *@return void
     *@date 2017/6/22
     */
    @After("execution(*  com.thinkgem.jeesite.modules.stamp.service.police.PoliceCompanyService.saveMakeCom(..))")
    public void logAfterSaveCom(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(arg.getClass() == MakeComDTO.class){
                MakeComDTO makeComDTO = (MakeComDTO)arg;
                PoliceLog policeLog = new PoliceLog();
                policeLog.setContent("录入" + makeComDTO.getCompany().getCompanyName());
                bindPoliceLog(makeComDTO.getCompany(),policeLog);
            }
        }
    }

    /*
     *@author hjw
     *@description 记录更新公司状态操作
     *@param [joinPoint]
     *@return void
     *@date 2017/6/22
     */
    @After("execution(*  com.thinkgem.jeesite.modules.stamp.service.police.PoliceCompanyService.deleteMakeCom(..))")
    public void logAfterDeleteMakeCom(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(arg.getClass() == Company.class){
                Company company = (Company)arg;
                if (company.getCompanyName() == "" || company.getCompanyName() == null) {
                    company = companyDao.get(company.getId());
                }
                PoliceLog policeLog = new PoliceLog();
                policeLog.setContent("删除" + company.getCompanyName());
                bindPoliceLog(company,policeLog);
            }
        }
    }



    /*
     *@author hjw
     *@description 记录更新公司状态操作
     *@param [joinPoint]
     *@return void
     *@date 2017/6/22
     */
    @After("execution(*  com.thinkgem.jeesite.modules.stamp.service.police.PoliceLicenseService.deleteMakeCom(..))")
    public void logAfterDeleteLicense(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(arg.getClass() == Company.class){
                Company company = (Company)arg;
                if (company.getCompanyName() == "" || company.getCompanyName() == null) {
                    company = companyDao.get(company.getId());
                }
                PoliceLog policeLog = new PoliceLog();
                policeLog.setContent("删除" + company.getCompanyName()+"所有许可（年审）申请信息");
                bindPoliceLog(company,policeLog);
            }
        }
    }

    /*
     *@author hjw
     *@description 记录更新公司状态操作
     *@param [joinPoint]
     *@return void
     *@date 2017/6/22
     */
    @After("execution(*  com.thinkgem.jeesite.modules.stamp.service.police.PoliceLicenseService.updateCheckState(..))")
    public void logAfterUpdateCheckState(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(arg.getClass() == Licence.class){
                Licence l = (Licence)arg;
                Licence licence = licenceDao.get(l);
                PoliceLog policeLog = new PoliceLog();
                policeLog.setContent(licence.getCheckState().getValue()+ licence.getCompName());
                bindPoliceLogByLicense(licence,policeLog);
            }
        }
    }
    public void bindPoliceLog(Company company, PoliceLog policeLog){
        //获取当前操作用户信息
        User user = UserUtils.getUser();
        Police police = user.getPoliceInfo();
        Area area = police.getArea();
        policeLog.setArea(area);
        policeLog.setId(IdGen.uuid());
        policeLog.setTitle(company.getCompanyName());

        policeLog.setOperator(user);
        policeLog.setCreateDate(new Date());
        policeLogDao.insert(policeLog);
    }
    public void bindPoliceLogByLicense(Licence licence, PoliceLog policeLog){
        //获取当前操作用户信息
        User user = UserUtils.getUser();
        Police police = user.getPoliceInfo();
        Area area = police.getArea();
        policeLog.setArea(area);
        policeLog.setId(IdGen.uuid());
        policeLog.setId(IdGen.uuid());
        policeLog.setTitle(licence.getCompName());

        policeLog.setOperator(user);
        policeLog.setCreateDate(new Date());
        policeLogDao.insert(policeLog);
    }


}
