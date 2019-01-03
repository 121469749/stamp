package com.thinkgem.jeesite.modules.stamp.exception.countSet;

/**
 *
 * 保存印章数量控制异常处理
 *
 * Created by Locker on 2017/8/18.
 */
public class CountSetSaveException extends CountSetException {

    public CountSetSaveException(String message) {
        super(message);
    }

    public CountSetSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
