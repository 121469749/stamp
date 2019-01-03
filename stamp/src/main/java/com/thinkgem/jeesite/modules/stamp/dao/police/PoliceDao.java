/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.dao.police;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公安机关DAO接口
 * @author Locker
 * @version 2017-05-13
 */
@MyBatisDao
public interface PoliceDao extends CrudDao<Police> {

    public Police getByArea(Police police);

    public List<Police> findPoliceByAreas(@Param(value="list")List<Area> areas,
                                          @Param(value="size")int size);


    public int countPoliceArea(Police police);


}