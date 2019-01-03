package com.thinkgem.jeesite.modules.stamp.service.dealer;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
@Service
public class ProvinceDealerUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private AreaDao areaDao;

    /**
     *
     * @param page
     * @param company
     * @return
     */
    public Page<Company> findPage(Page<Company> page, Company company) {

        Area currentArea = company.getArea();

        if(currentArea==null || "".equals(currentArea.getId())){

            currentArea = UserUtils.getUser().getCompanyInfo().getArea();

            List<Area> provinceSubAreas  = areaDao.getSubAreaByPareanId(currentArea.getId());

            company.setCompType(CompanyType.AGENCY);

            company.setPage(page);

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(provinceSubAreas));

            List<Company> companies = companyDao.findCompanyListByAreas(company);

            page.setList(companies);

        }else{

            company.setArea(currentArea);

            company.setCompType(CompanyType.AGENCY);

            company.setPage(page);

            List<Company> companies = companyDao.findList2(company);

            page.setList(companies);
        }

        return page;
    }

    /***
     *
     *
     *
     */
    public List<Role> getDealerRoles() {

        //获得当前用户
        User currentUser = UserUtils.getUser();

        List<Role> currentRoleList = currentUser.getRoleList();

        //检索经销商用户类型的权限
        //todo
        Role role = new Role();

        role.setDeptType("");

        role.setEnname("dealer-city");

        role.setUser(currentUser);

        List<Role> roleList = roleDao.findListByRoleType(role);

        for (Role role1 : currentRoleList) {

            roleList.remove(role1);

        }

        return roleList;
    }


    public User getAgencyUserByCompanyId(String companyId){

        if(companyId ==null ||"".equals(companyId)){

            return new User();

        }


        User  user = new User();

        user.setUserTypeId(companyId);

        user.setUserType(UserType.AGENY);

        user.setIsSysrole(Global.YES);

        user.setDelFlag("0");

        user = userDao.get(user);

        Company company = new Company(companyId,CompanyType.AGENCY);

        company = companyDao.get(company);

        user.setCompanyInfo(company);

        return user;

    }

}
