package com.thinkgem.jeesite.modules.stamp.entity.water;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 *
 * 水印实体
 *
 * Created by Locker on 2017/11/18.
 */
public class Water extends DataEntity<Water> {

    private String name;

    private String filePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
