package com.thinkgem.jeesite.modules.stamp.exception.stampMake;

/**
 *
 * 印章制作异常
 *
 * Created by Locker on 2017/5/31.
 */
public class StampMakeException extends RuntimeException {

    public StampMakeException(String message) {
        super(message);
    }

    public StampMakeException(String message, Throwable cause) {
        super(message, cause);
    }
}
