package com.thinkgem.jeesite.modules.sign.entity;

/**
 * Created by Administrator on 2017/8/31.
 */
public class Point {

    private int pageSize=1;

    private float x; //左上角

    private float y;//左上角

    private float width;// 宽

    private float high;//高

    private float pxWidth; //px 的宽度

    private float pxHigh;//px 的高度

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public float getPxWidth() {
        return pxWidth;
    }

    public void setPxWidth(float pxWidth) {
        this.pxWidth = pxWidth;
    }

    public float getPxHigh() {
        return pxHigh;
    }

    public void setPxHigh(float pxHigh) {
        this.pxHigh = pxHigh;
    }
}
