package com.thinkgem.jeesite.modules.stamp.common.entity;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.LoginStatus;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.LoginType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;

import java.util.Date;

/**
 * 登陆日志实体类
 */
public class LoginLog {

    private String id; //

    private String loginName; //登录名

    private String ip; //登陆ip

    private Date loginDate; //登陆时间

    private LoginStatus loginStatus;  //登陆成功与否

    private String reason; // 原因

    private UserType userType; //用户类型

    private String userId; // 用户id

    private LoginType loginType; //操作类型

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    protected Page<LoginLog> page;

    public LoginLog() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginNname) {
        this.loginName = loginNname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public Page<LoginLog> getPage() {
        return page;
    }

    public void setPage(Page<LoginLog> page) {
        this.page = page;
    }

}
