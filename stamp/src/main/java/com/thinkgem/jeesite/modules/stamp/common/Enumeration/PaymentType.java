package com.thinkgem.jeesite.modules.stamp.common.Enumeration;

/**
 * Created by Administrator on 2017/7/19.
 */
public enum PaymentType implements BaseEnum<String> {

    PHYSTAMP("1","物理印章"),
    ELESTAMP("2","电子印章"),
    UKEY("3","U盾");

    private String key;

    private String value;


    PaymentType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(String key) {

        for (PaymentType c : PaymentType.values()) {
            if (c.getKey().equals(key) ) {
                return c.value;
            }
        }
        return null;
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
