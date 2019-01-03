package com.thinkgem.jeesite.modules.stamp.common.Enumeration;


/**
 * Created by Locker on 2017/5/13.
 *
 * 公司/单位 企业状态
 * 对应CompanyEntity-CompState
 */
public enum CompanyState implements BaseEnum<String> {

    CHECKING("1","审核中"),
    USING("2","已许可"),
    STOP("3","暂停"),
    LOGOUT("4","已撤销");


    private String key;

    private String value;

    CompanyState(String key, String value) {
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

        for (CompanyState c : CompanyState.values()) {
            if (c.getKey().equals( key)) {
                return c.value;
            }
        }
        return null;
    }


}
