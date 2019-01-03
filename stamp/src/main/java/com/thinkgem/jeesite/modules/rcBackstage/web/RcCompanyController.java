package com.thinkgem.jeesite.modules.rcBackstage.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.rcBackstage.service.RcCompanyService;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.dealer.DealerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 润城后台管理员对公司的管理
 * <p>
 * <p>
 * Created by Locker on 2017/7/29.
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/company")
public class RcCompanyController extends BaseController {

    @Autowired
    private RcCompanyService rcCompanyService;

    @Autowired
    private DealerService dealerService;

    /**
     *
     *  润城后台管理员查看公司
     *
     * @param company
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Company company, Model model, HttpServletResponse response, HttpServletRequest request) {

        if (company.getCompType() == null) {

            company.setCompType(CompanyType.AGENCY);
        }

        Page<Company> page = rcCompanyService.companyPage(new Page<Company>(request,response),company);

        model.addAttribute("page",page);

        model.addAttribute("check", company);

        return "modules/jsps/rcBaskstage/company-list";
    }


    /**
     * 润城科技对公司管控停用
     *
     * @param companyId
     * @param companyType
     */
    @RequestMapping(value = "/systemOpration/stop")
    public String systemStopCompany(@RequestParam(value = "companyId") String companyId,
                                    @RequestParam(value = "companyType") CompanyType companyType,
                                    RedirectAttributes redirectAttributes) {

        String message = null;
        Condition condition = null;
        try {

            condition = dealerService.systemOpration(companyId, companyType, OprationState.STOP);

        } catch (Exception e) {

            message = e.getMessage();
        }

        if (condition == null) {

            addMessage(redirectAttributes, message);

        } else {

            addMessage(redirectAttributes, condition.getMessage());

        }

        return "redirect:" + adminPath + "/sys/company/list?repage";

    }

    /**
     * 润城科技对公司管控启用
     *
     * @param companyId
     * @param companyType
     */
    @RequestMapping(value = "/systemOpration/start")
    public String systemStartCompany(@RequestParam(value = "companyId") String companyId,
                                     @RequestParam(value = "companyType") CompanyType companyType,
                                     RedirectAttributes redirectAttributes) {

        String message = null;
        Condition condition = null;

        try {

            condition = dealerService.systemOpration(companyId, companyType, OprationState.OPEN);

        } catch (Exception e) {

            message = e.getMessage();
        }

        if (condition == null) {

            addMessage(redirectAttributes, message);

        } else {

            addMessage(redirectAttributes, condition.getMessage());

        }


        return "redirect:" + adminPath + "/sys/company/list?repage";

    }

}
