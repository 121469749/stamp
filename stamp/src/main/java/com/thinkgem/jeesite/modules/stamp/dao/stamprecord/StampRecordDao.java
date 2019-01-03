/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.dao.stamprecord;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 印章备案信息DAO接口
 * @author Locker
 * @version 2017-05-13
 */
@MyBatisDao
public interface StampRecordDao extends CrudDao<StampRecord> {

    public int countStampRecord(@Param(value="stampRecord") StampRecord stampRecord,
                                @Param(value="startDate")Date startDate,
                                @Param(value="endDate")Date endDate);

    public int editStampRecord(StampRecord stampRecord);

    public int editAttachs(StampRecord stampRecord);


    public List<StampRecord> findCountList(StampRecord stampRecord);

    public List<StampRecord> findRecordIdFromWorkType(StampRecord stampRecord);

    // 根据公司id查找用章企业的附件信息
    public String findAttasByCompanyId(@Param("companyId")String companyId);

    public int bindRecord(StampRecord stampRecord);

}