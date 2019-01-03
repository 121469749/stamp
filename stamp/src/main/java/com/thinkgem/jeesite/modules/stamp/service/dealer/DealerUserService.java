package com.thinkgem.jeesite.modules.stamp.service.dealer;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 经销商服务类
 * <p>
 * Created by Locker on 2017/7/3.
 */
@Service
public class DealerUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private OfficeDao officeDao;

    /**
     * 当前用户搜索只针对经销商用户，并且属于润城科技的经销👆下。
     *
     * @param page
     * @param user
     * @return
     */
    public Page<User> findPage(Page<User> page, User user) {

        User currentUser = UserUtils.getUser();

        user.setPage(page);

        //设置默认查询条件---start
        user.setUserType(UserType.AGENY);

        user.setIsSysrole(Global.YES);

        user.setOffice(null);

        user.setCompany(null);

        //end
//        System.out.println(user);

        List<User> userList = userDao.findList(user);

        //每个用户设置对应相关的公司信息---start
        for (User user1 : userList) {

            Company company = companyDao.get(new Company(user1.getUserTypeId(),CompanyType.AGENCY));

            user1.setCompanyInfo(company);

        }
        //end

        page.setList(userList);

        return page;
    }

    /***
     *
     * 过滤当前经销商权限，获得经销商角色信息
     *
     */
    public List<Role> getDealerRoles( ) {

        //获得当前用户
        User currentUser = UserUtils.getUser();

        List<Role> currentRoleList = currentUser.getRoleList();

        //检索经销商用户类型的权限
        Role role = new Role();
        List<Role> roleList = new ArrayList<Role>();
        role.setDeptType(Role.DEPTTYPE_DEALER_PRO);
        List<Role> roleList1 = roleDao.findListByRoleType(role);
        role.setDeptType(Role.DEPTTYPE_DEALER_CITY);
        List<Role> roleList2 = roleDao.findListByRoleType(role);

        roleList.addAll(roleList1);
        roleList.addAll(roleList2);


        return roleList;
    }


    /**
     * 经销商用户新增或更新
     *
     * @param user
     */
    public void saveDealer(User user) {

        //如果是新用户
        if (user.getId() == null || user.getId().length() == 0) {

            User currentUser = UserUtils.getUser();

            Company newCompany = user.getCompanyInfo();
            newCompany.setCompType(CompanyType.AGENCY);
            newCompany.setCompState(CompanyState.USING);
            // 生成单位编码（行政区划+6位递增序号）
            String CodeCount = "000000" + String.valueOf(companyDao.getSequenceNextVal(newCompany.getArea().getCode(),CompanyType.AGENCY.getKey()));
            CodeCount = CodeCount.substring(CodeCount.length()-6);
            String companyCode = newCompany.getArea().getCode() + CodeCount;
            newCompany.setCompanyCode(companyCode);

            //创建组织结构
            Office office = new Office();

            office.setName(newCompany.getCompanyName());
            office.setMaster(newCompany.getLegalName());
            office.setAddress(newCompany.getCompAddress());
            office.setPhone(newCompany.getCompPhone());
            office.setGrade("1");
            office.setType("1");
            office.setUseable("1");
            office.setArea(newCompany.getArea());

            Office parent = new Office();
            parent.setId("0");
            office.setParent(parent);
            office.setParentIds("0,");
            office.preInsert();

            //保存组织结构
            officeDao.insert(office);

//            newCompany.setArea(user.getCompanyInfo().getArea());

            newCompany.preInsert();

            user.setOffice(office);

            user.setCompany(newCompany);

            user.setIsSysrole(Global.YES);

            user.setUserTypeId(newCompany.getId());

            user.preInsert();

            companyDao.insert(newCompany);

            userDao.insert(user);

        } else {

            Company agencyCompany = user.getCompanyInfo();
           // agencyCompany = companyDao.getCompanyById(agencyCompany);
            //agencyCompany.setCompType(CompanyType.AGENCY);
            agencyCompany.setCompState(CompanyState.USING);
            agencyCompany.preUpdate();
            companyDao.update(agencyCompany);

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
            // 将当前用户同步到Activiti
        //    systemService.useSaveActivitiUser(user);
            // 清除用户缓存
            UserUtils.clearCache(user);
        }
    }

    /**
     * 检查一个地区是否只有一个经销商用户
     *
     * @param area
     * @return
     *         fasle 该区域已经存在一个用户
     *         true  该区域不存在用户
     */
    public boolean checkAreaOnlyUser(Area area){

        Company company = new Company();

        company.setArea(area);

        company.setSysOprState(OprationState.OPEN);

        company.setCompType(CompanyType.AGENCY);

        int result = companyDao.countAgencyByArea(company);

        if(result == 0 ){

            return true;
        }


        company = companyDao.get(company);

        String companyId = company.getId();


        User user = new User();

        user.setUserTypeId(companyId);

        user.setUserType(UserType.ADMIN);

//        user.setLoginFlag(Global.YES);

        user.setIsSysrole(Global.NO);

        result = userDao.countUserByUserTypeId(user);

        if(result ==0){

            return true;

        }else{

            return false;
        }

    }

    /**
     * 更新用户
     * 检查区域是否有改动
     *
     * @param user
     * @return
     *     true 有改动区域
     *     false 没有改动区域
     */
    public boolean checkAreaChanged(User user){

        User currentUser  = userDao.get(user);

        Company currentCompany = companyDao.get(new Company(currentUser.getUserTypeId(),CompanyType.AGENCY));

        Area originalArea = currentCompany.getArea();

        Area currentArea = user.getCompanyInfo().getArea();

        if(originalArea.getId().equals(currentArea.getId())){

            return false;

        }else{

            return true;
        }

    }


    public void deleteDealerUser(User user){

        userDao.delete(user);
        // 同步到Activiti
    //    systemService.useDeleteActivitiUser(user);
        // 清除用户缓存
        UserUtils.clearCache(user);

    }

}
