package com.thinkgem.jeesite.modules.sys.security.certificate;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.Principal;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * Shiro相关文件
 * 证书登陆的Realm
 */
public class CertificateRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(CertificateRealm.class);

    private SystemService systemService;

    @Autowired
    private CompanyDao companyDao;

    @Override
    public boolean supports(AuthenticationToken token) {
        // 使用证书进行登陆才会访问下面的方法
        return token != null && token instanceof CertificateToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) getAvailablePrincipal(principals);
        // 获取当前已登录的用户
        if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))) {

            Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());

            if (sessions.size() > 0) {
                // 如果是登录进来的，则踢出已在线用户
                if (UserUtils.getSubject().isAuthenticated()) {
                    for (Session session : sessions) {
                        getSystemService().getSessionDao().delete(session);
                    }
                }
                // 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
                else {
                    UserUtils.getSubject().logout();
                    throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
                }
            }
        }

        //System.out.println("principal login Name:"+principal.getLoginName());

        User user = getSystemService().getUserByLoginNameAndUserType(principal.getLoginName(),principal.getUserType());
        if (user != null) {

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            List<Menu> list = UserUtils.getMenuList();

            for (Menu menu : list) {
                if (StringUtils.isNotBlank(menu.getPermission())) {
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(menu.getPermission(), ",")) {
                        info.addStringPermission(permission);
                    }
                }
            }
            // 添加用户权限
            info.addStringPermission("user");
            // 添加用户角色信息
            for (Role role : user.getRoleList()) {
                info.addRole(role.getEnname());
            }
            //处理区公安权限 公安编辑权限在这里分配
            if (user.getUserType() == UserType.POLICE) {
                Police police = user.getPoliceInfo();
                if (user.getPoliceInfo() == null) {
                    police = UserUtils.getByIdAndUserType(user.getId(),UserType.POLICE).getPoliceInfo();
                }
                if (police.getArea().getType().equals("4")) {
                    info.addStringPermission("police:edit");
                }
            }

            //用章公司编辑权限分配
            if (user.getUserType() == UserType.USE && user.getIsSysrole().equals("1")) {
                Company company = companyDao.get(new Company(user.getUserTypeId(), CompanyType.USE));
                if (company.getCompType() == CompanyType.USE) {
                    info.addStringPermission("useCompany:edit");
                }
            }

            // 更新登录IP和时间
            getSystemService().updateUserLoginInfo(user);
            // 记录登录日志
            LogUtils.saveLog(Servlets.getRequest(), "系统登录");
            return info;
        } else {
            return null;
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CertificateToken token = (CertificateToken) authenticationToken;

        // 校验用户名密码
        User user = getSystemService().getUserByLoginNameAndUserType(token.getId(),token.getChosenRole());

        if (user != null) {

            if (Global.NO.equals(user.getLoginFlag())) {

                throw new AuthenticationException("msg:该帐号已禁止登录.");
            }
            //其他接口登录选择身份验证
            if (token.getChosenRole() != null) {
                Boolean isFind = false;
                try {
                    //获取身份
                    user = getSystemService().getUserByIdAndUserType(user.getId(),user.getUserType());

                    //判断是否公安部门
                    if (user.getUserType() == UserType.POLICE) {

                        if (token.getChosenRole()==user.getUserType()) {

                            isFind = true;
                        }
                        //后台管理员登录
                    } else if (token.getChosenRole()==UserType.ADMIN) {

                        isFind = true;

                    }else if(token.getChosenRole()==UserType.AGENY){

                        isFind =true;

                        //否则是经销商或用章单位或制章单位
                    } else {
                        Company company = user.getCompanyInfo();

                        if (company.getId()==null || "".equals(company.getId())) {
                            throw new AuthenticationException("msg:账号或密码错误.");
                        }

                        //类型正确之后  判断该公司是否已经被注销
                        if (company.getCompState() == CompanyState.STOP
                                || company.getCompState() == CompanyState.LOGOUT) {


                            throw new AuthenticationException("msg:该公司已经被注销或者停用了!");

                        } else {

                            //公司并非处于注销或者停用状态的话 根据企业状态来开启验证
                            if (company.getSysOprState() != OprationState.OPEN) {

                                throw new AuthenticationException("msg:该公司被系统管控中!");

                            } else {
                                isFind = true;
                            }
                        }

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    throw new AuthenticationException(e.getMessage());

                }

                //没找到
                if (!isFind) {
                    throw new AuthenticationException("msg:账号或密码错误.");
                }
            }

            return new SimpleAuthenticationInfo(new Principal(user,false),
                    user.getCertModulus(), getName());
        } else {

            return null;
        }
    }

    /**
     * 获取权限授权信息，如果缓存中存在，则直接从缓存中获取，否则就重新获取， 登录成功后调用
     */
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        }

        AuthorizationInfo info = null;

        info = (AuthorizationInfo) UserUtils.getCache(UserUtils.CACHE_AUTH_INFO);

        if (info == null) {
            info = doGetAuthorizationInfo(principals);
            if (info != null) {
                UserUtils.putCache(UserUtils.CACHE_AUTH_INFO, info);
            }
        }

        return info;
    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        authorizationValidate(permission);
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        authorizationValidate(permission);
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermittedAll(permissions, info);
    }

    /**
     * 授权验证方法
     *
     * @param permission
     */
    private void authorizationValidate(Permission permission) {
        // 模块授权预留接口
    }

    /**
     * 获取系统业务对象
     */
    public SystemService getSystemService() {
        if (systemService == null) {
            systemService = SpringContextHolder.getBean(SystemService.class);
        }
        return systemService;
    }
}
