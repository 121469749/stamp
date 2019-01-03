package com.thinkgem.jeesite.modules.stamp.exception.stampMake;

/**
 * Created by Administrator on 2017/6/6.
 */
public class StampExistException extends StampMakeException{

    public StampExistException(String message) {
        super(message);
    }

    public StampExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
