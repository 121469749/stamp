package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by hjw-pc on 2017/6/3.
 * 数据过滤工具类
 */
public class DataScopeFilterUtil {

    public static String  areaFilter(Area area, String fkAlias){
        StringBuilder sb = new StringBuilder();
        //省或直辖市
        if(area.getType().equals("2")){
            sb.append( "AND (" + fkAlias + ".parent_ids " + "LIKE " + "'%"+ area.getId() + "%' OR " + fkAlias + ".parent_id "+ "LIKE " + "'"+ area.getId() +"')");

        }
        //市
        else if(area.getType().equals("3")){
            sb.append( "AND (" + fkAlias + ".parent_ids " + "LIKE " + "'%"+ area.getId() + "%' OR " + fkAlias + ".parent_id "+ "LIKE " + "'"+ area.getId() +"')");

        }
        //区
        else if(area.getType().equals("4")){

            sb.append("AND " + fkAlias + ".id " + "= " + "'"+ area.getId() + "'");

        }
        return sb.toString();
    }

    public static String  areaFilterEquals(Area area, String fkAlias){
        StringBuilder sb = new StringBuilder();
        //省或直辖市
        if(area.getType().equals("2")){
            sb.append( "AND (" + fkAlias + ".parent_ids " + "= "+"'" + area.getId() + "'"+" OR " + fkAlias + ".parent_id "+ "= "+"'" + area.getId() +"')");

        }
        //市
        else if(area.getType().equals("3")){
            sb.append( "AND (" + fkAlias + ".parent_ids " + "= "+"'" + area.getId() + "'"+" OR " + fkAlias + ".parent_id "+ "= "+"'" + area.getId() +"')");

        }
        //区
        else if(area.getType().equals("4")){

            sb.append("AND " + fkAlias + ".id " + "= " + "'"+ area.getId() + "'");

        }
        return sb.toString();
    }
/**
 * @author xucaikai
 * @TODO (注：（刻章点）统计今天、本周、本月、今年中备案、已制作、已交付印章的数量sql的过滤)
  * @param stampState
 * @DATE: 2018\10\29 0029 11:27
 */

    public static String stampStateFilter(StampState stampState,String dateType){
        StringBuffer sb = new StringBuffer();
        //已备案
        switch (dateType){
            case "today"://统计今日
                if(stampState.getKey().equals(StampState.RECEPT.getKey())){//已备案
                    sb.append(" AND TO_DAYS(a.record_date) = TO_DAYS(NOW())");
                    break;
                }else if(stampState.getKey().equals(StampState.ENGRAVE.getKey())){//已制作
                    sb.append(" AND TO_DAYS(a.make_date) = TO_DAYS(NOW())");
                    break;
                }else if(stampState.getKey().equals(StampState.DELIVERY.getKey())){//已交付
                    sb.append(" AND TO_DAYS(a.delivery_date) = TO_DAYS(NOW())");
                    break;
                }
            case "thisWeek"://统计本周
                if(stampState.getKey().equals(StampState.RECEPT.getKey())){//已备案
                    sb.append(" AND YEARWEEK(date_format(a.record_date,'%Y-%m-%d')) = YEARWEEK(NOW())");
                    break;
                }else if(stampState.getKey().equals(StampState.ENGRAVE.getKey())){//已制作
                    sb.append(" AND YEARWEEK(date_format(a.make_date,'%Y-%m-%d')) = YEARWEEK(NOW())");
                    break;
                }else if(stampState.getKey().equals(StampState.DELIVERY.getKey())){//已交付
                    sb.append(" AND YEARWEEK(date_format(a.delivery_date,'%Y-%m-%d')) = YEARWEEK(NOW())");
                    break;
                }
            case "thisMonth"://统计本月
                if(stampState.getKey().equals(StampState.RECEPT.getKey())){//已备案
                    sb.append(" AND date_format(a.record_date,'%Y-%m') = date_format(NOW(),'%Y-%m')");
                    break;
                }else if(stampState.getKey().equals(StampState.ENGRAVE.getKey())){//已制作
                    sb.append(" AND date_format(a.make_date,'%Y-%m') = date_format(NOW(),'%Y-%m')");
                    break;
                }else if(stampState.getKey().equals(StampState.DELIVERY.getKey())){//已交付
                    sb.append("  AND date_format(a.delivery_date,'%Y-%m') = date_format(NOW(),'%Y-%m')");
                    break;
                }
            case "thisYear"://统计今年
                if(stampState.getKey().equals(StampState.RECEPT.getKey())){//已备案
                    sb.append("  AND YEAR(a.record_date) = YEAR(NOW())");
                    break;
                }else if(stampState.getKey().equals(StampState.ENGRAVE.getKey())){//已制作
                    sb.append("  AND YEAR(a.make_date) = YEAR(NOW())");
                    break;
                }else if(stampState.getKey().equals(StampState.DELIVERY.getKey())){//已交付
                    sb.append("  AND YEAR(a.delivery_date) = YEAR(NOW())");
                    break;
                }
        }

        return sb.toString();
    }

/**
 * @author xucaikai
 * @TODO (注：（刻章点）统计统计今天、本周、本月、今年中备案企业总数)
  * @param dateType
 * @DATE: 2018\10\29 0029 11:40
 */

    public static String dateFilter(String dateType){
        StringBuffer sb = new StringBuffer();
        //已备案
        switch (dateType){
            case "today"://统计今日
                    sb.append(" AND TO_DAYS(a.create_date) = TO_DAYS(NOW())");
                    break;
            case "thisWeek"://统计本周
                sb.append(" AND YEARWEEK(date_format(a.create_date,'%Y-%m-%d')) = YEARWEEK(NOW())");
                break;
            case "thisMonth"://统计本月
                sb.append(" AND date_format(a.create_date,'%Y-%m') = date_format(NOW(),'%Y-%m')");
                break;
            case "thisYear"://统计今年
                sb.append(" AND YEAR(a.create_date) = YEAR(NOW())");
                break;
        }

        return sb.toString();
    }

    /**
     * @author xucaikai
     * @TODO (注：（刻章点）统计统计今天、本周、本月、今年中备案企业总数)
     * @param dateType
     * @DATE: 2018\10\29 0029 11:40
     */

    public static String dateFilterForC(String dateType){
        StringBuffer sb = new StringBuffer();
        //已备案
        switch (dateType){
            case "today"://统计今日
                sb.append(" AND TO_DAYS(c.create_date) = TO_DAYS(NOW())");
                break;
            case "thisWeek"://统计本周
                sb.append(" AND YEARWEEK(date_format(c.create_date,'%Y-%m-%d')) = YEARWEEK(NOW())");
                break;
            case "thisMonth"://统计本月
                sb.append(" AND date_format(c.create_date,'%Y-%m') = date_format(NOW(),'%Y-%m')");
                break;
            case "thisYear"://统计今年
                sb.append(" AND YEAR(c.create_date) = YEAR(NOW())");
                break;
        }

        return sb.toString();
    }

    public static String stampStateFilter2(StampState stampState){
        StringBuffer sb = new StringBuffer();
        //已备案
                if(stampState.getKey().equals(StampState.RECEPT.getKey())){//已备案
                    sb.append("  AND a.record_date is not null");

                }else if(stampState.getKey().equals(StampState.ENGRAVE.getKey())){//已制作
                    sb.append("  AND a.make_date is not null");

                }else if(stampState.getKey().equals(StampState.DELIVERY.getKey())){//已交付
                    sb.append("  AND a.delivery_date is not null");

                }

        return sb.toString();
    }


    public static String areaFilter(List<Area> areas){

        //其实加下面这个话就得了，我要将这段话塞进去sql里面
        //AND a.area_id in(
          //  <foreach collection="list" item="item" separator=",">
          //      #{item.id}
         //   </foreach>
         //   )
        //

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AND a.area_id in ("  );
        for (int i=0;i<areas.size();i++) {
            stringBuilder.append("'"+areas.get(i).getId()+"'");
            if(i !=(areas.size()-1)){
                stringBuilder.append(",");
            }
        }
        //去掉最后的逗号

        stringBuilder.append(")");

        System.out.println(stringBuilder.toString());

        return stringBuilder.toString();
    }

}
