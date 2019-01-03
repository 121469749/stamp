package com.thinkgem.jeesite.modules.stamp.service.stamprecord;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampLogDao;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampLog;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *印章操作Service（转授权、启停）
 * Created by sjk on 2017-07-03.
 */
@Service
public class StampLogService {

    @Autowired
    private StampLogDao stampLogDao;

    @Transactional(readOnly = true)
    public Page<StampLog> showComLog(Page<StampLog> page, StampLog stampLog) {

        // 设置默认时间范围，默认当前月
        if (stampLog.getBeginDate() == null){
            stampLog.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (stampLog.getEndDate() == null){
            stampLog.setEndDate(DateUtils.addMonths(stampLog.getBeginDate(), 1));
        }

        if (stampLog.getCreateBy() == null) {
            stampLog.setCreateBy(new User());
        }

        stampLog.getCreateBy().setUserTypeId(UserUtils.getUser().getUserTypeId());

        stampLog.getCreateBy().setUserType(UserType.USE);

        stampLog.setPage(page);

        List<StampLog> logList = stampLogDao.findList(stampLog);

        page.setList(logList);

        return page;
    }

    /**
     * 插入日志
     */
    @Transactional
    public void insertLog(String title) {

        StampLog stampLog = new StampLog();

        stampLog.setTitle(title);

        stampLog.setCreateBy(UserUtils.getUser());

        stampLog.setCreateDate(new Date());

        stampLogDao.insert(stampLog);
    }
}
