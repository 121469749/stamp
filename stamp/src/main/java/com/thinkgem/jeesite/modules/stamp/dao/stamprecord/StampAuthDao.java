package com.thinkgem.jeesite.modules.stamp.dao.stamprecord;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampAuth;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 印章授权信息DAO接口
 * Created by sjk on 2017-05-20.
 */
@MyBatisDao
public interface StampAuthDao extends CrudDao<StampAuth> {

    /**
     * 更改印章使用授权
     * @param stampAuth
     * @return
     */
    public int changeAuthState(StampAuth stampAuth);

    /**
     * 判断表中是否存在该印章和用户的信息
     * @param stampAuth
     * @return
     */
    public int judgeExitStampAndUser(StampAuth stampAuth);

    /**
     * 取消印章的所有授权
     * @param stamp
     * @return
     */
    public int closeAllAuthByStamp(Stamp stamp);

    /**
     * 通过 印章和用户 查找单条信息
     * @param stamp
     * @param user
     * @return
     */
    public StampAuth getOneByStampAndUser(@Param(value = "stamp") Stamp stamp, @Param(value = "user") User user);

    /**
     * 查找印章的授权状况
     * @param stampAuth
     * @return
     */
    public List<StampAuth> findStampAuth(StampAuth stampAuth);

    /**
     * 查找针对某一印章未在授权表中的用户
     * @param stampAuth
     * @return
     */
    public List<User> findNoAuthUser(StampAuth stampAuth);

    /**
     * 查询员工可使用的印章
     */
    public List<StampAuth> findUsefulByUser(StampAuth stampAuth);

    /**
     * 查询印章的使用情况
     */
    public StampAuth findUsingByStamp(StampAuth stampAuth);
}
