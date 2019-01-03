package com.thinkgem.jeesite.modules.stamp.web.moneyCount;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.stamp.dto.money.MakeCompanyMoneyCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.money.CompanyMoneyExcelDo;
import com.thinkgem.jeesite.modules.stamp.service.money.count.MakeCompanyMoneyCountService;
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
 * Created by Locker on 2017/7/22.
 */
@Controller
@RequestMapping(value="${adminPath}/makeCompany/moneyCount")
public class MakeCompanyMoneyCountController {


    @Autowired
    private MakeCompanyMoneyCountService makeCompanyCountService;

    @RequestMapping(value="list")
    public String list(MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO, Model model,
                       HttpServletRequest request, HttpServletResponse response){


        System.out.println(makeCompanyMoneyCountDTO.toString());

        Page<Company> page =makeCompanyCountService.countList(makeCompanyMoneyCountDTO,new Page<Company>(request,response));

        model.addAttribute("page",page);

        model.addAttribute("currentMakeCompany", UserUtils.getUser().getCompanyInfo());

        model.addAttribute("makeCompanyMoneyCountDTO",makeCompanyMoneyCountDTO);

        return "modules/jsps/moneyCount/MakeCompany-list";
    }

    @RequestMapping(value = "/exportMoneyCount")
    public String exportMoneyCount(MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO, Model model,
                                   HttpServletRequest request, HttpServletResponse response){
        try {
            Company company = UserUtils.getUser().getCompanyInfo();

            StringBuffer title = new StringBuffer();
            title.append(company.getCompanyName()+"-收费统计");

            Date startDate = makeCompanyMoneyCountDTO.getStartDate();

            Date endDate = makeCompanyMoneyCountDTO.getEndDate();

            if(startDate == null && endDate == null){

                title.append("-时间至今");
            }else{

                title.append("-"+DateUtils.formatDate(startDate)+"-"+DateUtils.formatDate(endDate));
            }


            String fileName = company.getCompanyName()+"-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-收费统计报表"+".xlsx";
            List<CompanyMoneyExcelDo> list = makeCompanyCountService.excelExportService(makeCompanyMoneyCountDTO,new Page<Company>(request, response));
            new ExportExcel(title.toString(), CompanyMoneyExcelDo.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();


        }
        return "redirect:"+ Global.getAdminPath()+"/makeCompany/moneyCount/list?repage";


    }

}
