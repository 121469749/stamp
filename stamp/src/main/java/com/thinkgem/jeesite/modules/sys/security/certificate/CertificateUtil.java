package com.thinkgem.jeesite.modules.sys.security.certificate;

import com.thinkgem.jeesite.common.config.Global;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.security.rsa.RSAPublicKeyImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
/*keytool常用命令

-alias       产生别名

-keystore    指定密钥库的名称(就像数据库一样的证书库，可以有很多个证书，cacerts这个文件是jre自带的，

             你也可以使用其它文件名字，如果没有这个文件名字，它会创建这样一个)

-storepass   指定密钥库的密码

-keypass     指定别名条目的密码

-list        显示密钥库中的证书信息

-v           显示密钥库中的证书详细信息

-export      将别名指定的证书导出到文件

-file        参数指定导出到文件的文件名

-delete      删除密钥库中某条目

-import      将已签名数字证书导入密钥库

-keypasswd   修改密钥库中指定条目口令

-dname       指定证书拥有者信息

-keyalg      指定密钥的算法

-validity    指定创建的证书有效期多少天

-keysize     指定密钥长度 */
/**
 * 证书文件相关的工具类
 * @author bb
 *
 */
public class CertificateUtil {

    // 用去读取证书库的KeyStore
    private static KeyStore store;

    /**
     * 证书的DName前缀
     * C：国家
     * ST：省
     * L：市
     * O：公司
     * OU：公司组织
     * CN：用户名
     */
    //private final static String DNAME = "C=CN,ST=GuangDong,L=ZhuHai,O=PMVV,OU=PMVV,CN=";
    private static final String DNAME = "CN=";

    // 放置证书文件的位置
    private static final String STORE_FILE_PATH = Global.getConfig("pfx.realPath");

    // 服务端证书位置
    private static final String SERVER_CERT_FILE_PATH = STORE_FILE_PATH + "server.cer";

    // 服务端信任库位置
    private static final String TRUST_STORE_FILE_PATH = STORE_FILE_PATH + "server.keystore";

    // 服务端信任库的进入密码
    private static final String TRUST_STORE_PASSWORD = "112233";

    // 证书过期时间，以天为单位
    private static final int EXPIRE_TIME = 3650;

    static{
        try {
            // 设定KeyStore为JKS模式（读取pfx、p12文件）
            store = KeyStore.getInstance("JKS");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成用户证书
     * @param cn 用户名
     * @param password 证书文件的访问密码
     * @return 新生成的证书文件
     */
    public static File generateClientCert(String cn,String password){
        generateNewP12(cn,password);
        importCer(cn,password);

        return getCertFile(cn);
    }

    /**
     * 更新用户证书
     * @param cn 用户名
     * @param password 证书文件的访问密码
     * @return 更新后的证书文件
     */
    public static File updateClientCert(String cn,String password){
        generateNewP12(cn,password);
        removeOldClientCer(cn,password);
        removeOldServerCer(cn);
        importCer(cn,password);

        return getCertFile(cn);
    }

    /**
     * 生成服务端证书
     * @param domainName 服务端的域名
     */
    public static void generateServerCert(String domainName){
        String command = "keytool -validity " + EXPIRE_TIME + " -genkeypair -v -alias server" +
                " -keyalg RSA" +
                " -ext KeyUsage:critical=\"keyCertSign\" -ext BasicConstraints:critical=\"ca:true\" -ext EKU=\"serverAuth\"" +
                " -storetype PKCS12 -keystore " + TRUST_STORE_FILE_PATH +
                " -dname \"" + DNAME + domainName + "\" -storepass " +
                TRUST_STORE_PASSWORD + " -keypass " + TRUST_STORE_PASSWORD;
        execCommand(command);

        command = "keytool -export -v -alias server" +
                " -keystore " + TRUST_STORE_FILE_PATH + " -storetype PKCS12 -storepass " +
                TRUST_STORE_PASSWORD + " -rfc -file " + SERVER_CERT_FILE_PATH;
        execCommand(command);
    }

    /**
     * 获取证书中的Modulus字段，用于将证书与用户绑定
     * @param cn 用户名
     * @return 证书中的Modulus字段值
     *
     * RSAPublicKey ::= SEQUENCE { -- RSA算法时的公钥值
            modulus            INTEGER, -- n
            publicExponent     INTEGER -- e -- }
     */
    public static String getModulus(String cn){
        String modulus = null;

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(TRUST_STORE_FILE_PATH);
            store.load(inputStream,TRUST_STORE_PASSWORD.toCharArray());

            X509Certificate certificate = (X509Certificate) store.getCertificate(cn);
            modulus = getModulus(certificate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return modulus;
    }

    /**
     * 获取证书中的Modulus字段，用于将证书与用户绑定
     * @param certificate 要获取的证书对象
     * @return 证书中的Modulus字段值
     */
    public static String getModulus(X509Certificate certificate){
        RSAPublicKeyImpl publicKey = (RSAPublicKeyImpl) certificate.getPublicKey();
        BigInteger modulus = publicKey.getModulus();
        return modulus.toString();
    }

    /**
     * 获取证书文件
     * @param cn 用户名
     * @return 证书文件
     */
    public static File getCertFile(String cn){
        String filePath = STORE_FILE_PATH + cn + ".pfx";
        return new File(filePath);
    }

    /**
     * 生成新的p12证书
     * @param cn 用户名
     * @param password 密码
     */
    private static void generateNewP12(String cn,String password){
        String command = "keytool -validity " + EXPIRE_TIME + " -genkeypair -v -alias " + cn +
                " -keyalg RSA -storetype PKCS12 -keystore " + STORE_FILE_PATH + cn + ".pfx" +
                " -dname \"" + DNAME + cn + "\" -storepass " + password + " -keypass " + password;
        execCommand(command);
    }

    /**
     * 建立服务端证书和用户证书的信任关系
     * 并互相导入证书，使客户端与服务端互相信任
     * @param cn 用户名
     * @param password 密码
     */
    private static void importCer(String cn,String password){
        String command;

        // 生成csr认证请求文件
        command = "keytool -certreq -alias " + cn + " -file " + STORE_FILE_PATH +
                cn + ".csr -keystore " + STORE_FILE_PATH + cn +
                ".pfx -storepass " + password;
        execCommand(command);

        // 将csr请求文件拿给服务端证书签名，并导出签名完毕的cer
        command = "keytool -validity " + EXPIRE_TIME + " -gencert -alias server " +
                "-ext KeyUsage:critical=\"digitalSignature,keyEncipherment,dataEncipherment,nonRepudiation\" -ext BasicConstraints:critical=\"ca:false\" " +
                "-infile " + STORE_FILE_PATH + cn +".csr -outfile " + STORE_FILE_PATH + cn + ".cer -keystore " +
                TRUST_STORE_FILE_PATH + " -storepass " + TRUST_STORE_PASSWORD;
        execCommand(command);

        // 服务端证书导入客户端的信任库中
        command = "keytool -noprompt -import -v -alias server" +
                " -file " + SERVER_CERT_FILE_PATH + " -keystore " +
                STORE_FILE_PATH + cn + ".pfx" + " -storepass " + password;
        execCommand(command);

        // 将签名完毕的cer返回到用户证书pfx中，完成信任关系的建立
        command = "keytool -importcert -alias " + cn + " -file " + STORE_FILE_PATH +
                cn + ".cer -keystore " + STORE_FILE_PATH + cn +
                ".pfx -storepass " + password;
        execCommand(command);

        // 将用户证书导入服务端的信任库中
        command = "keytool -noprompt -import -v -alias " + cn +
                " -file " + STORE_FILE_PATH + cn + ".cer -keystore " + TRUST_STORE_FILE_PATH +
                " -storepass " + TRUST_STORE_PASSWORD;
        execCommand(command);
    }

    /**
     * 从原先的客户端证书库remove掉客户端证书
     * @param cn 用户名
     * @param password 密码
     */
    private static void removeOldClientCer(String cn,String password){
        String command = "keytool -delete -alias " + cn + " -keystore " +
                STORE_FILE_PATH + cn + ".pfx -storepass " + password;
        execCommand(command);
    }

    /**
     * 从信任库中remove掉客户端证书
     * @param cn 用户名
     */
    private static void removeOldServerCer(String cn){
        String command = "keytool -delete -alias " + cn + " -keystore " +
                TRUST_STORE_FILE_PATH + " -storepass " + TRUST_STORE_PASSWORD;
        execCommand(command);
    }

    /**
     * 执行cmd命令，通过JRE中的keytool工具进行证书文件操作
     * @param command 要执行的命令
     * @return 执行的Process对象
     */
    private static Process execCommand(String command){
        System.out.println(command);
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return process;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取证书(X509Certificate)序列号
     * （发放证书的实体有责任为证书指定序列号，以使其区别于该实体发放的其它证书。
     * 此信息用途很多。例如，如果某一证书被撤消，其序列号将放到证书撤消清单 (CRL) 中）
     * @param path(.cer)
     * @return 序列号（数据库保存十进制，证书显示的是十六进制）
     */
    public static String getEncryptCertId(String path) {
        System.out.println(initCert(path).getSerialNumber().toString());
        return initCert(path).getSerialNumber().toString();
    }

    /**
     * @param path
     */
    private static X509Certificate initCert(String path) {
        X509Certificate encryptCertTemp = null;
        CertificateFactory cf = null;
        FileInputStream is = null;
        try {
          cf = CertificateFactory.getInstance("X.509");
          is = new FileInputStream(path);
          encryptCertTemp = (X509Certificate) cf.generateCertificate(is);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return encryptCertTemp;
        }
    }

    public static void main(String[] args){

        /*String domainName = "印章数据中心CA";
        generateServerCert(domainName);*/

        //generateClientCert("夏博","112233");

        getEncryptCertId("D:\\stamp\\pfx\\38b56bf2f901462f8895e9d0e936f1dc.cer");
    }
}
