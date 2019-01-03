package com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis;

import java.util.Map;

/**
 * @Auther: bb
 * @Date: 2018-08-26
 * @Description:
 */
public class SeriesData {

    private String name;
    private int value;
    private Map<String,String> itemStyle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Map<String, String> getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(Map<String, String> itemStyle) {
        this.itemStyle = itemStyle;
    }
}
