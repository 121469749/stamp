/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.web.company;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;

/**
 * 公司Controller
 * @author Locker
 * @version 2017-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/units/company")
public class CompanyController extends BaseController {

	@Autowired
	private CompanyService companyService;
	
	@ModelAttribute
	public Company get(@RequestParam(required=false) String id) {
		Company entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyService.get(id);
		}
		if (entity == null){
			entity = new Company();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Company company, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Company> page = companyService.findPage(new Page<Company>(request, response), company); 
		model.addAttribute("page", page);
		System.out.println("I'm here!");
		return "modules/stamp/units/company/companyList";
	}

	@RequestMapping(value = "form")
	public String form(Company company, Model model) {
		model.addAttribute("company", company);
		model.addAttribute("companyStates", CompanyState.values());
		model.addAttribute("companyTypes", CompanyType.values());
		return "modules/stamp/units/company/companyForm";
	}

	@RequestMapping(value = "save")
	public String save(Company company, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, company)){
			return form(company, model);
		}
		companyService.save(company);
		addMessage(redirectAttributes, "保存公司成功");
		return "redirect:"+Global.getAdminPath()+"/units/company/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Company company, RedirectAttributes redirectAttributes) {
		companyService.delete(company);
		addMessage(redirectAttributes, "删除公司成功");
		return "redirect:"+Global.getAdminPath()+"/units/company/?repage";
	}
}