package com.thinkgem.jeesite.modules.stamp.web.dataAnalysis;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis.AreaMapDTO;
import com.thinkgem.jeesite.modules.stamp.service.dataAnalysis.AdminStampCountService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2018\11\19 0019.
 */
@Controller
@RequestMapping(value = "${adminPath}/adminCount")
public class AdminStampCountController {
    @Autowired
    private AdminStampCountService as;

    @RequestMapping(value = "/admin-data-analysis")
    public String index(){
        return "modules/jsps/count/rcback/admin-data-analysis";
    }

    @RequestMapping(value = "/areaMap")
    public String areaMap(){
        return "modules/jsps/count/rcback/admin-areaMap";
    }



    /**
     * @author xucaikai
     * @TODO (注：辖区申请、已制作、已交付印章总数(管理员))
     * @param
     * @DATE: 2018\10\18 0018 9:08
     */

    @RequestMapping(value = "/getTotalApplyCount")
    @ResponseBody
    public String getTotalApplyCount(String areaName){

        List<Integer> totalList = as.getTotalApplyCount(areaName);

        return JsonMapper.toJsonString(totalList);
    }


    /**
     * @author xucaikai
     * @TODO (注：辖区申请印章总数)
     * @param
     * @DATE: 2018\10\18 0018 9:08
     */

    @RequestMapping(value = "/getTotalApplyCountList")
    @ResponseBody
    public String getTotalApplyCountList(@RequestParam String areaName){

        List<AreaMapDTO> totalList = as.getTotalApplyCountList(areaName);

        return JsonMapper.toJsonString(totalList);
    }

}
