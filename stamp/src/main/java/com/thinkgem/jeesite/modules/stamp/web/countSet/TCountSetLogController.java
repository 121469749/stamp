/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.web.countSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.TCountSetLog;
import com.thinkgem.jeesite.modules.stamp.service.countSet.TCountSetLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 历史分配记录Controller
 * @author howen
 * @version 2017-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/countsetlog/tCountSetLog")
public class TCountSetLogController extends BaseController {

	@Autowired
	private TCountSetLogService tCountSetLogService;

	@ModelAttribute
	public TCountSetLog get(@RequestParam(required=false) String id) {
		TCountSetLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCountSetLogService.get(id);
		}
		if (entity == null){
			entity = new TCountSetLog();
		}
		return entity;
	}

	@RequestMapping(value = {"list", ""})
	public String list( TCountSetLog tCountSetLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TCountSetLog> page = tCountSetLogService.findPage(new Page<TCountSetLog>(request, response), tCountSetLog);
		model.addAttribute("tCountSetLog",tCountSetLog);
		model.addAttribute("page", page);
		return "modules/jsps/countSet/tCountSetLogList";
	}

	@RequestMapping(value = {"list1"})
	public String list1(@RequestParam(value = "companyId") String companyId, TCountSetLog tCountSetLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCountSetLog.setCompanyId(companyId);
		Page<TCountSetLog> page = tCountSetLogService.findPage(new Page<TCountSetLog>(request, response), tCountSetLog);
		model.addAttribute("page", page);
		model.addAttribute("tCountSetLog",tCountSetLog);
		return "modules/jsps/countSet/tCountSetLogList";
	}




//	@RequiresPermissions("countsetlog:tCountSetLog:view")
//	@RequestMapping(value = "form")
//	public String form(TCountSetLog tCountSetLog, Model model) {
//		model.addAttribute("tCountSetLog", tCountSetLog);
//		return "stamp/countsetlog/tCountSetLogForm";
//	}

//	@RequiresPermissions("countsetlog:tCountSetLog:edit")
//	@RequestMapping(value = "save")
//	public String save(TCountSetLog tCountSetLog, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, tCountSetLog)){
//			return form(tCountSetLog, model);
//		}
//		tCountSetLogService.save(tCountSetLog);
//		addMessage(redirectAttributes, "保存数据保存成功成功");
//		return "redirect:"+Global.getAdminPath()+"/countsetlog/tCountSetLog/?repage";
//	}

//	@RequiresPermissions("countsetlog:tCountSetLog:edit")
//	@RequestMapping(value = "delete")
//	public String delete(TCountSetLog tCountSetLog, RedirectAttributes redirectAttributes) {
//		tCountSetLogService.delete(tCountSetLog);
//		addMessage(redirectAttributes, "删除数据保存成功成功");
//		return "redirect:"+Global.getAdminPath()+"/countsetlog/tCountSetLog/?repage";
//	}

}