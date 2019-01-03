/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.dao.stamp;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.dto.count.PoliceCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.StampMakeComp;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.DeclareParents;
import org.junit.Test;
import org.springframework.security.access.method.P;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 印章信息DAO接口
 *
 * @author Locker
 * @version 2017-05-13
 */
@MyBatisDao
public interface StampDao extends CrudDao<Stamp> {

    /**
     * 启停印章状态
     *
     * @param stamp
     * @return
     */
    public int updateStampState(Stamp stamp);

    public int updateStampState2(Stamp stamp);

    /**
     * 查询某用章公司名下的所有 已交付 可用 物理印章
     * @param stamp
     * @return
     */
    public List<Stamp> listStampByComId(Stamp stamp);

//
//    /**
//     * 批量插入 Stamp信息
//     * @param item
//     * @return
//     */
//    public void insertStampList(List<Stamp> item);

    /**
     * 通过印章类型，用章公司，
     * 计算该公司 这种印章类型的 数目有多少个
     *
     * @param stampType
     * @param useCompany
     * @return
     */
    public int getStampTypeCountByUseCompanyAndStampType(@Param(value = "stampType") String stampType,
                                                         @Param(value = "useCompany") Company useCompany,
                                                         @Param(value = "stampShape") String stampShape);

    /**
     * 检查该用章公司的新的印章名是否存在
     *
     * @param stampName
     * @param useCompany
     * @return
     */
    public int checkStampNameExist(@Param(value = "stampName") String stampName,
                                   @Param(value = "stampShape") String stampShape,
                                   @Param(value = "useCompany") Company useCompany);

    /**
     * 根据印章使用状态查询印章
     *
     * @param Stamp
     * @return
     */
    public List<Stamp> listStampByUseState(Stamp Stamp);

    /**
     * 根据印章状态查询印章
     *
     * @param stamp
     * @return
     */
    public List<Stamp> listStampByStampState(Stamp stamp);


    /**
     * 检查 当前的印章id是否可以开始制作
     *
     * @param stampId
     * @return 1- 可以
     * 0- 不可以
     */
    public int checkStampNeedMake(@Param(value = "stampId") String stampId,
                                  @Param(value = "stampShape") String stampShape);

    /**
     * 根据 印章名称、用章公司 查找印章（模糊查询）
     *
     * @param company
     * @param stamp
     * @return
     */
    public List<Stamp> listStampByName(@Param("company") Company company, @Param("stamp") Stamp stamp);

    /**
     * 印章与物理印模绑定
     *
     * @param stamp
     */
    public void bindStampAndMoulage(Stamp stamp);
    /**
     * @param stamp
     * @return
     * @author bb
     * 更新物理印章写入芯片状态前作检查
     */
    Stamp getStampByAntiFakeId(Stamp stamp);
    /**
     * @author bb
     * 更新物理印章写入芯片状态
     * @param stamp
     * @return
     */
    int updateChip(Stamp stamp);
    /**
     * @author bb
     * 根据印章编码查询印章
     * @param stamp
     * @return
     */
    Stamp getStampByStampCode(Stamp stamp);
    /**
     * @author bb
     * 分别统计多证合一和备案系统录入的印章数
     * @param stamp
     * @return
     */
    int countStampFromDZHY(@Param(value = "stamp") Stamp stamp, @Param(value = "Ids")List<String> Ids);
    int countStampFromBA(@Param(value = "stamp") Stamp stamp, @Param(value = "Ids")List<String> Ids);
    /**
     * @author xucaikai
     * 统计印章类型的数量
     * @param stamp
     * @return
     */
    List<Integer> countStampFromStampType(@Param(value="stamp")Stamp stamp,
                                           @Param(value="list")List<Dict> list,
                                           @Param(value="size") int size);


    /**
     * @author xucaikai
     * (公安)统计今天、本周、本月、今年中备案、已制作、已交付印章的数量
     * @param stamp
     * @return
     */
    int stampStateCountFromPolice(@Param(value = "stamp") Stamp stamp,
                                  @Param(value = "list") List<Company> list,
                                  @Param(value = "size") int size);
    /**
     * @author xucaikai
     * (公安)统计辖区备案企业总数
     * @param stamp
     * @return
     */
    int UseComCountFromPolice(Stamp stamp);
    /**
     * @author xucaikai
     * 统计辖区已备案总数
     * @param stamp
     * @return
     */
    int getTotalApplyCount(Stamp stamp);

    /**
     * @author xucaikai
     * 统计辖区已制作、已交付总数
     * @param stamp
     * @return
     */
    int getTotalCountForPolice(@Param(value = "stamp") Stamp stamp,
                               @Param(value = "list") List<Company> list,
                               @Param(value = "size") int size);

    List<PoliceCountDTO> getTotalCountForPolice (@Param(value = "stamp") Stamp stamp);

    /**
     * @author xucaikai
     * 辖区备案企业总数
     * @param stamp
     * @return
     */
    int getTotalUseComCount(Stamp stamp);

    /**
     * @author xucaikai
     * 每年中每个月的印章数量统计
     * @param stamp
     * @return
     */

    List<Object> stampPerMonthForYearPolice(@Param(value = "stamp") Stamp stamp,
                                            @Param(value = "list")List<Company> list,
                                            @Param(value = "size") int size);
    List<Object> stampPerMonthForYearMakeCom(Stamp stamp);
    /**
     * @author xucaikai
     * 统计该区域下刻章点所刻制的印章数量
     * @param stamp
     * @return
     */

    List<Integer> makeComStampDataSources(@Param(value = "stamp") Stamp stamp,
                                           @Param(value = "list")List<Company> list,
                                           @Param(value = "size") int size);

    /**
     * @author xucaikai
     * 区域印章数量统计
     * @param stamp
     * @return
     */

    List<PoliceCountDTO> stampAreaDataBySource();

    /**
     * 以下是刻章点的统计函数
    * */

    /**
     * @author xucaikai
     * 申请印章总数
     * @param stamp
     * @return
     */
    int getUseComCount(Stamp stamp);
    /**
     * @author xucaikai
     * 备案、已制作、已交付印章的总数
     * @param stamp
     * @return
     */
    int getTotalCountForState(Stamp stamp);


    /**
     * @author xucaikai
     * 统计今天、本周、本月、今年中备案、已制作、已交付印章的数量
     * @param stamp
     * @return
     */
    int stampStateCount(Stamp stamp);
    /**
     * @author xucaikai
     * 刻章点统计统计今天、本周、本月、今年中备案企业总数
     * @param stamp
     * @return
     */
    int UseComCountForDate(Stamp stamp);


    /**
     * 印章交付
     * @param stamp
     */
    public void deliveryStamp(Stamp stamp);


    //公安业务

    /**
     * @param [police]
     * @return java.util.List<com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp>
     * @author hjw
     * @description 根据当前公安区域查询印章
     * @date 2017/6/2
     */
    public List<Stamp> findListByPoliceArea(Stamp stamp);

    /**
     * @author 许彩开
     * @TODO (注：刻章点查询印章)
      * @param stamp
     * @DATE: 2017\12\22 0022 11:00
     */

    public  List<Stamp>  findListByPoliceArea2(Stamp stamp);


    /**
     * @author 许彩开
     * @TODO (注：此查询通过lastRecord.companyName查询 )
      * @param stamp
     * @DATE: 2018\7\6 0006 11:28
     */

    public  List<Stamp>  findListByPoliceArea3(Stamp stamp);

    /*
     *@author hjw
     *@description  查询stamp附带company信息 ， 用于公安
     *@param [stamp]
     *@return com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp
     *@date 2017/6/5
     */
    public Stamp getWithCom(Stamp stamp);


    /**
     * 检查该公司下 是否有这个印章
     *
     * @param stamp
     * @return
     */
    public int checkStampExist(Stamp stamp);


    /**
     * @author 许彩开
     * @TODO (注：更新印章临时使用状态标志)
      * @param stamp
     * @DATE: 2017\12\25 0025 15:50
     */

    public int updateStateFlag(Stamp stamp);

    /**
     * 通过 公司id 印章名称 形式 获取印章
     *
     * @param stamp
     * @return
     */
    public Stamp getStampByShapesAndName(Stamp stamp);


    public String getMoulageIdByStampId(Stamp stamp);

    /**
     * 补刻操作-更新老印章的状态
     *
     * @param stamp
     * @return
     */
    public int changeOldStampState(Stamp stamp);

    public int updateRepairStamp(Stamp stamp);


    /**
     * 统计该地区用章公司的物理印章总数
     *
     * @param companys
     * @return
     */
    public int countStampByAreaUseCompany(@Param(value = "list") List<Company> companys, @Param(value = "size") int size);


    /**
     * 只针对物理印章
     *
     * @param stampCode
     * @return
     */
    public int checkStampCodeExist(String stampCode);


    /**
     * 更新印章编码
     *
     * @param stamp
     * @return
     */
    public int updateStampCode(Stamp stamp);

    /**
     * 更新回执编码
     * @param stamp
     * @return
     */
    public int updateReturnCode(Stamp stamp);

    /**
     *
     * 刻章点 通过其公司和用章单位公司查找对应的印章
     *
     * @param stamp
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Stamp> findStampByMakeAndUseCompany(@Param(value = "stamp") Stamp stamp,
                                                    @Param(value = "startDate") Date startDate,
                                                    @Param(value = "endDate") Date endDate);


    public List<Stamp> findStampByMakeCompany(@Param(value = "stamp") Stamp stamp,
                                              @Param(value = "startDate") Date startDate,
                                              @Param(value = "endDate") Date endDate);

    public int countStampNumber(@Param(value = "stamp") Stamp stamp,
                                @Param(value = "startDate") Date startDate,
                                @Param(value = "endDate") Date endDate);


    public List<Stamp> findListByMoreCondition(Stamp stamp);

    public int updateEleModel(Stamp stamp);

    public List<Stamp> countStampList(Stamp stamp);

    public int countStamp(Stamp stamp);
    public List<Map<String,String>> countStamp2(Stamp stamp);


    public List<Stamp> policeCountStampList(Stamp stamp);

    public List<Stamp> policeCountStampList2(Stamp stamp);

    public List<Stamp> policeCountPhyStampList(Stamp stamp);
    public List<Stamp> policeCountEleStampList(Stamp stamp);

    public int saveWaterImage(Stamp stamp);

    public int checkMakeStampCount(Stamp stamp);

    public int checkStampTypeCount(Stamp stamp);

    public List<Stamp> checkSTC1(Stamp stamp);

    public int checkSTC2(Stamp stamp);

    public int checkStampType01isOnly(Stamp stamp);

    public Stamp findEngStampFromRecordId(Stamp stamp);

    public Stamp findEleStampFromRecordId(Stamp stamp);


    /**
     * 查询对应的用章单位Id
     * @param stamp
     * @return
     */
    public List getUseComId(Stamp stamp);

    /**
     * 查询刻章点对应的印模
     */
    public StampMakeComp getMakeCompEleModel(String makeCompId);

    public List<Stamp> findListByCondition(Stamp stamp);

    public List<Stamp> findListByConditionDeveryList(Stamp stamp);

    /**
     * 功能描述:查询当前公司名下可用的印章列表
     * @param:
     * @return:
     * @auther: linzhibao
     * @date: 2018-08-28 15:59
     */
    public List<Stamp> findStampListByCompanyInfo(Stamp stamp);


}
