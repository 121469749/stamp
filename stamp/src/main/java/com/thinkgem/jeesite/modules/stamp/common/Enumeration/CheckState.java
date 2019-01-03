package com.thinkgem.jeesite.modules.stamp.common.Enumeration;


/**
 * Created by hjw-pc on 2017/5/14.
 */

public enum CheckState implements BaseEnum {

    CHECKING("1","审核中"),
    CHECKFAIL("2","审核不通过"),
    CHECKSUCCESS("3","审核通过");

    private String key ;

    private String value;

    CheckState(String key,String value) {
        this.key=key;
        this.value=value;
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

        for (CheckState c : CheckState.values()) {
            if (c.getKey().equals( key)) {
                return c.value;
            }
        }
        return null;
    }
}
