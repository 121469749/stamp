package com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity;


import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;

/**
 * Created by Administrator on 2017/5/14.
 */
public class TestEntity {

    private String name;

    private UserType userType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "name='" + name + '\'' +
                ", userType=" + userType +
                '}';
    }
}
