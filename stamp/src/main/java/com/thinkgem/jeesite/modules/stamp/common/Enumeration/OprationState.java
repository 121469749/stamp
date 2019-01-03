package com.thinkgem.jeesite.modules.stamp.common.Enumeration;


/**
 * Created by Administrator on 2017/5/15.
 */
public enum OprationState implements BaseEnum<String> {

    OPEN("1","启用"),
    STOP("2","停用"),
    LOGOUT("3","已缴销"),
    REPORT("4","已挂失"),
    CHANGE("5","已缴销(变更)"),
    REPAIR("6","已缴销(补刻)");

    private String key;
    private String value;

    OprationState(String key, String value) {
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

    public static String getValue(String key) {

        for (OprationState c : OprationState.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return null;
    }
}
