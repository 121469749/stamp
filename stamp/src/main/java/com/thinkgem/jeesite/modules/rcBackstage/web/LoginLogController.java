package com.thinkgem.jeesite.modules.rcBackstage.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.entity.LoginLog;
import com.thinkgem.jeesite.modules.stamp.service.common.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Locker on 2017/7/28.
 */
@Controller
@RequestMapping(value="{adminPath}/sys/loginlog")
public class LoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 登陆日志查看
     * @param loginLog
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/list")
    public String list(LoginLog loginLog, Model model, HttpServletRequest request, HttpServletResponse response){

        Page<LoginLog> page = loginLogService.findPage(new Page<LoginLog>(request,response),loginLog);

        model.addAttribute("page",page);

        model.addAttribute("loginlog",loginLog);

        return "modules/loginlog/LoginLogList";
    }


}
