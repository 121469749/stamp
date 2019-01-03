package com.thinkgem.jeesite.modules.stamp.exception.moneySetting;

/**
 * Created by Locker on 2017/7/21.
 */
public class MoneySettingNotFoundException extends MoneySettingException {

    public MoneySettingNotFoundException(String message) {
        super(message);
    }

    public MoneySettingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
