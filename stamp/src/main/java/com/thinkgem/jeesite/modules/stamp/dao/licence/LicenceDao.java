/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.dao.licence;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 许可证业务信息DAO接口
 * @author Locker
 * @version 2017-05-13
 */
@MyBatisDao
public interface LicenceDao extends CrudDao<Licence> {




    /**
     * 更改licence审核状态
     * @param licence
     */
    public void updateCheckState(Licence licence);


    /**
     *@author hjw
     *@description  获得最新license
     *@param [licence]
     *@return com.thinkgem.jeesite.modules.stamp.info.entity.licence.Licence
     *@date 2017/5/26
     */
    public Licence getNewestLicense(Licence licence);

    /*
     *@author hjw
     *@description  查找license历史
     *@param [licence]
     *@return java.util.List<com.thinkgem.jeesite.modules.stamp.entity.licence.Licence>
     *@date 2017/6/2
     */
    public List<Licence> findHistoryList(Licence licence);

    public void deleteInMakeCom(@Param("id") String companyId, @Param("workType") WorkType workType);
}