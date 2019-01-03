package com.thinkgem.jeesite.modules.stamp.common.Enumeration;



/**
 * Created by Locker on 2017/5/13.
 *
 *  办理业务类型枚举类
 *     Licence Entity - workType
 *
 */
public enum WorkType implements BaseEnum<String> {


    OPEN("1","开办申请"),
    ANNUAL("2","年审申请"),
    CHANGE("3","变更申请"),
    LOGOUT("4","注销申请");


    private String key ;


    private String value;

    WorkType(String key,String value) {
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

        for (WorkType c : WorkType.values()) {
            if (c.getKey().equals( key)) {
                return c.value;
            }
        }
        return null;
    }
}
