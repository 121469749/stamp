package com.thinkgem.jeesite.modules.stamp.web.phone;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.SysState;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.service.stamp.StampService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 移动端Controller接口
 * Created by sjk on 2017-07-03.
 */
@Controller
@RequestMapping(value = "${adminPath}/mobile")
public class PhoneInterfaceController {

    @Autowired
    private StampService stampService;

    /**
     * 根据印章使用状态查询印章列表
     * @param state 状态
     */
    @RequestMapping(value = "/useState")
    @ResponseBody
    public String useStateList(String state, HttpServletRequest request, HttpServletResponse response) {

        Stamp stamp = new Stamp();

        Condition condition = new Condition();

        //使用中
        if (state.equals("using")) {

            stamp.setStampState(StampState.DELIVERY);

            stamp.setUseState(OprationState.OPEN);

            stamp.setSysState(SysState.USABLE);
        }

        //停用
        if (state.equals("stop")) {

            stamp.setStampState(StampState.DELIVERY);

            stamp.setUseState(OprationState.STOP);
        }

        //缴销
        if (state.equals("destroy")) {

            stamp.setUseState(OprationState.LOGOUT);
        }

        //挂失
        if (state.equals("missing")) {

            stamp.setUseState(OprationState.REPORT);
        }

        //待交付
        if (state.equals("made")) {

            stamp.setStampState(StampState.ENGRAVE);
        }

        stamp.setUseComp(new Company(UserUtils.getUser().getUserTypeId()));

        try {

            System.out.println("user>>>>>>>>>>>" + JsonMapper.toJsonString(UserUtils.getUser()));

            Page<Stamp> page = stampService.listStampByUseStatePage(new Page<Stamp>(request, response, -1), stamp);

            condition.setCode(Condition.SUCCESS_CODE);

            condition.setEntity(page.getList());

            return JsonMapper.toJsonString(condition);

        } catch (Exception e) {

            e.printStackTrace();

            condition.setCode(Condition.ERROR_CODE);

            condition.setMessage("系统繁忙");

            return JsonMapper.toJsonString(condition);
        }
    }

    /**
     * 显示刻制中印章列表
     */
    @RequestMapping(value = "/showMaking")
    @ResponseBody
    public String showMaking(HttpServletRequest request, HttpServletResponse response) {

        Stamp stamp = new Stamp();

        Condition condition = new Condition();

        stamp.setStampState(StampState.RECEPT);

        try {

            Page<Stamp> page = stampService.listStampByStampStatePage(new Page<Stamp>(request, response, -1), stamp);

            condition.setCode(Condition.SUCCESS_CODE);

            condition.setEntity(page.getList());

            return JsonMapper.toJsonString(condition);

        } catch (Exception e) {

            e.printStackTrace();

            condition.setCode(Condition.ERROR_CODE);

            condition.setMessage("系统繁忙");

            return JsonMapper.toJsonString(condition);
        }
    }

    /**
     * 显示印章详细信息
     * @param stampId 印章id
     */
    @RequestMapping(value = "/showStamp")
    @ResponseBody
    public String showStamp(String stampId,String stampShape) {

        Condition<Stamp> condition = new Condition<Stamp>();

        try {

            Stamp stamp = stampService.findStampInfo(stampId,stampShape);

            condition.setCode(Condition.SUCCESS_CODE);

            condition.setEntity(stamp);

            return JsonMapper.toJsonString(condition);

        } catch (Exception e) {

            e.printStackTrace();

            condition.setCode(Condition.ERROR_CODE);

            condition.setMessage("系统繁忙");

            return JsonMapper.toJsonString(condition);
        }
    }

    /**
     * 更改印章状态
     */
    @RequestMapping(value = "/changeStampState")
    @ResponseBody
    public String changeStampStatus(Stamp stamp, String state) {

        Condition condition = new Condition();

        //判断是否是公司管理员
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
}
