package com.thinkgem.jeesite.modules.stamp.web.count.police;

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
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.StampCountExcelByMakeCompanyDTO;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.StampCountExcelByPoliceDTO;
import com.thinkgem.jeesite.modules.stamp.service.count.police.PoliceCountService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 公安角色的统计控制层
 *
 * Created by Locker on 2017/10/6.
 */

@Controller
@RequestMapping(value="${adminPath}/police/count")
public class PoliceCountController {


    @Autowired
    private PoliceCountService countService;

    @Autowired
    private AreaService areaService;

    /**
     * 公安对印章的查询
     * @param dto
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/stamp/list")
    public String stampCount(StampCountDTO dto, Model model, HttpServletRequest request, HttpServletResponse response){

        Page<Stamp> page = countService.countPage(dto,new Page<Stamp>(request,response));

        model.addAttribute("stampStates", StampState.values());

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        return "modules/jsps/count/police/stamp-list";
    }

    /**
     * 公安对对企业信息的统计
     * @author bb
     * @param dto
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/useCompany/list")
    public String useCompanyCount(CompanyCountDTO dto, HttpServletResponse response, HttpServletRequest request, Model model){

        Page<Company> page=countService.useCompanyCountPage(new Page<Company>(request,response),dto);

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        return "modules/jsps/count/police/police-useCompany-count-list";
    }

    /**
     * 导出印章excel报表
     * @param dto
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/stamp/exportExcel")
    public String stampCountListExportExcel(StampCountDTO dto,HttpServletRequest request,HttpServletResponse response){

        try {

            Police police = UserUtils.getUser().getPoliceInfo();

            Area area = areaService.get(police.getArea());

            StringBuffer title = new StringBuffer();

            title.append(area.getName()+"-公安机关-印章数据统计");

            String fileName = area.getName()+"-公安机关-印章数据统计-"+ DateUtils.getDate("yyyyMMddHHmmss")+"-简单报表"+".xlsx";
            List<StampCountExcelByPoliceDTO> list = countService.countExcel(dto);
            new ExportExcel(title.toString(), StampCountExcelByPoliceDTO.class).setDataList(list).write(response, fileName).dispose();

            return null;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "redirect:"+ Global.getAdminPath()+"/police/count/stamp/list?repage";
    }

}
