package com.thinkgem.jeesite.modules.stamp.web.dealer;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.service.dealer.DealerSubUserService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 省经销商子用户管理
 *
 * Created by Locker on 2017/10/2.
 */
@Controller
@RequestMapping(value="${adminPath}/dealer/city/sub/user")
public class DealerCityUserController extends BaseController {

    @Autowired
    private DealerSubUserService dealerSubUserService;

    @Autowired
    private SystemService systemService;


    @ModelAttribute
    public User get(@RequestParam(required = false) String id) {

        if (StringUtils.isNotBlank(id)) {
            User user = systemService.getUserByIdAndUserType(id, UserType.AGENY);
            return user;
        } else {
            User user = new User();
            user.setUserType(UserType.AGENY);
            return user;
        }
    }

    /**
     *
     * 当前市经销商 子单位用户列表
     *
     * @param user
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<User> page = dealerSubUserService.findPage(new Page<User>(request, response), user);

        model.addAttribute("page", page);
        //todo
        return "modules/jsps/dealer/subUser/citySubUserList";
    }

    @RequestMapping(value="/form")
    public String form(User user, Model model){

        model.addAttribute("user", user);

        //过滤角色列表
        List<Role> roleList =dealerSubUserService.getSubDealerRoles(Role.DEPTTYPE_DEALER_CITY_SUB);

        model.addAttribute("allRoles", roleList);

        //todo
        return "modules/jsps/dealer/subUser/citySubUserForm";

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
    @RequestMapping(value = "/save")
    public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        //默认为非管理员角色用户
        user.setIsSysrole(Global.NO);

        User currentUser = UserUtils.getUser();
        //设置归属的公司
        user.setUserTypeId(currentUser.getUserTypeId());
        user.setUserType(currentUser.getUserType());
        //默认登陆标记
        user.setLoginFlag(Global.YES);

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


        //todo
        return "redirect:" + adminPath + "/dealer/city/sub/user/list";
    }


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


        return "redirect:" + adminPath + "/dealer/city/sub/user/list";
    }


    @RequestMapping(value = "/changeLoginFlag")
    public String changeLoginFlag(User user, RedirectAttributes redirectAttributes) {

        systemService.changeLoginFlag(user);

        return "redirect:" + adminPath + "/dealer/city/sub/user/list";
    }

    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {

        User user = UserUtils.getUser();

        if (loginName != null && loginName.equals(oldLoginName)) {

            return "true";
        } else if (loginName != null && systemService.getUserByLoginName(loginName, user.getUserType()) == null) {

            return "true";
        }
        return "false";
    }


}
