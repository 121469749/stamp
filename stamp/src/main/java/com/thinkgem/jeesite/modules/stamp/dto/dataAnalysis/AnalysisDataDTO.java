package com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: bb
 * @Date: 2018-08-09
 * @Description:
 */
public class AnalysisDataDTO {

    public List<String> legendData = new ArrayList<String>();// 图例值
    public List<SeriesData> seriesData = new ArrayList<SeriesData>();// 列表值

    public AnalysisDataDTO(List<String> legendData, List<SeriesData> seriesData) {
        this.legendData = legendData;
        this.seriesData = seriesData;
    }
}
