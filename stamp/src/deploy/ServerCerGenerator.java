import com.thinkgem.jeesite.modules.sys.security.certificate.CertificateUtil;

public class ServerCerGenerator {

    public static void main(String[] args){

        // domainName 为服务器的IP地址或域名
        String domainName = "119.29.234.210";

        // 生成服务端证书
        CertificateUtil.generateServerCert(domainName);
    }
}
