package com.thinkgem.jeesite.modules.check.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.check.dto.QueryDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;

import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */
@MyBatisDao
public interface CheckDao {

    public List<Stamp> findCheckStamp(QueryDTO dto);

    //润成经销商时效管控
    public List<Stamp> findCheckStamp2(QueryDTO dto);


    public Company getUseCompany(String id);

    public Company getMakeCompany(String id);

    public Stamp findStamp(Stamp stamp);

}
