package com.thinkgem.jeesite.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.rsa.RSAPublicKeyImpl;

import javax.servlet.http.HttpServletRequest;
import java.security.cert.X509Certificate;

@Controller
public class SSLController {

    @RequestMapping("/test")
    public void test(HttpServletRequest request){
        if(request.isSecure()){
            final String name = "javax.servlet.request.X509Certificate";
            X509Certificate[] certificates = (X509Certificate[]) request.getAttribute(name);

            X509Certificate certificate = certificates[0];
            RSAPublicKeyImpl publicKey = (RSAPublicKeyImpl) certificate.getPublicKey();
            System.out.println(publicKey.getModulus());
        }
    }
}
