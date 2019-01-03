package com.thinkgem.jeesite.modules.stamp.web.dataAnalysis;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis.AnalysisDataDTO;
import com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis.PerMonthDataDTO;
import com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis.SeriesData;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.service.dataAnalysis.PoliceStampCountService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 通过日期、印章状态计算各种数量
 * @auther: bb
 * @date: 2018-08-03 11:08
 */
@Controller
@RequestMapping(value = "${adminPath}/baseCount")
public class PoliceStampCountController {

    @Autowired
    private PoliceStampCountService bs;



    @RequestMapping(value="/policeDataAnalysisPage")
    public String policeDataAnalysisPage(HttpServletRequest request, HttpServletResponse response, Model model){
        Police police = UserUtils.getUser().getPoliceInfo();
        Area area = police.getArea();
        if(area.getType().equals("4")){//区
            model.addAttribute("area","4");
        }else{//市
            model.addAttribute("area","3");
        }
        return "modules/jsps/count/police/police-data-analysis";

    }

    /**
     * @description: 数据来源统计
     * @auther: bb
     * @param: []
     * @return:
     */
    @RequestMapping(value = "/showPieDataSource")
    @ResponseBody
    public AnalysisDataDTO showPieDataSource(){
        Map<String,List<Object>> sourceMap = new HashMap<String,List<Object>>();
        List<String> legendData = new ArrayList<String>();
        List<SeriesData> seriesData = new ArrayList<SeriesData>();

        sourceMap = bs.getStampCountBySource();
        for (Map.Entry<String,List<Object>> entry : sourceMap.entrySet()){
            legendData.add(entry.getKey());

            SeriesData sd = new SeriesData();
            sd.setName(entry.getKey());
            sd.setValue((Integer) entry.getValue().get(0));
            Map colorMap = new HashMap();
            colorMap.put("color", (String) entry.getValue().get(1));
            sd.setItemStyle(colorMap);

            seriesData.add(sd);
        }

        AnalysisDataDTO data = new AnalysisDataDTO(legendData,seriesData);

        return data;
    }


    /**
     * @description: 区域印章数量统计
     * @auther: xucaikai
     * @param: []
     * @return:
     */
    @RequestMapping(value = "/showAreaDataSources")
    @ResponseBody
    public AnalysisDataDTO showAreaDataSources(){

        Map<String,Integer> sourceMap = new HashMap<String,Integer>();
        List<String> legendData = new ArrayList<String>();
        List<SeriesData> seriesData = new ArrayList<SeriesData>();

        sourceMap = bs.getStampAreaDataBySource();
        for (Map.Entry<String,Integer> entry : sourceMap.entrySet()){
            legendData.add(entry.getKey());

            SeriesData sd = new SeriesData();
            sd.setName(entry.getKey());
            sd.setValue( entry.getValue());

            seriesData.add(sd);
        }

        AnalysisDataDTO data = new AnalysisDataDTO(legendData,seriesData);
        return data;
    }


    /**
     * @description: 每年中每个月的印章数量统计
     * @auther: xucaikai
     * @param: []
     * @return:
     */
    @RequestMapping(value = "/showLineDataSources")
    @ResponseBody
    public PerMonthDataDTO showLineDataSources(@RequestParam String areaName){
        List<String> legendData = new ArrayList<String>();
        List<List> seriesData = new ArrayList<List>();

        Map<String,List> sourceMap = new HashMap<String,List>();
        sourceMap = bs.getStampPerMonthForYear(areaName);
        for (Map.Entry<String,List> entry : sourceMap.entrySet()){
            legendData.add(entry.getKey());
            seriesData.add(entry.getValue());
        }

        PerMonthDataDTO data = new PerMonthDataDTO(legendData,seriesData);
        return data;
    }


    /**
     * @description: 统计该区域下每个刻章点所刻制的印章数量
     * @auther: xucaikai
     * @param: []
     * @return:
     */
    @RequestMapping(value = "/showBarMakeComDataSources")
    @ResponseBody
    public AnalysisDataDTO showBarMakeComDataSources(){

        Map<String,Integer> sourceMap = new HashMap<String,Integer>();
        List<String> legendData = new ArrayList<String>();
        List<SeriesData> seriesData = new ArrayList<SeriesData>();

        sourceMap = bs.getMakeComDataSources();
        for (Map.Entry<String,Integer> entry : sourceMap.entrySet()){
            legendData.add(entry.getKey());

            SeriesData sd = new SeriesData();
            sd.setName(entry.getKey());
            sd.setValue( entry.getValue());

            seriesData.add(sd);
        }
        AnalysisDataDTO data = new AnalysisDataDTO(legendData,seriesData);
        return data;
    }


    /**
     * @author xucaikai
     * @TODO (注：(公安)统计今天、本周、本月、今年中备案、已制作、已交付印章的数量及企业数量)
     * @param
     * @DATE: 2018\10\18 0018 9:08
     */

    @RequestMapping(value = "/stampAndUseComCountForPolice")
    @ResponseBody
    public String stampAndUseComCountForPolice(String dateFlag){

        List<Integer> totalList = bs.stampAndUseComCountForPolice(dateFlag);

        return JsonMapper.toJsonString(totalList);
    }

    /**
     * @author xucaikai
     * @TODO (注：辖区申请印章总数)
     * @param
     * @DATE: 2018\10\18 0018 9:08
     */

    @RequestMapping(value = "/getTotalApplyCount")
    @ResponseBody
    public String getTotalApplyCount(){

        List<Integer> totalList = bs.getTotalApplyCount();

        return JsonMapper.toJsonString(totalList);
    }


}
