package com.thinkgem.jeesite.modules.stamp.web.police.user;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.police.service.PoliceUserService;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
@Controller
@RequestMapping(value="${adminPath}/police/user")
public class PoliceUserController  extends BaseController {

    @Autowired
    private PoliceUserService policeUserService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/list")
    public String list(Model model, Area area, HttpServletRequest request, HttpServletResponse response){

        Page<User> page = policeUserService.findPoliceUserList(area,new Page<User>(request,response));

        model.addAttribute("page",page);

        model.addAttribute("area",area);

        Police currentPolice = UserUtils.getUser().getPoliceInfo();

        int subAreaNumbers = areaService.checkSubAreasExist(currentPolice.getArea());

        model.addAttribute("subAreaNumbers",subAreaNumbers);

        model.addAttribute("currentPolice",currentPolice);

        return "modules/police/policeUserList";
    }

    @RequestMapping(value="/form")
    public String form(User user,Model model,RedirectAttributes redirectAttributes){

        if(StringUtils.isNotBlank(user.getId())){

            user = policeUserService.getPoliceUser(user);

//            if(Global.NO.equals(user.getIsSysrole())){
//
//                addMessage(redirectAttributes,"该用户不是公安区域管理员，不可进行修改");
//                redirectAttributes.addAttribute("flag","0");
//                return "redirect:" + adminPath + "/police/user/list?repage";
//
//            }

        }

        model.addAttribute("user", user);

        //过滤角色列表
        List<Role> roleList = policeUserService.findPoliceRoleList();

        model.addAttribute("allRoles", roleList);

        model.addAttribute("message",redirectAttributes.getFlashAttributes().get("message"));

        return "modules/police/policeUserForm";
    }

    @RequestMapping(value="/save")
    public String save(User user,Model model, RedirectAttributes redirectAttributes){
        user.setIsSysrole(Global.YES);
        //设置归属的公司
        user.setUserType(UserType.POLICE);

        Area area  = user.getPoliceInfo().getArea();

        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {

            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        //表单验证

        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){

            addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model,redirectAttributes);
        }

        //新增用户
        // 一个地区应该只有一个公安
        Police police = new Police();

        police.setArea(area);


        if("".equals(user.getId())&&policeUserService.checkPoliceAreaOnlyOne(police)){

            Area checkArea = areaService.get(user.getPoliceInfo().getArea().getId());

            user.getPoliceInfo().setArea(area);
//            if(!((!"3".equals(checkArea.getType()))||(!"2".equals(checkArea.getType())))){
//
//                addMessage(model,"公安机关管辖区域只能是市级或者省级,添加失败");
//
//                return form(user,model);
//            }

            addMessage(redirectAttributes,"当前选择区域下已经存在一名公安机关,添加失败!");

            return form(user,model,redirectAttributes);
        }

        //更新用户
        // 一个地区应该只有一个公安
        if(user.getId()!=null&&!"".equals(user.getId())){

            Area checkArea = areaService.get(user.getPoliceInfo().getArea().getId());

            //先检查是否有区域的改动
            if(policeUserService.checkAreaChanged(user)){
                //true 证明改动过
                //改动区域下的检查
                user.getPoliceInfo().setArea(area);

//                if(!((!"3".equals(checkArea.getType()))||(!"2".equals(checkArea.getType())))){
//
//                    addMessage(model,"公安机关管辖区域只能市级或者省级,添加失败");
//
//                    return form(user,model);
//                }

                //一个地区应该只有一个公安
                if(!policeUserService.checkPoliceAreaOnlyOne(police)){

                    addMessage(redirectAttributes,"当前选择区域下已经存在一名公安机关,更改失败!");

                    return form(user,model,redirectAttributes);
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
        Condition condition = policeUserService.saveNewPoliceUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
            UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }

        if(condition.getCode()==Condition.SUCCESS_CODE){
            addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
            redirectAttributes.addAttribute("flag","1");
        }else {

            addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'失败\n");
            redirectAttributes.addAttribute("flag","0");

        }



        return "redirect:" + adminPath + "/police/user/list?repage";
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
