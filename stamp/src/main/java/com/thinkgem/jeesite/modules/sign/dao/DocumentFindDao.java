package com.thinkgem.jeesite.modules.sign.dao;


import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sign.entity.Document;

import java.util.List;

/**
 *
 *
 *
 * Created by Locker on 2017/9/14.
 */
@MyBatisDao
public interface DocumentFindDao {

    public Document get(Document document);

    public List<Document> findList(Document document);

    public Document findByMd5(Document document);

    public int checkFile(Document document);

}
