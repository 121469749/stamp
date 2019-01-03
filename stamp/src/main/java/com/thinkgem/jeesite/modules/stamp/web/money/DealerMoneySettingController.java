package com.thinkgem.jeesite.modules.stamp.web.money;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.PaymentType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.money.DealerMoneySettingService;
import com.thinkgem.jeesite.modules.stamp.service.money.MoneySettingService;
import com.thinkgem.jeesite.modules.stamp.vo.moneySetting.MoneySettingVo;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Locker on 2017/7/17.
 */
@Controller
@RequestMapping(value = "${adminPath}/dealer/moneySetting")
public class DealerMoneySettingController {

    @Autowired
    private DealerMoneySettingService dealerMoneySettingService;

    @Autowired
    private MoneySettingService moneySettingService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CompanyService companyService;

    /**
     * @param company
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/province/setting/list")
    public String provinceList(Company company, HttpServletRequest request, HttpServletResponse response, Model model) {

        company.setCompType(CompanyType.AGENCY);

        Page<Company> page = dealerMoneySettingService.findCompanyPageByProvince(company, new Page<Company>(request, response));

        model.addAttribute("page", page);

        model.addAttribute("company", company);

        return "modules/jsps/moneySetting/dealer/moneySettingList";
    }

    @RequestMapping(value = "/province/setting/form")
    public String provinceForm(@RequestParam(value = "companyId") String id, Model model) {


        List<MoneySetting> moneySettingList = moneySettingService.findMoneySettingByCompanyId(id);

        List<Dict> dicts = moneySettingService.findStampTextureDict();

        Company agenyCompany = companyService.get(new Company(id, CompanyType.AGENCY));

        Area agenyArea = areaService.get(new Area(agenyCompany.getArea().getId()));

        //获得当前用户
        Company currentCompany = UserUtils.getUser().getCompanyInfo();

        Area currentArea = currentCompany.getArea();

        if (moneySettingList.size() == 0) {

            model.addAttribute("dicts", dicts);

            model.addAttribute("agenyCompany", agenyCompany);

            model.addAttribute("agenyArea", agenyArea);

            model.addAttribute("currentArea", currentArea);

            model.addAttribute("currentCompany", currentCompany);

            return "modules/jsps/moneySetting/dealer/moneySettingForm-new";

        } else if (dicts.size() == (moneySettingList.size() - 2)) {


            MoneySettingVo moneySettingVo = new MoneySettingVo(moneySettingList);

            model.addAttribute("moneySettingVo", moneySettingVo);

            model.addAttribute("agenyCompany", agenyCompany);

            model.addAttribute("agenyArea", agenyArea);

            model.addAttribute("currentArea", currentArea);

            model.addAttribute("currentCompany", currentCompany);

            return "modules/jsps/moneySetting/dealer/moneySettingForm-equals";

        } else if (dicts.size() > (moneySettingList.size() - 2)) {

            List<Dict> lastDict = new ArrayList<Dict>();

            boolean ifExist = false;

            for (Dict dict : dicts) {

                ifExist = false;

                for (MoneySetting moneySetting : moneySettingList) {

                    if (moneySetting.getPaymentType() == PaymentType.PHYSTAMP) {

                        if (dict.getValue().equals(moneySetting.getStampTexture())) {

                            ifExist = true;

                            break;
                        }
                    }
                }
                //如果不存在添加在剩下没添加的里面
                if (!ifExist) {
                    lastDict.add(dict);
                }

            }


            MoneySettingVo moneySettingVo = new MoneySettingVo(lastDict, moneySettingList);

            model.addAttribute("moneySettingVo", moneySettingVo);

            model.addAttribute("agenyCompany", agenyCompany);

            model.addAttribute("agenyArea", agenyArea);

            model.addAttribute("currentArea", currentArea);

            model.addAttribute("currentCompany", currentCompany);

            return "modules/jsps/moneySetting/dealer/moneySettingForm-greater";

        } else {

            List<MoneySetting> moneySettings = new ArrayList<MoneySetting>();

            //剔除不相关的
            for (MoneySetting moneySetting : moneySettingList) {

                if (moneySetting.getPaymentType() != PaymentType.PHYSTAMP) {

                    moneySettings.add(moneySetting);

                    continue;
                }

                for (Dict dict : dicts) {

                    if (moneySetting.getStampTexture().equals(dict.getValue())) {
                        moneySettings.add(moneySetting);

                    }
                }

            }

            MoneySettingVo moneySettingVo = new MoneySettingVo(moneySettings);

            model.addAttribute("moneySettingVo", moneySettingVo);

            model.addAttribute("agenyCompany", agenyCompany);

            model.addAttribute("agenyArea", agenyArea);

            model.addAttribute("currentArea", currentArea);

            model.addAttribute("currentCompany", currentCompany);

            return "modules/jsps/moneySetting/dealer/moneySettingForm-less";
        }

    }

    @RequestMapping(value = "/city/setting/list")
    public String cityList(Area area, HttpServletRequest request, HttpServletResponse response, Model model) {


        if (area == null || area.getId() == null || "".equals(area.getId())) {

            Company company = UserUtils.getUser().getCompanyInfo();

            area.setParent(company.getArea());

        }

        Page<Area> page = dealerMoneySettingService.findAreasByParentId(area, new Page<Area>(request, response));

        model.addAttribute("page", page);

        model.addAttribute("area", area);

        return "modules/jsps/moneySetting/city/moneySettingList";
    }

    @RequestMapping(value = "/city/setting/form")
    public String cityForm(@RequestParam(value = "areaId", required = false) String areaId, Model model) {

        Area area = new Area(areaId);


        List<MoneySetting> moneySettingList = moneySettingService.findMoneySettingByArea(area);

        area = areaService.get(area);

        List<Dict> dicts = moneySettingService.findStampTextureDict();


        //获得当前用户
        Company currentCompany = UserUtils.getUser().getCompanyInfo();

        Area currentArea = currentCompany.getArea();

        model.addAttribute("makeCompanyArea", area);

        model.addAttribute("currentArea", currentArea);

        model.addAttribute("currentCompany", currentCompany);

        if (moneySettingList.size() == 0) {

            model.addAttribute("dicts", dicts);


            return "modules/jsps/moneySetting/city/moneySettingForm-new";

        } else if (dicts.size() == (moneySettingList.size() - 2)) {


            MoneySettingVo moneySettingVo = new MoneySettingVo(moneySettingList);

            model.addAttribute("moneySettingVo", moneySettingVo);


            return "modules/jsps/moneySetting/city/moneySettingForm-equals";

        } else if (dicts.size() > (moneySettingList.size() - 2)) {

            List<Dict> lastDict = new ArrayList<Dict>();

            boolean ifExist = false;

            for (Dict dict : dicts) {

                ifExist = false;

                for (MoneySetting moneySetting : moneySettingList) {

                    if (moneySetting.getPaymentType() == PaymentType.PHYSTAMP) {

                        if (dict.getValue().equals(moneySetting.getStampTexture())) {

                            ifExist = true;

                            break;
                        }
                    }
                }
                //如果不存在添加在剩下没添加的里面
                if (!ifExist) {
                    lastDict.add(dict);
                }

            }


            MoneySettingVo moneySettingVo = new MoneySettingVo(lastDict, moneySettingList);

            model.addAttribute("moneySettingVo", moneySettingVo);


            return "modules/jsps/moneySetting/city/moneySettingForm-greater";

        } else {

            List<MoneySetting> moneySettings = new ArrayList<MoneySetting>();

            //剔除不相关的
            for (MoneySetting moneySetting : moneySettingList) {

                if (moneySetting.getPaymentType() != PaymentType.PHYSTAMP) {

                    moneySettings.add(moneySetting);

                    continue;
                }

                for (Dict dict : dicts) {

                    if (moneySetting.getStampTexture().equals(dict.getValue())) {
                        moneySettings.add(moneySetting);

                    }
                }

            }

            MoneySettingVo moneySettingVo = new MoneySettingVo(moneySettings);

            model.addAttribute("moneySettingVo", moneySettingVo);


            return "modules/jsps/moneySetting/city/moneySettingForm-less";
        }

    }

}
