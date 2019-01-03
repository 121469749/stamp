package com.thinkgem.jeesite.modules.sign.web;

import com.thinkgem.jeesite.modules.sign.common.condition.Condition;
import com.thinkgem.jeesite.modules.sign.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.sign.entity.Document;
import com.thinkgem.jeesite.modules.sign.entity.Seal;
import com.thinkgem.jeesite.modules.sign.service.CheckService;
import com.thinkgem.jeesite.modules.sign.service.DocumentInfoService;
import com.thinkgem.jeesite.modules.sign.service.SealFindService;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Locker on 2017/8/31.
 */
@Controller
@RequestMapping(value="${adminPath}/check")
public class CheckController {

    @Autowired
    private CheckService checkService1;

    @Autowired
    private DocumentInfoService documentInfoService;

    @Autowired
    private SealFindService sealFindService;


    @RequestMapping(value="/index")
    public String page(@RequestParam(value="id")String documentId, Model model){

        Document document = new Document(documentId);

        document.setUser(UserUtils.getUser());

        document = documentInfoService.get(document);

        if(document!=null){
            Stamp stamp = sealFindService.findStamp(document.getSeal().getId());
            document.setStamp(stamp);
        }

        model.addAttribute("document",document);
        return "modules/sign/check-stamp";
    }

    /**
     * 检测文件MD5
     * @param documentId
     * @param multipartFile
     * @return
     */
    @RequestMapping(value="/checkExist")
    @ResponseBody
    public String checkExist(@RequestParam(value="id") String documentId, @RequestParam(value="file")MultipartFile file){

        Condition condition = documentInfoService.checkExist(documentId,file);

        return JsonMapper.toJsonString(condition);
    }


    /**
     * 验章Action
     * @param file
     * @return
     */
    @RequestMapping(value="/document")
    @ResponseBody
    public String checkDocument(@RequestParam(value = "file",required = false)MultipartFile file){

        if(file==null||file.isEmpty()){
            return JsonMapper.toJsonString(new Condition(Condition.ERROR_CODE,"请上传文件!"));
        }

        Document document = checkService1.checkFile(file);

        return JsonMapper.toJsonString(document);

    }

    @RequestMapping(value="/page")
    public String checkPage(@RequestParam(value = "id",required = false)String documentId, Model model){

        Document document =documentInfoService.get(new Document(documentId));

        Seal seal =sealFindService.get(document.getSeal().getId());

        document.setSeal(seal);

        model.addAttribute("document",document);

        return "modules/check-message";
    }

}
