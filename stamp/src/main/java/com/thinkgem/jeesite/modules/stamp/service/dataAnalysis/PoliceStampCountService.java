package com.thinkgem.jeesite.modules.stamp.service.dataAnalysis;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.fieldtype.AreaType;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dto.count.PoliceCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Auther: bb
 * @Date: 2018-08-09
 * @Description: 通过日期、印章状态计算各种数量
 */
@Service
public class PoliceStampCountService {

    @Autowired
    private StampDao stampDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private AreaDao areaDao;
    /**
     * @author bb
     * @TODO (注：数据来源统计)
      * @param
     * @DATE: 2018\10\17 0017 14:53
     */

    public Map getStampCountBySource() {
        final String color_dzhy = "#ffaeb3";
        final String color_ba = "#5fc3e8";
        int count_dzhy = 0;// 多证合一数
        int count_ba = 0;// 备案系统录入数

        Map<String,List<Object>> map = new HashMap<String,List<Object>>();
        List<String> makeCompIds = new ArrayList<String>();
        Stamp stamp = new Stamp();
        Company makeComp = new Company();

        stamp.setStampShape(StampShape.PHYSTAMP.getKey());// 计算物理印章

        if (UserUtils.getUser().getUserType() == UserType.MAKE){// 对象为刻章点

            makeCompIds.add(UserUtils.getUser().getCompanyInfo().getId());
            count_dzhy = stampDao.countStampFromDZHY(stamp,makeCompIds);
            count_ba = stampDao.countStampFromBA(stamp,makeCompIds);

        }else if (UserUtils.getUser().getUserType() == UserType.POLICE){// 对象为公安

            Police police = UserUtils.getUser().getPoliceInfo();
            // 区域过滤，查询当前公安区域下所属的制章单位
            makeComp.setCompType(CompanyType.MAKE);
            makeComp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "a3"));
            List<Company> makeComps = companyDao.findMakeComList(makeComp);
            for (Company makeComp2 : makeComps){
                makeCompIds.add(makeComp2.getId());
            }


            count_dzhy = stampDao.countStampFromDZHY(stamp,makeCompIds);
            count_ba = stampDao.countStampFromBA(stamp,makeCompIds);


        }

        List<Object> dzhyList = new ArrayList<Object>();
        dzhyList.add(count_dzhy);
        dzhyList.add(color_dzhy);
        List<Object> baList = new ArrayList<Object>();
        baList.add(count_ba);
        baList.add(color_ba);

        map.put("多证合一",dzhyList);
        map.put("备案系统录入",baList);

        return map;
    }

/**
 * @author xucaikai
 * @TODO (注：区域印章数量统计)
  * @param
 * @DATE: 2018\10\17 0017 14:53
 */

    public Map getStampAreaDataBySource() {
        Map<String,Integer> map = new HashMap<String,Integer>();
        Stamp stamp = new Stamp();
        Company makeComp = new Company();
        long a = System.currentTimeMillis();
        stamp.setStampShape(StampShape.PHYSTAMP.getKey());// 计算物理印章
        List<PoliceCountDTO> resultList = stampDao.stampAreaDataBySource();
        long b = System.currentTimeMillis();
        System.out.println("区域印章数量统计时间："+(b-a)+"ms");

        Police police = UserUtils.getUser().getPoliceInfo();
        List<Area> areaList = areaDao.getSubAreaByPareanId(police.getArea().getId());
        for (Area area : areaList){
            int tempCount = 0;
            for(int i = 0; i<resultList.size();i++){
                if(area.getId().equals(resultList.get(i).getAreaId()) && resultList.get(i).getStampCount() !=null){
                    tempCount += Integer.parseInt(resultList.get(i).getStampCount());
                }
            }
            map.put(area.getName(), tempCount);
        }
        return map;
    }
    /**
     * @author xucaikai
     * @TODO (注：统计该区域下每个刻章点所刻制的印章数量)
     * @param
     * @DATE: 2018\10\17 0017 14:53
     */
    public Map getMakeComDataSources(){
        Map<String,Integer> map = new HashMap<String,Integer>();
        List<String> makeCompIds = new ArrayList<String>();
        Stamp stamp = new Stamp();
        Company makeComp = new Company();

        Police police = UserUtils.getUser().getPoliceInfo();
        // 区域过滤，查询当前公安区域下所属的制章单位
        makeComp.setCompType(CompanyType.MAKE);
        makeComp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "a3"));
        List<Company> makeCompsList = companyDao.findMakeComList(makeComp);
        List<Integer> countList = stampDao.makeComStampDataSources(stamp,makeCompsList,makeCompsList.size());
        for (int i=0;i<makeCompsList.size();i++){
            map.put(makeCompsList.get(i).getCompanyName(),countList.get(i));
        }
        return map;
    }

    /**
     * @author xucaikai
     * @TODO (注：每年中每个月的印章数量统计)
     * @param
     * @DATE: 2018\10\17 0017 14:53
     */

    public Map getStampPerMonthForYear(String areaName) {
        Map<String,List> map = new HashMap<String,List>();
        List<Object> perMonthList = new ArrayList<>();
        Set<String> setYear = new HashSet<>();

        perMonthList = rtPerMonthList(areaName);
        Object obj[] = perMonthList.toArray();
        for(Object object:obj){
            String tempStr = object.toString();
            tempStr = tempStr.substring(tempStr.indexOf(",")+1,tempStr.lastIndexOf(","));
            setYear.add(tempStr);
        }

        String [] strings = perMonthList.toString().split("},");
        List<String> listOneYear = new ArrayList<>();
        List<Integer> listNum = new ArrayList<>();

        Iterator ite = setYear.iterator();
        while(ite.hasNext()){
            listOneYear.clear();
            String year = ite.next().toString();
            for (String strings1 : strings){
                if (strings1.contains(year)){
                    listOneYear.add(strings1);
                }
            }
            listNum = returnList(listOneYear);
            String tempYear = year.substring(year.indexOf("=")+1);
            map.put(tempYear+"年",listNum);
        }

        return map;
    }
    /**
     * @author xucaikai
     * @TODO (注：返回每年每月刻制数量)
      * @param
     * @DATE: 2018\10\25 0025 16:19
     */

    public List<Object> rtPerMonthList(String areaName){
        List<Object> perMonthList = new ArrayList<>();
        Stamp stamp = new Stamp();
        Company makeComp = new Company();

        stamp.setStampShape(StampShape.PHYSTAMP.getKey());// 计算物理印章

        if (UserUtils.getUser().getUserType() == UserType.MAKE) {// 对象为刻章点
            Company makeCom = UserUtils.getUser().getCompanyInfo();
            stamp.setMakeComp(new Company(makeCom.getId()));
            perMonthList = stampDao.stampPerMonthForYearMakeCom(stamp);

        }else if(UserUtils.getUser().getUserType() == UserType.POLICE){// 对象为公安
            Police police = UserUtils.getUser().getPoliceInfo();
            // 区域过滤，查询当前公安区域下所属的制章单位
            makeComp.setCompType(CompanyType.MAKE);
            makeComp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "a3"));
            List<Company> makeCompsList = companyDao.findMakeComList(makeComp);

            perMonthList = stampDao.stampPerMonthForYearPolice(stamp,makeCompsList,makeCompsList.size());
        }else{//润成后台管理员
                Area area = areaDao.getAreaByName(areaName);
                makeComp.setCompType(CompanyType.MAKE);
                makeComp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(area, "a3"));
                List<Company> makeCompsList = companyDao.findMakeComList(makeComp);
                if(makeCompsList.size()!=0) {
                    perMonthList = stampDao.stampPerMonthForYearPolice(stamp,makeCompsList,makeCompsList.size());
                }
        }

        return perMonthList;
    }
    /**
     * @author xucaikai
     * @TODO (注：)
      * @param list
     * @DATE: 2018\10\23 0023 15:05
     */

    public List<Integer> returnList(List<String> list){
        List<Integer> listNum = new ArrayList<>();
        int j=0;
        for (int i=1;i<=12;i++){
            String myMonth = "month="+i;
            String currentList = "";
            if (j<list.size()) {
                currentList = list.get(j);
            }
            if (currentList.contains(myMonth)){
                String currentList1= currentList.replace("}]","");
                listNum.add(Integer.parseInt(currentList1.substring(currentList1.indexOf("num=")).replaceAll("num=","")));
                j++;
            }else{
                listNum.add(0);
            }
        }
        return listNum;
    }


    /**
     * @author xucaikai
     * @TODO (注：(公安)统计今天、本周、本月、今年中备案、已制作、已交付印章的数量及企业数量)
     * @param
     * @DATE: 2018\10\18 0018 9:14
     */
    public List<Integer> stampAndUseComCountForPolice(String dateFlag){
        Stamp stamp = new Stamp();
        List<Integer> list = new ArrayList<>();
        Company makeComp = new Company();

        int twApplyCount =0;
        int twEngraveCount =0;
        int twDeliveryCount =0;
        int twUseComCount =0;

        stamp.setStampShape(StampShape.PHYSTAMP.getKey());// 计算物理印章
        Police police = UserUtils.getUser().getPoliceInfo();
        // 区域过滤，查询当前公安区域下所属的制章单位
        makeComp.setCompType(CompanyType.MAKE);
        makeComp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "a3"));
        List<Company> makeCompsList = companyDao.findMakeComList(makeComp);


        //已承接（已备案）
        stamp.getSqlMap().put("stampStateFilter",DataScopeFilterUtil.stampStateFilter(StampState.RECEPT,dateFlag));
        twApplyCount = stampDao.stampStateCountFromPolice(stamp,makeCompsList,makeCompsList.size());

        //已制作
        stamp.getSqlMap().put("stampStateFilter",DataScopeFilterUtil.stampStateFilter(StampState.ENGRAVE,dateFlag));
        twEngraveCount = stampDao.stampStateCountFromPolice(stamp,makeCompsList,makeCompsList.size());

        //已交付
        stamp.getSqlMap().put("stampStateFilter",DataScopeFilterUtil.stampStateFilter(StampState.DELIVERY,dateFlag));
        twDeliveryCount = stampDao.stampStateCountFromPolice(stamp,makeCompsList,makeCompsList.size());

        stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "sa"));
        stamp.getSqlMap().put("dateFilterForC",DataScopeFilterUtil.dateFilterForC(dateFlag));
        twUseComCount =stampDao.UseComCountFromPolice(stamp);

        list.add(twApplyCount);
        list.add(twEngraveCount);
        list.add(twDeliveryCount);
        list.add(twUseComCount);

        return list;
    }

    /**
     * @author xucaikai
     * @TODO (注：辖区申请印章总数(公安))
     * @param
     * @DATE: 2018\10\18 0018 9:14
     */
    public List<Integer> getTotalApplyCount(){
        Stamp stamp = new Stamp();
        List<Integer> list = new ArrayList<>();
        Company makeComp = new Company();

        int totalApplyCount =0;
        int totalEngraveCount =0;
        int totalDeliveryCount =0;
        int totalUseComCount =0;

        Police police = UserUtils.getUser().getPoliceInfo();
        // 区域过滤，查询当前公安区域下所属的制章单位
        makeComp.setCompType(CompanyType.MAKE);
        makeComp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "a3"));
        List<Company> makeCompsList = companyDao.findMakeComList(makeComp);

        //申请印章总数
        stamp.getSqlMap().put("stampStateFilter2",DataScopeFilterUtil.stampStateFilter2(StampState.DELIVERY));
        totalApplyCount = stampDao.getTotalCountForPolice(stamp,makeCompsList,makeCompsList.size());

        //已制作
        stamp.getSqlMap().put("stampStateFilter2",DataScopeFilterUtil.stampStateFilter2(StampState.ENGRAVE));
        totalEngraveCount = stampDao.getTotalCountForPolice(stamp,makeCompsList,makeCompsList.size());

        //已交付
        stamp.getSqlMap().put("stampStateFilter2",DataScopeFilterUtil.stampStateFilter2(StampState.DELIVERY));
        totalDeliveryCount = stampDao.getTotalCountForPolice(stamp,makeCompsList,makeCompsList.size());

        //辖区备案企业总数
        stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "sa"));
        totalUseComCount = stampDao.getTotalUseComCount(stamp);

        list.add(totalApplyCount);
        list.add(totalEngraveCount);
        list.add(totalDeliveryCount);
        list.add(totalUseComCount);

        return list;
    }

}
