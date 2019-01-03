package com.thinkgem.jeesite.modules.stamp.exception.countSet;

/**
 *
 * 印章制作数量基础异常
 *
 * Created by Locker on 2017/8/18.
 */
public class CountSetException extends RuntimeException {

    public CountSetException(String message) {
        super(message);
    }

    public CountSetException(String message, Throwable cause) {
        super(message, cause);
    }
}
