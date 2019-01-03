package com.thinkgem.jeesite.modules.stamp.common.util.attachment.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.dao.AttachmentDao;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.applet.Main;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by Locker on 17/5/9.
 */
@Service
public class AttachmentService {

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private AreaDao areaDao;

    /**
     * 保存一个文件
     *
     * @param realAttachment
     * @param attachment
     * @return
     */
    @Transactional(readOnly = false)
    public Condition<Attachment> saveAttachment(MultipartFile realAttachment, Attachment attachment) {

        Condition<Attachment> condition = new Condition<Attachment>();

        try {
            //如果没有文件上传
            //则返回 错误代码.
            if (realAttachment.isEmpty()) {
                System.out.println("I'm here! Attachment is Empty");
                condition.setCode(Condition.NOTFOUND_CODE);
                condition.setMessage("请选择文件！");
                return condition;
            }

            // 有文件上传, 就把文件存起来取虚拟路径
            String attachmentPath = saveRealAttachments(realAttachment, attachment);
            //如果不为空
            if (!("".equals(attachmentPath))) {
                //设置虚拟路径
                attachment.setAttachPath(attachmentPath);

                attachmentDao.save(attachment);


                condition.setMessage("上传成功!");
                condition.setCode(Condition.SUCCESS_CODE);
                condition.setEntity(attachment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("服务器繁忙!请稍后再试...");

        } finally {

            return condition;

        }
    }


    public String saveSealFile(MultipartFile sealFile, Stamp stamp) throws Exception {

        //相对路径
        StringBuffer virtualPath = new StringBuffer(Global.getConfig("seal.virtualPath"));
        //实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("seal.realPath"));

        try {
            //获得网络文件名
            String netAttachName = sealFile.getOriginalFilename();

            //  获取该文件的后缀文件类型
            int lastIndex = netAttachName.lastIndexOf(".");
            String lastName = netAttachName.substring(lastIndex, netAttachName.length());


            Company useCompany = stamp.getUseComp();

            Area area = useCompany.getArea();

            //文件名
            StringBuffer attachName = new StringBuffer();

            String areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));

            //拼接目录 获得完整物理路径
            realPath.append(areasRealPath + "/" + useCompany.getCompanyName());

            virtualPath.append(areasRealPath.toString() + "/" + useCompany.getCompanyName());

            attachName.append(useCompany.getCompanyName() + "_" +
                    stamp.getStampName() + "_" + stamp.getStampType() + "_" + System.currentTimeMillis() + lastName);

            virtualPath.append("/" + attachName.toString());

            createDir(realPath.toString());

            realPath.append("/" + attachName.toString());

            System.out.println("realPath:" + realPath.toString());

            System.out.println("attachName:" + attachName.toString());

            System.out.println("virtualPath:" + virtualPath.toString());

            sealFile.transferTo(new File(realPath.toString()));

            return virtualPath.toString();

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }

    }

//    @Transactional(readOnly = true)
//    public List<Attachment> findAttachmentByRecordAndType(String recordId, String fileType) throws Exception {
//
//        StampRecord stampRecord = stampRecordDao.get(new StampRecord(recordId));
//
//        String jsonAttachs = stampRecord.getAttachs();
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Attachment.class);
//
//        mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, Attachment.class);
//
//        List<Attachment> attachments = (List<Attachment>) mapper.readValue(jsonAttachs, javaType);
//
//        List<Attachment> targetAttachment = new ArrayList<Attachment>();
//
//        System.out.println(attachments.toString());
//
//        //检索出符合fileType的附件对象
//        for (int i = 0; i < attachments.size(); i++) {
//
//            Attachment attachment = attachments.get(i);
//
//            if (fileType.equals(attachment.getAttachType())) {
//
//                targetAttachment.add(attachment);
//            }
//
//            //System.out.println(attachment);
//        }
//
//        //System.out.println(targetAttachment.toString());
//
//        targetAttachment = attachmentDao.findListByJsonList(fileType, targetAttachment);
//
//        return targetAttachment;
//
//
//    }

    @Transactional(readOnly = true)
    public List<Attachment> findAttachmentByJsonAttachs(String jsonAttachs) throws Exception {

        List<String> idList = jsonStrToList(jsonAttachs);

        if(idList.size()!= 0) {
            return attachmentDao.findListByIdList(idList);
        }else{
            return null;
        }
    }


    /**
     * 保存备案记录表
     *
     * @param realAttachment
     * @param companyName
     * @param stampName
     * @return
     */
    public String saveAttachmentInHost(MultipartFile realAttachment, String companyName, String stampName) {

        //相对路径
        StringBuffer virtualPath = new StringBuffer(Global.getConfig("attachment.virtualPath"));
        //实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachment.realPath"));

        try {
            //获得网络文件名
            String netAttachName = realAttachment.getOriginalFilename();

            //  获取该文件的后缀文件类型
            int lastIndex = netAttachName.lastIndexOf(".");
            String lastName = netAttachName.substring(lastIndex, netAttachName.length());

            // 获得当前用户
            User user = UserUtils.getUser();

            String areasRealPath = null;

            Area area = null;

            //文件名
            StringBuffer attachName = new StringBuffer();

            //对用户类型进行判断
            //如果 用户是 公司 类型
            if (user.getUserType() != UserType.POLICE) {

                area = areaDao.get(new Area(user.getCompanyInfo().getArea().getId()));

            }
            // 如果用户是公安机关
            if (user.getUserType() == UserType.POLICE) {
//                Company com = user.getPoliceInfo();
                area = user.getPoliceInfo().getArea();

            }

            areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));

            attachName.append(user.getCompanyInfo().getCompanyName() + "_" + stampName + "_" + System.currentTimeMillis() + lastName);

            virtualPath.append(areasRealPath.toString() + attachName.toString());

            // 如果 areaRealPath为空 则说明 该上传用户不是 公司和机关
            if (!(areasRealPath == null)) {
                //拼接目录 获得完整物理路径
                realPath.append(areasRealPath);
                //检查路径是否存在
                // 否则创建
                createDir(realPath.toString());


            } else {
                //初始化路径
                createDir(realPath.toString());

                attachName.append(user.getCompany().getCompanyName() + "_" + System.currentTimeMillis() + lastName);

                virtualPath.append(user.getCompany().getCompanyName() + "_" + System.currentTimeMillis() + lastName);
            }

            realPath.append(attachName.toString());

            System.out.println("realPath:" + realPath.toString());

            System.out.println("attachName:" + attachName.toString());

            System.out.println("virtualPath:" + virtualPath.toString());

            realAttachment.transferTo(new File(realPath.toString()));


        } catch (Exception e) {

            e.printStackTrace();

            virtualPath.delete(0, virtualPath.length());

        } finally {

            return virtualPath.toString();
        }


    }

    /**
     * 保存印模信息
     * @author bb
     * @param moulageFile
     * @param stampShape
     * @param companyName
     * @param stampName
     */
    public String[] saveMoulage(MultipartFile moulageFile, String stampShape, String companyName, String stampName)
            throws IOException, Exception {
        //相对路径
        StringBuffer realPath = null;
        //实际路径
        StringBuffer virtualPath = null;

        /*if ("2".equals(stampShape)) {

            realPath = new StringBuffer(Global.getConfig("attachment.moulageEle"));

            virtualPath = new StringBuffer(Global.getConfig("attachment.moulageEleVirtualPath"));
        }*/

        //1.先完成物理印模上传
        if ("1".equals(stampShape)) {

            realPath = new StringBuffer(Global.getConfig("attachment.moulagePhy"));

            virtualPath = new StringBuffer(Global.getConfig("attachment.moulagePhyVirtualPath"));
        }


        try {

            //获得网络文件名
            String netAttachName = moulageFile.getOriginalFilename();

            //  获取该文件的后缀文件类型
            int lastIndex = netAttachName.lastIndexOf(".");

            String lastName = netAttachName.substring(lastIndex, netAttachName.length());

            String areasRealPath = null;

            // 获得当前用户
            User user = UserUtils.getUser();

            //文件名
            StringBuffer attachName = new StringBuffer();

            //对用户类型进行判断
            //如果 用户是 公司 类型
            if (user.getUserType() != UserType.POLICE) {
                Area area = areaDao.get(new Area(user.getCompanyInfo().getArea().getId()));

                areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));

                attachName.append(companyName + "_" + stampName + "_" +
                        stampShape + "_" + System.currentTimeMillis() + lastName);

                virtualPath.append(areasRealPath.toString() + attachName.toString());

            }
            // 如果用户是公安机关
            if (user.getUserType() == UserType.POLICE) {
//                Company com = user.getPoliceInfo();
                Area area = areaDao.get(new Area(user.getPoliceInfo().getArea().getId()));

                areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));

                attachName.append(companyName + "_" + stampName + "_" +
                        stampShape + "_" + System.currentTimeMillis() + lastName);
                virtualPath.append(areasRealPath.toString() + attachName.toString());

            }

            if (!(areasRealPath == null)) {
                //拼接目录 获得完整物理路径
                realPath.append(areasRealPath);
                //检查路径是否存在,无则创建
                //1.物理的
                createDir(realPath.toString());
                //2.电子的
                String tempRealPath = realPath.toString().replace("moulagePhy","moulageEle");
                createDir(tempRealPath);
                //3.公安部电子的
                String tempRealPath2 = realPath.toString().replace("moulagePhy","moulageEle_ES");
                createDir(tempRealPath2);
            }

            realPath.append(attachName.toString());

            System.out.println("realPath:" + realPath.toString());
            System.out.println("attachName:" + attachName.toString());
            System.out.println("virtualPath:" + virtualPath.toString());

            moulageFile.transferTo(new File(realPath.toString()));

            //2.将上传完的物理印模进行处理(电子的)
            String realPath2 = realPath.toString().replace("moulagePhy","moulageEle").replaceFirst("_1","_2");
            String virtualPath2 = virtualPath.toString().replace("moulagePhy","moulageEle").replaceFirst("_1","_2");
            realPath2 = realPath2.split("\\.")[0] + ".png";
            virtualPath2 = virtualPath2.split("\\.")[0] + ".png";

            String realPath3 = realPath.toString().replace("moulagePhy","moulageEle_ES").replaceFirst("_1","_3");
            String virtualPath3 = virtualPath.toString().replace("moulagePhy","moulageEle_ES").replaceFirst("_1","_3");
            String realPath3_png = realPath3.split("\\.")[0] + ".png";
            realPath3 = realPath3.split("\\.")[0] + ".bmp";
            virtualPath3 = virtualPath3.split("\\.")[0] + ".bmp";

            File file = new File(realPath.toString());

            System.out.println("realPath2:" + realPath2.toString());
            System.out.println("virtualPath2:" + virtualPath2.toString());
            System.out.println("realPath3:" + realPath3.toString());
            System.out.println("virtualPath3:" + virtualPath3.toString());

            // -------------本系统使用---------------------
            // 灰度化再变颜色
            ByteArrayOutputStream baos = changeGrayColorImage(file);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            // 压缩处理:实际尺寸
            Thumbnails.of(bais).scale(0.11083449).outputQuality(1f).outputFormat("png").toFile(realPath2);

            // -------------公安部电子印章使用---------------------
            Color srcColor1 = new Color(0, 0, 0);
            Color targetColor1 = new Color(255, 0, 0);
            // 压缩处理：规范尺寸(400dpi) 2种格式：bmp和png
            Thumbnails.of(file).scale(0.41583449).outputQuality(1f).outputFormat("bmp").toFile(realPath3);
            // 将黑色变成红色
            replaceImageColor1(srcColor1, targetColor1, realPath3);
            BufferedImage bi = ImageIO.read(new File(realPath3));
            ImageIO.write(bi,"png",new File(realPath3_png));
            bi.flush();// 清空缓冲区数据
            // 将png的印模变透明
            transferAlpha(realPath3_png,realPath3_png);

            // 处理完毕，返回物理和电子印模的虚拟路径
            String[] path = new String[3];
            path[0] = virtualPath.toString();
            path[1] = virtualPath2;
            path[2] = virtualPath3;

            return path;

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }
    }

    /**
     * 先将图片灰度化再改变颜色值
     * @param file
     * @throws Exception
     * @return ByteArrayOutputStream
     */
    public static ByteArrayOutputStream changeGrayColorImage(File file) {

        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);

            for (int i = 0; i < bufferedImage.getWidth(); i++) {
                for (int j = 0; j < bufferedImage.getHeight(); j++) {
                    final int color = bufferedImage.getRGB(i, j);
                    final int r = (color >> 16) & 0xff;
                    final int g = (color >> 8) & 0xff;
                    final int b = color & 0xff;
                    int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                    //System.out.println(i + " : " + j + " " + gray);
                    int newPixel = 0;
                    if (gray < 30){
                        newPixel = (255 << 24) | (255 << 16) | (0 << 8) | 0;
                    }
                    grayImage.setRGB(i, j, newPixel);
                }
            }
            //返回处理后图像的byte[]
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(grayImage, "png", byteArrayOutputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return byteArrayOutputStream;
        }
    }


    /**
     * 保存附件类型
     *
     * @param types
     * @return
     */
    public List<Attachment> setUUIDList(List<String> types) {

        List<Attachment> attachments = new ArrayList<Attachment>();

        for (int i = 0; i < types.size(); i++) {
            if(types.get(i).equals("-1")||types.get(i).equals("undefined")) {
                continue;
            }else{
                Attachment attachment = new Attachment();
                attachment.setUUID();
                attachment.setAttachType(types.get(i));
                attachments.add(attachment);
            }
        }

        return attachments;
    }

    /**
     * 保存现场照片!
     *
     * @param photos
     * @return
     */
    public List<Attachment> setUUIDList(MultipartFile[] photos) {

        List<Attachment> attachments = new ArrayList<Attachment>();

        for (int i = 0; i < photos.length; i++) {
            Attachment attachment = new Attachment();
            attachment.setUUID();
            attachment.setAttachType("99");
            attachments.add(attachment);
        }

        return attachments;
    }

    public void saveAttachmentsInHosts(List<Attachment> attachments, MultipartFile[] files, String companyName)
            throws Exception {

        try {

            for (int i = 0; i < attachments.size(); i++) {

                Attachment attachment = attachments.get(i);

                String virtualPath = saveRealAttachments(files[i], attachment.getAttachType(), companyName);

                String fileSuffix = virtualPath.substring(virtualPath.lastIndexOf(".") + 1, virtualPath.length());

                attachment.setAttachPath(virtualPath);

                attachment.setFileSuffix(fileSuffix);
            }


        } catch (Exception e) {

            throw e;
        }

    }

    /**
     * @author 许彩开
     * @TODO (注：重构上传文件的保存，从临时文件里复制文件到正式路径)
      * @param
     * @DATE: 2018\8\27 0027 9:33
     */

    public void copyFileFromTemporary(List<Attachment> attachments, List<String> fileList, String companyName)
            throws Exception {
        fileList.removeAll(Collections.singleton(""));

        try {

            for (int i = 0; i < attachments.size(); i++) {

                Attachment attachment = attachments.get(i);

                String virtualPath = copyFileFromTemporaryFolders(fileList.get(i), attachment.getAttachType(), companyName);

                String fileSuffix = virtualPath.substring(virtualPath.lastIndexOf(".") + 1, virtualPath.length());

                attachment.setAttachPath(virtualPath);

                attachment.setFileSuffix(fileSuffix);
            }


        } catch (Exception e) {

            throw e;
        }

    }


    public void saveAttachmentsInHosts(List<Attachment> attachments, MultipartFile[] files, String companyName, String stampName)
            throws Exception {

        try {


            for (int i = 0; i < attachments.size(); i++) {

                Attachment attachment = attachments.get(i);

                String virtualPath = saveRealAttachments(files[i], attachment.getAttachType(), companyName, stampName);

                attachment.setAttachPath(virtualPath);
            }


        } catch (Exception e) {

            throw e;
        }

    }

    /**
     * 保存文件到服务器中
     * /省名/市名/公司id_文件类型键_时间戳.后缀
     *
     * @param realAttachment 一定有文件 上传才会到这一步
     * @param attachment
     * @return
     */
    private String saveRealAttachments(MultipartFile realAttachment, Attachment attachment) throws Exception {

        //相对路径
        StringBuffer virtualPath = new StringBuffer(Global.getConfig("attachment.virtualPath"));
        //实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachment.realPath"));

        try {
            //获得网络文件名
            String netAttachName = realAttachment.getOriginalFilename();

            //  获取该文件的后缀文件类型
            int lastIndex = netAttachName.lastIndexOf(".");
            String lastName = netAttachName.substring(lastIndex, netAttachName.length());

            // 获得当前用户
            User user = UserUtils.getUser();

            String areasRealPath = null;

            Area area = null;

            //文件名
            StringBuffer attachName = new StringBuffer();

            //对用户类型进行判断
            //如果 用户是 公司 类型
            if (user.getUserType() != UserType.POLICE) {

                area = areaDao.get(new Area(user.getCompanyInfo().getArea().getId()));

            }

            // 如果用户是公安机关
            if (user.getUserType() == UserType.POLICE) {
//                Company com = user.getPoliceInfo();
                area = user.getPoliceInfo().getArea();

            }


            areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));

            attachName.append(user.getCompanyInfo().getCompanyName() + "_" +
                    attachment.getAttachType() + "_" + System.currentTimeMillis() + lastName);

            virtualPath.append(areasRealPath.toString() + attachName.toString());
            // 如果 areaRealPath为空 则说明 该上传用户不是 公司和机关
            if (!(areasRealPath == null)) {
                //拼接目录 获得完整物理路径
                realPath.append(areasRealPath);
                //检查路径是否存在
                // 否则创建
                createDir(realPath.toString());


            } else {
                //初始化路径
                createDir(realPath.toString());

                attachName.append(user.getCompany().getCompanyName() + "_" + attachment.getAttachType()
                        + "_" + System.currentTimeMillis() + lastName);

                virtualPath.append(user.getCompany().getCompanyName() + "_" + attachment.getAttachType()
                        + "_" + System.currentTimeMillis() + lastName);
            }

            realPath.append(attachName.toString());

            System.out.println("realPath:" + realPath.toString());

            System.out.println("attachName:" + attachName.toString());

            System.out.println("virtualPath:" + virtualPath.toString());

            realAttachment.transferTo(new File(realPath.toString()));

            return virtualPath.toString();

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }

    }

    /**
     * 保存文件到服务器中
     * /省名/市名/公司id_文件类型键_时间戳.后缀
     * 一定有文件 上传才会到这一步
     *
     * @param realAttachment
     * @param fileType
     * @param companyName    公司名
     * @return
     */
    private String saveRealAttachments(MultipartFile realAttachment, String fileType, String companyName) throws Exception {

        //相对路径
        StringBuffer virtualPath = new StringBuffer(Global.getConfig("attachment.virtualPath"));
        //实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachment.realPath"));

        try {
            //获得网络文件名
            String netAttachName = realAttachment.getOriginalFilename();

            //  获取该文件的后缀文件类型
            int lastIndex = netAttachName.lastIndexOf(".");

            String lastName = netAttachName.substring(lastIndex, netAttachName.length());

            String fileSuffix = netAttachName.substring(lastIndex + 1, netAttachName.length());

            Area area = null;

            String areasRealPath = null;

            // 获得当前用户
            User user = UserUtils.getUser();

            //文件名
            StringBuffer attachName = new StringBuffer();

            //对用户类型进行判断
            //如果 用户是 公司 类型
            if (user.getUserType() != UserType.POLICE) {
                area = areaDao.get(new Area(user.getCompanyInfo().getArea().getId()));

            }
            // 如果用户是公安机关
            if (user.getUserType() == UserType.POLICE) {
//                Company com = user.getPoliceInfo();
                area = areaDao.get(new Area(user.getPoliceInfo().getArea().getId()));

            }

            areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));

            attachName.append(companyName + "_" +
                    fileType + "_" + System.currentTimeMillis() + lastName);
            virtualPath.append(areasRealPath.toString() + attachName.toString());

            // 如果 areaRealPath为空 则说明 该上传用户不是 公司和机关
            if (!(areasRealPath == null)) {
                //拼接目录 获得完整物理路径
                realPath.append(areasRealPath);
                //检查路径是否存在
                // 否则创建
                createDir(realPath.toString());


            } else {
                //初始化路径
                createDir(realPath.toString());

                attachName.append(companyName + "_" + fileType
                        + "_" + System.currentTimeMillis() + lastName);

                virtualPath.append(companyName + "_" + fileType
                        + "_" + System.currentTimeMillis() + lastName);
            }

            realPath.append(attachName.toString());

            System.out.println("realPath:" + realPath.toString());

            System.out.println("attachName:" + attachName.toString());

            System.out.println("virtualPath:" + virtualPath.toString());

            realAttachment.transferTo(new File(realPath.toString()));

            return virtualPath.toString();

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }


    }


    /**
     * @author 许彩开
     * @TODO (注：重构上传文件的保存，从临时文件里复制文件)
     * 保存文件到服务器中
     * /省名/市名/公司id_文件类型键_时间戳.后缀
     * 一定有文件 上传才会到这一步
     *
     * @param fileName
     * @param fileType
     * @param companyName    公司名
     * @return
     * @DATE: 2018\8\27 0027 9:37
     */

    private String copyFileFromTemporaryFolders(String fileName, String fileType, String companyName) throws Exception {

        //相对路径
        StringBuffer virtualPath = new StringBuffer(Global.getConfig("attachment.virtualPath"));
        //实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachment.realPath"));
        //临时文件夹实际路径
        StringBuffer tempRealPath = new StringBuffer(Global.getConfig("attachmentTemp.realPath"));

        try {

            //  获取该文件的后缀文件类型
            int lastIndex = fileName.lastIndexOf(".");

            String lastName = fileName.substring(lastIndex, fileName.length());

            String fileSuffix = fileName.substring(lastIndex + 1, fileName.length());

            Area area = null;

            String areasRealPath = null;

            // 获得当前用户
            User user = UserUtils.getUser();

            //文件名
            StringBuffer attachName = new StringBuffer();

            //对用户类型进行判断
            //如果 用户是 公司 类型
            if (user.getUserType() != UserType.POLICE) {
                area = areaDao.get(new Area(user.getCompanyInfo().getArea().getId()));

            }
            // 如果用户是公安机关
            if (user.getUserType() == UserType.POLICE) {
//                Company com = user.getPoliceInfo();
                area = areaDao.get(new Area(user.getPoliceInfo().getArea().getId()));

            }

            areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));

            attachName.append(companyName + "_" +
                    fileType + "_" + System.currentTimeMillis() + lastName);
            virtualPath.append(areasRealPath.toString() + attachName.toString());

            // 如果 areaRealPath为空 则说明 该上传用户不是 公司和机关
            if (!(areasRealPath == null)) {
                //拼接目录 获得完整物理路径
                realPath.append(areasRealPath);
                //检查路径是否存在
                // 否则创建
                createDir(realPath.toString());


            } else {
                //初始化路径
                createDir(realPath.toString());

                attachName.append(companyName + "_" + fileType
                        + "_" + System.currentTimeMillis() + lastName);

                virtualPath.append(companyName + "_" + fileType
                        + "_" + System.currentTimeMillis() + lastName);
            }

            realPath.append(attachName.toString());
            //源文件名
            String srcFileName = tempRealPath.append(fileName).toString();
            //目标文件名
            String descFileName =realPath.toString();

            //从临时文件夹里拷贝文件到真正路径
            FileUtils.copyFile(srcFileName,descFileName);
            //复制完成之后，将临时文件夹文件删除
            FileUtils.deleteFile(srcFileName);

            System.out.println("realPath:" + realPath.toString());

            System.out.println("attachName:" + attachName.toString());

            System.out.println("virtualPath:" + virtualPath.toString());


            return virtualPath.toString();

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }


    }



    /**
     * 保存 印章交付现场图片
     * <p>
     * 一定有文件 上传才会到这一步
     *
     * @param realAttachment
     * @param fileType
     * @param companyName    公司名
     * @param stampName      印章名
     * @return
     */
    private String saveRealAttachments(MultipartFile realAttachment, String fileType, String companyName, String stampName) throws Exception {

        //相对路径
        StringBuffer virtualPath = new StringBuffer(Global.getConfig("attachment.virtualPath"));
        //实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachment.realPath"));

        try {
            //获得网络文件名
            String netAttachName = realAttachment.getOriginalFilename();

            //  获取该文件的后缀文件类型
            int lastIndex = netAttachName.lastIndexOf(".");

            String lastName = netAttachName.substring(lastIndex, netAttachName.length());

            String areasRealPath = null;

            Area area = null;

            // 获得当前用户
            User user = UserUtils.getUser();

            //文件名
            StringBuffer attachName = new StringBuffer();

            //对用户类型进行判断
            //如果 用户是 公司 类型
            if (user.getUserType() != UserType.POLICE) {

                area = areaDao.get(new Area(user.getCompanyInfo().getArea().getId()));
            }
            // 如果用户是公安机关
            if (user.getUserType() == UserType.POLICE) {
//                Company com = user.getPoliceInfo();
                area = areaDao.get(new Area(user.getPoliceInfo().getArea().getId()));
            }

            areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));

            attachName.append(companyName + "_" +
                    fileType + "_" + stampName + "_" + System.currentTimeMillis() + lastName);

            virtualPath.append(areasRealPath.toString() + attachName.toString());

            // 如果 areaRealPath为空 则说明 该上传用户不是 公司和机关
            if (!(areasRealPath == null)) {
                //拼接目录 获得完整物理路径
                realPath.append(areasRealPath);
                //检查路径是否存在
                // 否则创建
                createDir(realPath.toString());


            } else {
                //初始化路径
                createDir(realPath.toString());

                attachName.append(companyName + "_" + stampName + "_" +
                        fileType + "_" + System.currentTimeMillis() + lastName);

                virtualPath.append(companyName + "_" + stampName + "_" +
                        fileType + "_" + System.currentTimeMillis() + lastName);
            }

            realPath.append(attachName.toString());

            System.out.println("realPath:" + realPath.toString());

            System.out.println("attachName:" + attachName.toString());

            System.out.println("virtualPath:" + virtualPath.toString());

            realAttachment.transferTo(new File(realPath.toString()));

            return virtualPath.toString();

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }


    }


    /**
     * 把文件保存到相印的路径中
     */
    private void createDir(String realPath) throws Exception {

        try {

            File file = new File(realPath);

//            System.out.println("正在创建目录......");
            //目录存在则不需要创建
            if (!file.exists()) {
                System.out.println("目录结构不存在!正在创建...");
                file.mkdirs();
                System.out.println("目录创建成功!\n路径为:" + file.getAbsolutePath());
            } else {
                System.out.println("目录已经存在...\n路径为:" + file.getAbsolutePath());
            }

        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 递归获取目录
     *
     * @param area
     */
    private String getRealFilePath(Area area) throws Exception {

        try {


            StringBuffer realFilePath = new StringBuffer("");

            if (!"2".equals(area.getType())) {

                //realFilePath.append(area.getName() + "\\");

                realFilePath.append(getRealFilePath(areaDao.get(new Area(area.getParentId()))) + area.getName() + "/");

            } else {

                realFilePath.append(area.getName() + "/" + realFilePath.toString());
            }

            return realFilePath.toString();

        } catch (Exception e) {

            throw e;
        }
    }


    private List<String> jsonStrToList(String jsonAttachs) throws Exception {

        List<Attachment> attachments = jsonStrToAttachments(jsonAttachs);

        List<String> idList = new ArrayList<String>();

        for (Attachment attachment : attachments) {
            idList.add(attachment.getId());
        }

        return idList;
    }


    public List<Attachment> jsonStrToAttachments(String jsonAttachs) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Attachment.class);

        mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, Attachment.class);

        List<Attachment> attachments = (List<Attachment>) mapper.readValue(jsonAttachs, javaType);

        return attachments;
    }

    public Attachment getAttachment(Attachment attachment){

        return attachmentDao.getAttachment(attachment);
    }

    // 批量保存附件列表
    public void insertAttachmentList(List<Attachment> attachmentList){
        attachmentDao.insertAttachmentList(attachmentList);
    }

    public String findAttachmentsPathByIds(String ids){
        return attachmentDao.findAttachmentsPathByIds(ids);
    }



    /**
     * @author 许彩开
     * @TODO (注：开始异步上传图片)
     * @param files
     * @DATE: 2018\8\21 0021 11:13
     */
    @Transactional(readOnly = false)
    public Condition startUploadImage(MultipartFile [] files){

        Condition<StampRecord> condition = new Condition();

        //相对路径
        StringBuffer virtualPath = new StringBuffer(Global.getConfig("attachmentTemp.virtualPath"));
        //临时文件夹实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachmentTemp.realPath"));

        StringBuffer waterRealPath = new StringBuffer(Global.getConfig("water.realPath"));

        //接收POST方式上传的文件，存放到临时文件夹
        if(files.length>0) {
            for (MultipartFile file : files) {

                String netAttchName = file.getOriginalFilename();//获取文件名
                String fielType = file.getContentType();//获取文件类型
                long fileSize = file.getSize();//获取文件大小
                System.out.println("文件大小："+fileSize);
                if (!check_size(fileSize)) {//判断文件大小是否符合规则

                    condition.setCode(102);//102错误码代表文件过大
                    return condition;
                }
                //  获取该文件的后缀文件类型
                int lastIndex = netAttchName.lastIndexOf(".");

                //获取文件后缀名(包含了点号),并后缀名小写化
                String lastName = netAttchName.substring(lastIndex, netAttchName.length()).toLowerCase();
                System.out.println("文件类型："+lastName);
                if (!is_img(lastName)) {
                    //如果不是图片类型，直接返回error为101，代表文件类型错误
                    condition.setCode(101);
                    return condition;
                }
                //水印：“印章备案专用”
                String waterPath =waterRealPath.append("picWater.png").toString();

                StringBuffer fname_new = new StringBuffer();
                fname_new.append(System.currentTimeMillis() + lastName);//利用当前时间戳构造新的文件名称

                realPath.append(fname_new);
                virtualPath.append(fname_new);

                try {
                    //初始化路径
                    createDir(realPath.toString());

                    file.transferTo(new File(realPath.toString())); //将临时文件区的文件移动到暂存区中

//                    //压缩图片
//                    Thumbnails.of(realPath.toString())//源路径
//                            //.size(2736,3648)
//                            .scale(1f)//指定图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，这里的大小是指图片的长宽。
//                            .watermark(Positions.CENTER, ImageIO.read(new File(waterPath)),0.5f)//watermark(位置，水印图，透明度)
//                            .outputQuality(1f)//是图片的质量，值也是在0到1，越接近于1质量越好，越接近于0质量越差。
//                            .toFile(realPath.toString());//输出路径

                    condition.setCode(200);
                    condition.setMessage("/img"+virtualPath);//返回文件虚拟路径给客户端看
                    return condition;

                }catch (Exception e){
                    e.printStackTrace();
                    condition.setCode(400); //400错误码代表文件非法上传(不是httppost提交)
                    return condition;
                }

            }
        }else{

            condition.setCode(404);//404错误码代表文件为空

        }

        return condition;

    }

    /**
     *判断文件后缀是否是图片
     *@param  suffix :带点的后缀名(如.jpg)
     *@return Boolean 如果是图片后缀名返回true,否则返回false
     */

    public Boolean is_img(String suffix){
        String[] img = new String[]{".jpg",".png",".gif",".jpeg",".bmp"};
        return Arrays.asList(img).contains(suffix.toLowerCase());
    }

    /**
     *判断文件大小是否非法
     *@param  size :单位为byte的整数，文件大小
     *@return bool 如果文件大小小于或等于规定的最大值返回true,否则返回false
     */

    public Boolean check_size(long size) {

        String MaxSize = Global.getConfig("web.maxUploadSize");//upload上传文件最大值

        if(size<Long.parseLong(MaxSize)){
            return true;
        }else{
            return false;
        }

    }


    /**
     * @author 许彩开
     * @TODO (注：删除临时文件夹里的文件)
     * @param fileName
     * @DATE: 2018\8\24 0024 14:43
     */

    public Condition deleteTempFile(String fileName){
        Condition<StampRecord> condition = new Condition();
        //临时文件夹实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachmentTemp.realPath"));

        realPath.append(fileName);

        File file = new File(realPath.toString());

        if(file.isFile() && file.exists()){
            Boolean succeedDelete = file.delete();
            if(succeedDelete){
                condition.setCode(200);
                System.out.println("删除单个文件"+fileName+"成功！");

            }
            else{
                condition.setCode(404);
                System.out.println("删除单个文件"+fileName+"失败！");

            }
        }else{
            condition.setCode(404);
//            System.out.println("删除单个文件"+fileName+"失败！");

        }
        return condition;

    }

    // 遍历像素点变颜色
    public static void replaceImageColor1(Color srcColor, Color targetColor, String tmpPath) throws IOException {

        File srcfile = new File(tmpPath);

        BufferedImage bi = ImageIO.read(srcfile);

        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                // System.out.println(bi.getRGB(i, j));
                if (srcColor.getRGB() == bi.getRGB(i, j)) {
                    // System.out.println(i+","+j+"
                    // from:"+srcColor.getRGB()+"to"+targetColor.getRGB());
                    bi.setRGB(i, j, targetColor.getRGB());
                }
            }
        }
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("bmp");
        ImageWriter writer = it.next();
        File f = new File(tmpPath);
        ImageOutputStream ios = ImageIO.createImageOutputStream(f);
        writer.setOutput(ios);
        writer.write(bi);
        bi.flush();
        ios.flush();
        ios.close();
    }

    // 将背景变透明
    public static void transferAlpha(String imagePath, String outputFile) throws Exception {
        Image image = ImageIO.read(new File(imagePath));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIcon imageIcon = new ImageIcon(image);
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
            int alpha = 0;
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);
                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0xff00) >> 8;
                    int B = (rgb & 0xff);
                    if (((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) {
                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                    }
                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
            ImageIO.write(bufferedImage, "png", new File(outputFile));
            //ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        // return byteArrayOutputStream.toByteArray();
    }

    // 二值化，非白即红
    public static void binaryImage(String imagePath, String outputFile) throws IOException{
        Image img = ImageIO.read(new File(imagePath));
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
        g2D.drawImage(img, 0, 0, null);
        g2D.dispose();
        int alpha = 0;
        for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
            for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
                int pixel = bufferedImage.getRGB(x, y);
                int r = ((pixel & 0x00ff0000) >>> 16);
                int g = ((pixel & 0x0000ff00) >>> 8);
                int b = ((pixel & 0x000000ff));
                if (r != 255 && g != 255 && b != 255) {
                    //非白色像素改为红色
                    int red = Color.RED.getRGB();
                    bufferedImage.setRGB(x, y, red);
                } else {
                    //白色像素改为透明
                    /*pixel = (alpha << 24) | (pixel & 0x00ffffff);
                    bufferedImage.setRGB(x, y, pixel);*/
                }
            }
        }
        ImageIO.write(bufferedImage, "png", new File(outputFile));
    }


    public static void main(String[] args) throws Exception {
        String path1 = "C:/怀集县怀城镇远钊食品加工场_公章-1_1_1536716023015.BMP";// 第三方
        String path2 = "C:/广东美的集团有限公司-公章1.bmp"; // 何卫东
        String realPath2 = "C:/toPNG.png";
        String realPath3 = "D:\\stamp\\moulageEle_ES\\广东省\\肇庆市\\广东电网有限责任公司肇庆鼎湖供电局_公章-1_3_1536746506395.png";
        String realPath4 = "D:\\stamp\\moulageEle_ES\\广东省\\肇庆市\\toPNG.png";

         Color srcColor1 = new Color(0, 0, 0);
         Color targetColor1 = new Color(255, 0, 0);

        //灰度化再变颜色
        //File file = new File(realPath3);
        //ByteArrayOutputStream baos = changeGrayColorImage(file);
        //ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        // 压缩处理
        //Thumbnails.of(bais).scale(0.41583449).outputQuality(1f).outputFormat("BMP").toFile(realPath3);
        //new File(realPath2).renameTo(new File(realPath3));


        // 1.获取到排版的印模原图后，先统一转BMP格式
        /*BufferedImage bi = ImageIO.read(new File(path2));

        BufferedImage bi2 = new BufferedImage(bi.getWidth(),bi.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D g2D = bi2.createGraphics();
        g2D.drawImage(bi,0,0,null);

        ImageIO.write(bi2,"bmp",new File(realPath3));
        bi.flush();// 清空缓冲区数据*/

        //// 压缩处理（400dpi）
        //Thumbnails.of(path1).scale(0.41583449).outputQuality(1f).outputFormat("bmp").toFile(realPath3);
        //// 将黑色变成红色
        //replaceImageColor1(srcColor1, targetColor1, realPath3);
        //
        //changeGrayColorImage(new File(path1));
        //Thumbnails.of(realPath2).scale(0.11083449).outputQuality(1f).outputFormat("png").toFile(realPath2);


        Thumbnails.of(realPath3).scale(0.267175572519084).outputQuality(1f).outputFormat("png").toFile(realPath4);
    }

}
