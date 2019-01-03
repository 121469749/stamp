/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.log.entity.ModifyInfoLog;
import com.thinkgem.jeesite.modules.log.service.ModifyInfoLogService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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

/**
 * 操作日志明细Controller
 * @author xucaikai
 * @version 2018-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/log/tLogDetail")
public class ModifyInfoLogController extends BaseController {

	@Autowired
	private ModifyInfoLogService tLogDetailService;
	
	@ModelAttribute
	public ModifyInfoLog get(@RequestParam(required=false) String id) {
		ModifyInfoLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tLogDetailService.get(id);
		}
		if (entity == null){
			entity = new ModifyInfoLog();
		}
		return entity;
	}
	
	@RequiresPermissions("log:tLogDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(ModifyInfoLog tLogDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
//		tLogDetail.setSysUserType(UserUtils.getUser().getUserType().getKey());
		Page<ModifyInfoLog> page = tLogDetailService.findPage(new Page<ModifyInfoLog>(request, response), tLogDetail);
		model.addAttribute("page", page);
		model.addAttribute("tLogDetail", new ModifyInfoLog());
		model.addAttribute("sysUserType",tLogDetail.getSysUserType());
		return "modules/log/tLogDetailList";
	}

	@RequiresPermissions("log:tLogDetail:view")
		@RequestMapping(value = "form")
	public String form(ModifyInfoLog tLogDetail, Model model) {
		model.addAttribute("tLogDetail", tLogDetail);
		return "modules/log/tLogDetailForm";
	}

	@RequiresPermissions("log:tLogDetail:edit")
	@RequestMapping(value = "save")
	public String save(ModifyInfoLog tLogDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tLogDetail)){
			return form(tLogDetail, model);
		}
		tLogDetailService.save(tLogDetail);
		addMessage(redirectAttributes, "保存操作日志明细成功");
		return "redirect:"+Global.getAdminPath()+"/log/tLogDetail/?repage";
	}
	
	@RequiresPermissions("log:tLogDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(ModifyInfoLog tLogDetail, RedirectAttributes redirectAttributes) {
		tLogDetailService.delete(tLogDetail);
		addMessage(redirectAttributes, "删除操作日志明细成功");
		return "redirect:"+Global.getAdminPath()+"/log/tLogDetail/?repage";
	}

}