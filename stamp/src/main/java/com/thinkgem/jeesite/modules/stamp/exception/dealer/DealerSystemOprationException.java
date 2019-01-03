package com.thinkgem.jeesite.modules.stamp.exception.dealer;

/**
 * Created by Administrator on 2017/7/7.
 */
public class DealerSystemOprationException extends RuntimeException{

    public DealerSystemOprationException(String message) {
        super(message);
    }

    public DealerSystemOprationException(String message, Throwable cause) {
        super(message, cause);
    }
}
