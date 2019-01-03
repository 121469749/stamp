package com.thinkgem.jeesite.modules.stamp.dao.makeStampCompany;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;

/**
 * Created by Administrator on 2017/5/20.
 */
@MyBatisDao
public interface MakeCompanyStampDao {

    public String geteleModel(String makecompId);
}
