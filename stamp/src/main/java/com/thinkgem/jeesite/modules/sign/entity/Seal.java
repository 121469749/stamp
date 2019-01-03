package com.thinkgem.jeesite.modules.sign.entity;


import com.thinkgem.jeesite.modules.sign.common.persistence.PageEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * Created by Administrator on 2017/9/13.
 */
public class Seal extends PageEntity<Seal> {

    private String sealName; //印章名称

    private String sealPath; //印模图路径

    private int allowUse; //可以使用次数

    private int alreadyUse; //已经使用次数

    private User user; //所属者

    public Seal() {
    }

    public Seal(String id) {
        super(id);
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getSealPath() {
        return sealPath;
    }

    public void setSealPath(String sealPath) {
        this.sealPath = sealPath;
    }

    public int getAllowUse() {
        return allowUse;
    }

    public void setAllowUse(int allowUse) {
        this.allowUse = allowUse;
    }

    public int getAlreadyUse() {
        return alreadyUse;
    }

    public void setAlreadyUse(int alreadyUse) {
        this.alreadyUse = alreadyUse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
