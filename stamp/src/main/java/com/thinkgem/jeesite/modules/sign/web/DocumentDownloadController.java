package com.thinkgem.jeesite.modules.sign.web;

import com.thinkgem.jeesite.modules.sign.common.config.Global;
import com.thinkgem.jeesite.modules.sign.entity.Document;
import com.thinkgem.jeesite.modules.sign.service.DocumentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/9/17.
 */
@Controller
@RequestMapping(value = "${adminPath}/download")
public class DocumentDownloadController {


    @Autowired
    private DocumentInfoService documentInfoService;

    @RequestMapping(value = "/document/org")
    public void org(@RequestParam(value = "id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Document document = new Document(id);
        document = documentInfoService.get(document);

        String filePath = Global.getConfig("file.realPath") + document.getFileOrgPath();
        System.out.println("document.getFileName():"+document.getFileName());

        response.setHeader("content-disposition", "attachment;filename=" + new String(document.getFileName().getBytes("gb2312"),"ISO8859-1") + ".pdf");

        InputStream in = new FileInputStream(filePath);

        int len = 0;
        byte[] buffer = new byte[1024];

        OutputStream out = response.getOutputStream();

        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
    }


    @RequestMapping(value = "/document/sinature")
    public void singature(@RequestParam(value = "id") String id, HttpServletResponse response) throws IOException {

        Document document = new Document(id);

        document = documentInfoService.get(document);

        String filePath = Global.getConfig("file.realPath") + document.getFileSinaturePath();

        response.setHeader("content-disposition", "attachment;filename=" + document.getFileName() + ".pdf");


        InputStream in = new FileInputStream(filePath);

        int len = 0;
        byte[] buffer = new byte[1024];
        OutputStream out = response.getOutputStream();

        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
    }

}
