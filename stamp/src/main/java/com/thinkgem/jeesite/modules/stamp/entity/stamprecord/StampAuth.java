package com.thinkgem.jeesite.modules.stamp.entity.stamprecord;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampAuthState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation.Enumeration;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.Date;

/**
 * Created by sjk on 2017-05-20.
 */

/**
 * 印章授权信息Entity
 * @author sjk
 * @version 2017-05-20
 */
public class StampAuth extends DataEntity<StampAuth> {

    private Stamp stamp;    //印章信息
    private User user;      //被授权用户
    private Date startTime;    //起始时间
    private Date endTime;       //结束时间

    @Enumeration(enumClass = StampAuthState.class)
    private StampAuthState stampAuthState;      //印章授权状态

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public StampAuthState getStampAuthState() {
        return stampAuthState;
    }

    public void setStampAuthState(StampAuthState stampAuthState) {
        this.stampAuthState = stampAuthState;
    }
}
