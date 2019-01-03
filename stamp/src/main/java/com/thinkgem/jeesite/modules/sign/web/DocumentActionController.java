package com.thinkgem.jeesite.modules.sign.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sign.common.condition.Condition;
import com.thinkgem.jeesite.modules.sign.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.sign.entity.Document;
import com.thinkgem.jeesite.modules.sign.service.DocumentActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Locker on 17/9/15.
 */
@Controller
@RequestMapping(value="${adminPath}/document/info")
public class DocumentActionController extends BaseController{

    @Autowired
    private DocumentActionService documentActionService;

    /**
     * 用户上传文件
     * @param file
     * @param document
     * @return
     */
    @RequestMapping(value="/upload")
    @ResponseBody
    public String upload(@RequestParam(value="file",required = false)MultipartFile file, Document document){

        if(file==null||file.isEmpty()){

            return JsonMapper.toJsonString(new Condition(Condition.ERROR_CODE,"请选择文件上传!"));

        }
        //  获取该文件的后缀文件类型
        int lastIndex = file.getOriginalFilename().lastIndexOf(".");

        String lastName = file.getOriginalFilename().substring(lastIndex, file.getOriginalFilename().length());

        if(!".pdf".equals(lastName)){


            return JsonMapper.toJsonString(new Condition(Condition.ERROR_CODE,"目前只支持PDF文件!"));
        }

        Condition condition = documentActionService.add(document,file);

        return JsonMapper.toJsonString(condition);
    }


    /**
     * 删除文件
     * @param document
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/delete")
    public String delete(Document document, RedirectAttributes redirectAttributes) {
        documentActionService.deleteDoc(document);
        addMessage(redirectAttributes, "文件删除成功！");
        return "redirect:"+ Global.getAdminPath()+"/document/info/index";
    }

}

