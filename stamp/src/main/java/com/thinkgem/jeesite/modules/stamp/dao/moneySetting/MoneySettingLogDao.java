package com.thinkgem.jeesite.modules.stamp.dao.moneySetting;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySettingLog;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;

/**
 * Created by Locker on 2017/7/17.
 */
@MyBatisDao
public interface MoneySettingLogDao {

    public int insert(MoneySettingLog moneySettingLog);

}
