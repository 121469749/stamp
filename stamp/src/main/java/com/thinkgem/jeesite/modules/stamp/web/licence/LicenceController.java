/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.web.licence;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.service.licence.LicenceService;

/**
 * 许可证业务信息Controller
 * @author Locker
 * @version 2017-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/stamp.info/licence/licence")
public class LicenceController extends BaseController {

	@Autowired
	private LicenceService licenceService;

	@Autowired
	private CompanyService companyService;


	@ModelAttribute
	public Licence get(@RequestParam(required=false) String id) {
		Licence entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = licenceService.get(id);
		}
		if (entity == null){
			entity = new Licence();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Licence licence, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Licence> page = licenceService.findPage(new Page<Licence>(request, response), licence);
		model.addAttribute("page", page);
		return "modules/stamp.info/licence/licenceList";
	}


	public String form(Licence licence, Model model) {

		model.addAttribute("licence", licence);

		if(licence.getWorkType() == WorkType.OPEN){

			return "开办申请form表单";
		}

		if(licence.getWorkType() == WorkType.CHANGE){

			return  "变更申请form表单";
		}

		if(licence.getWorkType() == WorkType.ANNUAL){
			return "年审form表单";
		}

		if(licence.getWorkType() == WorkType.LOGOUT){

			return "撤销form表单";
		}

		return "错误请求";
	}

	@RequestMapping(value = "save")
	public String save(Licence licence, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, licence)){
			return form(licence, model);
		}
		licenceService.save(licence);
		addMessage(redirectAttributes, "保存许可证业务成功");
		return "redirect:"+Global.getAdminPath()+"/stamp.info/licence/licence/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Licence licence, RedirectAttributes redirectAttributes) {
		licenceService.delete(licence);
		addMessage(redirectAttributes, "删除许可证业务成功");
		return "redirect:"+Global.getAdminPath()+"/stamp.info/licence/licence/?repage";
	}

}