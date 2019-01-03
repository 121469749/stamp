package com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity;

import com.thinkgem.jeesite.common.utils.IdGen;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Administrator on 2017/5/8.
 */
public class Attachment implements Serializable{

    private String id;

    private String attachPath;

    private String attachType;

    private String fileSuffix;

    public Attachment(){

    }

    public Attachment(String id) {
        this.id = id;
    }

    public Attachment(String id, String attachType) {
        this.id = id;
        this.attachType = attachType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    public void setUUID(){

        setId(IdGen.uuid());

    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id='" + id + '\'' +
                ", attachPath='" + attachPath + '\'' +
                ", attachType='" + attachType + '\'' +
                '}';
    }
}

