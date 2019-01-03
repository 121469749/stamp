package com.thinkgem.jeesite.modules.sys.security;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.EhCacheUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.LoginStatus;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.LoginType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.common.entity.LoginLog;
import com.thinkgem.jeesite.modules.stamp.dao.common.LoginLogDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.xss.XSSRequestWrapper;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thoughtworks.xstream.converters.basic.URIConverter;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.thinkgem.jeesite.modules.sys.utils.LogUtils.CACHE_MENU_NAME_PATH_MAP;

/**
 * Created by hjw-pc on 2017/7/27.
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

    private static final String ADMIN_PATH = Global.getAdminPath();

    @Autowired
    private LoginLogDao loginLogDao;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);

        User user = UserUtils.getUser();
        UserType type = user.getUserType();

        String redirectUrl;
        if(type == UserType.USE){
            redirectUrl = "/usecomlogin";
        }
        else if(type == UserType.MAKE){
            redirectUrl = "/makecomlogin";
        }
        else if(type == UserType.POLICE){
            redirectUrl = "/policelogin";
        }
        else if(type == UserType.AGENY){
            redirectUrl = "/agenylogin";
        }
        else if(type == UserType.ADMIN){
            redirectUrl = ADMIN_PATH + "/login";
        }
        else{
            redirectUrl = this.getRedirectUrl(request, response, subject);
        }

        try {
            //log
            LoginLog loginLog = new LoginLog();
            loginLog.setId(IdGen.uuid());
            loginLog.setIp(request.getLocalAddr());
            loginLog.setLoginDate(new Date());
            loginLog.setLoginName(user.getLoginName());
            loginLog.setUserId(user.getId());
            loginLog.setUserType(user.getUserType());
            loginLog.setLoginStatus(LoginStatus.success);
            loginLog.setLoginType(LoginType.out);
            loginLogDao.insert(loginLog);

            subject.logout();
            // 清理Cookie与HttpSession
//            HttpServletRequest request2 = (HttpServletRequest) request;
//            HttpServletResponse response2 = (HttpServletResponse) response;

            CookieUtils.clearCookie((HttpServletRequest) request,(HttpServletResponse) response);
            ((HttpServletRequest) request).getSession().invalidate();

        } catch (SessionException var6) {
//            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
        }
        super.issueRedirect(request, response, redirectUrl);
        return false;
    }
}
