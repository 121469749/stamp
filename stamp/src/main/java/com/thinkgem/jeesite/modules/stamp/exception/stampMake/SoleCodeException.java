package com.thinkgem.jeesite.modules.stamp.exception.stampMake;

/**
 *
 * 企业信用统一代码错误异常
 *
 * Created by Locker on 2017/5/31.
 */
public class SoleCodeException extends StampMakeException{

    public SoleCodeException(String message) {
        super(message);
    }

    public SoleCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
