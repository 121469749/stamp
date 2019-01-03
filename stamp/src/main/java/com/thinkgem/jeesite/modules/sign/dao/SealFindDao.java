package com.thinkgem.jeesite.modules.sign.dao;


import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sign.entity.Seal;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@MyBatisDao
public interface SealFindDao {

    public List<Seal> findList(Seal seal);

    public Seal get(Seal seal);

    public Stamp findStamp(Seal seal);

    public Stamp findStampById(Seal seal);
}
