package com.thinkgem.jeesite.modules.stamp.common.Enumeration;

/**
 * Created by Administrator on 2017/6/16.
 */
public enum ServiceTypeEnum implements BaseEnum<String> {

    STAMPSERVICE("2","印章业务"),
    LICENSESERVICE("1","许可证业务")
    ;

    private String key;
    private String value;


    ServiceTypeEnum(String key, String value) {
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

        for (ServiceTypeEnum c : ServiceTypeEnum.values()) {
            if (c.getKey().equals( key)) {
                return c.value;
            }
        }
        return null;
    }
}
