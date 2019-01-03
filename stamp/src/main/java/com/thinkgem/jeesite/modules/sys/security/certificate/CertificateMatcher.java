package com.thinkgem.jeesite.modules.sys.security.certificate;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.X509Certificate;

/**
 * Shiro相关文件
 * 用于验证证书登陆是否通过
 */
public class CertificateMatcher implements CredentialsMatcher {
    private static Logger logger = LoggerFactory.getLogger(CertificateMatcher.class);

    /**
     * 比对登陆中的Modulus字段以及数据库中用户的Modulus字段是否一致
     * @param token 证书Token
     * @param info 数据库中的数据
     * @return 是否正确
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        X509Certificate tokenCertificate = (X509Certificate) token.getCredentials();
        String tokenModulus = CertificateUtil.getModulus(tokenCertificate);

        String accountModulus = (String) info.getCredentials();

        return tokenModulus.equals(accountModulus);
    }
}
