package com.thinkgem.jeesite.modules.stamp.web.stampMake;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Moulage;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.StampMakeService;
import com.thinkgem.jeesite.modules.stamp.service.stamp.MoulageService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 *
 *
 *
 * Created by Locker on 2017/8/27.
 */
@Controller
@RequestMapping(value="${adminPath}/stampMakeInfo")
public class StampMakeInfoController {


    @Autowired
    private MoulageService moulageService;

    @Autowired
    private StampMakeService stampMakeService;


    @Autowired
    private AttachmentService attachmentService;

    /**
     * 通过id 获得单个印模信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value="/getMoulageInfo", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getMoulageInfo(@RequestParam(value="id") String id){

        Company makeCompany = UserUtils.getUser().getCompanyInfo();
        Moulage moulage = new Moulage();

        moulage.setId(id);
        moulage.setMakeCompany(makeCompany);

        moulage = moulageService.get(moulage);

        return JsonMapper.toJsonString(moulage);
    }

    /**
     * 通过印章id
     * 获得原有印章物理印模的信息
     *
     * @param stampId
     * @return
     */
    @RequestMapping(value = "/repairMoulage", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public Moulage repairMoulage(@RequestParam(value = "id") String stampId) {

        Moulage moulage = stampMakeService.getMoulage(stampId);

        return moulage;
    }


    /**
     * 通过社会统一码 获得用章单位信息
     * @param companyName
     * @return
     */
    @RequestMapping(value = "/findUseCompany", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getUserCompanyInfo(String companyName){

        Condition condition = stampMakeService.findUseCompany(companyName);

        return JsonMapper.toJsonString(condition);
    }

    /**
     * 验证文件是否存在
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/checkEleModelFileExist", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String checkEleModelFileExist(@RequestParam(value="id")String id, HttpServletRequest request, HttpServletResponse response){

        Stamp stamp = new Stamp(id);

        stamp.setStampShape(StampShape.PHYSTAMP.getKey());

        stamp=stampMakeService.findStampInfo(stamp);

        String eleModel = stamp.getEleModel();

        File file = new File(Global.getConfig("attachment.absolutePath")+eleModel);

        if(file.exists()){

            return JsonMapper.toJsonString(new Condition(Condition.SUCCESS_CODE));
        }else{

            return JsonMapper.toJsonString(new Condition(Condition.ERROR_CODE));
        }

    }


    /**
     * 下载电子印模图
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value="/download/EleModelFile")
    public void EleModelFileDownload(@RequestParam(value="id")String id, HttpServletRequest request, HttpServletResponse response){

        Stamp stamp = new Stamp(id);

        stamp.setStampShape(StampShape.PHYSTAMP.getKey());

        stamp=stampMakeService.findStampInfo(stamp);

        String eleModel = stamp.getEleModel();

        File file = new File(Global.getConfig("attachment.absolutePath")+eleModel);

        outputFile(file,response,eleModel);

    }


    /**
     * 验证文件是否存在
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/checkFileExist", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String checkFileExist(@RequestParam(value="id")String id, HttpServletRequest request, HttpServletResponse response){

        Attachment attachment = new Attachment(id);

        attachment = attachmentService.getAttachment(attachment);

        File file = new File(Global.getConfig("attachment.absolutePath")+attachment.getAttachPath());

        if(file.exists()){

            return JsonMapper.toJsonString(new Condition(Condition.SUCCESS_CODE));
        }else{

            return JsonMapper.toJsonString(new Condition(Condition.ERROR_CODE));
        }

    }

    /**
     * 下载文件
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value="/download/file")
    public void fileDownload(@RequestParam(value="id")String id, HttpServletRequest request, HttpServletResponse response){

        Attachment attachment = new Attachment(id);

        attachment = attachmentService.getAttachment(attachment);

        String attachPath = attachment.getAttachPath();

        File file = new File(Global.getConfig("attachment.absolutePath")+attachPath);

        outputFile(file,response,attachPath);

    }

    protected void outputFile(File file,HttpServletResponse response,String attachName){

        if(file.exists()){

            String[] fileName=attachName.split("/");

            try {
                InputStream in = new FileInputStream(file);
                response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName[fileName.length-1], "UTF-8"));
                OutputStream out = response.getOutputStream();
                int len = 0;
                //5.创建数据缓冲区
                byte[] buffer = new byte[1024];
                //6.通过response对象获取OutputStream流
                //7.将FileInputStream流写入到buffer缓冲区
                while ((len = in.read(buffer)) > 0) {
                    //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
