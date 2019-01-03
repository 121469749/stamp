/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.log.entity.ModifyInfoLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志明细DAO接口
 * @author xucaikai
 * @version 2018-08-08
 */
@MyBatisDao
public interface ModifyInfoLogDao extends CrudDao<ModifyInfoLog> {

    /**
     * @author 许彩开
     * @TODO (注：返回所修改字段名)
      * @param tableName
     * @DATE: 2018\8\8 0008 17:58
     */

    public String findColComment (@Param("tableName")  String tableName, @Param("s") String s,@Param("db") String db);

/**
 * @author 许彩开
 * @TODO (注：返回修改的表注释)
  * @param tableName
 * @DATE: 2018\8\8 0008 17:58
 */

    public String findTableComment (@Param("tableName")  String tableName ,@Param("db") String db);

    public List<ModifyInfoLog> findModifyInfoLog(ModifyInfoLog mil);

}