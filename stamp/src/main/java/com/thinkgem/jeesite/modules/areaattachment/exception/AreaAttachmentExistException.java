package com.thinkgem.jeesite.modules.areaattachment.exception;

import com.thinkgem.jeesite.modules.areaattachment.entity.AreaAttachment;

/**
 *
 * 区域列表附件存在异常
 * Created by Locker on 2017/6/16.
 */
public class AreaAttachmentExistException extends AreaAttachmentException {

    public AreaAttachmentExistException(String message) {
        super(message);
    }

    public AreaAttachmentExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
