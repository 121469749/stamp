package com.thinkgem.jeesite.modules.stamp.service.money;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.moneySetting.MoneySettingDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */
@Service
public class RunchengMoneySettingService {


    @Autowired
    private CompanyDao companyDao;

    @Transactional(readOnly = true)
    public Page<Company> findCompanyPage(Company company,Page<Company> page){

        company.setCompType(CompanyType.AGENCY);

        company.setPage(page);

        List<Company> companies = companyDao.findList2(company);

        page.setList(companies);

        return page;
    }


}
