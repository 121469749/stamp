package com.thinkgem.jeesite.modules.sys.security.certificate;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import org.apache.shiro.authc.AuthenticationToken;

import java.security.cert.X509Certificate;

/**
 * Shiro相关文件
 * 用于保存上传给客户端的证书的Token
 */
public class CertificateToken implements AuthenticationToken {
    private String id;
    private X509Certificate certificate;

    private UserType chosenRole;

    public CertificateToken(UserType chosenRole,X509Certificate certificate){
        this.chosenRole = chosenRole;
        setCertificate(certificate);
    }

    @Override
    public Object getPrincipal() {
        return id;
    }

    @Override
    public Object getCredentials() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {

        // 读取证书中的DName字段
        String subject = certificate.getSubjectX500Principal().toString();

        // 切割字符串，只取出CN=后面的字段
        this.id = subject.split("CN=")[1];
        this.certificate = certificate;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public String getId() {
        return id;
    }

    public UserType getChosenRole() {
        return chosenRole;
    }

    public void setChosenRole(UserType chosenRole) {
        this.chosenRole = chosenRole;
    }
}
