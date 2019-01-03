package com.thinkgem.jeesite.modules.stamp.common.util.attachment.web;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.TestEntity;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import org.apache.ibatis.annotations.Param;
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
 * Created by Locker on 17/5/9.
 */
@Controller
@RequestMapping(value = "${adminPath}/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * @author 许彩开
     * @TODO (注：开始异步上传图片)
     * @param files
     * @DATE: 2018\8\21 0021 11:16
     */

    @RequestMapping(value = "startUploadImage" ,method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String startUploadImage(@RequestParam(value = "file") MultipartFile[] files){

        Condition condition = new Condition();
        condition = attachmentService.startUploadImage(files);

        return JsonMapper.toJsonString(condition);
    }

    /**
     * @author 许彩开
     * @TODO (注：删除临时文件夹里的文件)
     * @param fileName
     * @DATE: 2018\8\24 0024 14:43
     */

    @RequestMapping(value = "deleteTempFile")
    @ResponseBody
    public String deleteTempFile(String fileName){

      Condition condition = attachmentService.deleteTempFile(fileName);

      return JsonMapper.toJsonString(condition);

    }


//
//    @RequestMapping(value = "/attachmentTest")
//    public String fileTest() {
//
//
//        return "modules/test/testAttachmentForm";
//    }

//    @RequestMapping(value = "/saveAttachment", method = RequestMethod.POST)
//    @ResponseBody
//    public String saveFile(@RequestParam(value = "attachment") MultipartFile realAttachment, Attachment attachment) {
//        Condition condition = attachmentService.saveAttachment(realAttachment, attachment);
//        return JsonMapper.toJsonString(condition);
//    }
//    @RequestMapping(value="/test")
//    public String testController(Model model){
//
//        model.addAttribute("userTypes", UserType.values());
//        model.addAttribute("testEntity",new TestEntity());
//        return "modules/test/testAttachmentForm";
//    }
//
//    @RequestMapping(value="/submitTest")
//    public void submitTest(TestEntity testEntity){
//
//        System.out.println("toString:"+testEntity.toString());
//        System.out.println("Enum Test:"+testEntity.getUserType());
//        System.out.println("name Test:"+testEntity.getName());
//    }
//
//    @RequestMapping(value="/testAttachment")
//    public String testAttachs(){
//
//
//        return  "modules/test/testAttachment";
//    }
//
//    @RequestMapping(value="/testSave",method = RequestMethod.POST)
//    @ResponseBody
//    public Condition testsubmit(@RequestParam(value = "attachment")MultipartFile multipartFile ,Attachment attachment){
//
//
//       Condition condition= attachmentService.saveAttachment(multipartFile,attachment);
//
//       System.out.println(condition.toString());
//
//       return condition;
//    }

//    @RequestMapping(value="/getAttachment",method = RequestMethod.POST,produces = { "text/plain;charset=UTF-8" })
//    @ResponseBody
//    public String getAttachmentsByRecordId(@RequestParam(value="id")String id,
//                                           @RequestParam(value="fileType")String fileType) throws Exception {
//
//        List<Attachment> attachmentList=attachmentService.findAttachmentByRecordAndType(id,fileType);
//
//        System.out.println(attachmentList.toString());
//
//        return JsonMapper.toJsonString(attachmentList);
//
//    }
}
