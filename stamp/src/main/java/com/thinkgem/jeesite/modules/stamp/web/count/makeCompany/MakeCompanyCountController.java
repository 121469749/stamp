package com.thinkgem.jeesite.modules.stamp.web.count.makeCompany;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.dto.count.CompanyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.StampCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.CompanyCountExcelByPoliceDTO;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.CompanyCountExcelDo;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.StampCountExcelByMakeCompanyDTO;
import com.thinkgem.jeesite.modules.stamp.service.count.makeCompany.MakeCompanyCountService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * 刻章单位统计
 *
 * Created by Locker on 2017/7/25.
 */
@Controller
@RequestMapping(value="${adminPath}/makeCompany/count")
public class MakeCompanyCountController {

    @Autowired
    private MakeCompanyCountService makeCompanyCountService;

    /**
     * 印章列表
     * @param dto
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/stamp/list")
    public String stampList(StampCountDTO dto,Model model,HttpServletRequest request,HttpServletResponse response){


        Page<Stamp> page = makeCompanyCountService.count(dto,new Page<Stamp>(request,response));

        model.addAttribute("stampStates", StampState.values());

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        return "modules/jsps/count/makeCompany/stamp-list";
    }

    /**
     * 导出印章列表
     * @param dto
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/stamp/exportExcel")
    public String stampCountListExportExcel(StampCountDTO dto,HttpServletRequest request,HttpServletResponse response){

        try {
            Company company = UserUtils.getUser().getCompanyInfo();

            StringBuffer title = new StringBuffer();

            title.append(company.getCompanyName()+"-"+"印章数据统计");

            String fileName = company.getCompanyName()+"-印章数据统计-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-简单报表"+".xlsx";
            List<StampCountExcelByMakeCompanyDTO> list = makeCompanyCountService.countExcel(dto);
            new ExportExcel(title.toString(), StampCountExcelByMakeCompanyDTO.class).setDataList(list).write(response, fileName).dispose();

            Condition condition = new Condition(Condition.SUCCESS_CODE,"成功导出");

            System.out.println("导出数据成功");
            return JsonMapper.toJsonString(condition);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "redirect:"+ Global.getAdminPath()+"/makeCompany/count/stamp/list?repage";
    }


    /**
     * 刻章点对企业信息的统计
     * @author bb
     * @param dto
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/list")
    public String list(CompanyCountDTO dto, HttpServletResponse response, HttpServletRequest request, Model model){

        long a = System.currentTimeMillis();
        Page<Company> page = makeCompanyCountService.makeCompanyCountPage(new Page<Company>(request,response),dto);

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        model.addAttribute("currentMakeCompany", UserUtils.getUser().getCompanyInfo());
        long b = System.currentTimeMillis();

        System.out.println("企业信息统计总消耗时间："+(b-a));

        return "modules/jsps/count/makeCompany/makeCompany-stampCount-list";
    }

    /**
     *  对企业的备案详情做统计
     * @author bb
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/detail/record")
    public String detailRecord(@RequestParam(value = "id")String id,Model model){
        try {

            Company useCompany = makeCompanyCountService.getUseCompany(id);

            List<StampRecord> list = makeCompanyCountService.findRecordDetailInfoByUseCompany(id);

            model.addAttribute("list", list);

            model.addAttribute("useCompany",useCompany);

        } catch (IOException e) {
            e.printStackTrace();
            return "modules/jsps/404";
        }

        return "modules/jsps/count/makeCompany/useCompany-record-detail";
    }

    /**
     * 导出统计报表
     * @param dto
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/exportExcel")
    public String exportExcel(CompanyCountDTO dto, HttpServletResponse response, HttpServletRequest request, Model model){
        try {
            Company company = UserUtils.getUser().getCompanyInfo();

            StringBuffer title = new StringBuffer();

            title.append(company.getCompanyName()+"-简单统计");

            Date startDate = dto.getStartDate();

            Date endDate = dto.getEndDate();

            if(startDate == null && endDate == null){

                title.append("-时间至今");
            }else{

                title.append("-"+ DateUtils.formatDate(startDate)+"-"+ DateUtils.formatDate(endDate));
            }


            String fileName = company.getCompanyName()+"-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-简单统计报表"+".xlsx";
//            List<CompanyCountExcelDo> list = makeCompanyCountService.exportExcel(new Page<Company>(request, response),dto);
            List<CompanyCountExcelByPoliceDTO> list = makeCompanyCountService.exportCompany(new Page<Company>(request,response),dto);
            new ExportExcel(title.toString(), CompanyCountExcelByPoliceDTO.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "redirect:"+ Global.getAdminPath()+"/makeCompany/count/list?repage";

    }

}
