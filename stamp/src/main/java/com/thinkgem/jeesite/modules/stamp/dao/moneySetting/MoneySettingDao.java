package com.thinkgem.jeesite.modules.stamp.dao.moneySetting;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CheckState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Locker on 2017/7/17.
 */
@MyBatisDao
public interface MoneySettingDao {

    public int insert(MoneySetting moneySetting);

    public List<MoneySetting> findListByCompanyId(@Param(value="companyId")String companyId);

    public int update(MoneySetting moneySetting);

    public List<MoneySetting> findList(MoneySetting moneySetting);

    public MoneySetting getMoneySetting(MoneySetting moneySetting);

}
