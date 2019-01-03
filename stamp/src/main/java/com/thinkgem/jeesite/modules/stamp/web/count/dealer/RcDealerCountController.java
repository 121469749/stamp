package com.thinkgem.jeesite.modules.stamp.web.count.dealer;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.dto.count.CompanyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.StampCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.StampCountExcelByPoliceDTO;
import com.thinkgem.jeesite.modules.stamp.service.count.dealer.RcDealerCountService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 *
 * 润城经销商的统计控制层
 * Created by Administrator on 2017/10/7.
 */
@Controller
@RequestMapping(value = "${adminPath}/rc/dealer")
public class RcDealerCountController {

    @Autowired
    private RcDealerCountService rcDealerCountService;

    /**
     *
     * 印章列表
     * @param dto
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stamp/count/list")
    public String stampCountPage(StampCountDTO dto, Model model, HttpServletRequest request, HttpServletResponse response) {

        Page<Stamp> page = rcDealerCountService.countStampPage(dto, new Page<Stamp>(request, response));

        model.addAttribute("stampStates", StampState.values());

        model.addAttribute("page", page);

        model.addAttribute("dto", dto);

        return "modules/jsps/count/dealer/rcdealer/stamp-list";
    }

    /**
     * 导出excel
     * @param dto
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stamp/count/exportExcel")
    public String stampCountExportExcel(StampCountDTO dto, Model model, HttpServletRequest request, HttpServletResponse response) {

        try {

            Company company = UserUtils.getUser().getCompanyInfo();

            StringBuffer title = new StringBuffer();

            title.append("润城经销商-印章数据统计");

            String fileName = title.toString()+ DateUtils.getDate("yyyyMMddHHmmss")+"-简单报表"+".xlsx";
            List<StampCountExcelByPoliceDTO> list = rcDealerCountService.countStampExcel(dto);
            new ExportExcel(title.toString(), StampCountExcelByPoliceDTO.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "redirect:"+ Global.getAdminPath()+"/rc/dealer/stamp/count/list?repage";


    }

    /**
     * 用章单位 统计列表
     * @param dto
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/useCompany/count/list")
    public String useCompanyCountPage(CompanyCountDTO dto, HttpServletResponse response, HttpServletRequest request, Model model) {


        Page<Company> page = rcDealerCountService.countUseCompanyPage(new Page<Company>(request, response), dto);

        model.addAttribute("page", page);

        model.addAttribute("dto", dto);


        return "modules/jsps/count/dealer/rcdealer/rcDealer-useCompany-count-list";
    }

    /**
     * 单个用章单位统计
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/useCompany/count/detail")
    public String useCompanyDetailPage(@RequestParam(value = "id") String id, Model model) {
        try {


            List<StampRecord> list = rcDealerCountService.findRecordDetailInfoByUseCompany(id);

            model.addAttribute("list", list);

            Company useCompany = rcDealerCountService.getUseCompany(id);

            model.addAttribute("useCompany", useCompany);

        } catch (IOException e) {

            e.printStackTrace();

            return "modules/jsps/404";
        }

        return "modules/jsps/count/dealer/rcdealer/rcDealer-useCompany-record-detail";
    }

}
