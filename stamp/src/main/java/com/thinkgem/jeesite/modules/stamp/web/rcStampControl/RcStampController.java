package com.thinkgem.jeesite.modules.stamp.web.rcStampControl;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.check.dto.QueryDTO;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Electron;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.service.rcStampControl.RcStampControlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/11/19.
 */
@Controller
@RequestMapping(value="${adminPath}/control/stamp")
public class RcStampController extends BaseController {

    @Autowired
    private RcStampControlService rcStampControlService;

    @RequestMapping(value="/page")
    public String page(QueryDTO dto, HttpServletRequest request, HttpServletResponse response, Model model){

        Page<Stamp> page = rcStampControlService.findPage(dto,request,response);

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        return "modules/jsps/prescription/prescription";
    }

    /**
     * 为印章添加润城控制时效
     * @param electron
     * @return
     */
    @RequestMapping(value="/control",method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String control(Electron electron){

        Condition condition =  rcStampControlService.controlStamp(electron);

        return JsonMapper.toJsonString(condition);
    }



    /**
     * @author 许彩开
     * @TODO (注：润城科技管控停用)
      * @param companyId
     * @DATE: 2017\12\21 0021 14:27
     */

    //@RequiresPermissions("dealer:company:edit")
    @RequestMapping(value = "/systemOpration/stop")
    public String systemStopCompany(@RequestParam(value = "companyId") String companyId,
                                    RedirectAttributes redirectAttributes) {

        String message = null;

        Condition condition = null;
        try {

            condition = rcStampControlService.systemOpration(companyId, OprationState.STOP);

        } catch (Exception e) {

            message = e.getMessage();
        }

        if (condition == null) {

            addMessage(redirectAttributes, message);

        } else {

            addMessage(redirectAttributes, condition.getMessage());

        }

            return "redirect:" + adminPath + "/control/stamp/page?repage";


    }


    /**
     * @author 许彩开
     * @TODO (注：润城科技管控启用)
      * @param companyId
     * @DATE: 2017\12\21 0021 14:26
     */

    //@RequiresPermissions("dealer:company:edit")
    @RequestMapping(value = "/systemOpration/start")
    public String systemStartCompany(@RequestParam(value = "companyId") String companyId,
                                    RedirectAttributes redirectAttributes) {

        String message = null;

        Condition condition = null;
        try {

            condition = rcStampControlService.systemOpration(companyId, OprationState.OPEN);

        } catch (Exception e) {

            message = e.getMessage();
        }

        if (condition == null) {

            addMessage(redirectAttributes, message);

        } else {

            addMessage(redirectAttributes, condition.getMessage());

        }

        return "redirect:" + adminPath + "/control/stamp/page?repage";


    }


}
