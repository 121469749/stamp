package com.thinkgem.jeesite.modules.sign.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.sign.entity.Document;
import com.thinkgem.jeesite.modules.sign.service.DocumentInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/9/14.
 */
@Controller
@RequestMapping(value="${adminPath}/document/info")
public class DocumentInfoController {

    @Autowired
    private DocumentInfoService documentInfoService;


    /**
     *
     * @param document
     * @param model
     * @return
     */
    @RequestMapping(value="/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Document document, Model model){


        Page<Document> page = new Page<Document>(request,response);
        page = documentInfoService.findPage(page,document);
        System.out.println(page.getList().toString());
        model.addAttribute("document",document);
        model.addAttribute("page",page);
        model.addAttribute("user",UserUtils.getUser());

        //todo 文件列表页面
        return "modules/sign/sign_file_list";
    }

    /**
     * 跳转到文件上传页面
     * @return
     */
    @RequestMapping(value="/uploadPage")
    public String upload(){
        return "modules/sign/upload-file";
    }

    /**
     * 查看未签署文件
     * @param documentId
     * @param model
     * @return
     */
    @RequestMapping(value="/orginal/get")
    public String orgGet(@RequestParam(value="id")String documentId, Model model){
        model.addAttribute("document",documentId);
        return "modules/sign/check-undone-file";
    }

    /**
     * 真正浏览文件页面
     * @param documentId
     * @param model
     * @return
     */
    @RequestMapping(value="/orginal/show")
    public String orgShow(@RequestParam(value="id")String documentId, Model model){

        Document document = new Document(documentId);
        document.setUser(UserUtils.getUser());

        document = documentInfoService.get(document);

        model.addAttribute("document",document);

        return "modules/sign/viewer-check";
    }

    /**
     * 查看签署文件页面
     * @param documentId
     * @param model
     * @return
     */
    @RequestMapping(value="/singture/get")
    public String singatureGet(@RequestParam(value="id")String documentId, Model model){

        model.addAttribute("document",documentId);

        return "modules/sign/check-done-file";

    }

    @RequestMapping(value = "/singture/show")
    public String singatrueShow(@RequestParam(value="id")String documentId, Model model){

        Document document = new Document(documentId);

        document.setUser(UserUtils.getUser());

        document = documentInfoService.get(document);

        model.addAttribute("document",document);

        return "modules/sign/sign_prev_iframe";
    }


}

