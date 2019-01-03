package com.thinkgem.jeesite.modules.stamp.web.dealer;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.ServiceTypeEnum;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.AreaAttachmentDirService;
import com.thinkgem.jeesite.modules.stamp.service.dealer.DealerService;
import com.thinkgem.jeesite.modules.stamp.vo.policeOperation.MakeComLicenseAttachmentsVO;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Locker on 2017/7/2.
 */
@Controller
@RequestMapping(value = "${adminPath}/dealer")
public class DealerController extends BaseController {

    @Autowired
    private DealerService dealerService;

    @Autowired
    private AreaAttachmentDirService areaAttachmentDirService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AreaService areaService;

    /**
     * 经销商查询用章单位列表
     * 根据区域过滤
     * @param company
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/makeCompany/list")
    public String makeCompanyList(Company company, Model model, HttpServletRequest request, HttpServletResponse response) {

        company.setCompType(CompanyType.MAKE);

        Page<Company> page = dealerService.findCompanyPage(company, new Page<Company>(request, response));

        model.addAttribute("page", page);

        model.addAttribute("company", company);

//        //get current area return children area list
//        List<Area> areas = areaService.findCurrentAreaSubAreas();
//        model.addAttribute(areas);
        List<Area> areas = dealerService.findAncestors();
        model.addAttribute("areas", areas);

        return "modules/jsps/dealer/stampmaker-list";
    }

    @RequestMapping(value = "/useCompany/list")
    public String useCompanyList(Company company, Model model, HttpServletRequest request, HttpServletResponse response) {

        company.setCompType(CompanyType.USE);

        Page<Company> page = dealerService.findCompanyPage(company, new Page<Company>(request, response));

        model.addAttribute("page", page);

        model.addAttribute("company", company);

        return "modules/jsps/dealer/useCompany-list";

    }


    /**
     * 根据公司 id 和公司类型查找 对应信息
     *
     * @param id
     * @param companyType
     * @param model
     * @return
     */

    @RequestMapping(value = "/company/detail")
    public String companyDetail(@RequestParam(value = "companyId") String id,
                                @RequestParam(value = "companyType") CompanyType companyType, Model model) {

        if (companyType == CompanyType.MAKE) {

            Company company = dealerService.getCompany(id, companyType);

            MakeComLicenseAttachmentsVO vo = new MakeComLicenseAttachmentsVO();

            vo.setCompany(company);

            vo.setLicence(company.getLicence());

            try {

                vo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(company.getLicence().getAttachs()));

            } catch (Exception e) {

                e.printStackTrace();

                return "modules/jsps/search";
            }

            List<Dict> list = areaAttachmentDirService.getCurrentAreaAttachmentList(ServiceTypeEnum.LICENSESERVICE, company.getArea());

            model.addAttribute("dir", list);

            model.addAttribute("vo", vo);

            return "modules/jsps/dealer/stampmaker-detail";

            // return "modules/jsps/buliding";

        } else if (companyType == CompanyType.USE) {

            Company company = dealerService.getCompany(id, companyType);

            model.addAttribute("company", company);

            //todo 其他信息的显示

            return "modules/jsps/dealer/useCompany-detail";

        } else {


            //查无此信息
            return "modules/jsps/search";
        }

    }


    /**
     * 润城科技管控停用
     *
     * @param companyId
     * @param companyType
     */
    @RequiresPermissions("dealer:company:edit")
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

        if (companyType == CompanyType.MAKE) {

            return "redirect:" + adminPath + "/dealer/makeCompany/list?repage";
        } else {

            return "redirect:" + adminPath + "/dealer/useCompany/list?repage";
        }

    }

    /**
     * 润城科技管控启用
     *
     * @param companyId
     * @param companyType
     */
    @RequiresPermissions("dealer:company:edit")
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


        if (companyType == CompanyType.MAKE) {

            return "redirect:" + adminPath + "/dealer/makeCompany/list?repage";
        } else {

            return "redirect:" + adminPath + "/dealer/useCompany/list?repage";
        }


    }

}
