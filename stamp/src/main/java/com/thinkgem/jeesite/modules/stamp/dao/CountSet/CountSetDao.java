package com.thinkgem.jeesite.modules.stamp.dao.CountSet;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.dto.countSet.CountSetDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import net.sf.ehcache.search.aggregator.Count;

/**
 * Created by Administrator on 2017/8/17.
 */
@MyBatisDao
public interface CountSetDao {

    public int insert(CountSet countSet);

    public int update(CountSet countSet);

    public CountSet get(CountSet countSet);

     void updateEleStampCountByCompanyId(CountSetDTO countSetDTO);

    void updatePhyStampCountByCompanyId(CountSetDTO countSetDTO);

    CountSet getEleCountByCompany(Company currentCompany);

    CountSet getPhyCountByCompany(Company currentCompany);
}
