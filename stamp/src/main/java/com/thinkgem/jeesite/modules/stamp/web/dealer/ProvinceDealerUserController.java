package com.thinkgem.jeesite.modules.stamp.web.dealer;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.dealer.DealerUserService;
import com.thinkgem.jeesite.modules.stamp.service.dealer.ProvinceDealerUserService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
@Controller
@RequestMapping(value="${adminPath}/dealer/province/user")
public class ProvinceDealerUserController  extends BaseController {


    @Autowired
    private DealerUserService dealerUserService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProvinceDealerUserService provinceDealerUserService;



    /**
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list(Company company, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<Company> page = provinceDealerUserService.findPage(new Page<Company>(request,response),company);

        model.addAttribute("page",page);

        model.addAttribute("company",company);

        return "modules/jsps/dealer/ProvinceDealerUserList";

    }



    @RequestMapping(value = "/form")
    public String form(User user, Model model) {

        user  = provinceDealerUserService.getAgencyUserByCompanyId(user.getUserTypeId());


        model.addAttribute("user",user);

        //过滤角色列表
        List<Role> roleList = provinceDealerUserService.getDealerRoles();

        model.addAttribute("allRoles", roleList);

        return "modules/jsps/dealer/ProvinceDealerUserForm";
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

        //默认属性设置
        user.setIsSysrole(Global.YES);

        //设置归属的公司
        user.setUserType(UserType.AGENY);

        Area area  = user.getCompanyInfo().getArea();

        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {

            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        //表单验证
        if (!beanValidator(model, user)){

            return form(user, model);
        }

        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){

            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }

        //新增用户
        // 一个地区应该只有一个经销商

        if("".equals(user.getId())&&!dealerUserService.checkAreaOnlyUser(area)){

            Area checkArea = areaService.get(user.getCompanyInfo().getArea().getId());

            user.getCompanyInfo().setArea(area);

            if(!((!"3".equals(checkArea.getType())))){

                addMessage(model,"经销商管辖区域只能是市级,添加失败");

                return form(user,model);
            }

            addMessage(model,"当前选择区域下已经存在一名经销商用户,添加失败!");

            return form(user,model);
        }

        //更新用户
        // 一个地区应该只有一个经销商
        if(user.getId()!=null&&!"".equals(user.getId())){

            Area checkArea = areaService.get(user.getCompanyInfo().getArea().getId());

            //先检查是否有区域的改动
            if(dealerUserService.checkAreaChanged(user)){
                //true 证明改动过
                //改动区域下的检查
                user.getCompanyInfo().setArea(area);

                if(!((!"3".equals(checkArea.getType())))){

                    addMessage(model,"经销商管辖区域只能市级,添加失败");

                    return form(user,model);
                }

                //一个地区应该只有一个经销商
                if(!dealerUserService.checkAreaOnlyUser(area)){

                    addMessage(model,"当前选择区域下已经存在一名经销商用户,更改失败!");

                    return form(user,model);
                }
            }
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
        dealerUserService.saveDealer(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
            UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }

        addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");

        return "redirect:" + adminPath + "/dealer/province/user/list?repage";
    }



    @RequestMapping(value = "/delete")
    public String delete(User user, RedirectAttributes redirectAttributes) {

        if (UserUtils.getUser().getId().equals(user.getId())) {

            addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");

        }  else {

            dealerUserService.deleteDealerUser(user);
            addMessage(redirectAttributes, "删除用户成功");
        }

        return "redirect:" + adminPath + "/dealer/province/user/list?repage";
    }


    @RequestMapping(value = "/changeLoginFlag")
    public String changeLoginFlag(User user, RedirectAttributes redirectAttributes) {

        systemService.changeLoginFlag(user);

        return "redirect:" + adminPath + "/dealer/province/user/list?repage";
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
        } else if (loginName != null && systemService.getUserByLoginName(loginName,user.getUserType()) == null) {

            return "true";
        }
        return "false";
    }



}
