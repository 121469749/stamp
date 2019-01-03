/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.areaattachment.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.areaattachment.dto.AreaAttachmentDTO;
import com.thinkgem.jeesite.modules.areaattachment.entity.AreaAttachment;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentExistException;
import com.thinkgem.jeesite.modules.areaattachment.service.AreaAttachmentService;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 区域对应办事附件Controller
 *
 * @author Locker
 * @version 2017-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/areaattachment/areaAttachment")
public class AreaAttachmentController extends BaseController {

    @Autowired
    private AreaAttachmentService areaAttachmentService;

    /**
     *
     * 区域附件列表显示
     *
     * @param areaAttachment
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list(AreaAttachment areaAttachment, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<AreaAttachment> page = areaAttachmentService.findPage(new Page<AreaAttachment>(request, response), areaAttachment);

        model.addAttribute("page", page);

        return "modules/areaattachment/areaAttachmentList";
    }

    /**
     *
     * 保存区域附件
     *
     * @param areaAttachmentDTO
     * @return
     */
    @RequestMapping(value = "/save", produces = {"text/plain;charset=UTF-8"}, method = RequestMethod.POST)
    @ResponseBody
    public String addNewAreaAttachment(AreaAttachmentDTO areaAttachmentDTO) {

        Condition condition = null;

        try {

            condition = beanValidatorAreaAttachmentDTO(areaAttachmentDTO);

            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);
            }

            condition = areaAttachmentService.saveAreaAttachment(areaAttachmentDTO);

            condition.setUrl(Global.getAdminPath()+"/areaattachment/areaAttachment/list");

            condition.setMessage("区域附件修改成功!");

        } catch (AreaAttachmentExistException e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "该区域，已经存在该办事类型的设定!\n请不要再次添加!");

        } catch (AreaAttachmentException e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "信息有误,请正确填写!");

        } finally {

            return JsonMapper.toJsonString(condition);
        }
    }

    /**
     *
     * 跳转到新建区域附件页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/form")
    public String toForm(Model model) {

        model.addAttribute("areaAttachmentDTO", new AreaAttachmentDTO());

        return "modules/areaattachment/areaAttachmentForm";
    }

    /**
     *
     * 获得 单个区域附件信息
     *
     * @param areaAttachmentId
     * @param model
     * @return
     */
    @RequestMapping(value="/get")
    public String showAreaAttachment(@RequestParam(value="id") String areaAttachmentId, Model model){

        AreaAttachmentDTO areaAttachmentDTO = areaAttachmentService.getAreaAttachmentById(areaAttachmentId);

        model.addAttribute("areaAttachmentDTO",areaAttachmentDTO);

        return "modules/areaattachment/areaAttachmentForm";
    }

    /**
     * 删除操作
     * @param areaAttachmentId
     * @return
     */
    @RequestMapping(value="/delete")
    public String deleteAreaAttachment(@RequestParam(value="id") String areaAttachmentId){

        areaAttachmentService.deleteAreaAttachment(areaAttachmentId);

        return "redirect:"+Global.getAdminPath()+"/areaattachment/areaAttachment/?repage";
    }

    /**
     * 输入验证
     *
     * @param areaAttachmentDTO
     * @return
     */
    private Condition beanValidatorAreaAttachmentDTO(AreaAttachmentDTO areaAttachmentDTO) {


        AreaAttachment areaAttachment = areaAttachmentDTO.getAreaAttachment();
        //对地区的验证
        if (!areaAttachmentService.checkAreaIfExist(areaAttachment.getArea().getId())) {

            return new Condition(Condition.ERROR_CODE, "地区不正确!");
        }

        //对业务类型的验证

        Dict serviceType = new Dict("service_type",areaAttachment.getType(),null);

        if (!areaAttachmentService.checkDictValueExist(serviceType)) {

            return new Condition(Condition.ERROR_CODE, "业务类型不正确!");
        }

        if(areaAttachmentDTO.getAttachList()==null||areaAttachmentDTO.getAttachList().size()==0){

            return new Condition(Condition.ERROR_CODE, "附件类型不能为空!");

        }

        //对文件类型字典的验证

        List<String> dictValue = areaAttachmentDTO.getAttachList();


        Dict fileType = null;

        Condition condition = new Condition(Condition.SUCCESS_CODE);

        for (String str : dictValue) {

            if("on".equals(str)){
                continue;
            }

            fileType = new Dict("file_type",str,null);

            if (!areaAttachmentService.checkDictValueExist(fileType)) {

                condition.setMessage("附件类型不正确!");

                condition.setCode(Condition.ERROR_CODE);

                break;
            }

        }

        return condition;
    }

}