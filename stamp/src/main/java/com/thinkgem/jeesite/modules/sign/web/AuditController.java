package com.thinkgem.jeesite.modules.sign.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.GetAddr;
import com.thinkgem.jeesite.common.utils.MacUtils;
import com.thinkgem.jeesite.modules.sign.common.condition.Condition;
import com.thinkgem.jeesite.modules.sign.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.sign.dto.SingatureDTO;
import com.thinkgem.jeesite.modules.sign.entity.Audit;
import com.thinkgem.jeesite.modules.sign.service.AuditService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * update by bb on 2018/07/16.
 */
@Controller
@RequestMapping(value="${adminPath}/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @RequestMapping(value="/index_use")
    public String index(Audit audit, HttpServletRequest request, HttpServletResponse response, Model model){

        Page<Audit> page = auditService.findPage(new Page<Audit>(request,response), audit);

        model.addAttribute("page",page);
        model.addAttribute("user", UserUtils.getUser());

        //审计列表页面
        return "modules/sign/sign_audit";
    }

    @RequestMapping(value="/index_make")
    public String index2(Audit audit, HttpServletRequest request, HttpServletResponse response, Model model){

        Page<Audit> page = auditService.findPage(new Page<Audit>(request,response), audit);

        model.addAttribute("page",page);
        model.addAttribute("user", UserUtils.getUser());

        //审计列表页面
        return "modules/jsps/stampmaker/stampengrave/stampengrave-audit";
    }

    /**
     * 签章
     * @param audit
     * @return
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public void index3(Audit audit, HttpServletRequest request){

        //审计相关
        try {
            audit.setIp(GetAddr.getIp(request));
            audit.setMac(MacUtils.getMac());//此处获取的是服务端Mac地址
        } catch (Exception e) {
            e.printStackTrace();
        }
        audit.setUser(UserUtils.getUser());
        audit.setAuditType(Audit.AUDIT_VERIFY);
        audit.setAuditDate(new Date());
        //audit.setFileName();
        //audit.setAuditResult();
        //audit.setReason();

        auditService.addAudit(audit);
    }

}
