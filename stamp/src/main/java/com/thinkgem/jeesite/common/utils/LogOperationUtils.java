package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.modules.log.dao.ModifyInfoLogDao;
import com.thinkgem.jeesite.modules.log.entity.ModifyInfoLog;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by xucaikai on 2018\8\1 0001.
 */
public class LogOperationUtils {



    @Autowired
    private ModifyInfoLogDao tLogDetailDao;
    /**
     * @author 许彩开
     * @TODO (注：用于记录更新前的数据)
      * @param tableName 表名
     * @DATE: 2018\8\1 0001 15:07
     */
    
    public void updateStart(String tableName){


    }

    /**
     * @author 许彩开
     * @TODO (注：用于记录更新后的数据)
     * @param source,target
     * @DATE: 2018\8\1 0001 15:07
     */

    public void updateEnd(Object source, Object target){

        ModifyInfoLog tLogDetail = new ModifyInfoLog();
//        Map<String,String> map= ReflectUtils.packageModifyContent(source,target);

//        tLogDetail.setColumnName(map.get("srcName"));
//        tLogDetail.setColumnText("");
//        tLogDetail.setOldValue(map.get("targetValue"));
//        tLogDetail.setNewValue(map.get("srcValue"));
//
//        tLogDetail.preInsert();
//
//        tLogDetailDao.insert(tLogDetail);

    }
}
