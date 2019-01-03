package com.thinkgem.jeesite.modules.stamp.web.police.page;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentDirNotFoundException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.ServiceTypeEnum;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.service.AreaAttachmentDirService;
import com.thinkgem.jeesite.modules.stamp.service.licence.LicenceService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceCompanyService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceLicenseService;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.vo.policeOperation.MakeComLicenseAttachmentsVO;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by hjw-pc on 2017/5/20.
 */
@Controller
@RequestMapping(value = "${adminPath}/policeLicensePage")
public class PoliceLicensePageController extends BaseController {

    @Autowired
    private  PoliceCompanyService policeCompanyService;
    @Autowired
    private  PoliceLicenseService policeLicenseService;
    @Autowired
    private AreaAttachmentDirService areaAttachmentDirService;
    @Autowired
    private AttachmentService attachmentService;

    /**
     * @param [licence, model]
     * @return java.lang.String
     * @author hjw
     * @description license详情以及其公司信息显示
     * @date 2017/5/22
     */
    @RequestMapping(value = "/LicenseHistoryDetail")
    public String LicenseHistoryDetail(Licence licence, Model model) {

        licence = policeLicenseService.get(licence);
        Company company = policeCompanyService.getMakeCompany(licence.getMakeComp());

        MakeComLicenseAttachmentsVO vo = new MakeComLicenseAttachmentsVO();
        vo.setCompany(company);
        vo.setLicence(licence);
      //  vo.setAttachmentList(policeLicenseService.getListAttachment(licence.getAttachs()));
        try {
            vo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {

        }


        model.addAttribute("vo", vo);
        setAttachmentDirInModel(model,company.getArea());
        return "modules/jsps/police/police-examine-history-detailed";
    }


    /**
     * @param [licence, model]
     * @return java.lang.String
     * @author hjw
     * @description 显示用于公安审核制章公司和license界面
     * @date 2017/5/22
     */
    @RequestMapping(value = "/checkLicense")
    public String checkLicense(Licence licence, Model model) {


        licence = policeLicenseService.get(licence);


        Company company = policeCompanyService.getMakeCompany(licence.getMakeComp());

        MakeComLicenseAttachmentsVO vo = new MakeComLicenseAttachmentsVO();
        vo.setCompany(company);
        vo.setLicence(licence);
        try {
            vo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {

        }
        model.addAttribute("vo", vo);
        setAttachmentDirInModel(model,company.getArea());

        return "modules/jsps/police/police-examine-detailed";
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
