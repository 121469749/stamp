package com.thinkgem.jeesite.modules.stamp.web.money;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.PaymentType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.money.DealerMoneySettingService;
import com.thinkgem.jeesite.modules.stamp.service.money.MoneySettingService;
import com.thinkgem.jeesite.modules.stamp.vo.moneySetting.MoneySettingVo;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
@Controller
@RequestMapping(value = "${adminPath}/moneySetting/makeCompany")
public class MakeMoneySettingController {

    @Autowired
    private DealerMoneySettingService dealerMoneySettingService;

    @Autowired
    private MoneySettingService moneySettingService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CompanyService companyService;


    @RequestMapping(value="/moneySetting/form")
    public String form(Model model){

        List<MoneySetting> moneySettingList = moneySettingService.findCurrentMakeCompanyMoneySetting();

        List<Dict> dicts = moneySettingService.findStampTextureDict();

        Company makeCompany = UserUtils.getUser().getCompanyInfo();

        model.addAttribute("currentCompany",makeCompany);

        if (moneySettingList.size() == 0) {

            model.addAttribute("dicts", dicts);


            return "modules/jsps/moneySetting/makeCompany/moneySettingForm-new";

        } else if (dicts.size() == (moneySettingList.size() - 2)) {


            MoneySettingVo moneySettingVo = new MoneySettingVo(moneySettingList);

            model.addAttribute("moneySettingVo", moneySettingVo);

            return "modules/jsps/moneySetting/makeCompany/moneySettingForm-equals";

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


            return "modules/jsps/moneySetting/makeCompany/moneySettingForm-greater";

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

            return "modules/jsps/moneySetting/makeCompany/moneySettingForm-less";
        }

    }

    @RequestMapping(value="/moneySetting/list")
    public String list(){


        return null;
    }

    public void save(){


    }

}
