package com.thinkgem.jeesite.modules.stamp.web.makeCompanyLicense;

import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentDirNotFoundException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.ServiceTypeEnum;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.dao.makeStampCompany.MakeCompanyLicenseDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.service.AreaAttachmentDirService;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.MakeCompanyLicenseService;
import com.thinkgem.jeesite.modules.stamp.vo.makeStamp.LicenseAttachmentVo;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 制章公司的许可证申请
 * 页面控制层
 * Created by Locker on 2017/5/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/makeStampPage")
public class MakeCompanyLicensePageController {


    @Autowired
    private MakeCompanyLicenseService makeCompanyLicenseService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AreaAttachmentDirService areaAttachmentDirService;


    @Autowired
    private MakeCompanyLicenseDao makeCompanyLicenseDao;

    /**
     * 检查需要经过开办审核阶段
     * 并跳转页面
     * @param
     * @return
     */
//    @RequestMapping("/toOpenPage")
//    public String toOpenLicence(Model model){
//
//    }

    /**
     * 检查到了年审阶段
     * 并跳转到页面
     *
     * @param
     * @return
     * @author Locker
     */
    @RequestMapping("/toYearPage")
    public String toYearLicencePage() {

        if (!makeCompanyLicenseService.judgeYear()) {

            return "还没到年审界面";
        }

        return "modules/jsps/stampmaker/stampmaker-yearly-start";
    }

    /**
     * 检查是否可以进入到变更页面
     * 并跳转页面
     *
     * @return
     * @author Locker
     */
    @RequestMapping(value = "/toChangePage")
    public String toChangeLicencePage() {

        return "modules/jsps/stampmaker/stampmaker-change-start";
    }

    /**
     * 检查是否可以进入撤销许可证页面
     * 并跳转页面
     *
     * @return
     * @author Locker
     */
    @RequestMapping(value = "/toBackoutPage")
    public String toBackoutPage() {

        return "modules/jsps/stampmaker/stampmaker-cancellaction-start";
    }


    /**
     * 检查需要经过开办审核阶段
     * 并跳转页面
     *
     * @param
     * @return
     */
    @RequestMapping("/openLicenseNoExam")
    public String openLicenseNoExam(Model model) {
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();


        Licence licence = makeCompanyLicenseService.checkNewestLicence(company);
        LicenseAttachmentVo licenseAttachmentVo = new LicenseAttachmentVo();
        licenseAttachmentVo.setLicence(licence);
        try {

            licenseAttachmentVo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        model.addAttribute("vo", licenseAttachmentVo);
        setAttachmentDirInModel(model);
        model.addAttribute(licence);
        return "modules/jsps/stampmaker/stampmaker-start-noexamine";
    }


    @RequestMapping("/annualNoExam")
    public String annualNoExam(Model model) {
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();

        Licence licence = makeCompanyLicenseService.checkNewestLicence(company);


        LicenseAttachmentVo licenseAttachmentVo = new LicenseAttachmentVo();
        licenseAttachmentVo.setLicence(licence);
        try {
            licenseAttachmentVo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {

        }
        model.addAttribute("vo", licenseAttachmentVo);
        setAttachmentDirInModel(model);
        model.addAttribute(licence);
        return "modules/jsps/stampmaker/stampmaker-yearly-noexamine";
    }


    @RequestMapping("/changeNoExam")
    public String changeNoExam(Model model) {
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();


        Licence licence = makeCompanyLicenseService.checkNewestLicence(company);


        LicenseAttachmentVo licenseAttachmentVo = new LicenseAttachmentVo();
        licenseAttachmentVo.setLicence(licence);
        try {
            licenseAttachmentVo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {

        }
        model.addAttribute("vo", licenseAttachmentVo);
        setAttachmentDirInModel(model);
        model.addAttribute(licence);
        return "modules/jsps/stampmaker/stampmaker-change-noexamine";
    }


    @RequestMapping("/cancelNoExam")
    public String cancelNoExam(Model model) {
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();


        Licence licence = makeCompanyLicenseService.checkNewestLicence(company);


        LicenseAttachmentVo licenseAttachmentVo = new LicenseAttachmentVo();
        licenseAttachmentVo.setLicence(licence);
        try {
            licenseAttachmentVo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {

        }
        model.addAttribute("vo", licenseAttachmentVo);
        setAttachmentDirInModel(model);
        model.addAttribute(licence);
        return "modules/jsps/stampmaker/stampmaker-cancellation-noexamine";
    }


    @RequestMapping("/openLicensefinish")
    public String openLicensefinish(Model model) {
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();
        Licence licence = makeCompanyLicenseDao.getLicence(company.getId(), WorkType.OPEN);
        LicenseAttachmentVo licenseAttachmentVo = new LicenseAttachmentVo();
        licenseAttachmentVo.setLicence(licence);
        try {

            licenseAttachmentVo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        model.addAttribute("vo", licenseAttachmentVo);
        setAttachmentDirInModel(model);
        model.addAttribute(licence);
        return "modules/jsps/stampmaker/stampmaker-start-finish";
    }


    @RequestMapping("/annualfinish")
    public String annualfinish(Model model) {
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();
        Licence licence = makeCompanyLicenseDao.getLicence(company.getId(), WorkType.ANNUAL);
        LicenseAttachmentVo licenseAttachmentVo = new LicenseAttachmentVo();
        licenseAttachmentVo.setLicence(licence);
        try {
            licenseAttachmentVo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {

        }
        model.addAttribute("vo", licenseAttachmentVo);
        setAttachmentDirInModel(model);
        model.addAttribute(licence);
        return "modules/jsps/stampmaker/stampmaker-yearly-finish";
    }


    @RequestMapping("/changefinish")
    public String changefinish(Model model) {
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();
        Licence licence = makeCompanyLicenseDao.getLicence(company.getId(), WorkType.CHANGE);
        LicenseAttachmentVo licenseAttachmentVo = new LicenseAttachmentVo();
        licenseAttachmentVo.setLicence(licence);
        try {
            licenseAttachmentVo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {

        }
        model.addAttribute("vo", licenseAttachmentVo);
        setAttachmentDirInModel(model);
        model.addAttribute(licence);
        return "modules/jsps/stampmaker/stampmaker-change-finish";
    }


    @RequestMapping("/cancelfinish")
    public String cancelfinish(Model model) {
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();
        Licence licence = makeCompanyLicenseDao.getLicence(company.getId(), WorkType.LOGOUT);
        LicenseAttachmentVo licenseAttachmentVo = new LicenseAttachmentVo();
        licenseAttachmentVo.setLicence(licence);
        try {
            licenseAttachmentVo.setAttachmentList(attachmentService.findAttachmentByJsonAttachs(licence.getAttachs()));
        } catch (Exception e) {

        }
        model.addAttribute("vo", licenseAttachmentVo);
        setAttachmentDirInModel(model);
        model.addAttribute(licence);
        return "modules/jsps/stampmaker/stampmaker-cancellation-finish";
    }

    /**
     * 附件目录set
     *
     * @param model
     */
    private void setAttachmentDirInModel(Model model) {

        try {

            List<Dict> list = areaAttachmentDirService.getCurrentAreaAttachmentList(ServiceTypeEnum.LICENSESERVICE);

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
