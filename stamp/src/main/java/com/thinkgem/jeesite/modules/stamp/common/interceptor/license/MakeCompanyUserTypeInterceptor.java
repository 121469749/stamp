package com.thinkgem.jeesite.modules.stamp.common.interceptor.license;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 制章点公司
 * 权限校验器
 * 是否为刻章点
 * Created by Locker on 2017/5/19.
 */
public class MakeCompanyUserTypeInterceptor implements HandlerInterceptor {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获得当前用户
        User user = UserUtils.getUser();

        if ((user.getUserType() == UserType.MAKE)) {

            if ((user.getCompanyInfo().getCompType() == CompanyType.MAKE)) {

                logger.debug("通过校验!");

                return true;
            }

            logger.debug("不是制章点用户！");

            return false;
        }

        logger.debug("用户类型不对!");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

//        logger.debug("postHandle Null!");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        logger.debug(ex.getMessage());
//        logger.debug("MakeStampCompany异常");
    }
}
