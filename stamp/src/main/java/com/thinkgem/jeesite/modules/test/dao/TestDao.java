/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.test.entity.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 测试DAO接口
 * @author ThinkGem
 * @version 2013-10-17
 */
@MyBatisDao
public interface TestDao extends CrudDao<Test> {


    /**
     * @author 许彩开
     * @TODO (注：测试用)
     * @param
     * @DATE: 2018\5\24 0024 10:14
     */

    public List<Company> testUpdate(@Param(value = "soleCode") String soleCode,
                                    @Param(value="area_id")String area_id);

    public List<Stamp> returnStamp1(@Param(value = "use_comp") String use_comp);


    public Company returnMakeComp(@Param(value = "id") String id);

    public void updateAreaId (@Param(value="area_id")String area_id,@Param(value = "id") String id);
}
