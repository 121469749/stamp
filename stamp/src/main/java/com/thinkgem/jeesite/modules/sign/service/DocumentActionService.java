package com.thinkgem.jeesite.modules.sign.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sign.common.condition.Condition;
import com.thinkgem.jeesite.modules.sign.common.config.Global;
import com.thinkgem.jeesite.modules.sign.common.utils.DirUtil;
import com.thinkgem.jeesite.modules.sign.common.utils.FileUtil;
import com.thinkgem.jeesite.modules.sign.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sign.dao.DocumentActionDao;
import com.thinkgem.jeesite.modules.sign.entity.Document;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 *
 * 文件动作服务
 *
 * Created by Locker on 2017/9/14.
 */
@Service
@Transactional(readOnly =  false)
public class DocumentActionService extends CrudService<DocumentActionDao, Document> {


    @Autowired
    private DocumentActionDao documentActionDao;

    /**
     *
     * 添加文件
     *
     * @param document
     * @param multipartFile
     * @return
     */
    public Condition add(Document document, MultipartFile multipartFile){

        Condition condition=null;

        try {

            if(document.getFileName()==null||"".equals(document.getFileName().trim())){
                //获得网络文件名
                String netAttachName = multipartFile.getOriginalFilename();

                //  获取该文件的后缀文件类型
                int lastIndex = netAttachName.lastIndexOf(".");

                String fileName = netAttachName.substring(0, lastIndex);

                document.setFileName(fileName);
            }

            document.setId(IdGen.uuid());

            document.setCreateDate(new Date());

            document.setStatus(Global.NO);

            document.setFileOrgPath(unDoneFileUpload(multipartFile,document.getFileName(), UserUtils.getUser().getName()));

            document.setHexCodeOrginal(FileUtil.MD5ByFile(new File( Global.getConfig("file.realPath")+document.getFileOrgPath())));

            document.setUser(UserUtils.getUser());

            if(documentActionDao.insert(document)==1){

                condition= new Condition(Condition.SUCCESS_CODE);

            }else{
                throw new Exception();
            }

        } catch (Exception e) {

            e.printStackTrace();

            condition=new Condition(Condition.ERROR_CODE,"服务器繁忙!\n请再次上传!");

        }finally {

            return condition;
        }

    }


    /**
     *
     * 处理文件存储
     *
     * @param file
     * @param fileName
     * @param userName
     * @return
     * @throws IOException
     * @throws Exception
     */
    protected String unDoneFileUpload(MultipartFile file, String fileName, String userName) throws IOException,Exception{

        try {
            String realPath = Global.getConfig("file.undone.realPath");

            String virtualPath = Global.getConfig("file.undone.virtualPath");

            //获得网络文件名
            String netAttachName = file.getOriginalFilename();

            //  获取该文件的后缀文件类型
            int lastIndex = netAttachName.lastIndexOf(".");

            String lastName = netAttachName.substring(lastIndex, netAttachName.length());

            //文件名
            StringBuffer attachName = new StringBuffer();

            attachName.append(userName);

            DirUtil.createDir(realPath+attachName.toString());

            attachName.append("/").append(fileName+"-"+System.currentTimeMillis()).append(lastName);

            realPath += attachName.toString();

            file.transferTo(new File(realPath));

            virtualPath+=attachName.toString();

            return virtualPath;

        } catch (IOException e) {

            e.printStackTrace();

            throw e;

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }
    }


    /**
     * 逻辑删除文件
     * @param document
     */
    public void deleteDoc(Document document){
        super.delete(document);
    }

}
