package com.thinkgem.jeesite.modules.stamp.exception.stampMake;

/**
 * Created by hjw-pc on 2017/6/25.
 * 刻章点license注册异常
 */
public class RegisterLicenseException extends RuntimeException{


        private static final long serialVersionUID = 1L;

        public RegisterLicenseException() {
            super();
        }

        public RegisterLicenseException(String message) {
            super(message);

        }

        public RegisterLicenseException(String message, Throwable cause) {
            super(message, cause);
            System.out.println(message);
        }

        public RegisterLicenseException(Throwable cause) {
            super(cause);
        }
    }


