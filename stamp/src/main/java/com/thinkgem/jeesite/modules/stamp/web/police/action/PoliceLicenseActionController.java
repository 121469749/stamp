package com.thinkgem.jeesite.modules.stamp.web.police.action;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CheckState;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.exception.police.RegisterMakeComException;
import com.thinkgem.jeesite.modules.stamp.service.licence.LicenceService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceLicenseService;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
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

/**
 * Created by hjw-pc on 2017/5/20.
 */
@Controller
@RequestMapping(value = "${adminPath}/policeLicenseAction")
public class PoliceLicenseActionController extends BaseController{

    @Autowired
    private  LicenceService licenceService;
    @Autowired
    private  PoliceLicenseService policeLicenseService;
    @Autowired
    private  AttachmentService attachmentService;

    /**
     * 审核刻章license
     * @param licence
     * @param model
     * @return
     */
    @RequestMapping(value = "checkLicenseForm")
    public String checkLicenseForm(Licence licence, Model model){
        licence = licenceService.get(licence);

        model.addAttribute("licence", licence);
        model.addAttribute("checkStates", CheckState.values());
        return "modules/stamp/units/police/checkUpdateLicence";

    }
    /**
     * 更新Licence, 审核状态
     * @param licence
     * @return
     */
    @RequiresPermissions("police:edit")
    @ResponseBody
    @RequestMapping(value = "updateCheckState",method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    public String updateCheckState(Licence licence){
        Condition condition = new Condition();
        StringBuffer sb = new StringBuffer();
        try{
            Boolean isValidated = true;
            if(licence.getCheckReason().length() == 0){
                sb.append("评审意见不能为空");
                isValidated = false;
            }
            if(licence.getCheckState() != CheckState.CHECKFAIL && licence.getCheckState() != CheckState.CHECKSUCCESS){
                sb.append("请选择是否通过审核");
                isValidated = false;
            }

            if(!isValidated){
                throw new RuntimeException();
            }

            policeLicenseService.updateCheckState(condition, licence);

        }catch (Exception e){
            System.out.println(e.toString());
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage(sb.toString());
             System.out.println(e.toString());
            return JsonMapper.toJsonString(condition);
        }
        condition.setCode(Condition.SUCCESS_CODE);
        condition.setMessage("提交成功");

        //保存成功 返回重定向url 前端跳转
        condition.setUrl(Global.getAdminPath()+"/policeMakeComPage/showCheckingCompany");
        return JsonMapper.toJsonString(condition);


    }

//    /**
//     *@author hjw
//     *@description  附件上传
//     *@param [multipartFile, attachment]
//     *@return java.lang.String
//     *@date 2017/5/25
//     */
//    @RequestMapping(value = "uploadAttachment")
//    @ResponseBody
//    public String uploadAttachment(@RequestParam(value = "attachment")MultipartFile multipartFile , Attachment attachment){
//        Condition condition= attachmentService.saveAttachment(multipartFile,attachment);
//        System.out.println(condition.getEntity().toString());
//        return JsonMapper.toJsonString(condition);
//    }




}
