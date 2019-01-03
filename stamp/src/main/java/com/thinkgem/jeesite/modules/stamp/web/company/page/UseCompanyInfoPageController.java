package com.thinkgem.jeesite.modules.stamp.web.company.page;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sjk on 2017-06-13.
 */
@Controller
@RequestMapping(value = "${adminPath}/useCompanyInfoPage")
public class UseCompanyInfoPageController {

    @Autowired
    private CompanyService companyService;

    /**
     * 显示用章公司首页
     * @return
     */
    @RequestMapping(value = "/showUseComIndex")
    public String showUseComIndex(Model model) {

        Company company = companyService.get(new Company(UserUtils.getUser().getUserTypeId(), CompanyType.USE));

        model.addAttribute("company", company);

        return "modules/jsps/useUnit/useUnit-index";
    }


    /**
     * 跳转到修改用章公司信息页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/showComInfo")
    public String showComInfo(Model model) {

        Company company = companyService.get(new Company(UserUtils.getUser().getUserTypeId(), CompanyType.USE));

        model.addAttribute("company", company);

        return "modules/jsps/useUnit/useUnit-modify-information";
    }
}
