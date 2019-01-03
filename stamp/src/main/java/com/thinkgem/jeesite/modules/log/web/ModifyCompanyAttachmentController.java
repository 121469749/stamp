/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.thinkgem.jeesite.modules.log.entity.ModifyCompanyAttachment;
import com.thinkgem.jeesite.modules.log.service.ModifyCompanyAttachmentService;

/**
 * 企业信息变更Controller
 * @author linzhibao
 * @version 2018-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/stamp/modifyCompanyAttachment")
public class ModifyCompanyAttachmentController extends BaseController {

	@Autowired
	private ModifyCompanyAttachmentService modifyCompanyAttachmentService;
	
	@ModelAttribute
	public ModifyCompanyAttachment get(@RequestParam(required=false) String id) {
		ModifyCompanyAttachment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = modifyCompanyAttachmentService.get(id);
		}
		if (entity == null){
			entity = new ModifyCompanyAttachment();
		}
		return entity;
	}
	
	@RequiresPermissions("stamp:modifyCompanyAttachment:view")
	@RequestMapping(value = {"list", ""})
	public String list(ModifyCompanyAttachment modifyCompanyAttachment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ModifyCompanyAttachment> page = modifyCompanyAttachmentService.findPage(new Page<ModifyCompanyAttachment>(request, response), modifyCompanyAttachment); 
		model.addAttribute("page", page);
		return "log/stamp/modifyCompanyAttachmentList";
	}

	@RequiresPermissions("stamp:modifyCompanyAttachment:view")
	@RequestMapping(value = "form")
	public String form(ModifyCompanyAttachment modifyCompanyAttachment, Model model) {
		model.addAttribute("modifyCompanyAttachment", modifyCompanyAttachment);
		return "log/stamp/modifyCompanyAttachmentForm";
	}

	@RequiresPermissions("stamp:modifyCompanyAttachment:edit")
	@RequestMapping(value = "save")
	public String save(ModifyCompanyAttachment modifyCompanyAttachment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, modifyCompanyAttachment)){
			return form(modifyCompanyAttachment, model);
		}
		modifyCompanyAttachmentService.save(modifyCompanyAttachment);
		addMessage(redirectAttributes, "保存xxx成功");
		return "redirect:"+Global.getAdminPath()+"/stamp/modifyCompanyAttachment/?repage";
	}
	
	@RequiresPermissions("stamp:modifyCompanyAttachment:edit")
	@RequestMapping(value = "delete")
	public String delete(ModifyCompanyAttachment modifyCompanyAttachment, RedirectAttributes redirectAttributes) {
		modifyCompanyAttachmentService.delete(modifyCompanyAttachment);
		addMessage(redirectAttributes, "删除xxx成功");
		return "redirect:"+Global.getAdminPath()+"/stamp/modifyCompanyAttachment/?repage";
	}

}