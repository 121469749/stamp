package com.thinkgem.jeesite.modules.stamp.common.util.timerTask;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.FileUtils;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by xucaikai on 2018\8\27 0027.
 */
public class DeleteFileTimerTask extends TimerTask {
    @Override
    public void run() {
        try{
            //此处执行删除删除文件夹里的文件
            //临时文件夹实际路径
  /*          StringBuffer tempRealPath = new StringBuffer(Global.getConfig("attachmentTemp.realPath"));

            FileUtils.deleteAllFile(tempRealPath.toString());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println("定时删除临时文件===成功执行==："+ df.format(new Date()));*/
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println("此定时任务没有任何操作！"+df.format(new Date()));

        }catch (Exception e){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println("定时删除临时文件异常："+ df.format(new Date()));
        }
    }
}
