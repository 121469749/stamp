package com.thinkgem.jeesite.modules.stamp.web.company.page;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampLog;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampOperation;
import com.thinkgem.jeesite.modules.stamp.service.stamp.StampService;
import com.thinkgem.jeesite.modules.stamp.service.stamprecord.StampLogService;
import com.thinkgem.jeesite.modules.stamp.service.stamprecord.StampOperationService;
import com.thinkgem.jeesite.modules.stamp.vo.useCompany.UseLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * Created by sjk on 2017-06-12.
 */
@Controller
@RequestMapping(value = "${adminPath}/useCompanyLogPage")
public class UseCompanyLogPageController {

    @Autowired
    private StampOperationService stampOperationService;

    @Autowired
    private StampLogService stampLogService;

    @Autowired
    private StampService stampService;

    /**
     * 显示印章日志界面
     * @return
     */
    @RequestMapping(value = "/showStampLog")
    public String showStampLog(StampOperation stampOperation, Model model, HttpServletRequest request, HttpServletResponse response) {

        Page<StampOperation> page = stampOperationService.showStampHistory(new Page<StampOperation>(request, response), stampOperation);

        Stamp stamp = stampService.get(stampOperation.getStamp());

        UseLogVo useLog = new UseLogVo();

        useLog.setPage(page);

        useLog.setStamp(stamp);

        model.addAttribute("useLog", useLog);

        return "/modules/jsps/useUnit/useUnit-journal";
    }

    /**
     * 显示操作日志
     * @return
     */
    @RequestMapping(value = "/showSysLog")
    public String showSysLog(Model model, HttpServletRequest request, HttpServletResponse response, StampLog stampLog, String begin, String end) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            if (StringUtils.isNotBlank(begin)) {
                //日期转换
                stampLog.setBeginDate(df.parse(begin));
            }

            if (StringUtils.isNotBlank(end)) {
                //日期转换
                stampLog.setEndDate(df.parse(end));
            }

            /*Page<Log> page = useComLogService.showCompanyLog(new Page<Log>(request, response), log);

            model.addAttribute("page", page);*/

            Page<StampLog> page = stampLogService.showComLog(new Page<StampLog>(request, response), stampLog);

            model.addAttribute("page", page);

            return "/modules/jsps/useUnit/useUnit-system-journal";

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute("errorMessage", "系统繁忙");

            return "/modules/jsps/500";
        }
    }

    /**
     * 显示盖章日志
     */
    @RequestMapping(value = "/showUseLog")
    public String showUseLog(Model model, HttpServletRequest request, HttpServletResponse response, StampOperation stampOperation, String begin, String end) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            if (StringUtils.isNotBlank(begin)) {
                //日期转换
                stampOperation.setBeginDate(df.parse(begin));
            }

            if (StringUtils.isNotBlank(end)) {
                //日期转换
                stampOperation.setEndDate(df.parse(end));
            }

            if (stampOperation.getStamp() == null) {
                stampOperation.setStamp(new Stamp());
            }

            if (StringUtils.isBlank(stampOperation.getStamp().getStampShape())) {
                stampOperation.getStamp().setStampShape("1");
            }

            Page<StampOperation> page = stampOperationService.showUseHistory(new Page<StampOperation>(request, response), stampOperation);

            model.addAttribute("page", page);

            return "/modules/jsps/useUnit/useUnit-stampjournal";


        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute("errorMessage", "系统繁忙");

            return "/modules/jsps/500";
        }
    }
}
