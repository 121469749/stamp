package com.thinkgem.jeesite.modules.stamp.web.company.action;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.stamprecord.StampLogService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * Created by sjk on 2017-06-13.
 */
@Controller
@RequestMapping(value = "${adminPath}/useCompanyInfoAction")
public class UseCompanyInfoActionController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StampLogService stampLogService;

    /**
     * 更新公司信息
     * @param model
     * @param company
     * @return
     */
    @RequestMapping(value = "/updateComInfo",
                    method = RequestMethod.POST,
                    produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String updateComInfo(Model model, Company company, HttpServletRequest request, String startDate, String endDate) {

        Condition condition = new Condition();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (StringUtils.isNotBlank(startDate)) {
                //日期转换
                company.setBusStartDate(df.parse(startDate));
            }

            if (StringUtils.isNotBlank(endDate)) {
                //日期转换
                company.setBusEndDate(df.parse(endDate));
            }


            //保存更改
            company.setCompType(CompanyType.USE);
            companyService.updateBaseInfo(company);

            //记录日志
            stampLogService.insertLog("修改公司信息");

            condition.setCode(Condition.SUCCESS_CODE);
            condition.setMessage("修改成功");
            condition.setUrl("/useCompanyInfoPage/showUseComIndex");

            return JsonMapper.toJsonString(condition);

        } catch (Exception e) {

            e.printStackTrace();
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("系统繁忙");

            return JsonMapper.toJsonString(condition);
        }
    }
}
