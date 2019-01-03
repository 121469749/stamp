package com.thinkgem.jeesite.modules.stamp.web.money;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.dto.money.MoneySettingDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.stamp.service.money.MoneySettingService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
@Controller
@RequestMapping(value="${adminPath}/moneySetting")
public class MoneySettingController {

    @Autowired
    private MoneySettingService moneySettingService;


//    @RequestMapping(value="/form")
//    public String form(Model model){
//
//        MoneySettingVo moneySettingVo = moneySettingService.findCurrentMoneySetting();
//
//        model.addAttribute("moneySettingVo", moneySettingVo);
//
//        Company currentCompany = moneySettingService.getCurrentArea();
//
//        model.addAttribute("company",currentCompany);
//
//        return "modules/jsps/moneySetting/moneySettingForm";
//    }

    @RequestMapping(value="/form")
    public String Form(Model model){

        List<Dict> dicts = moneySettingService.findStampTextureDict();

        System.out.println(dicts);

        model.addAttribute("dicts",dicts);

        return "modules/jsps/moneySetting/runcheng/moneySettingForm";
    }



}
