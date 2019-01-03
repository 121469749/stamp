package com.thinkgem.jeesite.modules.stamp.web.count.dealer;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.dto.count.CompanyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.dealer.StampTypeCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.count.dealer.DealerCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 经销商对制章单位的统计
 *
 * Created by Administrator on 2017/10/7.
 */
@Controller
@RequestMapping(value="${adminPath}/dealer/makeCompany")
public class DealerCountController {

    @Autowired
    private DealerCountService countService;

    /**
     * 统计列表
     * @param dto
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/count/list")
    public String countPage(CompanyCountDTO dto, Model model, HttpServletRequest request, HttpServletResponse response){

        Page<Company> page = countService.countMakeCompany(new Page<Company>(request,response),dto);

        model.addAttribute("page",page);

        model.addAttribute("dto",dto);

        return "modules/jsps/count/dealer/dealer-makeCompany-count-list";
    }

    /**
     * 单条统计详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/count/detail")
    public String countDetail(@RequestParam(value = "id")String id,Model model){

        Company makeCompany = countService.getMakeCompany(id);

        List<StampTypeCountDTO> list = countService.makeCompanyCountDetail(id);

        model.addAttribute("list",list);
        model.addAttribute("makeCompany",makeCompany);

        return "modules/jsps/count/dealer/dealer-makeCompany-record-detail";
    }

}
