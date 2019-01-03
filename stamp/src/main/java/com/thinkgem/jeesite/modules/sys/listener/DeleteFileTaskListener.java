package com.thinkgem.jeesite.modules.sys.listener;

import com.thinkgem.jeesite.modules.stamp.common.util.timerTask.TimerManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by xucaikai on 2018\8\27 0027.
 * 用于监听定时删除文件，
 */
public class DeleteFileTaskListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new TimerManager();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
