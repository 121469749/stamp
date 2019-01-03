package com.thinkgem.jeesite.modules.stamp.common.Enumeration;

/**
 *
 * 印章类型
 * Created by Locker on 2017/7/12.
 */
public enum  StampShape implements BaseEnum<String> {

    PHYSTAMP("1","物理印章"),
    ELESTAMP("2","电子印章");

    private String key;

    private String value;


    StampShape(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(String key) {

        for (StampShape c : StampShape.values()) {
            if (c.getKey() .equals(key) ) {
                return c.value;
            }
        }
        return null;
    }
}
