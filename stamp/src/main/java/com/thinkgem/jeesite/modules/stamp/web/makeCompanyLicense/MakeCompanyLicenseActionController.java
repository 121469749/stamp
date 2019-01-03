package com.thinkgem.jeesite.modules.stamp.web.makeCompanyLicense;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentDirNotFoundException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.ServiceTypeEnum;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.LicenseApplyDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.exception.police.RegisterMakeComException;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.RegisterLicenseException;
import com.thinkgem.jeesite.modules.stamp.service.AreaAttachmentDirService;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.MakeCompanyLicenseService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 刻章点的许可证申请
 * Action Controller
 * Created by Locker on 2017/5/19.
 */
@Controller
@RequestMapping(value="${adminPath}/makeStampAction")
public class MakeCompanyLicenseActionController  {

    @Autowired
    private MakeCompanyLicenseService makeCompanyLicenseService;
    @Autowired
    private AreaAttachmentDirService areaAttachmentDirService;

    /**
     *
     * @param licence
     * @param model
     * @return
     */
    @RequestMapping(value="/licenseForm")
    private String form(Licence licence, Model model) {


        LicenseApplyDTO licenseApplyDTO = new LicenseApplyDTO();

        model.addAttribute("licenseApplyDTO", licenseApplyDTO);

        setAttachmentDirInModel(model);

        User user = UserUtils.getUser();

        Company company = user.getCompanyInfo();

        //显示上次结果（若存在）

            Licence lastLicense = makeCompanyLicenseService.getLicence(company.getId(), licence.getWorkType());
            if (lastLicense != null) {
                model.addAttribute("result", lastLicense);
            }


        model.addAttribute(company);

        if(licence.getWorkType() == WorkType.OPEN){

            return "modules/jsps/stampmaker/stampmaker-start-start";
        }

        if(licence.getWorkType() == WorkType.CHANGE){

            return "modules/jsps/stampmaker/stampmaker-change-start";
        }

        if(licence.getWorkType() == WorkType.ANNUAL){
            return "modules/jsps/stampmaker/stampmaker-yearly-start";
        }

        if(licence.getWorkType() == WorkType.LOGOUT){

            return "modules/jsps/stampmaker/stampmaker-cancellation-start";
        }

        return "错误请求";
    }

    /**
     * 提交 开办申请、年审信息、变更申请、注销申请 接口
     * 先表单验证，验证通过之后就存
     *
     * @param licenseApplyDTO
     * @author Locker
     */
    @RequestMapping(value="/submit",method = RequestMethod.POST,produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String submitLicense(LicenseApplyDTO licenseApplyDTO, @RequestParam(value = "attachment")MultipartFile multipartFile[]){
        Condition condition = new Condition();
        try {
            licenseValidator(licenseApplyDTO, multipartFile, condition);

        } catch (RegisterLicenseException re) {
            //后台验证不通过
            return JsonMapper.toJsonString(condition);
        }
        //验证通过
       //设置不可改属性
        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();
        licenseApplyDTO.getLicence().setCompName(company.getCompanyName());
        licenseApplyDTO.getLicence().setMakeComp(company);


        try {

           makeCompanyLicenseService.saveLicence(licenseApplyDTO, multipartFile,condition);

        }catch (RuntimeException re){

            return JsonMapper.toJsonString(condition);

        }


        return JsonMapper.toJsonString(condition);
    }


    /**
     * 附件目录set
     *
     * @param model
     */
    private void setAttachmentDirInModel(Model model){

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

    private void licenseValidator(LicenseApplyDTO licenseApplyDTO, MultipartFile[] multipartFiles, Condition condition){
        StringBuffer messageBuffer = new StringBuffer();
        Licence licence = licenseApplyDTO.getLicence();
        if (multipartFiles.length < licenseApplyDTO.getFileType().size()) {

            messageBuffer.append("请上传所有的附件\n");

        }

        //经济性质
        if( "".equals(licence.getBusType()) || licence.getBusType() == null){
            messageBuffer.append("经济性质不能为空\n");
        }
        if("".equals(licence.getLegalName()) || licence.getLegalName() == null){
            messageBuffer.append("法人姓名不能为空\n");

        }
        if ("".equals(licence.getLegalCertCode()) || licence.getLegalCertCode() == null) {
            messageBuffer.append("法人身份证号不能为空\n");
        }
        if ("".equals(licence.getLegalPhone()) || licence.getLegalPhone() == null) {
            messageBuffer.append("法人手机号不能为空\n");

        }
        if ("".equals(licence.getPoliceName()) || licence.getPoliceName() == null) {
            messageBuffer.append("治安负责人姓名不能为空\n");

        }
        if ("".equals(licence.getPoliceIdCode()) || licence.getPoliceIdCode() == null) {
            messageBuffer.append("治安负责人身份证号不能为空\n");

        }
        if ("".equals(licence.getPolicePhone()) || licence.getPolicePhone() == null) {
            messageBuffer.append("治安负责人手机号不能为空\n");

        }
        if ("".equals(licence.getHeadCount()) || licence.getHeadCount() == null) {
            messageBuffer.append("企业总人数不能为空\n");

        }
        if ("".equals(licence.getSpecialCount()) || licence.getSpecialCount() == null) {
            messageBuffer.append("特业从业人数不能为空\n");

        }

        //to sum up
        if(messageBuffer.length() > 0){
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage(messageBuffer.toString());
            throw new RegisterLicenseException();
        }
    }
}
