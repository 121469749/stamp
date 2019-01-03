package com.thinkgem.jeesite.modules.stamp.web.picture;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.service.picture.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 照片相似度控制层
 * Created by Locker on 2017/7/15.
 */
@Controller
@RequestMapping(value = "${adminPath}/picture")
public class PictureController {

    protected static final String pattern1= "jpg|JPG|png|PNG|jpeg|JPEG|bmp|BMP|gif|GIF";

    @Autowired
    private PictureService pictureService;

    /**
     * 图片相似度比较
     *
     * @param files
     * @return
     */
    @RequestMapping(value = "/comparePicture1", method = RequestMethod.POST, produces = {" text/plain;charset=UTF-8 "})
    @ResponseBody
    public String compareTwoPicture1(@RequestParam(value = "picture") MultipartFile[] files) throws IOException {


        Condition condition = validationFile(files);

        if(condition.getCode() == Condition.ERROR_CODE){

            return JsonMapper.toJsonString(condition);

        }

        condition = pictureService.comparePicture(files);

        return JsonMapper.toJsonString(condition);
    }

    /**
     * 图片相似度比较
     *
     * @param files
     * @return
     */
    @RequestMapping(value = "/comparePicture2", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String compareTwoPicture2(@RequestParam(value = "picture") MultipartFile[] files) throws IOException {

        Condition condition = validationFile(files);

        if(condition.getCode() == Condition.ERROR_CODE){

            return JsonMapper.toJsonString(condition);

        }


        double result = pictureService.comparePicture2(files);

        condition = new Condition(Condition.SUCCESS_CODE);

        condition.setMessage("两张图片相似度:" + result * 100 + "%");

        return JsonMapper.toJsonString(condition);
    }

    @RequestMapping(value = "/picturePage")
    public String toPictureComparePage() {

        return "modules/jsps/picture-similarity";
    }

    private Condition validationFile(MultipartFile[] files) {

        StringBuffer stringBuffer = new StringBuffer();

        Condition condition = new Condition(Condition.SUCCESS_CODE);

        if (files.length < 2) {

            stringBuffer.append("请上传两张图片!\n");

            condition.setCode(Condition.ERROR_CODE);
        } else {

            if (files[0].getOriginalFilename() == null || "".equals(files[0].getOriginalFilename())) {

                stringBuffer.append("请正确上传图片2\n");
                condition.setCode(Condition.ERROR_CODE);
            }else{

                int lastIndex = files[0].getOriginalFilename().lastIndexOf(".");
                String lastName = files[0].getOriginalFilename().substring(lastIndex+1, files[0].getOriginalFilename().length());

                if(!lastName.matches(pattern1)){

                    stringBuffer.append("请正确上传图片1!\n");
                    condition.setCode(Condition.ERROR_CODE);

                }

            }

            if (files[1].getOriginalFilename() == null || "".equals(files[1].getOriginalFilename())) {


                stringBuffer.append("请正确上传图片2\n");
                condition.setCode(Condition.ERROR_CODE);
            }else{

                int lastIndex = files[1].getOriginalFilename().lastIndexOf(".");
                String lastName = files[1].getOriginalFilename().substring(lastIndex+1, files[1].getOriginalFilename().length());

                if(!lastName.matches(pattern1)){

                    stringBuffer.append("请正确上传图片2!\n");
                    condition.setCode(Condition.ERROR_CODE);

                }

            }



        }

        condition.setMessage(stringBuffer.toString());

        return condition;
    }




}
