package com.thinkgem.jeesite.modules.sign.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.sign.dao.AuditDao;
import com.thinkgem.jeesite.modules.sign.entity.Audit;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 安全审计功能Service
 * @author bb
 * @version 2018-07-16
 */
@Service
@Transactional(readOnly = true)
public class AuditService {

    @Autowired
    private AuditDao auditDao;
    @Autowired
    private UserDao userDao;

    public Page<Audit> findPage(Page<Audit> page, Audit audit){

        audit.setPage(page);
        if (!UserUtils.getUser().getUserType().equals(UserType.POLICE)){
            audit.setUser(UserUtils.getUser());
        }

        List<Audit> auditList = auditDao.findList(audit);
        //若公安查询
        if (UserUtils.getUser().getUserType().equals(UserType.POLICE)){
            Iterator<Audit> it = auditList.iterator();
            while (it.hasNext()){
                Audit aduit_temp = it.next();
                if (!aduit_temp.getAuditType().equals(Audit.AUDIT_MAKE)){
                    it.remove();
                }
            }
            for (Audit audit1 : auditList){
                User user = audit1.getUser();
                user.setUserType(UserType.MAKE);
                user = userDao.get(user);
                audit1.setUser(user);
            }
        }

        page.setCount(auditList.size());
        page.setList(auditList);

        return page;
    }

    @Transactional(readOnly = false)
    public boolean addAudit(Audit audit) {
        if (auditDao.insert(audit) > 0){
            return true;
        }
        return false;
    }

    @Transactional(readOnly = false)
    public void updateAudit(Audit audit) {
        auditDao.update(audit);
    }

}
