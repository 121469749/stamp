package com.thinkgem.jeesite.modules.stamp.web.moneyCount;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.stamp.dto.money.DealerCompanyMoneyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.money.MakeCompanyMoneyCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.money.CompanyMoneyExcelDo;
import com.thinkgem.jeesite.modules.stamp.service.money.count.DealerMoneyCountService;
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
 * Created by Locker on 17/7/24.
 */
@Controller
@RequestMapping(value = "${adminPath}/dealer/moneyCount")
public class DealerMoneyCountController {

    @Autowired
    private DealerMoneyCountService dealerMoneyCountService;

    /**
     * 市经销商 收费统计列表
     * @param makeCompanyMoneyCountDTO
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/city/list")
    public String cityDealerList(MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO, Model model,
                                 HttpServletRequest request, HttpServletResponse response){


        Page<Company> page = dealerMoneyCountService.cityCountPage(new Page<Company>(request,response),makeCompanyMoneyCountDTO);

        model.addAttribute("page",page);

        model.addAttribute("currentMakeCompany", UserUtils.getUser().getCompanyInfo());

        model.addAttribute("makeCompanyMoneyCountDTO",makeCompanyMoneyCountDTO);

        return "modules/jsps/moneyCount/CityDealerCompany-list";
    }

    @RequestMapping(value = "/city/excelExport")
    public String cityExport(MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO, Model model,
                           HttpServletRequest request, HttpServletResponse response){
        try {
            Company company = UserUtils.getUser().getCompanyInfo();

            Company checkCompany = makeCompanyMoneyCountDTO.getCompany();

            StringBuffer title = new StringBuffer();
            title.append(company.getCompanyName()+"-收费统计");

            Date startDate = makeCompanyMoneyCountDTO.getStartDate();

            Date endDate = makeCompanyMoneyCountDTO.getEndDate();

            if(checkCompany.getArea()!=null){

                title.append("-"+checkCompany.getArea().getName());
            }else{
                title.append("-"+company.getArea().getName());
            }

            if(startDate == null && endDate == null){

                title.append("-至今");
            }else{

                title.append("-"+ DateUtils.formatDate(startDate)+"-"+DateUtils.formatDate(endDate));
            }
            String fileName = company.getCompanyName()+"-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-收费统计报表"+".xlsx";

            List<CompanyMoneyExcelDo> list = dealerMoneyCountService.cityCountExcel(new Page<Company>(request, response),makeCompanyMoneyCountDTO);

            new ExportExcel(title.toString(), CompanyMoneyExcelDo.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();


        }
        return "redirect:"+ Global.getAdminPath()+"/dealer/moneyCount/city/list?repage";

    }


    @RequestMapping(value = "/province/list")
    public String provinceDealerList(DealerCompanyMoneyCountDTO dto,Model model,
                                     HttpServletRequest request, HttpServletResponse response){

        Page<Company> page = dealerMoneyCountService.provinceCountPage(new Page<Company>(request,response),dto);

        model.addAttribute("page",page);

        model.addAttribute("currentMakeCompany", UserUtils.getUser().getCompanyInfo());

        model.addAttribute("dealerCompantMoenyCountDTO",dto);


        return "modules/jsps/moneyCount/ProvinceDealerCompany-list";
    }

    @RequestMapping(value="/province/excelExport")
    public String provinceExportExport(DealerCompanyMoneyCountDTO dto,Model model,
                                 HttpServletRequest request, HttpServletResponse response){
        try {
            Company company = UserUtils.getUser().getCompanyInfo();


            StringBuffer title = new StringBuffer();
            title.append(company.getCompanyName()+"-收费统计");

            Date startDate = dto.getStartDate();

            Date endDate = dto.getEndDate();

            if(dto.getArea()!=null){

                title.append("-"+dto.getArea().getName());
            }else{
                title.append("-"+company.getArea().getName());
            }

            if(startDate == null && endDate == null){

                title.append("-至今");
            }else{

                title.append("-"+ DateUtils.formatDate(startDate)+"-"+DateUtils.formatDate(endDate));
            }
            String fileName = company.getCompanyName()+"-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-收费统计报表"+".xlsx";

            List<CompanyMoneyExcelDo> list = dealerMoneyCountService.provinceExcelExport(new Page<Company>(request, response),dto);

            new ExportExcel(title.toString(), CompanyMoneyExcelDo.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();


        }
        return "redirect:"+ Global.getAdminPath()+"/dealer/moneyCount/province/list?repage";

    }

    @RequestMapping(value = "/rc/list")
    public String rcDealerList(DealerCompanyMoneyCountDTO dto,Model model,
                                     HttpServletRequest request, HttpServletResponse response){

        Page<Company> page = dealerMoneyCountService.rcCountPage(new Page<Company>(request,response),dto);

        model.addAttribute("page",page);

        model.addAttribute("currentMakeCompany", UserUtils.getUser().getCompanyInfo());

        model.addAttribute("dealerCompantMoenyCountDTO",dto);

        return "modules/jsps/moneyCount/RcDealerCompany-list";
    }

    @RequestMapping(value="/rc/excelExport")
    public String  test(DealerCompanyMoneyCountDTO dto,Model model,
                     HttpServletRequest request, HttpServletResponse response){
        try {
            Company company = UserUtils.getUser().getCompanyInfo();


            StringBuffer title = new StringBuffer();
            title.append(company.getCompanyName()+"-收费统计");

            Date startDate = dto.getStartDate();

            Date endDate = dto.getEndDate();

            if(dto.getArea()!=null){

                title.append("-"+dto.getArea().getName());
            }else{
                title.append("-"+company.getArea().getName());
            }

            if(startDate == null && endDate == null){

                title.append("-至今");
            }else{

                title.append("-"+ DateUtils.formatDate(startDate)+"-"+DateUtils.formatDate(endDate));
            }
            String fileName = company.getCompanyName()+"-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-收费统计报表"+".xlsx";

            List<CompanyMoneyExcelDo> list = dealerMoneyCountService.rcCountexcelExport(new Page<Company>(request, response),dto);

            new ExportExcel(title.toString(), CompanyMoneyExcelDo.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();


        }
        return "redirect:"+ Global.getAdminPath()+"/dealer/moneyCount/rc/list?repage";

    }

}
