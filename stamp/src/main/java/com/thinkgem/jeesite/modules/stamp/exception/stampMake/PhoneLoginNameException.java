package com.thinkgem.jeesite.modules.stamp.exception.stampMake;

/**
 *
 * 以手机做用户名 异常
 *
 * Created by Locker on 2017/5/31.
 */
public class PhoneLoginNameException extends StampMakeException {


    public PhoneLoginNameException(String message) {
        super(message);
    }

    public PhoneLoginNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
