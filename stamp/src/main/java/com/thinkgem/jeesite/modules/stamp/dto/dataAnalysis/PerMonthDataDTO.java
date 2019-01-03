package com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xucaikai
 * @Date: 2018-08-09
 * @Description:
 */
public class PerMonthDataDTO {

    public List<String> legendData = new ArrayList<String>();// 图例值
    public List<List> seriesData = new ArrayList<List>();// 列表值

    public PerMonthDataDTO(List<String> legendData, List<List> seriesData) {
        this.legendData = legendData;
        this.seriesData = seriesData;
    }
}
