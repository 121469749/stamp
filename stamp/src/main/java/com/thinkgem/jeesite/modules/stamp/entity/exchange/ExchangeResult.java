package com.thinkgem.jeesite.modules.stamp.entity.exchange;

import java.io.Serializable;

/**
 * @Auther: linzhibao
 * @Date: 2018-09-05 09:22
 * @Description:
 */
public class ExchangeResult implements Serializable{

    private Integer code;

    private String msg;

    private String description;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExchangeResult() {
    }

    public ExchangeResult(Integer code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    @Override
    public String toString() {
        return "code=" + this.code + ",msg=" + this.msg + ",description="  + this.description;
    }
}

