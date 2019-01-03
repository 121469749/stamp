package com.thinkgem.jeesite.modules.stamp.common.Enumeration;


/**
 * Created by Locker on 2017/5/13.
 *  印章授权状态
 *
 */
public enum StampAuthState implements BaseEnum<String> {

    AUTH_TRUE("1","有权限"),
    AUTH_FALSE("2","没有权限");

    private String key;

    private String value;

    StampAuthState(String key, String value) {

        this.key=key;

        this.value=value;

    }

    public static String getValue(String key) {

        for (StampAuthState c : StampAuthState.values()) {
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
