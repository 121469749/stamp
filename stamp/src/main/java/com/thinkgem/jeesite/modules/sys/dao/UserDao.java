/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户DAO接口
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {

    /**
     * 根据登录名称查询用户
     *
     * @param user
     * @return
     */
    public User getByLoginName(User user);

    /**
     * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
     *
     * @param user
     * @return
     */
    public List<User> findUserByOfficeId(User user);

    /**
     * 查询全部用户数目
     *
     * @return
     */
    public long findAllCount(User user);

    /**
     * 更新用户密码
     *
     * @param user
     * @return
     */
    public int updatePasswordById(User user);


    /**
     *
     * 重置用户密码
     *
     * @param user
     * @return
     */
    public int changePassword(User user);

    /**
     * 更新登录信息，如：登录IP、登录时间
     *
     * @param user
     * @return
     */
    public int updateLoginInfo(User user);

    /**
     * 删除用户角色关联数据
     *
     * @param user
     * @return
     */
    public int deleteUserRole(User user);

    /**
     * 插入用户角色关联数据
     *
     * @param user
     * @return
     */
    public int insertUserRole(User user);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public int updateUserInfo(User user);

    /**
     * 指定 user信息 绑定userTypeId
     *
     * @param user
     * @return
     */
    public int updateUseTypeIdAndComp(User user);

    /**
     * 通过CompanyId获取公司子用户列表
     *
     * @param companyId
     * @return
     */
//    public List<User> findUserByUserTypeId(@Param(value = "companyId") String companyId);


    /**
     * 检查 loginName 是否唯一
     *
     * @param loginName
     * @return
     */
    public int checkLoginName(@Param(value = "loginName") String loginName, @Param(value = "userType") UserType userType);

    /**
     * @author 许彩开
     * @TODO (注：查找该手机号码已经备案了多少个公司)
      * @param loginName
     * @param userType
     * @DATE: 2017\12\20 0020 8:52
     */

    public int checkLoginNameNumber(@Param(value = "loginName") String loginName, @Param(value = "userType") UserType userType);

    /**
     * 查找公司下的全部员工
     *
     * @param user
     * @return
     */
    public List<User> findAllUserInCompany(User user);

//	/**
//	 * 检查员工工号是否已存在
//	 * @param no
//	 * @param companyId
//	 * @return
//	 */
//	public int checkNo(@Param(value = "no") String no, @Param(value = "companyId") String companyId);

    /*
     *@author hjw
     *@description 更改loginflag根据usertypeid
     *@param [userTypeId, loginFlag]
     *@return int
     *@date 2017/6/23
     */
    public int updateLoginFlagByUserTypeId(@Param(value = "userTypeId") String userTypeId,
                                           @Param(value = "loginFlag") String loginFlag,
                                           @Param(value = "userType") UserType userType,
                                           @Param(value="updateBy")User user,
                                           @Param(value="updateDate")Date date);


    /*
     *@author hjw
     *@description 删除用户根据usertypeid
     *@param [userTypeId, loginFlag]
     *@return int
     *@date 2017/6/23
     */
    public int deleteByUserTypeId(User user);

    /**
     * 通过UserTypeId(获取公司用户列表
     *
     * @return
     */
    public List<User> findUsersByUserTypeId(User user);

    /**
     * 主要
     * 计算该公司下的经销商在启用用户有多少个
     *
     * @param user
     * @return
     */
    public int countUserByUserTypeId(User user);

    /**
     * 系统管控状态
     *
     * @return
     */
    //public int systemOprationLoginFlag(@Param(value="userTypeId")String userTypeId,@Param(value="loginFlag")String loginFlag);
    public int systemOprationLoginFlag(User user);


    public User getUseComapnySysUser(@Param(value = "userTypeId") String userTypeId, @Param(value = "userType") UserType userType);

    public List<User> findPoliceUser(@Param(value="user") User user,@Param(value="list")List<Police> polices,@Param(value="size")int size);

//    public List<User> findPoliceSysUser(@Param(value = "user") User user,
//                                        @Param("list") List<Police> polices,
//                                        @Param("size") int size);

    public List<User> findUserByUserTypeIds(@Param(value = "user") User user,
                                            @Param(value = "company") List<Company> companies);

    public int changeLoginFlag(User user);

    /**
     * 通过更多的条件去搜索List<User>
     * @param user
     * @return
     */
    public List<User> findUserByMore(User user);

    int updateCert(User user);

    /**
     * 根据名字和企业ID查找用户
     * @param user
     * @return
     */
    public User findUserByNameAndComPanyId(User user);

}
