package com.thinkgem.jeesite.modules.stamp.service.common;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.entity.LoginLog;
import com.thinkgem.jeesite.modules.stamp.dao.common.LoginLogDao;
import com.thinkgem.jeesite.modules.stamp.dao.police.PoliceLogDao;
import com.thinkgem.jeesite.modules.stamp.entity.police.PoliceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 登录日志服务层
 *
 * Created by hjw-pc on 2017/7/27.
 */
@Service
public class LoginLogService {

    @Autowired
    private LoginLogDao loginLogDao;

    /**
     * 分页查询登录日志列表
     * @param page
     * @param loginLog
     * @return
     */
    public Page<LoginLog> findPage(Page<LoginLog> page, LoginLog loginLog){

        loginLog.setPage(page);
        page.setList(loginLogDao.findList(loginLog));
        return page;
    }
}
