package com.thinkgem.jeesite.modules.stamp.web.count;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.stamp.dto.count.DealerCompanyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.money.DealerCompanyMoneyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.money.MakeCompanyMoneyCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.CompanyCountExcelDo;
import com.thinkgem.jeesite.modules.stamp.service.count.DealerCompanyCountService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 *
 * 旧统计方法，可以无视、作废
 *
 * Created by Locker on 2017/7/26.
 */
@Controller
@RequestMapping(value="${adminPath}/dealer/count")
public class DealerCompanyCountController {

    @Autowired
    private DealerCompanyCountService dealerCompanyCountService;

    @RequestMapping(value="/city/list")
    public String cityDealerList(MakeCompanyMoneyCountDTO dto, Model model,
                                 HttpServletRequest request, HttpServletResponse response){

        Page<Company> page = dealerCompanyCountService.cityDealerCompanyCountPage(new Page<Company>(request,response),dto);

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        model.addAttribute("currentDealer", UserUtils.getUser().getCompanyInfo());

        return "modules/jsps/count/CityDealerCompany-list";
    }

    @RequestMapping(value="/city/exportExcel")
    public String cityDealerExcelExport(MakeCompanyMoneyCountDTO dto, Model model,
                                        HttpServletRequest request, HttpServletResponse response){
        try {
            Company company = UserUtils.getUser().getCompanyInfo();

            StringBuffer title = new StringBuffer();

            title.append(company.getCompanyName()+"-简单统计");

            Date startDate = dto.getStartDate();

            Date endDate = dto.getEndDate();

            if(startDate == null && endDate == null){

                title.append("-时间至今");
            }else{

                title.append("-"+ DateUtils.formatDate(startDate)+"-"+DateUtils.formatDate(endDate));
            }


            String fileName = company.getCompanyName()+"-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-简单统计报表"+".xlsx";
            List<CompanyCountExcelDo> list = dealerCompanyCountService.cityDealerExcel(new Page<Company>(request, response),dto);
            new ExportExcel(title.toString(), CompanyCountExcelDo.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "redirect:"+ Global.getAdminPath()+"/dealer/countlist/city/list?repage";
    }


    @RequestMapping(value="/province/list")
    public String provinceDealerList(DealerCompanyCountDTO dto, Model model,
                                     HttpServletRequest request, HttpServletResponse response){

        Page<Company> page = dealerCompanyCountService.provinceDealerCompanyCountPage(new Page<Company>(request,response),dto);

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        model.addAttribute("currentDealer", UserUtils.getUser().getCompanyInfo());

        return "modules/jsps/count/ProvinceDealerCompany-list";
    }

    @RequestMapping(value="/province/excelExport")
    public String provinceDealerExcelExport(DealerCompanyCountDTO dto, Model model,
                                            HttpServletRequest request, HttpServletResponse response){
        try {
            Company company = UserUtils.getUser().getCompanyInfo();

            StringBuffer title = new StringBuffer();

            title.append(company.getCompanyName()+"-简单统计");

            Date startDate = dto.getStartDate();

            Date endDate = dto.getEndDate();

            if(startDate == null && endDate == null){

                title.append("-时间至今");
            }else{

                title.append("-"+ DateUtils.formatDate(startDate)+"-"+DateUtils.formatDate(endDate));
            }


            String fileName = company.getCompanyName()+"-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-简单统计报表"+".xlsx";
            List<CompanyCountExcelDo> list = dealerCompanyCountService.provinceDealerExcel(new Page<Company>(request, response),dto);
            new ExportExcel(title.toString(), CompanyCountExcelDo.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "redirect:"+ Global.getAdminPath()+"/dealer/countlist/province/list?repage";
    }

    @RequestMapping(value = "/rc/list")
    public String rcDealerList(DealerCompanyCountDTO dto, Model model,
                               HttpServletRequest request, HttpServletResponse response){

        Page<Company> page = dealerCompanyCountService.rcDealerCompanyCountPage(new Page<Company>(request,response),dto);

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        model.addAttribute("currentDealer", UserUtils.getUser().getCompanyInfo());

        return "modules/jsps/count/RcDealerCompany-list";

    }


    @RequestMapping(value="/rc/excelExport")
    public String rcDealerExcelExport(DealerCompanyCountDTO dto, Model model,
                                      HttpServletRequest request, HttpServletResponse response){

        try {
            Company company = UserUtils.getUser().getCompanyInfo();

            StringBuffer title = new StringBuffer();

            title.append(company.getCompanyName()+"-简单统计");

            Date startDate = dto.getStartDate();

            Date endDate = dto.getEndDate();

            if(startDate == null && endDate == null){

                title.append("-时间至今");
            }else{

                title.append("-"+ DateUtils.formatDate(startDate)+"-"+DateUtils.formatDate(endDate));
            }


            String fileName = company.getCompanyName()+"-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-简单统计报表"+".xlsx";

            List<CompanyCountExcelDo> list = dealerCompanyCountService.rcDealerExcel(new Page<Company>(request, response),dto);

            new ExportExcel(title.toString(), CompanyCountExcelDo.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "redirect:"+ Global.getAdminPath()+"/dealer/countlist/rc/list?repage";
    }


}
