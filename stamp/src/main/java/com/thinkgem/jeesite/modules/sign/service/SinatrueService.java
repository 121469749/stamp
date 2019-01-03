package com.thinkgem.jeesite.modules.sign.service;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.thinkgem.jeesite.common.utils.SignPDF;
import com.thinkgem.jeesite.modules.sign.common.condition.Condition;
import com.thinkgem.jeesite.modules.sign.common.config.Global;
import com.thinkgem.jeesite.modules.sign.common.utils.DirUtil;
import com.thinkgem.jeesite.modules.sign.common.utils.FileUtil;
import com.thinkgem.jeesite.modules.sign.dao.DocumentActionDao;
import com.thinkgem.jeesite.modules.sign.dao.DocumentFindDao;
import com.thinkgem.jeesite.modules.sign.dao.SealFindDao;
import com.thinkgem.jeesite.modules.sign.dto.DocumentPathDTO;
import com.thinkgem.jeesite.modules.sign.entity.*;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.certificate.CertificateUtil;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/31.
 */
@Service
public class SinatrueService {

    @Autowired
    private DocumentFindDao documentFindDao;
    @Autowired
    private DocumentActionDao documentActionDao;
    @Autowired
    private SealFindDao sealFindDao;
    @Autowired
    private AuditService auditService;

    /**
     * 签章到当前页面
     * @param sealId
     * @param documentId
     * @param point
     * @param hexCode
     * @return
     */
    @Transactional(readOnly = false)
    public Condition singatureOne(String sealId, String documentId, Point point, String hexCode, Audit audit) {

        //当前User
        User user = UserUtils.getUser();

        //审计相关
        audit.setUser(user);
        audit.setSeal(sealId);
        audit.setAuditType(Audit.AUDIT_SIGN);
        audit.setAuditDate(new Date());
        audit.setCertSerialNumber(CertificateUtil.getEncryptCertId(user.getCertFilePath().replace(".pfx",".cer")));

        //获得当前选中印章信息，并获得实际路径
        String sealPath = getSealPath(sealId, user);

        if (sealPath == null || sealPath.equals("")) {
            //审计相关
            audit.setAuditResult(Audit.FAIL);
            audit.setReason("非法操作!找不到对应的印章信息");
            auditService.addAudit(audit);

            return new Condition(Condition.ERROR_CODE, "非法操作!找不到对应的印章信息");
        }

        //获取签章文件路径实体类信息
        DocumentPathDTO pathDTO = getDocumentSingaturePath(documentId, user.getName(), user);

        //建立done目录
        try {
            DirUtil.createDir(pathDTO.getDoneDir());
        } catch (Exception e) {
            e.printStackTrace();
            return new Condition(Condition.ERROR_CODE, "路径错误,请联系管理员!");
        }
        //完成签章，修改签章的文件
        Document document = finishSinatrue(documentId, pathDTO.getDoneVirtualPath(), user);

        if (document.getId() == null || document.getId().equals("")) {
            //审计相关
            audit.setAuditResult(Audit.FAIL);
            audit.setReason("找不到文件");
            auditService.addAudit(audit);

            return new Condition(Condition.ERROR_CODE, "非法操作!");
        }
        //审计相关
        audit.setFileName(document.getFileName());

        try {
            insertSealInPdf(pathDTO.getUndoneRealPath(), pathDTO.getDoneRealPath(), sealPath, point);

            //签署完文件后的16进制码
            document.setSingatureMD5(FileUtil.MD5ByFile(new File(pathDTO.getDoneRealPath())));//把签章后的文件转换为MD5码
            document.setHexCodeSignture(hexCode);//签章数据
            document.setStamp(sealFindDao.findStamp(new Seal(sealId)));//设置文件的印章

        } catch (IOException e) {
            //审计相关
            audit.setAuditResult(Audit.FAIL);
            audit.setReason("文件有误");
            auditService.addAudit(audit);

            e.printStackTrace();
            return new Condition(Condition.ERROR_CODE, "签章失败,请联系管理员!");
        } catch (com.itextpdf.text.DocumentException e) {
            //审计相关
            audit.setAuditResult(Audit.FAIL);
            audit.setReason("文件有误");
            auditService.addAudit(audit);

            e.printStackTrace();
            return new Condition(Condition.ERROR_CODE, "签章失败,请联系管理员!");
        }

        if (documentActionDao.finishSinatrue(document) == 1) {
            //审计相关
            audit.setAuditResult(Audit.SUCCESS);
            audit.setReason("签章完成");
            auditService.addAudit(audit);
            String auditId = audit.getId();
            System.out.println("auditId:"+auditId);

            return new Condition(Condition.SUCCESS_CODE, auditId, document);
        }

        //审计相关
        audit.setAuditResult(Audit.FAIL);
        audit.setReason("未知错误");
        auditService.addAudit(audit);

        return new Condition(Condition.ERROR_CODE, "未知错误!");
    }

    /**
     * 保存签章数据
     * @param SignData
     * @return
     */
    public Condition updateSignData(String SignData,String DocmentId,String AuditId){

        //更新审计表
        Audit audit = new Audit(AuditId);
        audit.setFileSigneddata(SignData);
        auditService.updateAudit(audit);

        Map<String,Object> map =new HashMap<String, Object>();
        map.put("SignData",SignData);
        map.put("DocmentId",DocmentId);
        try{
            documentActionDao.updateSignData(map);
        }catch (Exception e){
            return new Condition(Condition.ERROR_CODE,"签章数据更新失败");
        }
        return new Condition(Condition.SUCCESS_CODE,"签章数据更新成功");
    }


    /**
     * pdf 签章操作
     * 印模图嵌入pdf文件中
     * @param inputPath
     * @param outputPath
     * @param imagePath
     * @param point
     * @throws IOException
     * @throws com.itextpdf.text.DocumentException
     */
    protected void insertSealInPdf(String inputPath, String outputPath, String imagePath, Point point) throws com.itextpdf.text.DocumentException,IOException {

        try {

           PdfReader reader = new PdfReader(inputPath);//选择需要印章的pdf
            /* PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outputPath));//加完印章后的pdf
            PdfContentByte over = stamp.getOverContent(point.getPageSize());//设置在第几页打印印章*/

            //todo 按比例处理宽高
            if (point.getPxHigh() != point.getPxWidth()) {

                Rectangle mediabox = reader.getPageSize(point.getPageSize());

                System.out.println(mediabox.getWidth() + "-" + mediabox.getHeight());

                float fileWidth = mediabox.getWidth();

                float fileHeight = mediabox.getHeight();

                point.setX(point.getX() / point.getPxWidth() * fileWidth);

                point.setY(point.getY() / point.getPxHigh() * fileHeight - point.getHigh() / point.getPxHigh() * fileHeight);

                point.setHigh(point.getHigh() / point.getPxHigh() * fileHeight);

                point.setWidth(point.getWidth() / point.getPxWidth() * fileWidth);
            }

            System.out.println("point.getX():" + point.getX() + ",point.getY():" + point.getY() +
                    ",point.getWidth():" +point.getWidth() + ",point.getHigh():" + point.getHigh());
            /*Image img = Image.getInstance(imagePath);//选择图片
            img.setAlignment(1);
            img.scaleAbsolute(point.getWidth(), point.getHigh());//控制图片大小
            img.setAbsolutePosition(point.getX(), point.getY());//控制图片位置
            over.addImage(img);*/
            reader.close();

            SinatureCerDTO sinatureCerDTO = new SinatureCerDTO();
            sinatureCerDTO.setUndoneRealPath(inputPath);
            sinatureCerDTO.setDoneRealPath(outputPath);
            sinatureCerDTO.setCerPath(UserUtils.getUser().getCertFilePath());
            sinatureCerDTO.setCerPassword(UserUtils.getUser().getLoginName());
            sinatureCerDTO.setSealPath(imagePath);
            sinatureCerDTO.setLlx(point.getX());
            sinatureCerDTO.setLly(point.getY());
            sinatureCerDTO.setUrx(point.getX() + point.getWidth());
            sinatureCerDTO.setUry(point.getY() + point.getHigh());
            sinatureCerDTO.setPageSize(point.getPageSize());
            sinatureCerDTO.setSign_domain("sign_"+ UUID.randomUUID().toString().replaceAll("-", ""));
            SignPDF.sign(sinatureCerDTO);


            //stamp.close();


        } catch (IOException e) {
            throw e;
        } /*catch (com.itextpdf.text.DocumentException e) {
            throw e;
        }*/

    }

    /**
     * 处理签章文件路径等问题
     * @param documentId
     * @param userName
     * @param user
     * @return
     */
    protected DocumentPathDTO getDocumentSingaturePath(String documentId, String userName, User user) {

        DocumentPathDTO dto = new DocumentPathDTO();

        //获得当前选中的文件信息， 并获得实际未签署路径和签署路径
        Document document = new Document(documentId);
        document.setUser(user);
        document = documentFindDao.get(document);

        //未签署真实路径
        StringBuffer undoneBuffer = new StringBuffer();
        if (document.getFileSinaturePath() != null && !"".equals(document.getFileSinaturePath())){
            undoneBuffer.append(Global.getConfig("file.realPath")).append(document.getFileSinaturePath());
        } else {
            undoneBuffer.append(Global.getConfig("file.realPath")).append(document.getFileOrgPath());
        }
        System.out.println(undoneBuffer.toString());
        dto.setUndoneRealPath(undoneBuffer.toString());

        //已签署真实路径
        StringBuffer doneBuffer = new StringBuffer();
        //获取该文件的后缀文件类型
        int lastIndex = document.getFileOrgPath().lastIndexOf(".");
        String lastName = document.getFileOrgPath().substring(lastIndex, document.getFileOrgPath().length());
        long time = System.currentTimeMillis();
        doneBuffer.append(Global.getConfig("file.done.realPath")).append(userName).append("/");
        dto.setDoneDir(doneBuffer.toString());
        doneBuffer.append(document.getFileName() + "-" + time + lastName);
        System.out.println(doneBuffer.toString());
        dto.setDoneRealPath(doneBuffer.toString());

        //已签署虚拟路径
        StringBuffer doneVirtualPathBuf = new StringBuffer();
        doneVirtualPathBuf.append(Global.getConfig("file.done.virtualPath")).append(userName).append("/").append(document.getFileName() + "-" + time + lastName);
        System.out.println(doneVirtualPathBuf.toString());
        dto.setDoneVirtualPath(doneVirtualPathBuf.toString());

        //end
        return dto;
    }

    protected Document finishSinatrue(String documentId, String fileDonePath, User user) {

        Document document = new Document(documentId);
        document.setUser(user);

        document = documentFindDao.get(document);

        document.setFileSinaturePath(fileDonePath);
        document.setSingalDate(new Date());
        document.setStatus(Global.YES);

        return document;
    }

    /**
     * 获得某个印章的实际路径
     * @param sealId
     * @param user
     * @return
     */
    protected String getSealPath(String sealId, User user) {
        //获得当前选中印章信息，并获得实际路径
        Seal seal = new Seal(sealId);
        seal.setUser(user);
        Stamp stamp =new Stamp();
        stamp = sealFindDao.findStamp(seal);


        StringBuffer sealPathBuffer = new StringBuffer();

        sealPathBuffer.append(Global.getConfig("ele_model.path")).append(stamp.getEleModel());

        return sealPathBuffer.toString();
    }


}
