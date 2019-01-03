package com.thinkgem.jeesite.modules.stamp.vo.useCompany;

import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.List;

/**
 * Created by sjk on 2017-06-01.
 */
public class UserAndStampVo {

    private List<User> userList;    //用户
    private List<Stamp> stampList;  //印章

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Stamp> getStampList() {
        return stampList;
    }

    public void setStampList(List<Stamp> stampList) {
        this.stampList = stampList;
    }
}
