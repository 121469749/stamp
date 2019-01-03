package com.thinkgem.jeesite.modules.sign.dao;


import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sign.entity.Document;

import java.util.Map;

/**
 *
 *
 *
 * Created by Locker on 2017/9/13.
 */
@MyBatisDao
public interface DocumentActionDao extends CrudDao<Document>{

    public int insert(Document document);

    public int finishSinatrue(Document document);

    public int updateSignData(Map map);

}
