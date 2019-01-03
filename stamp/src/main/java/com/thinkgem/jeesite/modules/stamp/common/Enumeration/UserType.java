package com.thinkgem.jeesite.modules.stamp.common.Enumeration;



/**
 * Created by Locker on 2017/5/13.
 *
 *  用户类型枚举类
 *
 *  User Entity -userType
 *
 */
public enum UserType implements BaseEnum<String> {

    ADMIN("1","系统管理员"),
    AGENY("2","经销商"),
    MAKE("3","制章点"),
    USE("4","用章单位"),
    POLICE("5","公安机关");

    private String key;

    private String value;

    UserType(String key, String value) {
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    public static String getValue(String key) {
        for (UserType c : UserType.values()) {
            if (c.getKey().equals(key) ) {
                return c.value;
            }
        }
        return null;
    }

    public static UserType getUserTypeByKey(String key) {

        for (UserType c : UserType.values()) {
            if (c.getKey().equals(key) ) {
                return c;
            }
        }
        return null;
    }

}
