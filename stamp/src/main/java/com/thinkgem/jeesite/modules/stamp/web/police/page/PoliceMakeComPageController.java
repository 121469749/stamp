package com.thinkgem.jeesite.modules.stamp.web.police.page;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentDirNotFoundException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.areaattachment.service.AreaAttachmentService;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CheckState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.ServiceTypeEnum;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.service.AreaAttachmentDirService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceLicenseService;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceCompanyService;
import com.thinkgem.jeesite.modules.stamp.vo.policeOperation.MakeComLicenseAttachmentsVO;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjw-pc on 2017/5/20.
 */
@Controller
@RequestMapping(value ="${adminPath}/policeMakeComPage")
public class PoliceMakeComPageController extends BaseController{
    @Autowired
    private  PoliceCompanyService policeCompanyService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private  PoliceLicenseService policeLicenseService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AreaAttachmentDirService areaAttachmentDirService;


    /**
     * 显示未审核列表 即审核中
     * 数据过滤当前区域
     * @param model
     * @return
     */
    @RequestMapping(value = "/showCheckingCompany")
    public String showCheckingCompany(Licence licence, HttpServletRequest request, HttpServletResponse response, Model model){


        licence.setCheckState(CheckState.CHECKING);
        if (licence.getWorkType() == null)
        {
            licence.setWorkType(WorkType.OPEN);
        }
        Page<Licence> page = policeLicenseService.findLicenseList(new Page<Licence>(request, response),licence);
        model.addAttribute(page);

                model.addAttribute("workTypes", WorkType.values());

        model.addAttribute(licence);

        return "modules/jsps/police/police-index";

    }


    /**
     * 显示审核历史
     * @param model
     * @return
     */
    @RequestMapping(value = "/history")
    public String history(Licence licence, HttpServletRequest request, HttpServletResponse response, Model model){

        licence.setCheckState(CheckState.CHECKING);
        if(licence.getWorkType() ==null){
            licence.setWorkType(WorkType.LOGOUT);

        }
        Page<Licence> page = policeLicenseService.findHistoryList(new Page<Licence>(request, response),licence);
        model.addAttribute("workTypes", WorkType.values());
        model.addAttribute(page);
        model.addAttribute(licence);
        return "modules/jsps/police/police-examine-history";

    }

    /**
     * 显示makecompanys信息
     * 刻章点管理
     *
     * @param company
     * @param model
     * @return
     */
    @RequestMapping(value = "/showMakeCompany")
    public String makeCompanyList(Company company, HttpServletRequest request, HttpServletResponse response, Model model) {


        if(company.getCompType() != CompanyType.MAKE){
            company.setCompType(CompanyType.MAKE);
        }

        Page<Company> page = policeCompanyService.findMakeComList(new Page<Company>(request, response),company);

        model.addAttribute("page",page);
        model.addAttribute("company",company);
        return "modules/jsps/police/police-stampmaker-manage";
    }

    /**
     * @return java.lang.String
     * @author hjw
     * @description license详情以及其公司信息显示
     * @date 2017/5/22
     */
    @RequiresPermissions("police:edit")
    @RequestMapping(value = "/companyDetail")
    public String companyDetail(Company company1,String id, Model model) {
        company1.setCompType(CompanyType.MAKE);
        Licence licence = new Licence();
        licence.setMakeComp(companyService.getCom(company1));
        Company companyDetail = policeCompanyService.getMakeCompany2(companyService.getCom(company1));
        Licence licenceDetail = policeLicenseService.getNewestLicense(licence);
        MakeComLicenseAttachmentsVO vo = new MakeComLicenseAttachmentsVO();
        vo.setCompany(companyDetail);

        try {
            vo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licenceDetail.getAttachs()));
        } catch (Exception e) {

        }

        model.addAttribute("vo", vo);
        setAttachmentDirInModel(model,companyService.getCom(company1).getArea());

        return "modules/jsps/police/police-stampmaker-detail";
    }

    private void setAttachmentDirInModel(Model model, Area area) {

        try {

            List<Dict> list = areaAttachmentDirService.getCurrentAreaAttachmentList(ServiceTypeEnum.LICENSESERVICE,area);

            model.addAttribute("dir", list);

        } catch (AreaAttachmentDirNotFoundException e) {

            e.printStackTrace();

        } catch (AreaAttachmentException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
