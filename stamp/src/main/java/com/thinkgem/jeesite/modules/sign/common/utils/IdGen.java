package com.thinkgem.jeesite.modules.sign.common.utils;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by hjw-pc on 2017/7/14.
 */
public class IdGen implements SessionIdGenerator {


    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public Serializable generateId(Session session) {
        return IdGen.uuid();
    }
}
