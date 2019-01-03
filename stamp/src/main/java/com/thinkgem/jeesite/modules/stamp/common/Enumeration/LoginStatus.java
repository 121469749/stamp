package com.thinkgem.jeesite.modules.stamp.common.Enumeration;

/**
 * Created by hjw-pc on 2017/7/27.
 */
public enum LoginStatus implements BaseEnum<String> {

    success("1", "成功"),
    fail("2","失败")
    ;

    private String key;
    private String value;

    LoginStatus(String key, String value) {
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

        for (LoginStatus c : LoginStatus.values()) {
            if (c.getKey().equals( key)) {
                return c.value;
            }
        }
        return null;
    }
}
