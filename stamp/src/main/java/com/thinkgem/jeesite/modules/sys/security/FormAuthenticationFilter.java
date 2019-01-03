/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.LoginStatus;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.LoginType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.common.entity.LoginLog;
import com.thinkgem.jeesite.modules.stamp.dao.common.LoginLogDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.certificate.CertificateToken;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.utils.StringUtils;

import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * 表单验证（包含验证码）过滤类
 *
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    @Autowired
    private LoginLogDao loginLogDao;
    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
    public static final String DEFAULT_MESSAGE_PARAM = "message";
    public static final String DEFAULT_CHOSENROLE_PARAM = "chosenrole";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
    private String messageParam = DEFAULT_MESSAGE_PARAM;
    private String chosenParam = DEFAULT_CHOSENROLE_PARAM;


    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        if(request.isSecure() && getChosenRole(request) != null){
            final String name = "javax.servlet.request.X509Certificate";
            X509Certificate[] certificates = (X509Certificate[]) request.getAttribute(name);

            String userTypeKey = getChosenRole(request);
            UserType userType = UserType.getUserTypeByKey(userTypeKey);

            return new CertificateToken(userType,certificates[0]);
        }
        else {
            String username = getUsername(request);
            String password = getPassword(request);
            if (password == null) {
                password = "";
            }
            boolean rememberMe = isRememberMe(request);
            String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
            String captcha = getCaptcha(request);
            boolean mobile = isMobileLogin(request);
            //其他接口登录
            if (getChosenRole(request) != null) {

                String userTypeKey = getChosenRole(request);

                UserType userType = UserType.getUserTypeByKey(userTypeKey);

                return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile, userType);
            }
            return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
        }
    }

    /**
     * 获取登录用户名
     */
    protected String getUsername(ServletRequest request, ServletResponse response) {
        String username = super.getUsername(request);
        if (StringUtils.isBlank(username)) {
            username = StringUtils.toString(request.getAttribute(getUsernameParam()), StringUtils.EMPTY);
        }
        return username;
    }

    /**
     * 获取登录密码
     */
    @Override
    protected String getPassword(ServletRequest request) {
        String password = super.getPassword(request);
        if (StringUtils.isBlank(password)) {
            password = StringUtils.toString(request.getAttribute(getPasswordParam()), StringUtils.EMPTY);
        }
        return password;
    }

    /**
     * 获取记住我
     */
    @Override
    protected boolean isRememberMe(ServletRequest request) {
        String isRememberMe = WebUtils.getCleanParam(request, getRememberMeParam());
        if (StringUtils.isBlank(isRememberMe)) {
            isRememberMe = StringUtils.toString(request.getAttribute(getRememberMeParam()), StringUtils.EMPTY);
        }
        return StringUtils.toBoolean(isRememberMe);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    public String getMobileLoginParam() {
        return mobileLoginParam;
    }


    protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }

    public String getMessageParam() {
        return messageParam;
    }

    public String getChosenParam() {
        return chosenParam;
    }

    public String getChosenRole(ServletRequest request) {
        return WebUtils.getCleanParam(request, getChosenParam());
    }

    /**
     * 登录成功之后跳转URL
     */
    public String getSuccessUrl() {

        return super.getSuccessUrl();
    }

    @Override
    public void issueSuccessRedirect(ServletRequest request,
                                     ServletResponse response) throws Exception {
//		Principal p = UserUtils.getPrincipal();
//		if (p != null && !p.isMobileLogin()){

        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);

//		}else{
//			super.issueSuccessRedirect(request, response);
//		}


        //log
        LoginLog loginLog = new LoginLog();
        loginLog.setId(IdGen.uuid());
        loginLog.setIp(request.getLocalAddr());
        loginLog.setLoginDate(new Date());
        User user = UserUtils.getUser();
        loginLog.setLoginName(user.getLoginName());
        loginLog.setUserId(user.getId());
        loginLog.setUserType(user.getUserType());
        loginLog.setLoginStatus(LoginStatus.success);
        loginLog.setLoginType(LoginType.in);
        loginLogDao.insert(loginLog);
    }

    /**
     * 登录失败调用事件
     */
    @Override
    public boolean onLoginFailure(AuthenticationToken token,
                                  AuthenticationException e, ServletRequest request, ServletResponse response) {

        String className = e.getClass().getName(), message = "";
        if (AuthenticationException.class.getName().equals(className)) {
            message = "用户或密码错误, 请重试.";

        } else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")) {
            message = StringUtils.replace(e.getMessage(), "msg:", "");
        } else {
            message = "系统出现点问题，请稍后再试！";
            e.printStackTrace(); // 输出到控制台
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);

        //log
        LoginLog loginLog = new LoginLog();
        loginLog.setId(IdGen.uuid());
        loginLog.setIp(request.getLocalAddr());
        loginLog.setLoginDate(new Date());
        loginLog.setLoginStatus(LoginStatus.fail);
        loginLog.setLoginType(LoginType.in);
        loginLogDao.insert(loginLog);

        return true;
    }

}