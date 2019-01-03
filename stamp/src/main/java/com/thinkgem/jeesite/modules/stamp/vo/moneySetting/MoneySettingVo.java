package com.thinkgem.jeesite.modules.stamp.vo.moneySetting;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.PaymentType;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Locker on 2017/7/19.
 */
public class MoneySettingVo {

    private List<MoneySetting> phyMoneySettings;

    private List<MoneySetting> eleMoneySettings;

    private List<Dict> lastDicts;


    public MoneySettingVo(List<MoneySetting> moneySettingList){

        screenList(moneySettingList);
    }


    public MoneySettingVo( List<Dict> lastDicts,List<MoneySetting> moneySettingList) {

        this.lastDicts = lastDicts;

        screenList(moneySettingList);

    }

    public List<MoneySetting> getPhyMoneySettings() {
        return phyMoneySettings;
    }

    public void setPhyMoneySettings(List<MoneySetting> phyMoneySettings) {
        this.phyMoneySettings = phyMoneySettings;
    }

    public List<MoneySetting> getEleMoneySettings() {
        return eleMoneySettings;
    }

    public void setEleMoneySettings(List<MoneySetting> eleMoneySettings) {
        this.eleMoneySettings = eleMoneySettings;
    }

    public List<Dict> getLastDicts() {
        return lastDicts;
    }

    public void setLastDicts(List<Dict> lastDicts) {
        this.lastDicts = lastDicts;
    }

    protected void screenList(List<MoneySetting> moneySettingList) {

        List<MoneySetting> eleMoneySettings = new ArrayList<MoneySetting>();

        List<MoneySetting> phyMoneySettings = new ArrayList<MoneySetting>();

        for(MoneySetting moneySetting : moneySettingList){

            if(moneySetting.getPaymentType() == PaymentType.PHYSTAMP){

                phyMoneySettings.add(moneySetting);

            }else{

                eleMoneySettings.add(moneySetting);
            }

        }


        this.eleMoneySettings = eleMoneySettings;

        this.phyMoneySettings = phyMoneySettings;

    }

}
