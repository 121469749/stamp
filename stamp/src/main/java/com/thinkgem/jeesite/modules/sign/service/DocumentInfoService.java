package com.thinkgem.jeesite.modules.sign.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.sign.common.condition.Condition;
import com.thinkgem.jeesite.modules.sign.common.config.Global;
import com.thinkgem.jeesite.modules.sign.common.utils.FileUtil;
import com.thinkgem.jeesite.modules.sign.dao.DocumentFindDao;
import com.thinkgem.jeesite.modules.sign.entity.Document;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by Locker on 17/9/15.
 */
@Service
@Transactional(readOnly = true)
public class DocumentInfoService {

    @Autowired
    private DocumentFindDao documentFindDao;


    public Page<Document> findPage(Page<Document> page, Document document){

        document.setUser(UserUtils.getUser());

        document.setPage(page);

        List<Document> documents =documentFindDao.findList(document) ;

        page.setList(documents);

        return page;
    }


    public Document get(Document document){
        return documentFindDao.get(document);
    }


    public Condition checkExist(String id, MultipartFile file){

        Document document = new Document(id);

        long time = System.currentTimeMillis();

        String checkPath = Global.getConfig("file.check.realPath") + time;

        String checkMD5;

        try {

            file.transferTo(new File(checkPath));

            checkMD5 = FileUtil.MD5ByFile(new File(checkPath));

        }catch (Exception e){

            e.printStackTrace();

            return new Condition(Condition.ERROR_CODE,"上传文件出错!");
        }

        document.setHexCodeOrginal(checkMD5);

        document.setUser(UserUtils.getUser());

        if(documentFindDao.checkFile(document)==1){

            return new Condition(Condition.SUCCESS_CODE);
        }


        return new Condition(Condition.ERROR_CODE);
    }

}
