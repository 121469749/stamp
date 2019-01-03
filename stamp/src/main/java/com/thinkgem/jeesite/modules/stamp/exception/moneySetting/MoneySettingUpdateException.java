package com.thinkgem.jeesite.modules.stamp.exception.moneySetting;

import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;

/**
 * Created by Administrator on 2017/7/20.
 */
public class MoneySettingUpdateException extends MoneySettingException{

    public MoneySettingUpdateException(String message) {
        super(message);
    }

    public MoneySettingUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
