package com.thinkgem.jeesite.modules.stamp.web.company.action;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampAuthState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dto.useCompany.InsertOperationDto;
import com.thinkgem.jeesite.modules.stamp.dto.useCompany.InsertUserDto;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampAuth;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampOperation;
import com.thinkgem.jeesite.modules.stamp.service.stamp.StampService;
import com.thinkgem.jeesite.modules.stamp.service.stamprecord.StampAuthSerivce;
import com.thinkgem.jeesite.modules.stamp.service.stamprecord.StampOperationService;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.vo.useCompany.UserAndStampVo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjk on 2017-05-20.
 */
@Controller
@RequestMapping(value = "${adminPath}/useCompanyStampAction")
public class UseCompanyStampActionController {

    @Autowired
    private StampService stampService;

    @Autowired
    private StampAuthSerivce stampAuthSerivce;

    @Autowired
    private StampOperationService stampOperationService;

    /**
     * 更改印章状态
     * @param stamp
     * @param state
     * @return
     */
    @RequestMapping(value = "/changeStampState")
    @ResponseBody
    public String changeStampStatus(Stamp stamp, String state) {

        Condition condition = new Condition();

        if (UserUtils.getUser().getIsSysrole().equals("1")) {
            try {

                if (stampService.updateStampState(stamp, state)) {
                    condition.setCode(Condition.SUCCESS_CODE);
                    condition.setMessage("更改印章状态成功");
                    return JsonMapper.toJsonString(condition);
                }
            } catch (Exception e) {
                e.printStackTrace();
                condition.setCode(Condition.ERROR_CODE);
                condition.setMessage("系统繁忙");
                return JsonMapper.toJsonString(condition);
            }
        } else {
            condition.setCode(Condition.NOALLOW_CODE);
            condition.setMessage("没有权限");
        }

        return JsonMapper.toJsonString(condition);
    }

    /**
     * 更改印章授权
     * @param stampAuth
     * @return
     */
    @RequestMapping(value = "/changAuthState")
    @ResponseBody
    public String changAuthState(HttpServletRequest request, StampAuth stampAuth, String state) {

        Condition condition = new Condition();

        if (UserUtils.getUser().getIsSysrole().equals("1")) {
            try {
                condition = stampAuthSerivce.changeAuthState(request, stampAuth, state);
                return JsonMapper.toJsonString(condition);
            } catch (Exception e) {
                e.printStackTrace();
                condition.setCode(Condition.ERROR_CODE);
                condition.setMessage("系统繁忙");
                return JsonMapper.toJsonString(condition);
            }
        } else {
            condition.setCode(Condition.NOALLOW_CODE);
            condition.setMessage("没有权限");
            return JsonMapper.toJsonString(condition);
        }
    }

    /**
     * 为印章权限列表添加用户
     * @param stampAuth
     * @return
     */
    @RequestMapping(value = "/addUserToAuth")
    @ResponseBody
    public String addUserToAuth(StampAuth stampAuth) {

        Condition condition = new Condition();

        //判断操作权限
        if (UserUtils.getUser().getIsSysrole().equals("0")) {
            condition.setCode(Condition.NOALLOW_CODE);
            condition.setMessage("没有权限");
            return JsonMapper.toJsonString(condition);
        }

        condition = stampAuthSerivce.addUserToAuth(stampAuth);

        return JsonMapper.toJsonString(condition);


    }

    /**
     * 删除用户授权信息
     * @param stampAuth
     * @return
     */
    @RequestMapping(value = "/delUserFromAuth")
    @ResponseBody
    public String delUserFromAuth(StampAuth stampAuth) {

        Condition condition = new Condition();

        try {
            stampAuthSerivce.delete(stampAuth);
            condition.setCode(Condition.SUCCESS_CODE);
            condition.setMessage("删除成功");
            return JsonMapper.toJsonString(condition);

        } catch (Exception e) {
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("系统繁忙");
            return JsonMapper.toJsonString(condition);
        }
    }


    /**
     * 为物理印章录入显示用户列表
     * @return
     */
    @RequestMapping(value = "/showUser")
    @ResponseBody
    public String showUser() {

        Condition<UserAndStampVo> condition = new Condition<UserAndStampVo>();

        try {
            UserAndStampVo userAndStampVo = stampOperationService.showUserAndStamp(UserUtils.getUser().getUserTypeId());

            condition.setCode(Condition.SUCCESS_CODE);
            condition.setEntity(userAndStampVo);

            return JsonMapper.toJsonString(condition);

        } catch (Exception e) {
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("系统繁忙");

            return JsonMapper.toJsonString(condition);
        }
    }
}
