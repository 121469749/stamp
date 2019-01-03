/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.service.ES_;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.police.PoliceDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.ES_.EPS_PERSONALIZE_INFO;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.MappedByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公安部-电子印章-Service
 * @author bb
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly = false)
public class ESFuncService {

    @Autowired
    private AreaDao areaDao;
    @Autowired
    private PoliceDao policeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyDao companyDao;

    //个人化
    public void funcPersonalize(Stamp stamp, EPS_PERSONALIZE_INFO epi, Condition c){
        String str = "";
        StringBuffer sb = new StringBuffer();

        try {
            // 获取制作单位信息和备案单位信息
            Company makeComp = companyDao.get(new Company(stamp.getNowMakeComp().getId(),CompanyType.MAKE));
            Police police = new Police();
            police.setArea(new Area(makeComp.getArea().getParentId()));
            police = policeDao.getByArea(police);
            //
            epi.setDwVersion(1);
            //
            str = stamp.getStampCode();
            if ("".equals(str) || str == null)
                sb.append("印章编码不能为空");
            else if(!str.matches("^[0-9]{3,19}$"))
                sb.append("印章编码为3到19个数字");
            epi.setSzSealSerial(str);
            //
            str = new StringBuilder().append(stamp.getUseComp().getCompanyName()).append(DictUtils.getDictLabel(stamp.getStampType(),"stampType",null)).toString();
            if ("".equals(str) || str == null)
                sb.append("印章名称不能为空");
            else if (str.getBytes("GBK").length > 160)
                sb.append("印章名称不能超过160个字节");
            epi.setSzSealName(str);
            //
            switch (stamp.getStampType()){
                case "1":
                    str = "01";
                    break;
                case "2":
                    str = "02";
                    break;
                case "3":
                    str = "03";
                    break;
                case "4":
                    str = "04";
                    break;
                case "5":
                    str = "99";
                    break;
                case "6":
                    str = "05";
                    break;
                case "7":
                    str = "99";
                    break;
                default:
                    str = "";
                    break;
            }
            if ("".equals(str) || str == null)
                sb.append("印章类型编码不能为空");
            epi.setSzSealTypeCode(str);
            //
            str = StampState.RECEPT.getKey();
            epi.setSzStateCode(str);
            //
            str = police.getPoliceCode();
            if ("".equals(str) || null == str)
                sb.append("备案单位编码不能为空");
            else if (!str.matches("^[0-9]{2,12}$"))
                sb.append("备案单位编码为2到12个数字");
            epi.setSzApprovalCompanyCode(str);
            //
            str = police.getPrincipal();
            if ("".equals(str) || str == null)
                sb.append("备案单位名称（汉字）不能为空");
            else if (str.getBytes("GBK").length > 120)
                sb.append("备案单位名称（汉字）不能超过120个字节");
            epi.setSzApprovalCompanyNameChs(str);
            //
            str = police.getPrincipal();
            if ("".equals(str) || str == null)
                sb.append("备案人员名称不能为空");
            else if (str.getBytes("GBK").length > 30)
                sb.append("备案人员名称不能超过30个字节");
            epi.setSzApprover(str);
            //
            str = new SimpleDateFormat("yyyyMMdd").format(new Date());
            epi.setSzApprovalDate(str);
            //
            str = stamp.getUseComp().getCompanyCode();
            if ("".equals(str) || str == null)
                sb.append("使用单位编码不能为空");
            else if (!str.matches("^[0-9]{3,17}$"))
                sb.append("使用单位编码为3到17个数字");
            epi.setSzUserCompanyCode(str);
            //
            str = stamp.getUseComp().getCompanyName();
            if ("".equals(str) || str == null)
                sb.append("使用单位名称（汉字）不能为空");
            else if (str.getBytes("GBK").length > 120)
                sb.append("使用单位名称（汉字）不能超过120个字节");
            epi.setSzUserCompanyNameChs(str);
            //
            str = stamp.getUseComp().getLegalName();
            if ("".equals(str) || str == null)
                sb.append("法人代表不能为空");
            else if (str.getBytes("GBK").length > 30)
                sb.append("法人代表不能超过30个字节");
            epi.setSzPersonName(str);
            //
            str = stamp.getUseComp().getLegalCertCode();
            if (str.getBytes("GBK").length > 18)
                sb.append("法人代表身份证不能超过18个字节");
            epi.setSzPersonIdentity(str);
            //
            str = makeComp.getCompanyCode();
            if ("".equals(str) || str == null)
                sb.append("制作单位编码不能为空");
            else if (!str.matches("^[0-9]{2,17}$"))
                sb.append("制作单位编码为2到17个数字");
            epi.setSzProductCompanyCode(str);
            //
            str = makeComp.getCompanyName();
            if ("".equals(str) || str == null)
                sb.append("制作单位名称（汉字）不能为空");
            else if (str.getBytes("GBK").length > 120)
                sb.append("制作单位名称（汉字）不能超过120个字节");
            epi.setSzProductCompanyNameChs(str);
            //
            str = new SimpleDateFormat("yyyyMMdd").format(new Date());
            epi.setSzProductDate(str);
            //
            str = stamp.getLastRecord().getAgentName();
            if ("".equals(str) || str == null)
                sb.append("经办人不能为空");
            else if (str.getBytes("GBK").length > 30)
                sb.append("经办人不能超过30个字节");
            epi.setSzOperator(str);
            //
            str = stamp.getLastRecord().getAgentCertCode();
            if (str.getBytes("GBK").length > 18)
                sb.append("经办人身份证不能超过18个字节");
            epi.setSzOperatorID(str);
            //
            str = new SimpleDateFormat("yyyyMMdd").format(new Date());
            epi.setSzUndertakeDate(str);
            //
            epi.setSzCompressLabel("2");
            //
            /*str = new StringBuilder().append(Global.getConfig("attachment.absolutePath")).append(stamp.getEleModel()).toString();
            File file = new File(str);
            if (!file.exists() || !file.isFile())
                sb.append("印章原图像文件未找到");
            *//*else if (!str.matches("^.+\\.[Bb][Mm][Pp]$"))
                sb.append("印章原图像文件类型错误");*//*
            long len = file.length();
            if (len > 2097152l)
                sb.append("印章原图像文件不能超过2097152字节");// 2097152字节=2MB
            byte[] chs = toByteArray(str);
            epi.setPbPicture(chs);
            epi.setDwPictureLen(chs.length);*/
            str = new StringBuilder().append(Global.getConfig("webPath")).append(stamp.getEsEleModel()).toString();
            epi.setPbPictureUrl(str);
            //
            /*file = new File(str);
            if (!file.exists() || !file.isFile())
                sb.append("印章压缩图像文件未找到");
            *//*else if (!str.matches("^.+\\.[Pp][Nn][Gg]$"))
                sb.append("印章压缩图像文件类型错误");*//*
            len = file.length();
            if (len > 65535)
                sb.append("印章压缩图像文件不能超过65535字节");
            chs = toByteArray(str);
            epi.setPbPicCompress(chs);
            epi.setDwPicCompressLen(chs.length);*/
            str = new StringBuilder().append(Global.getConfig("webPath")).append(stamp.getEsEleModel().replace("bmp","png")).toString();
            epi.setPbPicCompressUrl(str);
            //
            epi.setSzMaterialCode("99");
            //
            epi.setSzInkpadDesc("");
            //
            str = stamp.getUseComp().getSoleCode();
            if (str.getBytes("GBK").length > 18)
                sb.append("社会信用统一码不能超过18个字节");
            epi.setSzSocialCredit(str);
            //
            switch (stamp.getUseComp().getType1()){
                case "1":
                    str = "01";
                    break;
                case "2":
                    str = "03";
                    break;
                case "4":
                    str = "04";
                    break;
                case "5":
                    str = "05";
                    break;
                case "7":
                    str = "02";
                    break;
                case "3":
                case "6":
                case "8":
                case "9":
                case "99":
                    str = "99";
                    break;
                default:
                    str = "";
                    break;
            }
            if (str.getBytes("GBK").length > 2)
                sb.append("单位类型编码不能超过2个字节");
            epi.setSzCompanyType(str);
            //
            epi.setSzSealSpec("20/20");

            //扩展不作要求
            //epi.setPbExtension1();
            //epi.setPbExtension2();
            //epi.setPbExtension3();
            //epi.setPbExtension4();
            //epi.setPbExtension5();

            if (sb.length() > 0) {
                c.setCode(Condition.ERROR_CODE);
                c.setMessage(sb.toString());
            }

            c.setCode(Condition.SUCCESS_CODE);
            c.setEntity(epi);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    //byte[]读入文件
    public static byte[] toByteArray(String filename) throws IOException {
        RandomAccessFile raf = null;
        FileChannel fc = null;
        try {
            raf = new RandomAccessFile(filename, "r");
            fc = raf.getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                raf.close();
                fc.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }


}