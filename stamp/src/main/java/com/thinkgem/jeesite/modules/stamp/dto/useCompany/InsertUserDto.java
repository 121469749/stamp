package com.thinkgem.jeesite.modules.stamp.dto.useCompany;

/**
 * Created by sjk on 2017-06-05.
 */
public class InsertUserDto {

    private String password;    //密码
    private String no;          //工号
    private String name;        //姓名
    private String phone;       //手机

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
