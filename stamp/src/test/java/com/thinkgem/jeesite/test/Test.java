package com.thinkgem.jeesite.test;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:/spring-context*.xml"})
public class Test {
	@org.junit.Test
	public void test(){
		String url = "http://localhost:8080/makecomlogin";

		String hostname = url.split("//")[1].split(":")[0];
		System.out.println(hostname);

		String lastUrl = url.split("//")[1].split("/")[1];
		System.out.println(lastUrl);

		url = "https://" + hostname + ":8443/" + lastUrl;

		System.out.println(url);
	}
}
