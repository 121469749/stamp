package com.thinkgem.jeesite.modules.stamp.service.police;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.stamp.dao.police.PoliceLogDao;
import com.thinkgem.jeesite.modules.stamp.entity.police.PoliceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hjw-pc on 2017/6/23.
 * 公安单位对刻章单位的使用日志的操作
 */
@Service
public class PoliceLogService {
  @Autowired
  private PoliceLogDao policeLogDao;


    /**
     *
     * @param page
     * @param policeLog
     * @return
     * 获取用户操作日志列表
     */
  public Page<PoliceLog> findPage(Page<PoliceLog> page, PoliceLog policeLog){
      if (policeLog.getEndDate() != null) {
          policeLog.setEndDate(DateUtils.addDays(policeLog.getEndDate(), 1));
          policeLog.setPage(page);
          page.setList(policeLogDao.findList(policeLog));
          policeLog.setEndDate(DateUtils.addDays(policeLog.getEndDate(), -1));

      } else {
          policeLog.setPage(page);
          page.setList(policeLogDao.findList(policeLog));
      }
      return page;
  }
}
