package com.thinkgem.jeesite.modules.stamp.common.interceptor.license;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CheckState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.dao.makeStampCompany.MakeCompanyLicenseDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.MakeCompanyLicenseService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对制章点点公司进行
 * 最新记录的检测
 * 如果有其他操作在进行
 * 则false-不可以进入
 * 否则true-可以进入
 * 当前用户所在公司不存在许可证时跳转到许可申请
 * 对申请中的审核跳转到所在审核页面
 * 已审核的申请跳转到结果显示页面
 * Created by Locker on 2017/5/19.
 */
public class MakeCompanyLastRecordInterceptor implements HandlerInterceptor {

    @Autowired
    private MakeCompanyLicenseDao makeCompanyLicenseDao;
    @Autowired
    private MakeCompanyLicenseService makeCompanyLicenseService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String workTypeStr = request.getParameter("workType");
        WorkType workType = Enum.valueOf(WorkType.class, workTypeStr);

        System.out.println(request.getRequestURI());
        System.out.println(request.getContextPath());
        System.out.println(request.getServletPath());

//        for (Cookie cookie : request.getCookies()) {
//            System.out.println(cookie.getName()+":"+cookie.getValue());
//            response.addCookie(cookie);
//        }
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();


        Licence newestLicence = null;


        newestLicence = makeCompanyLicenseService.checkNewestLicence(company);

        //不存在license拦截跳转到许可开办申请页面
        if (newestLicence == null && workType != WorkType.OPEN) {
           response.sendRedirect(request.getContextPath()+Global.getAdminPath() + "/makeStampAction/licenseForm?workType=OPEN");
            //request.getRequestDispatcher(Global.getAdminPath() + "/makeStampAction/licenseForm?workType=OPEN").forward(request,response);
            return true;
        } else if (newestLicence == null && workType == WorkType.OPEN) {
            return true;
        }

        //开办审核未通过
        Licence openLicense = makeCompanyLicenseDao.getLicence(company.getId(), WorkType.OPEN);
        if (openLicense.getCheckState() == CheckState.CHECKFAIL && workType != WorkType.OPEN) {
            response.sendRedirect(request.getContextPath()+response.encodeRedirectURL(Global.getAdminPath() + "/makeStampAction/licenseForm?workType=OPEN"));
           // request.getRequestDispatcher(Global.getAdminPath() + "/makeStampAction/licenseForm?workType=OPEN").forward(request,response);
            return true;
        } else if (openLicense.getCheckState() == CheckState.CHECKFAIL && workType == WorkType.OPEN) {
            return true;
        }
        //存在审核中的申请拦截跳转到审核界面，例外情况：开办申请
        if (newestLicence.getCheckState() == CheckState.CHECKING) {
            //开办申请审核中
            if (newestLicence.getWorkType() == WorkType.OPEN) {
                response.sendRedirect(request.getContextPath()+response.encodeRedirectURL(Global.getAdminPath() + "/makeStampPage/openLicenseNoExam"));
                //request.getRequestDispatcher(Global.getAdminPath() + "/makeStampPage/openLicenseNoExam").forward(request,response);

                return true;
            } else {
                //其他三种申请类型统一跳转到审核中的界面
                //获取该license的worktype
                WorkType workType1 = newestLicence.getWorkType();
                if (workType1 == WorkType.ANNUAL) {
                    response.sendRedirect(request.getContextPath()+response.encodeRedirectURL(Global.getAdminPath() + "/makeStampPage/annualNoExam"));
                    //request.getRequestDispatcher(Global.getAdminPath() + "/makeStampPage/annualNoExam").forward(request,response);


                } else if (workType1 == WorkType.CHANGE) {
                    response.sendRedirect(request.getContextPath()+response.encodeRedirectURL(Global.getAdminPath() + "/makeStampPage/changeNoExam"));
                    //request.getRequestDispatcher(Global.getAdminPath() + "/makeStampPage/changeNoExam").forward(request,response);
                } else if (workType1 == WorkType.LOGOUT) {
                    response.sendRedirect(request.getContextPath()+response.encodeRedirectURL(Global.getAdminPath() + "/makeStampPage/cancelNoExam"));
                    //request.getRequestDispatcher(Global.getAdminPath() + "/makeStampPage/cancelNoExam").forward(request,response);
                }

                return true;
            }
        } else {
            //跳转到相应的申请界面附带上次结果（若存在）
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

//        System.out.println(ex.getMessage());


    }

}
