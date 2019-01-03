package com.thinkgem.jeesite.modules.stamp.dao.water;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.water.Water;

import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */
@MyBatisDao
public interface WaterDao {

    public List<Water> findList(Water water);

    public Water get(Water water);

    public int insert(Water water);

    public int update(Water water);

    public int delete(Water water);


}
