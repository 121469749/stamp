package com.thinkgem.jeesite.modules.sign.web;

import com.thinkgem.jeesite.modules.sign.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.sign.entity.Seal;
import com.thinkgem.jeesite.modules.sign.service.SealFindService;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Locker on 2017/9/16.
 */
@Controller
@RequestMapping(value="${adminPath}/seal")
public class SealFindController {

    @Autowired
    private SealFindService findService;


    @RequestMapping(value="/list", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String list(@RequestParam(value="id")String id){

        String[] ids = id.split(",");

        List<Stamp> list = findService.findList(ids);


        return JsonMapper.toJsonString(list);
    }


}
