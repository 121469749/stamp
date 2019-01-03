package com.thinkgem.jeesite.modules.sys.security;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import java.io.Serializable;

public class Principal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // 编号
    private String loginName; // 登录名
    private String name; // 姓名
    private boolean mobileLogin; // 是否手机登录

    private UserType userType; //用户类型
//		private Map<String, Object> cacheMap;

    public Principal(User user, boolean mobileLogin) {
        this.id = user.getId();
        this.loginName = user.getLoginName();
        this.name = user.getName();
        this.mobileLogin = mobileLogin;
        this.userType =user.getUserType();
    }



    public String getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getName() {
        return name;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

    /**
     * 获取SESSIONID
     */
    public String getSessionid() {
        try {
            return (String) UserUtils.getSession().getId();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String toString() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}
