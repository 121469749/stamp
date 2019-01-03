package com.thinkgem.jeesite.modules.stamp.common.Enumeration;


/**
 * Created by Locker on 2017/5/13.
 * 单位/公司类型
 *  对应 CompanyEntity - compType
 *
 */
public enum CompanyType implements BaseEnum<String> {

    MAKE("1","刻章单位"),
    USE("2","用章公司"),
    AGENCY("3","经销商"),
    ;

    private String key ;

    private String value;

    CompanyType(String key, String value) {
        this.key=key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(String key) {

        for (CompanyType c : CompanyType.values()) {
            if (c.getKey() .equals(key) ) {
                return c.value;
            }
        }
        return null;
    }


}
