package com.thinkgem.jeesite.modules.rcBackstage.web;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.police.service.PoliceUserService;
import com.thinkgem.jeesite.modules.rcBackstage.service.RcUserService;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.thinkgem.jeesite.modules.sys.service.SystemService.entryptPassword;

/**
 *
 * 润城后台用户管理
 *
 * Created by Locker on 2017/7/27.
 */
@Controller
@RequestMapping(value="${adminPath}/rc/baskstage/user")
public class RcUserController extends BaseController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private RcUserService rcUserService;

    @Autowired
    private PoliceUserService policeUserService;

    /**
     * 查看用户列表信息
     * @param user
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/list")
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model){

        if(user.getUserType() == null){
            user.setUserType(UserType.ADMIN);
        }

        Page<User> page = rcUserService.findUsers(new Page<User>(request, response), user);

        model.addAttribute("page", page);

        model.addAttribute("checkUser",user);

        model.addAttribute("loginUser",UserUtils.getUser());

        return "modules/jsps/rcBaskstage/userList";
    }

    /**
     *
     * 查看或添加 公安用户页面
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value="/police/form")
    public String policeForm(User user,Model model){

        System.out.println(user.getId()!=null||"".equals(user.getId()));
        if(user.getId()!=null||"".equals(user.getId())){
            user = policeUserService.getPoliceUser(user);
        }

        model.addAttribute("user",user);

        //公安管理员角色类型
        Role role = new Role();
        role.setDeptType("5");
        List<Role> policeRoles= systemService.findRoleByType(role);

        model.addAttribute("allRoles",policeRoles);

        return "modules/jsps/rcBaskstage/policeUserForm";
    }

    /**
     *
     * 保存公安用户信息
     *
     * @param user
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="/police/save")
    public String savePoliceUser(User user,Model model,RedirectAttributes redirectAttributes){

        Police checkPolice = user.getPoliceInfo();

        if(policeUserService.checkPoliceAreaOnlyOne(checkPolice)){

            addMessage(redirectAttributes,"该地区已经存在一个公安机关单位了!");

            return "redirect:" + Global.getAdminPath() + "/rc/baskstage/user/list?repage";
        }

        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            user.setPassword(entryptPassword(user.getNewPassword()));
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

        user.setIsSysrole(Global.YES);

        // 保存用户信息
        Condition condition = policeUserService.saveNewPoliceUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
            UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }

        if(condition.getCode() == Condition.ERROR_CODE){

            //公安管理员角色类型
            Role role = new Role();
            role.setDeptType(Role.DEPTTYPE_POLIC_SYS);
            List<Role> policeRoles= systemService.findRoleByType(role);

            model.addAttribute("allRoles",policeRoles);

            redirectAttributes.addAttribute("user",user);

            return "modules/jsps/rcBaskstage/policeUserForm";

        }

        addMessage(redirectAttributes,"添加公安用户-"+checkPolice.getArea().getName()+"-成功!");

        return "redirect:" + Global.getAdminPath() + "/rc/baskstage/user/list?repage";
    }

    /**
     * 重置用户密码
     * @param user
     * @param newPassword
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="/reset/password")
    public String resetPassword(User user, @RequestParam(value="password") String newPassword, Model model, RedirectAttributes redirectAttributes){

        Condition condition=null;

        User user1 = systemService.getUserByIdAndUserType(user.getId(),user.getUserType());


        if(!user1.getIsSysrole().equals(Global.YES)){
            redirectAttributes.addFlashAttribute("flag", 0);
            addMessage(redirectAttributes,"只能重置管理员用户密码!");

        }

        if(StringUtils.isNotBlank(newPassword)){

            user.setPassword(entryptPassword(newPassword));

            condition = rcUserService.rcChangePassword(user);

            if(condition.getCode()==200){
                redirectAttributes.addFlashAttribute("flag", 1);
                addMessage(redirectAttributes,"重置密码成功!");

            }else{
                redirectAttributes.addFlashAttribute("flag", 0);
                addMessage(redirectAttributes,"重置密码失败!");

            }
        }

        return "redirect:" + Global.getAdminPath() + "/rc/baskstage/user/list?repage";
    }

    /**
     * 用户列表页面
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value="/reset/page")
    public String resetPasswordPage(User user,Model model){

        User user1 = systemService.getUserByIdAndUserType(user.getId(),user.getUserType());

        if(!user1.getIsSysrole().equals(Global.YES)){

            return "error";
        }

        model.addAttribute("user",user1);

        return "modules/jsps/rcBaskstage/userModifyPwd";
    }

    /**
     * 更改用户登陆状态
     * @param user
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/changeLoginFlag")
    public String changeLoginFlag(User user, RedirectAttributes redirectAttributes) {

        if (UserUtils.getUser().getId().equals(user.getId())) {

            addMessage(redirectAttributes, "停用用户失败, 不允许停用当前用户");
            redirectAttributes.addFlashAttribute("flag", 0);
        } else if (User.isAdmin(user.getId())) {

            addMessage(redirectAttributes, "停用用户失败, 不允许停用超级管理员用户");
            redirectAttributes.addFlashAttribute("flag", 0);
        } else {

            rcUserService.changeLoginFlag(user);


            if(user.getLoginFlag().equals("1")){

                addMessage(redirectAttributes, "启用用户成功");
                redirectAttributes.addFlashAttribute("flag", 1);
            }else if(user.getLoginFlag().equals("0")){

                addMessage(redirectAttributes, "停用用户成功");
                redirectAttributes.addFlashAttribute("flag", 1);
            }else{

                addMessage(redirectAttributes, "操作失败，非法操作!");
                redirectAttributes.addFlashAttribute("flag", 0);
            }

        }

        return "redirect:" + Global.getAdminPath() + "/rc/baskstage/user/list?repage";
    }


    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkPoliceLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {

        if (loginName != null && loginName.equals(oldLoginName)) {
            return "true";
        } else if (loginName != null && systemService.getUserByLoginName(loginName,UserType.POLICE) == null) {
            return "true";
        }
        return "false";
    }

}
