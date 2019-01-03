package com.thinkgem.jeesite.modules.stamp.web.police.action;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentDirNotFoundException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.ServiceTypeEnum;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.dto.police.MakeComDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.LicenseApplyDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.StampMakeDTO;
import com.thinkgem.jeesite.modules.stamp.exception.police.RegisterMakeComException;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.AreaException;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.RegisterLicenseException;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.SoleCodeException;
import com.thinkgem.jeesite.modules.stamp.service.AreaAttachmentDirService;
import com.thinkgem.jeesite.modules.stamp.service.licence.LicenceService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceLicenseService;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceCompanyService;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjw-pc on 2017/5/20.
 */
@Controller
@RequestMapping(value ="${adminPath}/policeMakeComAction")
public class PoliceMakeComActionController extends BaseController{

    @Autowired
    private  SystemService systemService;
    @Autowired
    private  PoliceCompanyService policeCompanyService;
    @Autowired
    private PoliceLicenseService policeLicenseService;
    @Autowired
    private LicenceService licenceService;
    @Autowired
    private  CompanyService companyService;
    @Autowired
    private  AttachmentService attachmentService;
    @Autowired
    private AreaAttachmentDirService areaAttachmentDirService;

    /**
     * 录入刻章公司表单
     *
     * @param model
     * @return
     */

    @RequestMapping(value = "/makeCompanyForm")
    public String makeComForm(MakeComDTO makeComDTO, Model model) {
        model.addAttribute("makeComDTO", makeComDTO);
        setAttachmentDirInModel(model);
        return "modules/jsps/police/police-stampmaker-input";
    }

    /**
     * 保存刻章公司
     *
     * @return
     */
    @RequiresPermissions("police:edit")
    @ResponseBody
    @RequestMapping(value = "/saveMakeCompany",method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    public String saveMakeCom(MakeComDTO makeComDTO,@RequestParam(value = "file")MultipartFile multipartFile[]) {

        Condition condition = new Condition();

        condition.setCode(Condition.SUCCESS_CODE);

        companyValidate(makeComDTO, multipartFile, condition);

        if(condition.getCode()==Condition.ERROR_CODE){
            return JsonMapper.toJsonString(condition);
        }


        try {
            policeCompanyService.saveMakeCom(condition,makeComDTO,multipartFile);
        }catch (AreaException a){
            condition = new Condition(Condition.ERROR_CODE, "该用章单位所属区域不正确!");
            return JsonMapper.toJsonString(condition);
        } catch (SoleCodeException s){
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("社会信用统一码错误");
            return JsonMapper.toJsonString(condition);
        } catch (RegisterMakeComException re){

            return JsonMapper.toJsonString(condition);

        } catch (RuntimeException r){
            r.printStackTrace();
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("系统繁忙!");
            return JsonMapper.toJsonString(condition);
        }

        if(condition.getCode() == Condition.ERROR_CODE) {
            return JsonMapper.toJsonString(condition);
        }
        condition.setMessage("录入刻章点成功!");
        //保存成功 返回重定向url 前端跳转
       condition.setUrl(Global.getAdminPath()+"/policeMakeComPage/showCheckingCompany");
        return JsonMapper.toJsonString(condition);

    }

    /**
     * 删除/撤销makecompany
     *
     * @param company
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("police:edit")
    @RequestMapping(value = "/deleteMakeCompany")
    //@ResponseBody
    public String deleteMakeCompany(Company company, HttpServletRequest request, HttpServletResponse response) {
        company.setCompState(CompanyState.LOGOUT);
        company = policeCompanyService.getMakeCompany(company);
        Condition condition = new Condition();
        policeCompanyService.deleteMakeCom(condition, company);
        policeLicenseService.deleteMakeCom(condition, company);
      //  return JsonMapper.toJsonString(condition);
        return "redirect:" + adminPath + "/policeMakeComPage/showMakeCompany";

    }

    /**
     * 更新刻章公司运行状态
     *
     * @param company
     * @return
     */
    @RequiresPermissions("police:edit")
    @RequestMapping(value = "/updateComState")
  //  @ResponseBody
    public String updateComState(Company company,String keyId,String id) {
        company.setCompType(CompanyType.MAKE);

        company.setId(id);

        company = companyService.get(company);
        if(keyId!=null&&keyId.equals("2")){
            company.setCompState(CompanyState.USING);
        }else{//暂停操作
            company.setCompState(CompanyState.STOP);
        }

        Condition condition = new Condition();

        policeCompanyService.updateComState(condition, company);
        //return JsonMapper.toJsonString(condition);

        return "redirect:" + adminPath + "/policeMakeComPage/showMakeCompany";
    }


    /*
     *@author hjw
     *@description  验证刻章点录入数据合法性
     *@param [licenseApplyDTO, multipartFiles, condition]
     *@return void
     *@date 2017/7/5
     */
    private void companyValidate(MakeComDTO makeComDTO, MultipartFile[] multipartFiles, Condition condition) {
        StringBuffer sb = new StringBuffer();

        List<String> fileTypes = makeComDTO.getFileType();

        if(makeComDTO.getCompany().getLegalName().length() == 0){
            sb.append("法定代表人姓名不能为空\n");
        }
        if(makeComDTO.getCompany().getLegalPhone().length() == 0){
            sb.append("法人联系电话不能为空\n");
        }
        if(makeComDTO.getCompany().getCompanyName().length() == 0){
            sb.append("企业名称不能为空\n");
        }
        if(makeComDTO.getCompany().getCompAddress().length() == 0){
            sb.append("企业地址不能为空\n");
        }
        if(makeComDTO.getCompany().getLegalCertCode().length() == 0){
            sb.append("法人身份证号码不能为空\n");
        }
        if(makeComDTO.getCompany().getPoliceName().length() == 0){
            sb.append("治安负责人姓名不能为空\n");
        }
        if(makeComDTO.getCompany().getPoliceIdCode().length() == 0){
            sb.append("治安负责人身份证号码不能为空\n");
        }
        if (makeComDTO.getCompany().getBusModel().length() == 0) {
            sb.append("经营方式不能为空\n");
        }
        if (makeComDTO.getCompany().getHeadCount().length() == 0) {
            sb.append("总人数不能为空\n");
        }
        if (makeComDTO.getCompany().getSpecialCount().length() == 0) {
            sb.append("特业从业人数不能为空\n");
        }
        if(multipartFiles == null || multipartFiles.length < makeComDTO.getFileType().size()){
            sb.append("请上传所有的附件\n");
        }

        for(MultipartFile file:multipartFiles){

            if(file.getSize()==0){

                condition.setCode(Condition.ERROR_CODE);

                sb.append("请上传完整的附件!\n");

                break;
            }

        }

        // TODO
        // 附件验证
        if (multipartFiles.length < 0) {

            condition.setCode(Condition.ERROR_CODE);

            sb.append("附件材料不齐!\n");
        }

        if (multipartFiles.length < fileTypes.size()) {

            System.out.println(multipartFiles.length);
            System.out.println(fileTypes.size());
            condition.setCode(Condition.ERROR_CODE);

            sb.append("附件不能为空!\n");

        }

        //to sum up
        if(condition.getCode()==Condition.ERROR_CODE){
            condition.setMessage(sb.toString());
        }
    }

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

}
