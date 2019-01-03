package com.thinkgem.jeesite.modules.sign.dto;

import com.thinkgem.jeesite.modules.sign.entity.Point;

/**
 * Created by Locker on 2017/9/1.
 */
public class SingatureDTO {

    private Point point;

    private String document;

    private String seal;

    private String hexCode;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }
}
