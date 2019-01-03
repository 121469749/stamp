package com.thinkgem.jeesite.modules.sign.service;

import com.thinkgem.jeesite.modules.sign.common.config.Global;
import com.thinkgem.jeesite.modules.sign.common.utils.FileUtil;
import com.thinkgem.jeesite.modules.sign.dao.DocumentFindDao;
import com.thinkgem.jeesite.modules.sign.dao.SealFindDao;
import com.thinkgem.jeesite.modules.sign.entity.Document;
import com.thinkgem.jeesite.modules.sign.entity.Seal;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by Administrator on 2017/9/16.
 */
@Service("checkService1")
@Transactional(readOnly = true)
public class CheckService {

    @Autowired
    private DocumentFindDao documentFindDao;

    @Autowired
    private SealFindDao sealFindDao;

    public Document checkFile(MultipartFile file){

        long time = System.currentTimeMillis();

        String checkPath = Global.getConfig("file.check.realPath") + time;

        String checkMD5="";

        try {

            file.transferTo(new File(checkPath));

            checkMD5 = FileUtil.MD5ByFile(new File(checkPath));

        }catch (Exception e){

            e.printStackTrace();

        }


        Document document = new Document();

        document.setSingatureMD5(checkMD5);

        document.setUser(UserUtils.getUser());

        document = documentFindDao.findByMd5(document);

        if(document!=null){

            Seal seal = sealFindDao.get(document.getSeal());

            document.setSeal(seal);

        }

        return document;
    }


}
