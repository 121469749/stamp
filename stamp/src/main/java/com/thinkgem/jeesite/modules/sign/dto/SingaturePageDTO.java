package com.thinkgem.jeesite.modules.sign.dto;


import com.thinkgem.jeesite.modules.sign.entity.Document;
import com.thinkgem.jeesite.modules.sign.entity.Seal;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;

/**
 * Created by Administrator on 2017/9/16.
 */
public class SingaturePageDTO {

    private String userName;

    private String password;

    private String realPath;

    private Document document;

    private Seal seal;
    private Stamp stamp;

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Seal getSeal() {
        return seal;
    }

    public void setSeal(Seal seal) {
        this.seal = seal;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }
}
