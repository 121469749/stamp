package com.thinkgem.jeesite.modules.stamp.web.countSet;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.dto.countSet.CountSetDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.TCountSetLog;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import com.thinkgem.jeesite.modules.stamp.exception.countSet.CountSetAbsenceException;
import com.thinkgem.jeesite.modules.stamp.exception.countSet.CountSetCountException;
import com.thinkgem.jeesite.modules.stamp.exception.countSet.CountSetException;
import com.thinkgem.jeesite.modules.stamp.exception.countSet.CountSetSaveException;
import com.thinkgem.jeesite.modules.stamp.service.countSet.DealerCountSetService;
import com.thinkgem.jeesite.modules.stamp.service.countSet.MakeCompanyCountSetService;
import com.thinkgem.jeesite.modules.stamp.service.countSet.RcCountSetService;
import com.thinkgem.jeesite.modules.stamp.service.countSet.TCountSetLogService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 印章 数量控制
 *
 * Created by Locker on 2017/8/17.
 */
@Controller
@RequestMapping(value = "${adminPath}/dealer/countSet")
public class DealerCountSetController {


    @Autowired
    private RcCountSetService rcCountSetService;

    @Autowired
    private DealerCountSetService dealerCountSetService;

    @Autowired
    private MakeCompanyCountSetService makeCompanyCountSetService;

    @Autowired
    private TCountSetLogService tCountSetLogService;

    @RequestMapping(value = "/rc/list")
    public String runchengList(Company company, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<Company> page = rcCountSetService.findRcCountSetPage(new Page<Company>(request, response), company);

        model.addAttribute("page", page);

        model.addAttribute("company", company);

        return "modules/jsps/countSet/RcDealerCompany-list";
    }

    @RequestMapping(value = "/rc/form")
    public String runchengForm(@RequestParam(value = "companyId") String companyId, Model model) {

        Company agenyCompany = new Company(companyId, CompanyType.AGENCY);

        agenyCompany = rcCountSetService.getCompanyInfo(agenyCompany);

        makeCompanyCountSetService.findCompanyCountSet(agenyCompany);

        model.addAttribute("agencyCompany", agenyCompany);

        String companyName = UserUtils.getUser().getCompany().getCompanyName();

        Company currentCompany = new Company();

        currentCompany.setCompanyName(companyName);

        makeCompanyCountSetService.findCompanyCountSet(currentCompany);

        model.addAttribute("currentCompany", currentCompany);

        return "modules/jsps/countSet/rc/countSetForm";
    }

    @RequestMapping(value = "/rc/save", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String runchengSave(Company company) {

        Condition condition = null;

        //保存分配历史记录
        int sumEleCount1 = 0;
        int sumPhyCount1 = 0;
        //获取制章单位
        Company makecompany = new Company(company.getId(), CompanyType.AGENCY);
        if (tCountSetLogService.getSumEleCount(company)==null||"".equals(tCountSetLogService.getSumEleCount(company)))
        {
            sumEleCount1 = 0;
        }else {
            sumEleCount1 = Integer.parseInt(tCountSetLogService.getSumEleCount(company));
        }

        if (tCountSetLogService.getSumPhyCount(company)==null||"".equals(tCountSetLogService.getSumPhyCount(company)))
        {
            sumPhyCount1 = 0;
        }else {
            sumPhyCount1 = Integer.parseInt(tCountSetLogService.getSumPhyCount(company));
        }

        int sumEleCount = sumEleCount1+company.getEleCountSet().getCount();
        int sumPhyCount = sumPhyCount1+company.getPhyCountSet().getCount();


        makecompany = rcCountSetService.getCompanyInfo(makecompany);


        TCountSetLog tCountSetLog = new TCountSetLog();
        tCountSetLog.setCompanyId(company.getId());

        tCountSetLog.setCompanyName(makecompany.getCompanyName());
        tCountSetLog.setEleCount(String.valueOf(company.getEleCountSet().getCount()));
        tCountSetLog.setPhyCount(String.valueOf(company.getPhyCountSet().getCount()));
        tCountSetLog.setEleSumcount(String.valueOf(sumEleCount));
        tCountSetLog.setPhySumcount(String.valueOf(sumPhyCount));
        tCountSetLogService.save(tCountSetLog);

        //获取当前经销商的印章数
        //获取当前选定经销商的印章数
        CountSet subEleCountSet = dealerCountSetService.getEleCountByCompany(company);
        CountSet subPhyCountSet = dealerCountSetService.getPhyCountByCompany(company);
        if (subEleCountSet == null || "null".equals(subEleCountSet)){
            subEleCountSet = new CountSet();
            subEleCountSet.setStampShape(StampShape.ELESTAMP);
        }
        if (subPhyCountSet == null || "null".equals(subPhyCountSet)){
            subPhyCountSet = new CountSet();
            subPhyCountSet.setStampShape(StampShape.PHYSTAMP);
        }

        try {
            Condition phyCondition = dealerCountSetService.rcSaveCount(company,subEleCountSet);

            Condition eleCondition = dealerCountSetService.rcSaveCount(company,subPhyCountSet);
            condition = new Condition(Condition.SUCCESS_CODE,phyCondition.getMessage()+"\n"+eleCondition.getMessage());

        } catch (CountSetSaveException e) {

            condition = new Condition(Condition.ERROR_CODE,"保存印章数量控制失败!");

        } catch (CountSetCountException e) {

            condition= new Condition(Condition.ERROR_CODE,e.getMessage());

        } catch (CountSetException e) {

            condition = new Condition(Condition.ERROR_CODE,"保存印章数量控制失败!\n请联系管理员");

        }

        return JsonMapper.toJsonString(condition);
    }
    
    @RequestMapping(value="/province/list")
    public String provinceList(Company company, HttpServletRequest request, HttpServletResponse response, Model model){

        Page<Company> page = dealerCountSetService.findProvinceDealerPage(company,new Page<Company>(request, response));

        model.addAttribute("page", page);

        Company currentAgencyCompany = UserUtils.getUser().getCompanyInfo();

        dealerCountSetService.findCompanyCountSet(currentAgencyCompany);

        model.addAttribute("currentAgencyCompany",currentAgencyCompany);

        model.addAttribute("company", company);

        return "modules/jsps/countSet/ProvinceDealerCompany-list";
    }

    @RequestMapping(value="/province/form")
    public String provinceForm(@RequestParam(value = "companyId") String companyId, Model model) {

        Company agencyCompany = new Company(companyId, CompanyType.AGENCY);

        agencyCompany = rcCountSetService.getCompanyInfo(agencyCompany);

        makeCompanyCountSetService.findCompanyCountSet(agencyCompany);

        model.addAttribute("agencyCompany", agencyCompany);

        Company currentCompany = UserUtils.getUser().getCompanyInfo();

        makeCompanyCountSetService.findCompanyCountSet(currentCompany);

        model.addAttribute("currentCompany", currentCompany);

        return "modules/jsps/countSet/province/countSetForm";
    }

    @RequestMapping(value = "/province/save", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String provinceSave(Company company){
        Condition condition = null;
        System.out.println(company.getId());
        //保存分配历史记录
        int sumEleCount1 = 0;
        int sumPhyCount1 = 0;
        //获取制章单位
        Company makecompany = new Company(company.getId(), CompanyType.AGENCY);

        if (tCountSetLogService.getSumEleCount(company)==null||"".equals(tCountSetLogService.getSumEleCount(company)))
        {
            sumEleCount1 = 0;
        }else {
            sumEleCount1 = Integer.parseInt(tCountSetLogService.getSumEleCount(company));
        }

        if (tCountSetLogService.getSumPhyCount(company)==null||"".equals(tCountSetLogService.getSumPhyCount(company)))
        {
            sumPhyCount1 = 0;
        }else {
            sumPhyCount1 = Integer.parseInt(tCountSetLogService.getSumPhyCount(company));
        }

        int sumEleCount = sumEleCount1+company.getEleCountSet().getCount();
        int sumPhyCount = sumPhyCount1+company.getPhyCountSet().getCount();


        makecompany = rcCountSetService.getCompanyInfo(makecompany);


        TCountSetLog tCountSetLog = new TCountSetLog();
        tCountSetLog.setCompanyId(company.getId());

        tCountSetLog.setCompanyName(makecompany.getCompanyName());
        tCountSetLog.setEleCount(String.valueOf(company.getEleCountSet().getCount()));
        tCountSetLog.setPhyCount(String.valueOf(company.getPhyCountSet().getCount()));
        tCountSetLog.setEleSumcount(String.valueOf(sumEleCount));
        tCountSetLog.setPhySumcount(String.valueOf(sumPhyCount));
        tCountSetLogService.save(tCountSetLog);

        Company parentCompany = UserUtils.getUser().getCompanyInfo();
        //获取当前经销商的印章数
        CountSet parentEleCountSet = dealerCountSetService.getEleCountByCompany(parentCompany);
        CountSet parentPhyCountSet = dealerCountSetService.getPhyCountByCompany(parentCompany);
        //获取当前被选定经销商的印章数
        CountSet subEleCountSet = dealerCountSetService.getEleCountByCompany(company);
        CountSet subPhyCountSet = dealerCountSetService.getPhyCountByCompany(company);
        if (subEleCountSet == null || "null".equals(subEleCountSet)){
            subEleCountSet = new CountSet();
            subEleCountSet.setStampShape(StampShape.ELESTAMP);
        }
        if (subPhyCountSet == null || "null".equals(subPhyCountSet)){
            subPhyCountSet = new CountSet();
            subPhyCountSet.setStampShape(StampShape.PHYSTAMP);
        }
        try {
            Condition phyCondition = dealerCountSetService.saveCount(company,subPhyCountSet,parentPhyCountSet);

            Condition eleCondition = dealerCountSetService.saveCount(company,subEleCountSet,parentEleCountSet);

            condition = new Condition(Condition.SUCCESS_CODE,phyCondition.getMessage()+"\n"+eleCondition.getMessage());

        }catch (CountSetAbsenceException e){

            condition = new Condition(Condition.ERROR_CODE,e.getMessage());

        }catch (CountSetCountException e) {

            condition= new Condition(Condition.ERROR_CODE,e.getMessage());

        } catch (CountSetException e) {

            condition = new Condition(Condition.ERROR_CODE,"保存印章数量控制失败!\n请联系管理员");

        }

        return JsonMapper.toJsonString(condition);
    }


    @RequestMapping(value="/city/list")
    public String cityList(Company company, HttpServletRequest request, HttpServletResponse response, Model model){

        Page<Company> page =makeCompanyCountSetService.findMakeCompanyPage(company,new Page<Company>(request, response));

        model.addAttribute("page", page);

        Company currentAgencyCompany = UserUtils.getUser().getCompanyInfo();

        makeCompanyCountSetService.findCompanyCountSet(currentAgencyCompany);

        model.addAttribute("currentAgencyCompany",currentAgencyCompany);

        model.addAttribute("company", company);

        return "modules/jsps/countSet/CityMakeCompany-list";
    }

    @RequestMapping(value="/city/form")
    public String cityForm(@RequestParam(value = "companyId") String companyId, Model model){

        Company makeCompany = new Company(companyId, CompanyType.MAKE);

        makeCompany = rcCountSetService.getCompanyInfo(makeCompany);

        makeCompanyCountSetService.findCompanyCountSet(makeCompany);

        model.addAttribute("agencyCompany", makeCompany);

        Company currentCompany = UserUtils.getUser().getCompanyInfo();

        makeCompanyCountSetService.findCompanyCountSet(currentCompany);

        model.addAttribute("currentCompany", currentCompany);

        return "modules/jsps/countSet/city/countSetForm";

    }

    @RequestMapping(value = "/city/save", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String citySave(Company company){

        Condition condition = null;
        System.out.println(company.getId());
        //保存分配历史记录
        int sumEleCount1 = 0;
        int sumPhyCount1 = 0;
        System.out.println(company.getId());
        //获取制章单位
        Company makecompany = new Company(company.getId(), CompanyType.MAKE);

        if (tCountSetLogService.getSumEleCount(company)==null||"".equals(tCountSetLogService.getSumEleCount(company)))
        {
            sumEleCount1 = 0;
        }else {
            sumEleCount1 = Integer.parseInt(tCountSetLogService.getSumEleCount(company));
        }

        if (tCountSetLogService.getSumPhyCount(company)==null||"".equals(tCountSetLogService.getSumPhyCount(company)))
        {
            sumPhyCount1 = 0;
        }else {
            sumPhyCount1 = Integer.parseInt(tCountSetLogService.getSumPhyCount(company));
        }

        int sumEleCount = sumEleCount1+company.getEleCountSet().getCount();
        int sumPhyCount = sumPhyCount1+company.getPhyCountSet().getCount();


        makecompany = rcCountSetService.getCompanyInfo(makecompany);


        TCountSetLog tCountSetLog = new TCountSetLog();
        tCountSetLog.setCompanyId(company.getId());

        tCountSetLog.setCompanyName(makecompany.getCompanyName());
        tCountSetLog.setEleCount(String.valueOf(company.getEleCountSet().getCount()));
        tCountSetLog.setPhyCount(String.valueOf(company.getPhyCountSet().getCount()));
        tCountSetLog.setEleSumcount(String.valueOf(sumEleCount));
        tCountSetLog.setPhySumcount(String.valueOf(sumPhyCount));
        tCountSetLogService.save(tCountSetLog);


        Company parentCompany = UserUtils.getUser().getCompanyInfo();

//        CountSetDTO lastCountSet = makeCompanyCountSetService.findCityLastCountSet(currentCompany);
        //获取当前经销商的印章数
        CountSet parentEleCountSet = dealerCountSetService.getEleCountByCompany(parentCompany);
        CountSet parentPhyCountSet = dealerCountSetService.getPhyCountByCompany(parentCompany);
        //获取当前选定经销商的印章数
        CountSet subEleCountSet = dealerCountSetService.getEleCountByCompany(company);
        CountSet subPhyCountSet = dealerCountSetService.getPhyCountByCompany(company);

        if (subEleCountSet == null || "null".equals(subEleCountSet)){
            subEleCountSet = new CountSet();
            subEleCountSet.setStampShape(StampShape.ELESTAMP);
        }
        if (subPhyCountSet == null || "null".equals(subPhyCountSet)){
            subPhyCountSet = new CountSet();
            subPhyCountSet.setStampShape(StampShape.PHYSTAMP);
        }

        try {
            Condition phyCondition = dealerCountSetService.saveCount(company,subPhyCountSet,parentPhyCountSet);

            Condition eleCondition = dealerCountSetService.saveCount(company,subEleCountSet,parentEleCountSet);

            condition = new Condition(Condition.SUCCESS_CODE,phyCondition.getMessage()+"\n"+eleCondition.getMessage());

        }catch (CountSetAbsenceException e){

            condition = new Condition(Condition.ERROR_CODE,e.getMessage());

        }catch (CountSetCountException e) {

            condition= new Condition(Condition.ERROR_CODE,e.getMessage());

        } catch (CountSetException e) {

            condition = new Condition(Condition.ERROR_CODE,"保存印章数量控制失败!\n请联系管理员");

        }

        return JsonMapper.toJsonString(condition);

    }

}
