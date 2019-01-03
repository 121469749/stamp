package com.thinkgem.jeesite.modules.stamp.exception.countSet;

/**
 *
 * 生产控制余量不足-异常
 *
 * Created by Locker on 2017/8/18.
 */
public class CountSetAbsenceException extends CountSetException{

    public CountSetAbsenceException(String message) {
        super(message);
    }

    public CountSetAbsenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
