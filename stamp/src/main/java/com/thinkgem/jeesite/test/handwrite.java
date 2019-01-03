package com.thinkgem.jeesite.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class handwrite {
    @org.junit.Before
    public void hw(){
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-*.xml");
    }
}
