package com.thinkgem.jeesite.modules.stamp.web.water;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.entity.water.Water;
import com.thinkgem.jeesite.modules.stamp.service.water.WaterService;
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

/**
 * Created by Administrator on 2017/11/18.
 */
@Controller
@RequestMapping(value = "${adminPath}/water")
public class WaterController {


    @Autowired
    private WaterService waterService;


    @RequestMapping(value="/page")
    public String page(Water water, HttpServletRequest request, HttpServletResponse response, Model model){

        Page<Water> page = new Page<Water>(request,response);

        page = waterService.findPage(water,page);

        model.addAttribute("water",water);
        model.addAttribute("page",page);
        //todo
        return "modules/jsps/waterImage/waterImage-list";
    }

    @RequestMapping(value="/form")
    public String get(Water water,Model model){

        if(water.getId()!=null&&(!water.getId().equals(""))){

            water = waterService.get(water);
        }

        model.addAttribute("water",water);

        //todo
        return "modules/jsps/waterImage/waterImage-add";
    }


    @RequestMapping(value="/save", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String save(Water water, @RequestParam(value="file",required = false)MultipartFile file){

        Condition condition = validWater(water,file);

        if(condition.getCode()==Condition.ERROR_CODE){
            return JsonMapper.toJsonString(condition);
        }

        condition = waterService.save(water,file);

        return JsonMapper.toJsonString(condition);
    }


    @RequestMapping(value="/delete", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"} )
    @ResponseBody
    public String delete(Water water){

        Condition condition =waterService.delete(water);

        return JsonMapper.toJsonString(condition);

    }

    /**
     * 验证
     * @param water
     * @param file
     */
    protected Condition validWater(Water water,MultipartFile file){

        StringBuffer message = new StringBuffer();

        Condition condition = new Condition(Condition.SUCCESS_CODE);

        if(water.getName()==null&&water.getName().equals("")){

            message.append("水印名称不能为空!\n");
            condition.setCode(Condition.ERROR_CODE);
        }

        if(water.getId()!=null &&(!water.getId().equals(""))){


        }else{

            if(file.isEmpty()){
                message.append("水印图不能为空!\n");
                condition.setCode(Condition.ERROR_CODE);
            }

        }

        if(condition.getCode()==Condition.ERROR_CODE){

            condition.setMessage(message.toString());

        }

        return condition;
    }


}
