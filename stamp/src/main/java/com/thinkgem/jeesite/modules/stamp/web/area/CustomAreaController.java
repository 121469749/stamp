package com.thinkgem.jeesite.modules.stamp.web.area;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */
@Controller
@RequestMapping(value="${adminPath}/sys/area/custom")
public class CustomAreaController {


    @Autowired
    private AreaService areaService;

    /**
     * 只搜索省
     * @param extId
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/onlyProvince")
    public List<Map<String, Object>> onlyProvince(@RequestParam(required=false) String extId, HttpServletResponse response) {

        //只搜索省
        List<Area> list = areaService.findAllOnlyProvince();

        List<Map<String, Object>> mapList = dealArea(list,extId);

        return mapList;
    }

    @ResponseBody
    @RequestMapping(value = "/onlyCity")
    public List<Map<String, Object>> onlyCity(@RequestParam(required=false) String extId, HttpServletResponse response) {

        List<Area> list = areaService.findAll();

        List<Map<String, Object>> mapList = dealArea(list,extId);
        return mapList;
    }


    @ResponseBody
    @RequestMapping(value = "/province")
    public List<Map<String, Object>> province(@RequestParam(required=false) String extId, HttpServletResponse response) {
        List<Area> list = areaService.findAllProvince();
        List<Map<String, Object>> mapList = dealArea(list,extId);
        return mapList;
    }

    @ResponseBody
    @RequestMapping(value="/currentCompanyArea")
    public List<Map<String, Object>> currentCompanyArea(@RequestParam(required=false) String extId, HttpServletResponse response) {

        List<Area> list = areaService.findCurrentAreaSubAreas();

        List<Map<String, Object>> mapList = dealArea(list,extId);
        return mapList;
    }

    @ResponseBody
    @RequestMapping(value="/currentCompanyAreaALayer")
    public List<Map<String, Object>> currentCompanyAreaALayer(@RequestParam(required=false) String extId, HttpServletResponse response) {
        List<Area> list = areaService.findCurrentAreaSubAreasALayerByCompany();
        List<Map<String, Object>> mapList = dealArea(list,extId);
        return mapList;
    }

    @ResponseBody
    @RequestMapping(value="/currentPoliceAreaALayer")
    public List<Map<String, Object>> currentPoliceAreaALayer(@RequestParam(required=false) String extId, HttpServletResponse response) {

        List<Area> list = areaService.findCurrentAreaSubAreasALayerByPolice();

        List<Map<String, Object>> mapList = dealArea(list,extId);

        return mapList;
    }


    //todo
    @ResponseBody
    @RequestMapping(value="/subLayer")
    public  List<Map<String, Object>> subLayer(@RequestParam(required=false) String extId, HttpServletResponse response){

        return null;
    }

    protected List<Map<String, Object>> dealArea(List<Area> list,String extId){

        List<Map<String, Object>> mapList = Lists.newArrayList();

        for (int i=0; i<list.size(); i++){
            Area e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }

        return mapList;
    }

}
