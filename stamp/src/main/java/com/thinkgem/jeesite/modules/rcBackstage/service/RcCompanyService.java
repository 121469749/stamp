package com.thinkgem.jeesite.modules.rcBackstage.service;


import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 *
 *
 * Created by Administrator on 2017/7/29.
 */
@Service
public class RcCompanyService {


    @Autowired
    private CompanyDao companyDao;


    @Transactional(readOnly = true)
    public Page<Company> companyPage(Page<Company> page, Company company) {

        company.setPage(page);

        List<Company> list = companyDao.sysFindList(company);

        page.setList(list);

        return page;
    }

    /**
     * 功能描述:传入多个id,多个之间逗号隔开
     * @param: [ids]
     * @return: java.util.List<com.thinkgem.jeesite.modules.stamp.entity.company.Company>
     * @auther: linzhibao
     * @date: 2018-08-28 14:42
     */
    public List<Company> findCompanyInfoByIds(String ids){

        return  companyDao.findCompanyInfoByIds(ids);
    }

}
