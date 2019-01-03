package com.thinkgem.jeesite.modules.sign.common.utils;

import java.io.File;

/**
 * Created by Administrator on 2017/8/31.
 */
public class DirUtil {

    /**
     * 把文件保存到相印的路径中
     */
    public static void createDir(String realPath) throws Exception {

        try {

            File file = new File(realPath);

            System.out.println("正在创建目录......");
            //目录存在则不需要创建
            if (!file.exists()) {
                System.out.println("目录结构不存在!正在创建...");
                file.mkdirs();
                System.out.println("目录创建成功!\n路径为:" + file.getAbsolutePath());
            } else {
                System.out.println("目录已经存在...\n路径为:" + file.getAbsolutePath());
            }

        } catch (Exception e) {
            throw e;
        }

    }

}
