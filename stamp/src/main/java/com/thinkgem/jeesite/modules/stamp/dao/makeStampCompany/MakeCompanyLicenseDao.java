package com.thinkgem.jeesite.modules.stamp.dao.makeStampCompany;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CheckState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/5/19.
 */
@MyBatisDao
public interface MakeCompanyLicenseDao {

    /**
     *
     * 取得该company
     * 公司业务办理信息的最新一条。
     *
     * @param makeCompId Company.id
     * @return
     * 返回检测状态
     * @author Locker
     */
    public CheckState checkOtherLicence(@Param("id") String makeCompId,@Param("workType")WorkType workType);


    /**
     *
     * 把新的许可证申请信息持久化
     *
     * @param licence
     * @author Locker
     */
    public void insertLicence(Licence licence);



    /**
     *
     * 取得该company
     * 公司业务办理信息的最新一条。
     *
     * @param makeCompId Company.id
     * @return
     * 返回检测状态
     * @author Locker
     */
    public Licence checkNewestLicence(@Param("makeCompId") String makeCompId, @Param("workType") WorkType workType);


    /**
     *
     * 取得该company
     * 公司业务办理信息的最新一条。
     *
     * @param makeCompId Company.id
     * @return
     * 返回检测状态
     * @author Locker
     */
    public Licence getLicence(@Param("makeCompId") String makeCompId, @Param("workType") WorkType workType);
}
