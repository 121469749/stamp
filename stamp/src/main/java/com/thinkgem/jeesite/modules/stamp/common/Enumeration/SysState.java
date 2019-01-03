package com.thinkgem.jeesite.modules.stamp.common.Enumeration;


/**
 *
 * 系统管控状态
 * Created by Locker on 2017/5/24.
 */
public enum  SysState implements BaseEnum<String> {

    USABLE("1","可用"),
    UNUSABLE("2","不可用")
    ;

    private String key;

    private String value;

    SysState(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
