package com.thinkgem.jeesite.modules.stamp.service.money;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.moneySetting.MoneySettingDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
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
public class DealerMoneySettingService {


    @Autowired
    private AreaDao areaDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private MoneySettingDao moneySettingDao;

    @Transactional(readOnly = true)
    public Page<Company> findCompanyPageByProvince(Company company,Page<Company> page){

        if(company.getArea()==null || "".equals(company.getArea().getId())){

            Area currentArea = UserUtils.getUser().getCompanyInfo().getArea();

            company.setArea(currentArea);

            List<Area> provinceSubAreas  = areaDao.getSubAreaByPareanId(company.getArea().getId());

            company.setPage(page);

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(provinceSubAreas));

            List<Company> companies = companyDao.findCompanyListByAreas(company);

            page.setList(companies);

        }else{

            Area currentArea = UserUtils.getUser().getCompanyInfo().getArea();

            company.setArea(currentArea);

            company.setPage(page);

            List<Company> companies = companyDao.findList2(company);

            page.setList(companies);

        }

        return page;
    }

    @Transactional(readOnly = true)
    public Page<Area> findAreasByParentId(Area area,Page<Area> page){

        area.setPage(page);

        List<Area> areas = areaDao.findSubAreasByParentId(area);

        page.setList(areas);

        return page;
    }


}
