package com.thinkgem.jeesite.modules.stamp.dao.stamp;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Moulage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 *  印模制作信息 Dao
 * Created by Locker on 2017/5/26.
 */
@MyBatisDao
public interface MoulageDao {


    /**
     * 保存物理印模 信息
     * @param moulage
     */
    public void insertMoulage(Moulage moulage);

    /**
     * 通过MoulageId
     * 获得Moluage信息
     * @param MoluageId
     * @return
     */
    public Moulage getMoulageById(@Param(value = "id") String MoluageId);

    public Moulage get(Moulage moulage);


    /**
     * 查找对应刻章点和用章单位公司下的某个印章类型的印模模版
     * @param moulage
     * @return
     */
    public List<Moulage> findListInMake(Moulage moulage);

}
