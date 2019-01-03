package com.thinkgem.jeesite.modules.police.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.police.PoliceDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.exception.policeUser.PoliceUserSaveException;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 公安管理员 相关服务类
 *
 * Created by Locker on 2017/7/15.
 */
@Service
public class PoliceUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PoliceDao policeDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private CompanyDao companyDao;

    /**
     * 公安用户管理员列表查询
     *
     * @param area
     * @param page
     * @return
     */
    public Page<User> findPoliceUserList(Area area, Page<User> page) {


        User user = new User();

        user.setUserType(UserType.POLICE);

        user.setPage(page);

        if (area.getId() == null || "".equals(area.getId())) {

            Police police = UserUtils.getUser().getPoliceInfo();

            Area currentArea = police.getArea();

            List<Area> subAreas = areaDao.findAreasByParentArea(currentArea);

            List<User> users = null;

            if (subAreas.size() == 0) {

                user.setUserTypeId(police.getId());

                users = userDao.findUsersByUserTypeId(user);
            } else {
                List<Police> polices = policeDao.findPoliceByAreas(subAreas, subAreas.size());

                users = userDao.findPoliceUser(user, polices, polices.size());

            }

            for (User user1 : users) {

                Police police1 = policeDao.get(user1.getUserTypeId());

                user1.setPoliceInfo(police1);

            }

            page.setList(users);

        } else {


            Police police = new Police();

            police.setArea(area);

            List<Police> polices = policeDao.findList(police);

            List<User> users = userDao.findPoliceUser(user, polices, polices.size());

            for (User user1 : users) {

                Police police1 = policeDao.get(user1.getUserTypeId());

                user1.setPoliceInfo(police1);

            }

            page.setList(users);
        }
        return page;
    }

    /**
     * 保存一个新的区域公安机关管理员用户
     *
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public Condition saveNewPoliceUser(User user) {

        Condition condition = new Condition();
        try {
            //公安机关管理员属性设置


            if(user.getId()==null || "".equals(user.getId())){
                Police police = user.getPoliceInfo();

                // 生成单位编码（行政区划+6位递增序号）
                String CodeCount = "000000" + String.valueOf(companyDao.getSequenceNextVal(police.getArea().getCode(),"4"));
                CodeCount = CodeCount.substring(CodeCount.length()-6);
                String policeCode = police.getArea().getCode() + CodeCount;
                police.setPoliceCode(policeCode);

                police.preInsert();

                policeDao.insert(police);

                user.setUserTypeId(police.getId());

                user.preInsert();



                userDao.insert(user);
            }else{

                user.preUpdate();

                userDao.update(user);

            }



            if (StringUtils.isNotBlank(user.getId())) {
                // 更新用户与角色关联
                userDao.deleteUserRole(user);
                if (user.getRoleList() != null && user.getRoleList().size() > 0) {
                    userDao.insertUserRole(user);
                } else {
//				throw new ServiceException(user.getLoginName() + "没有设置角色！");
                }
                // 清除用户缓存
                UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
            }

            condition.setCode(Condition.SUCCESS_CODE);

        } catch (Exception e) {

            condition.setCode(Condition.ERROR_CODE);

            e.printStackTrace();

            throw new PoliceUserSaveException(e.getMessage());

        } finally {

            return condition;
        }

    }


    /**
     * 获得某个公安的用户信息
     * @param user
     * @return
     */
    @Transactional(readOnly = true)
    public User getPoliceUser(User user){

        user.setUserType(UserType.POLICE);

        user = userDao.get(user);

        Police police = policeDao.get(new Police(user.getUserTypeId()));

        user.setPoliceInfo(police);

        return user;
    }

    /**
     *
     * 检索 公安管理员 的角色
     *
     * @return
     */
    public List<Role> findPoliceRoleList(){

        List<Role> list = null;

        Role role = new Role();

        role.setDeptType(Role.DEPTTYPE_POLIC_SYS);

        list = roleDao.findListByRoleType(role);

        return list;
    }

    /**
     * 检查当前区域是否已经存在一个公安机关
     *
     * @param police
     * @return true -- 存在一个
     * fasle -- 不存在 ，还没有
     */
    public boolean checkPoliceAreaOnlyOne(Police police) {

        System.out.println(policeDao.countPoliceArea(police));

        if (policeDao.countPoliceArea(police) == 0) {

            return false;
        }

        return true;
    }

    /**
     * 检查当前修改的公安用户其所属区域是否进行了改变
     * @param user
     * @return
     */
    public boolean checkAreaChanged(User user){

        User currentUser  = userDao.get(user);

        Police police = policeDao.get(user.getPoliceInfo());

        Area originalArea = police.getArea();

        Area currentArea = user.getPoliceInfo().getArea();

        if(originalArea.getId().equals(currentArea.getId())){

            return false;

        }else{

            return true;
        }

    }

}
