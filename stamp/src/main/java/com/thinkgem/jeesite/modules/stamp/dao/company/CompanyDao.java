/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.dao.company;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.dto.count.CompanyCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 公司DAO接口
 * @author Locker
 * @version 2017-05-13
 */
@MyBatisDao
public interface CompanyDao extends CrudDao<Company> {

//    /*
//     *@author hjw
//     *@description 查询制章公司list并附带licenselist
//     *@param [company]
//     *@return java.util.List<com.thinkgem.jeesite.modules.stamp.units.entity.company.Company>
//     *@date 2017/5/20
//     */
//	public List<Company> findListWithLicenseList(Company company);
    /**
     *
     * 更新企业运行状态
     * @param company
     */
    public void updateComDateState(Company company);

/**
 * @author 许彩开
 * @TODO (注：通过ID查找公司)
  *@param company
 * @DATE: 2018\3\1 0001 15:18
 */

     public Company getCompanyById(Company company);
    /**
     *
     * 更新企业运行状态
     * @param company
     */
    public void updateComState(Company company);

/**
 * @author 许彩开
 * @TODO (注：查询详情)
  * @param company
 * @DATE: 2018\1\2 0002 17:12
 */

    public Company getDetails(Company company);


    /**
     *
     * 通过唯一码和企业名称检测公司是否存在
     * @param soleCode
     * @return
     */
    public int checkCompanyBysoleCodeAndCompName(@Param(value = "soleCode") String soleCode,
                             @Param(value = "compName")String compName,
                             @Param(value="compType")CompanyType companyType);

    /**
     * 通过统一码和企业名称查找公司
     *
     * @param soleCode
     * @return
     */
    public Company getCompanyBysoleCodeAndCompName(@Param(value = "soleCode")String soleCode,
                                        @Param(value = "compName")String compName,
                                        @Param(value="compType")CompanyType companyType);

    /**
     * 通过统一码和企业名称查找公司
     *
     * @param compName
     * @return
     */
    public Company getCompanyByCompName(@Param(value = "compName")String compName,
                                                   @Param(value="compType")CompanyType companyType);


    //-------------------公安业务---------------------------------

    /*
     *@author hjw
     *@description  根据条件查询刻章点公司
     *              用于公安
     *@param [company]
     *@return java.util.List<com.thinkgem.jeesite.modules.stamp.entity.company.Company>
     *@date 2017/6/2
     */
    public List<Company> findMakeComList(Company company);

    /**
     * 通过
     * @param company
     * @return
     */
    public Company getByCompanyName(Company company);

    /**
     * 更改公司非重要信息
     * @param company
     */
    public void updateBaseInfo(Company company);

    /*
     *@author hjw
     *@description  公安同意变更申请
     *@param [company]
     *@return void
     *@date 2017/7/2
     */
    public void updateByLicense(Company company);

    /**
     * 通过区域检验该地区下的经销商
     * @param company
     */
    public int countAgencyByArea(Company company);

    /**
     *
     * @param company
     * @return
     */
    public List<Company> findCompanyListByAreas(Company company);


    public int systemOprationState(@Param(value="id") String companyId,
                                   @Param(value="compType") CompanyType companyType,
                                   @Param(value="oprState") OprationState oprationState);


    public int updateLastLicenseState(@Param(value = "company") Company company, @Param(value = "workType") WorkType workType);


    public List<Company> findCompanyByArea(Company company);

    //查询当前区域下某刻章点下的用章企业
    public List<Company> findUseCompByArea(CompanyCountDTO dto);

    /**
     * 更新公司信息-变更业务
     * @param company
     * @return
     */
    public int changeCompanyInfo(Company company);


    public List<Company> findList2(Company company);

    public Company getAgenyCompanyByArea(Company company);


    public List<Company> sysFindList(Company company);

    public List<Company> multipleConditionsFindList(Company company);

    public int editCompanyInfo(Company company);

    public List<Company> countCompanyPage(Company company);

    /**
     * 功能描述:根据公司id更新公司信息,刻章点用章企业政府单位皆可用, 只更新有值的数据,
     * 例如, company对象中只有公司名和id有值,只更新公司名,其他不更新
     * @param:
     * @return: 
     * @auther: linzhibao
     * @date: 2018-08-20 19:48
     */
    public int updateCompany(Company company);

    public int saveOldCompany(Company company);

    public List<Company> findCompanyInfoByIds(@Param("ids")String ids);

    /**
     * @description: 查询序列下一个值
     * @auther: bb
     * @date: 2018-09-10 16:55
     * @param: 
     * @return: 
     */
    public int getSequenceNextVal(@Param("seq_name") String seq_name,@Param("seq_type") String seq_type);


    /**
     * 功能描述: 查找符合条件的公司信息,包括用章企业和刻章点或经销商
     * @param:
     * @return:
     * @auther: linzhibao
     * @date: 2018-09-06 15:56
     */
    public Company findCompany(Company company);


}