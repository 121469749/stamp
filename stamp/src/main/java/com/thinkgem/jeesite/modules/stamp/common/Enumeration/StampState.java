package com.thinkgem.jeesite.modules.stamp.common.Enumeration;


/**
 * Created by Locker on 2017/5/13.
 *
 * 印章 状态
 *  对应StampEntity - stampState
 *
 */
public enum  StampState implements BaseEnum<String> {


    ENTERING("1","已录入"),
    RECEPT("2","已承接"),
    ENGRAVE("3","已制作"),
    DELIVERY("4","已交付");

    StampState(String key, String value) {
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

    public static String getValue(String key) {

        for (StampState c : StampState.values()) {
            if (c.getKey().equals( key)) {
                return c.value;
            }
        }
        return null;
    }
}
