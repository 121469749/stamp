package com.thinkgem.jeesite.modules.stamp.web.dataAnalysis;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis.AnalysisDataDTO;
import com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis.SeriesData;
import com.thinkgem.jeesite.modules.stamp.service.dataAnalysis.MakeComstampCountService;
import com.thinkgem.jeesite.modules.stamp.service.dataAnalysis.PoliceStampCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xucaikai on 2018\10\25 0025.
 */
@Controller
@RequestMapping(value = "${adminPath}/MakeComCount")
public class MakeComStampCountController {

    @Autowired
    private MakeComstampCountService mcs;
    /**
     * @description: 跳转统计页面
     * @auther: bb
     * @param: [request, response, model]
     * @return:
     */
    @RequestMapping(value="/index")
    public String page(HttpServletRequest request, HttpServletResponse response, Model model){
        return "modules/jsps/count/makeCompany/data-analysis";
    }

    /**
     * @description: 统计印章类型的数量
     * @auther: xucaikai
     * @param: []
     * @return:
     */
    @RequestMapping(value = "/countStampFromStampType")
    @ResponseBody
    public AnalysisDataDTO countStampFromStampType(){

        Map<String,Integer> sourceMap = new HashMap<String,Integer>();
        List<String> legendData = new ArrayList<String>();
        List<SeriesData> seriesData = new ArrayList<SeriesData>();

        sourceMap = mcs.getcountStampFromStampType();

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
     * @TODO (注：申请印章总数（刻章点）)
     * @param
     * @DATE: 2018\10\18 0018 9:08
     */

    @RequestMapping(value = "/getTotalApplyCount")
    @ResponseBody
    public String getTotalApplyCount(){

        List<Integer> totalList = mcs.getTotalApplyCountForMakeCom();

        return JsonMapper.toJsonString(totalList);
    }

    /**
     * @author xucaikai
     * @TODO (注：(刻章点)统计今天、本周、本月、今年中备案、已制作、已交付印章的数量)
     * @param
     * @DATE: 2018\10\18 0018 9:08
     */

    @RequestMapping(value = "/stampAndUseComCountForDate")
    @ResponseBody
    public String stampAndUseComCountForDate(String dateFlag){

        List<Integer> totalList = mcs.stampAndUseComCountForDate(dateFlag);

        return JsonMapper.toJsonString(totalList);
    }


}
