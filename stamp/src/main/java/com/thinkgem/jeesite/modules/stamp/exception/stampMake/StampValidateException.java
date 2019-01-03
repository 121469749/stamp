package com.thinkgem.jeesite.modules.stamp.exception.stampMake;

/**
 * Created by hjw-pc on 2017/7/7.
 */
public class StampValidateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public StampValidateException() {
        super();
    }

    public StampValidateException(String message) {
        super(message);

    }

    public StampValidateException(String message, Throwable cause) {
        super(message, cause);
        System.out.println(message);
    }

    public StampValidateException(Throwable cause) {
        super(cause);
    }
}
