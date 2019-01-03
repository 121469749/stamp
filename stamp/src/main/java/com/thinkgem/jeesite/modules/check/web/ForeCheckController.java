package com.thinkgem.jeesite.modules.check.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.check.dto.QueryDTO;
import com.thinkgem.jeesite.modules.check.service.CheckService;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 前台查验平台相关view-controller
 *
 * Created by Locker on 2017/11/18.
 */
@Controller
@RequestMapping(value = "/check")
public class ForeCheckController {


    @Autowired
    private CheckService checkService;

    /**
     * 查询页面
     * @param dto
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/page")
    public String page(QueryDTO dto, HttpServletRequest request, HttpServletResponse response, Model model){

        Page<Stamp> page = checkService.findPage(dto,request,response);

        model.addAttribute("page",page);
        System.out.println("page.getList().size()="+page.getList().size());

        model.addAttribute("dto",dto);

        //todo
        return "modules/jsps/portalWeb/index-search";
    }

    @RequestMapping(value="/page/search")
    @ResponseBody
    public Page<Stamp> page2(QueryDTO dto,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             Model model){

        System.out.println("dto"+dto.getCompanyName());
        System.out.println("dto"+dto.getStampCode());
        System.out.println("dto"+dto.getStampName());
        System.out.println("dto"+dto.getStampShape());
        //QueryDTO dto = new QueryDTO();
        Page<Stamp> page = checkService.findPage(dto,request,response);

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        //todo
        return page;
    }

//    @RequestMapping(value="/detail")
//    public String detail(Stamp stamp,Model model){
//
//        stamp = checkService.findStamp(stamp);
//
//        model.addAttribute("stamp",stamp);
//
//        //todo
//        return "";
//    }


}
