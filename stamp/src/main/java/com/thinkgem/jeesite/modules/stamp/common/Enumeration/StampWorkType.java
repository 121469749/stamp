package com.thinkgem.jeesite.modules.stamp.common.Enumeration;


/**
 * Created by Administrator on 2017/5/20.
 */
public enum StampWorkType implements BaseEnum<String> {

    APPLY("1","申请备案"),
    LOGOUT("2","缴销备案"),
    REPORT("3","挂失备案"),
    REPAIR("4","补刻备案"),
    CHANGE("5","变更备案")
    ;


    StampWorkType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;

    private String value;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
