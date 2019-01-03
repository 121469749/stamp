package com.thinkgem.jeesite.modules.stamp.common;


import org.hibernate.validator.constraints.Length;

/**
 *
 * 状态对象
 * 可以封装 状态码
 *         跳转URL
 *         实体对象(略)
 *  以Json 格式返回给前端 进行交互
 *   @author  Locker
 */
public class Condition<T> {

    // 成功状态码
    public static final int SUCCESS_CODE = 200;

    // 系统错误状态码
    public static final int ERROR_CODE = 500;

    // 查找不到的状态码
    public static final int NOTFOUND_CODE = 404;

    // 无权限 状态码
    public static final int NOALLOW_CODE = 403;

    private int code;

    private String url;

    private String message;

    private T entity;

    public Condition() {
    }

    public Condition(int code) {
        this.code = code;
    }

    public Condition(String message){
        this.message=message;
    }

    public Condition(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Condition(int code, String message, T entity) {
        this.code = code;
        this.message = message;
        this.entity = entity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "code=" + code +
                ", url='" + url + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
