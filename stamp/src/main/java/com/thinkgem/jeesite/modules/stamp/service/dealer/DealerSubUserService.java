package com.thinkgem.jeesite.modules.stamp.service.dealer;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Locker on 2017/10/2.
 */
@Service
@Transactional(readOnly = true)
public class DealerSubUserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 当前单位子用户 列表
     * @param page
     * @param user
     * @return
     */
    public Page<User> findPage(Page<User> page, User user){

        User currentUser = UserUtils.getUser();

        user.setPage(page);

        //设置默认查询条件---start
        user.setUserType(UserType.AGENY);

        user.setIsSysrole(Global.NO);

        user.setUserTypeId(currentUser.getUserTypeId());

        //end

        List<User> userList = userDao.findList(user);

        page.setList(userList);

        return page;
    }


    public List<Role> getSubDealerRoles(String deptType){

        Role role = new Role();

        role.setDeptType(deptType);

        List<Role> roleList = roleDao.findListByRoleType(role);

        return roleList;
    }


}
