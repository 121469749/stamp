package com.thinkgem.jeesite.modules.areaattachment.exception;

/**
 *
 * 区域附件列表不存在异常处理
 * Created by Locker on 2017/6/16.
 */
public class AreaAttachmentDirNotFoundException extends AreaAttachmentException{

    public AreaAttachmentDirNotFoundException(String message) {
        super(message);
    }

    public AreaAttachmentDirNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
