package com.thinkgem.jeesite.modules.sign.common.persistence;

import java.io.Serializable;

/**
 * Created by hjw-pc on 2017/7/14.
 */
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体编号（唯一标识）
     */
    protected String id;

    public BaseEntity(){

    }

    public BaseEntity(String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    //    /**
//     * 删除标记（0：正常；1：删除；2：审核；）
//     */
//    public static final String DEL_FLAG_NORMAL = "0";
//    public static final String DEL_FLAG_DELETE = "1";
//    public static final String DEL_FLAG_AUDIT = "2";
}
