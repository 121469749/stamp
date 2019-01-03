package com.thinkgem.jeesite.modules.sys.security.certificate;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 证书文件相关服务
 */
@Service
public class CertificateService {

    @Autowired
    private UserDao userDao;

    /**
     * 为用户生成一个证书，并绑定用户
     * @param user 要生成证书的用户
     * @return 生成的证书文件
     */
    public File generateCert(User user){
        File certFile = CertificateUtil.generateClientCert(user.getId(),user.getLoginName());
        user.setCertFilePath(certFile.getPath());

        String modulus = CertificateUtil.getModulus(user.getId());
        user.setCertModulus(modulus);

        userDao.updateCert(user);

        return certFile;
    }

    /**
     * 为用户更新证书，并重新绑定用户
     * @param user 要更新证书的用户
     * @return 更新后的证书文件
     */
    public File updateCert(User user){
        File certFile = CertificateUtil.updateClientCert(user.getId(),user.getLoginName());
        user.setCertFilePath(certFile.getPath());

        String modulus = CertificateUtil.getModulus(user.getId());
        user.setCertModulus(modulus);

        userDao.updateCert(user);
        return certFile;
    }

    /**
     * 获取证书文件
     * @param id 用户的id
     * @return 证书文件
     */
    public File getCertFile(String id){
        return CertificateUtil.getCertFile(id);
    }

    /**
     * 检查用户是否生成了证书
     * @param user 检查的用户
     * @return 是否生成证书
     */
    public boolean checkCert(User user){
        String certModulus = user.getCertModulus();
        return certModulus != null && !certModulus.equals("");
    }
}
