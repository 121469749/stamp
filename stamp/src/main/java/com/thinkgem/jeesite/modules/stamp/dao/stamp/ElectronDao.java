package com.thinkgem.jeesite.modules.stamp.dao.stamp;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Electron;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/6/23.
 */
@MyBatisDao
public interface ElectronDao {

    public int insert(Electron electron);

    public Electron get(Electron electron);

    public int rcControl(Electron electron);

    public int systemOprationState(@Param(value="id") String companyId,
                                   @Param(value="oprState") OprationState oprationState);
}
