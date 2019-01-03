package com.thinkgem.jeesite.common.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import com.thinkgem.jeesite.modules.sign.entity.SinatureCerDTO;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

/**
 * Created by bb on 2018-03-24.
 *
 * 签章工具类
 */
public class SignPDF {

    private final static String CER_REASON = "电子印章防伪";
    private final static String CER_LOCATION = "China";


    /**
     * @param sinatureCerDTO
     *        签章对象
     * @return
     */
    public static void sign(SinatureCerDTO sinatureCerDTO) {

        File signPdfSrcFile = new File(sinatureCerDTO.getUndoneRealPath());
        PdfReader reader = null;
        PdfStamper stp = null;
        FileInputStream fos = null;

        try {
            BouncyCastleProvider provider = new BouncyCastleProvider();
            Security.addProvider(provider);
            //读取keystore ，获得私钥和证书链
            //KeyStore ks = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
            KeyStore ks = KeyStore.getInstance("JKS");
            fos = new FileInputStream(sinatureCerDTO.getCerPath());
            // 私钥密码
            ks.load(fos, sinatureCerDTO.getCerPassword().toCharArray());
            String alias = (String) ks.aliases().nextElement();
            PrivateKey key = (PrivateKey) ks.getKey(alias, sinatureCerDTO.getCerPassword().toCharArray());
            Certificate[] chain = ks.getCertificateChain(alias);
            reader = new PdfReader(sinatureCerDTO.getUndoneRealPath());
            // 临时pdf文件
            File temp = new File(signPdfSrcFile.getParent(), System.currentTimeMillis() + ".pdf");
            //创建签章工具PdfStamper ，最后一个boolean参数
            //false的话，pdf文件只允许被签名一次，多次签名，最后一次有效
            //true的话，pdf可以被追加签名，验签工具可以识别出每次签名之后文档是否被修改
            stp = PdfStamper.createSignature(reader,
                    new FileOutputStream(sinatureCerDTO.getDoneRealPath()),
                    '\0', temp, true);
            stp.setFullCompression();
            // 获取数字签章属性对象，设定数字签章的属性
            PdfSignatureAppearance sap = stp.getSignatureAppearance();
            sap.setReason(CER_REASON);
            sap.setLocation(CER_LOCATION);
            // 读取图章图片，这个image是itext包的，image使用png格式透明图片
            Image image = Image.getInstance(sinatureCerDTO.getSealPath());
            sap.setImageScale(0);
            sap.setSignatureGraphic(image);
            //设置图章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
            sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
            //设置签名的位置，页码，签名域名称，多次追加签名的时候，签名域名称不能一样
            //签名的位置，是图章相对于pdf页面的位置坐标，原点为pdf页面左下角
            //四个参数的分别是，图章左下角x，图章左下角y，图章右上角x，图章右上角y
            //若多个章，签名域可用UUID生成：UUID.randomUUID().toString().replaceAll("-", "")
            sap.setVisibleSignature(new Rectangle(sinatureCerDTO.getLlx(),
                            sinatureCerDTO.getLly(),
                            sinatureCerDTO.getUrx(),
                            sinatureCerDTO.getUry()),
                    sinatureCerDTO.getPageSize(),
                    sinatureCerDTO.getSign_domain());
            stp.getWriter().setCompressionLevel(5);
            // 摘要算法
            ExternalDigest digest = new BouncyCastleDigest();
            // 签名算法
            ExternalSignature signature = new PrivateKeySignature(key, DigestAlgorithms.SHA1, provider.getName());
            // 调用itext签名方法完成pdf签章CryptoStandard.CMS 签名方式，建议采用这种
            MakeSignature.signDetached(sap, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (stp != null) {
                try {
                    stp.close();
                }  catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (reader != null) {
                reader.close();
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }


    public static void main(String[] args) throws IOException, DocumentException, GeneralSecurityException {
       SinatureCerDTO sinatureCerDTO = new SinatureCerDTO();
        sinatureCerDTO.setUndoneRealPath("c:\\0412_demo2.pdf");
        sinatureCerDTO.setDoneRealPath("c:\\0412_demo3.pdf");
        sinatureCerDTO.setCerPath("C:\\test\\pfx\\夏博.pfx");
        sinatureCerDTO.setCerPassword("112233");
        sinatureCerDTO.setSealPath("C:\\sig5.png");
        sinatureCerDTO.setLlx(215);
        sinatureCerDTO.setLly(335);
        sinatureCerDTO.setUrx(345);
        sinatureCerDTO.setUry(465);
        sinatureCerDTO.setPageSize(1);
        sinatureCerDTO.setSign_domain("sign3");
        sign(sinatureCerDTO);
    }
}
