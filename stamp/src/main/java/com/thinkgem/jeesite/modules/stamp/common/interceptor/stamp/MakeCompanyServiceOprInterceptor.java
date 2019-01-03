package com.thinkgem.jeesite.modules.stamp.common.interceptor.stamp;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CheckState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.dao.makeStampCompany.MakeCompanyLicenseDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * 判断制章公司是否具有进行业务操作的权限
 *
 * Created by Locker on 2017/5/19.
 */
public class MakeCompanyServiceOprInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MakeCompanyLicenseDao makeCompanyLicenseDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //取得当前用户
        User user = UserUtils.getUser();

        Company company = user.getCompanyInfo();

        //先判断 润城科技管理员权限
        if(company.getSysOprState() == OprationState.STOP){

            logger.debug("该公司处于管控状态!");
            response.sendRedirect(request.getContextPath() + Global.getAdminPath() + "/makeStampAction/licenseForm?workType=OPEN");
            return true;

        }

        //判断公司状态
        if((company.getCompState() != CompanyState.USING)){
            logger.debug("该公司年审未通过!");
            response.sendRedirect(request.getContextPath() +Global.getAdminPath() + "/makeStampAction/licenseForm?workType=OPEN");
            return true;
        }

        Licence licence = makeCompanyLicenseDao.getLicence(company.getId(), WorkType.OPEN);
        //许可证验证
        if(licence == null || licence.getCheckState() != CheckState.CHECKSUCCESS){
            logger.debug("该公司许可证审核未通过/没有申请开办许可!");
            response.sendRedirect(request.getContextPath() +Global.getAdminPath() + "/makeStampAction/licenseForm?workType=OPEN");
            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
