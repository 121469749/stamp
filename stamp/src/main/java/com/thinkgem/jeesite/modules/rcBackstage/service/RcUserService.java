package com.thinkgem.jeesite.modules.rcBackstage.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.police.PoliceDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Locker on 2017/7/27.
 */
@Service
public class RcUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private PoliceDao policeDao;

    /**
     *
     * 按照一定条件查询用户列表
     *
     * @param page
     * @param user
     * @return
     */
    public Page<User> findUsers(Page<User> page, User user) {


        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        //user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
        // 设置分页参数
        user.setPage(page);
        // 执行分页查询

        List<User> list = userDao.findUserByMore(user);

        if (user.getUserType() != UserType.ADMIN) {

            if(user.getUserType() != UserType.POLICE){
                //说明是公司
                for (User user1 : list) {

                    Company company = new Company();
                    company.setId(user1.getUserTypeId());
                    company.setCompType(user1.getUserType() == UserType.USE ?
                            CompanyType.USE : (user1.getUserType() == UserType.AGENY?CompanyType.AGENCY:CompanyType.MAKE));

                    company = companyDao.get(company);

                    user1.setCompanyInfo(company);

                }

            }else{
                //说明是公安机关
                for(User user1 : list){

                    Police police = new Police();

                    police.setId(user1.getUserTypeId());

                    police = policeDao.get(police);

                    user1.setPoliceInfo(police);

                }
            }
        }

        //查找更新者
        for(User user1 :list){
            for(UserType userType:UserType.values()){

                System.out.println(userType.getValue());

                User updateBy = user1.getUpdateBy();

                updateBy.setUserType(userType);

                updateBy = userDao.get(updateBy);

                if(updateBy!=null){

                    user1.setUpdateBy(updateBy);
                    break;
                }
            }
        }


        page.setList(list);

        return page;
    }

    /**
     * 更改用户登陆状态
     * @param user
     */
    @Transactional(readOnly = false)
    public void changeLoginFlag(User user){

        user.setUpdateDate(new Date());
        user.setUpdateBy(UserUtils.getUser());

        userDao.changeLoginFlag(user);

        user = userDao.get(user);
        // 清除用户缓存
        UserUtils.clearCache(user);
    }

    /**
     *
     * 润城后台重置用户密码
     *
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public Condition rcChangePassword(User user){

        if(userDao.changePassword(user)==1){
            user = userDao.get(user);
            // 清除用户缓存
            user.setLoginName(user.getLoginName());
            UserUtils.clearCache(user);
            return new Condition(Condition.SUCCESS_CODE);
        }

        return new Condition(Condition.ERROR_CODE);
    }

}
