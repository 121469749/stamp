package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjw-pc on 2017/6/16.
 */
@Controller
@RequestMapping(value = "${adminPath}/company/user")
public class CompanyUserController extends BaseController {
    @Autowired
    private SystemService systemService;


    @ModelAttribute
    public User get(@RequestParam(required = false) String id,UserType userType) {
        if (StringUtils.isNotBlank(id)) {

            return systemService.getUserByIdAndUserType(id,userType);

        } else {

            return new User();
        }
    }

    @RequiresPermissions("company:user:view")
    @RequestMapping(value = {"list", ""})
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {

        User currentUser = UserUtils.getUser();

        user.setUserType(currentUser.getUserType());

        //如果当前用户是公司/公安部门   进行用户信息数据过滤 区域过滤 office部门过滤 公司/公安部门类型过滤
        if(currentUser.getCompanyInfo() != null ) {

//            user.setOffice(currentUser.getOffice());
//            user.setCompany(currentUser.getCompany());
            user.setIsSysrole(Global.NO);
            user.setUserTypeId(currentUser.getUserTypeId());

        } else if(currentUser.getPoliceInfo() != null) {
//            user.setPoliceInfo(currentUser.getPoliceInfo());
//            user.setOffice(currentUser.getOffice());
            user.setIsSysrole(Global.NO);
            user.setUserTypeId(currentUser.getUserTypeId());

        //    user.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(currentUser.getPoliceInfo().getArea(), "a3"));
        }

        Page<User> page = systemService.findComUser(new Page<User>(request, response), user);

        model.addAttribute("page", page);
        return "modules/sys/companyUserList";
    }



    @RequiresPermissions("company:user:view")
    @RequestMapping(value = "/form")
    public String form(User user, Model model) {

        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }

        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(UserUtils.getUser().getOffice());
        }


        //如果当前用户是公司/公安部门  对角色进行数据过滤
        User currentUser = UserUtils.getUser();

        Role role = new Role();
        if(currentUser.getCompanyInfo().getId()!=null||"".equals(currentUser.getCompanyInfo().getId()))
        {

            if(currentUser.getCompanyInfo().getCompType() == CompanyType.MAKE){

                //刻章点用户角色类型
                role.setDeptType(Role.DEPTTYPE_MAKE_SUB);

            } else if(currentUser.getCompanyInfo().getCompType() == CompanyType.USE){

                //用章单位用户角色类型
                role.setDeptType(Role.DEPTTYPE_USE_SUB);
            }
//            else if(currentUser.getCompanyInfo().getCompType() == CompanyType.AGENCY){
//                //经销商用户角色类型
//                role.setDeptType(Role.);
//            }

        } else if(currentUser.getPoliceInfo().getId()!=null&&!("".equals(currentUser.getPoliceInfo().getId()))) {
            //公安子用户角色类型
            role.setDeptType(Role.DEPTTYPE_POLIC_SUB);


        }

        model.addAttribute("user", user);

        List<Role> roleList= systemService.findRoleByType(role);

        model.addAttribute("allRoles",roleList);

        return "modules/sys/companyUserForm";
    }

    /**
     *
     *
     * @param user
     * @param request
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("company:user:edit")
    @RequestMapping(value = "/save")
    public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        //默认为非管理员角色用户
        user.setIsSysrole(Global.NO);
        User currentUser = UserUtils.getUser();
        user.setCompany(currentUser.getCompany());
        //设置归属的公司
        user.setUserTypeId(currentUser.getUserTypeId());
        user.setUserType(currentUser.getUserType());
        //默认登陆标记
        user.setLoginFlag(Global.YES);
        if(currentUser.getCompanyInfo() != null ) {

            user.setCompanyInfo(currentUser.getCompanyInfo());
        }
        else if(currentUser.getPoliceInfo() != null) {

            user.setPoliceInfo(currentUser.getPoliceInfo());
        }

        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {

            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        if (!beanValidator(model, user)){

            return form(user, model);
        }
        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){

            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        List<String> roleIdList = user.getRoleIdList();
        for (Role r : systemService.findAllRole()){
            if (roleIdList.contains(r.getId())){
                roleList.add(r);
            }
        }
        user.setRoleList(roleList);

        // 保存用户信息
        systemService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
            UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }
        addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");

        return "redirect:" + adminPath + "/company/user/list?repage";
    }

    @RequiresPermissions("company:user:edit")
    @RequestMapping(value = "/delete")
    public String delete(User user, RedirectAttributes redirectAttributes) {

        if (UserUtils.getUser().getId().equals(user.getId())) {

            addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");

        } else if (User.isAdmin(user.getId())) {

            addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
        } else {

            systemService.deleteUser(user);
            addMessage(redirectAttributes, "删除用户成功");
        }

        return "redirect:" + adminPath + "/company/user/list?repage";
    }


    @RequiresPermissions("company:user:edit")
    @RequestMapping(value = "/changeLoginFlag")
    public String changeLoginFlag(User user, RedirectAttributes redirectAttributes) {

     systemService.changeLoginFlag(user);
        return "redirect:" + adminPath + "/company/user/list?repage";
    }
    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequiresPermissions("company:user:edit")
    @RequestMapping(value = "/checkLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {

        User user = UserUtils.getUser();

        if (loginName != null && loginName.equals(oldLoginName)) {

            return "true";
        } else if (loginName != null && systemService.getUserByLoginName(loginName,user.getUserType()) == null) {

            return "true";
        }
        return "false";
    }
}
