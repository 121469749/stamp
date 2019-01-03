package com.thinkgem.jeesite.modules.stamp.web.company.action;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.dto.useCompany.InsertOperationDto;
import com.thinkgem.jeesite.modules.stamp.service.stamprecord.StampOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sjk on 2017-06-13.
 */
@Controller
@RequestMapping(value = "${adminPath}/useCompanyLogAction")
public class UseCompanyLogActionController {

    @Autowired
    private StampOperationService stampOperationService;

    /**
     * 物理印章使用录入
     * @param webData
     * @return
     */
    @RequestMapping(value = "/insertOperation")
    @ResponseBody
    public String insertOperation(InsertOperationDto webData, HttpServletRequest request) {

        Condition condition = new Condition();
        condition = stampOperationService.insertPhyStampOpration(condition, webData, request);

        return JsonMapper.toJsonString(condition);
    }
}
