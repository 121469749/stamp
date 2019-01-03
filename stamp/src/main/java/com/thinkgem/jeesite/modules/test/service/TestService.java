/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.certificate.CertificateService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.test.entity.Test;
import com.thinkgem.jeesite.modules.test.dao.TestDao;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * 测试Service
 * @author ThinkGem
 * @version 2013-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {

    @Autowired
    private TestDao testDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CompanyDao companyDao;

    /**
     * @author 许彩开
     * @TODO (注：)
     * @param
     * @DATE: 2018\5\24 0024 9:59
     */
    @Transactional(readOnly = false)
    public void testUpdate(){
        String soleCode = "441200";
        String area_id = "cb5ec4bf151e43ccb7b4955ce9999a29";

        List<Company> use_comp = testDao.testUpdate(soleCode,area_id);

        System.out.println("用章公司一共"+use_comp.size()+"条");

        for(Company company : use_comp) {

            List<Stamp> stamp1 = testDao.returnStamp1(company.getId());


            if(stamp1.size()!=0) {
                //取第一条即可
                System.out.println("要查询的制章公司id==" + stamp1.get(0).getMakeComp().getId());

                Company make_comp = testDao.returnMakeComp(stamp1.get(0).getMakeComp().getId());

                System.out.println("制章公司地区编码==="+make_comp.getArea().getId());

                testDao.updateAreaId(make_comp.getArea().getId(),company.getId());
            }

            //更新用章公司的地区编码为         市编码==>区编码



            System.out.println("印章一共"+stamp1.size()+"条");
//
//            System.out.println("公司表2=id===" + company.getId()+"===area_id==="+company.getArea().getId());
        }
    }


    /**
     * @author 许彩开
     * @TODO (注：更新证书)
     * @param
     * @DATE: 2018\5\24 0024 9:59
     */
    @Transactional(readOnly = false)
    public void updateCertificate(){
        User user1 =new User();
        user1.setUserType(UserType.USE);

        List<User> userList = userDao.findList(user1);

        for (User user:userList){
            if(user.getCertFilePath()==null||user.getCertFilePath().isEmpty()){

                //生成证书并绑定
                certificateService.generateCert(user);

            }
        }
    }


    public static void main(String[] args) throws IOException {

    }

}
