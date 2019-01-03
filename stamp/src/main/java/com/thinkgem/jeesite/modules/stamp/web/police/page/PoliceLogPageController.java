package com.thinkgem.jeesite.modules.stamp.web.police.page;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.police.PoliceLog;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceLogService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.h2.mvstore.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * Created by hjw-pc on 2017/6/22.
 */
@Controller
@RequestMapping(value = "${adminPath}/policeLogPage")
public class PoliceLogPageController{

    @Autowired
    private PoliceLogService policeLogService;

    @RequestMapping(value = "/showSysLog")
    public String showSysLog(PoliceLog policeLog, HttpServletRequest request, HttpServletResponse response, Model model) {


        Police police = UserUtils.getUser().getPoliceInfo();
        Area area = police.getArea();
        policeLog.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(area, "sa"));
        Page<PoliceLog> page = policeLogService.findPage(new Page<PoliceLog>(request, response),policeLog);
        model.addAttribute("page",page);
        model.addAttribute("policeLog",policeLog);
        return "modules/jsps/police/police-system-journal";
    }
}
