/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.service.company;

import java.util.List;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;

/**
 * 公司Service
 *
 * @author Locker
 * @version 2017-05-13
 */
@Service
@Transactional(readOnly = true)
public class CompanyService extends CrudService<CompanyDao, Company> {

    public Company get(String id) {
        return super.get(id);
    }

    public List<Company> findList(Company company) {
        return super.findList(company);
    }

    public Page<Company> findPage(Page<Company> page, Company company) {
        return super.findPage(page, company);
    }

    @Transactional(readOnly = false)
    public void save(Company company) {
        super.save(company);
    }

    @Transactional(readOnly = false)
    public void delete(Company company) {
        super.delete(company);
    }


    /**
     * 更改非重要信息
     *
     * @param company
     */
    @Transactional(readOnly = false)
    public void updateBaseInfo(Company company) {
        dao.updateBaseInfo(company);
    }

    /**
     * @param company
     * @author 许彩开
     * @TODO (注：获取公司信息)
     * @DATE: 2018\1\2 0002 17:38
     */

    @Transactional(readOnly = false)
    public Company getCom(Company company) {
        return dao.getDetails(company);
    }

    @Transactional(readOnly = false)
    public int updateCompany(Company company) {
        return dao.updateCompany(company);
    }

    @Transactional(readOnly = false)
    public int saveOldCompany(Company company) {
        return dao.saveOldCompany(company);
    }

    public int checkCompanyBysoleCodeAndCompName(String soleCode, String compName,CompanyType companyType) {
        return dao.checkCompanyBysoleCodeAndCompName(soleCode,compName,companyType);
    }
}