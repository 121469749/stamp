package com.thinkgem.jeesite.modules.stamp.common.Enumeration;

/**
 * Created by hjw-pc on 2017/7/27.
 */
public enum LoginType implements BaseEnum<String> {

    in("1", "登入"),
    out("2","登出")
    ;

    private String key;
    private String value;

    LoginType(String key, String value) {
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

        for (LoginType c : LoginType.values()) {
            if (c.getKey().equals( key)) {
                return c.value;
            }
        }
        return null;
    }
}
