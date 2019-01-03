package com.thinkgem.jeesite.modules.stamp.web.money;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.PaymentType;
import com.thinkgem.jeesite.modules.stamp.dto.money.MoneySettingListDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.money.MoneySettingService;
import com.thinkgem.jeesite.modules.stamp.service.money.RunchengMoneySettingService;
import com.thinkgem.jeesite.modules.stamp.vo.moneySetting.MoneySettingVo;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by locker on 2017/7/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/moneySetting/runcheng")
public class RunChengMoneySettingController {


    @Autowired
    private RunchengMoneySettingService runchengMoneySettingService;

    @Autowired
    private MoneySettingService moneySettingService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/setting/list")
    public String list(Company company, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<Company> page = runchengMoneySettingService.findCompanyPage(company, new Page<Company>(request, response));

        model.addAttribute("page", page);

        model.addAttribute("company", company);

        return "modules/jsps/moneySetting/runcheng/moneySettingList";
    }


    @RequestMapping(value = "/setting/form")
    public String form(@RequestParam(value = "companyId") String id, Model model) {

        List<MoneySetting> moneySettingList = moneySettingService.findMoneySettingByCompanyId(id);

        List<Dict> dicts = moneySettingService.findStampTextureDict();

        Company agenyCompany = companyService.get(new Company(id, CompanyType.AGENCY));

        Area agenyArea = areaService.get(new Area(agenyCompany.getArea().getId()));


        //获得当前用户
        Company currentCompany = UserUtils.getUser().getCompanyInfo();

        Area currentArea = currentCompany.getArea();

        //缴费经销商
        model.addAttribute("agenyCompany", agenyCompany);

        //收费经销商-当前经销商
        model.addAttribute("currentCompany", currentCompany);

        if (moneySettingList.size() == 0) {

            model.addAttribute("dicts", dicts);


            return "modules/jsps/moneySetting/runcheng/moneySettingForm-new";

        } else if (dicts.size() == (moneySettingList.size() - 2)) {


            MoneySettingVo moneySettingVo = new MoneySettingVo(moneySettingList);

            model.addAttribute("moneySettingVo", moneySettingVo);

            return "modules/jsps/moneySetting/runcheng/moneySettingForm-equals";

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


            return "modules/jsps/moneySetting/runcheng/moneySettingForm-greater";

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

            return "modules/jsps/moneySetting/runcheng/moneySettingForm-less";
        }

    }


    @RequestMapping(value = "/setting/save", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String save(MoneySettingListDTO moneySettingListDTO) {

        List<MoneySetting> moneySettings = toMoneySettingList(moneySettingListDTO);

        Condition condition = moneySettingService.saveMoneySettingList(moneySettings);

        return JsonMapper.toJsonString(condition);
    }

    /**
     * 表单提交的MoneySettingListDTO  转换成List<MoneySetting>
     *
     * @param moneySettingListDTO
     * @return
     */
    protected List<MoneySetting> toMoneySettingList(MoneySettingListDTO moneySettingListDTO) {

        Company company = moneySettingListDTO.getCompany();

        Area area = moneySettingListDTO.getArea();

        List<MoneySetting> moneySettings = new ArrayList<MoneySetting>();

        MoneySetting moneySetting = null;

        List<String> ids = moneySettingListDTO.getId();

        List<Double> moneys = moneySettingListDTO.getMoney();

        List<PaymentType> paymentTypes = moneySettingListDTO.getPaymentType();

        List<String> stampTexture = moneySettingListDTO.getStampTexture();

        int phyNum = 0;

        if (ids == null) {

            for (int i = 0; i < paymentTypes.size(); i++) {

                moneySetting = new MoneySetting(company, area, paymentTypes.get(i), (int) (moneys.get(i) * 100));

                if (paymentTypes.get(i) == PaymentType.PHYSTAMP) {

                    moneySetting.setStampTexture(stampTexture.get(phyNum));

                    phyNum++;
                }


                moneySettings.add(moneySetting);

            }

        }else{

            for (int i = 0; i < paymentTypes.size(); i++) {

                moneySetting = new MoneySetting(company, area, paymentTypes.get(i), (int) (moneys.get(i) * 100));

                moneySetting.setId(ids.get(i));

                if (paymentTypes.get(i) == PaymentType.PHYSTAMP) {

                    moneySetting.setStampTexture(stampTexture.get(phyNum));

                    phyNum++;
                }


                moneySettings.add(moneySetting);

            }

        }


        return moneySettings;

    }


}
