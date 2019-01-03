package com.thinkgem.jeesite.modules.stamp.exception.police;

/**
 * Created by hjw-pc on 2017/6/2.
 * 刻章点录入异常
 */
public class RegisterMakeComException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RegisterMakeComException() {
        super();
    }

    public RegisterMakeComException(String message) {
        super(message);

    }

    public RegisterMakeComException(String message, Throwable cause) {
        super(message, cause);
        System.out.println(message);
    }

    public RegisterMakeComException(Throwable cause) {
        super(cause);
    }
}
