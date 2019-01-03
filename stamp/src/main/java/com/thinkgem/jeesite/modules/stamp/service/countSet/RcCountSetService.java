package com.thinkgem.jeesite.modules.stamp.service.countSet;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.dao.CountSet.CountSetDao;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import com.thinkgem.jeesite.modules.stamp.exception.countSet.CountSetCountException;
import com.thinkgem.jeesite.modules.stamp.exception.countSet.CountSetException;
import com.thinkgem.jeesite.modules.stamp.exception.countSet.CountSetSaveException;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *
 * 润城经销商 生产数量管控
 *
 * Created by Administrator on 2017/8/17.
 */
@Service
@Transactional(readOnly = true)
public class RcCountSetService extends BaseCountSetService {


    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CountSetDao countSetDao;

    @Autowired
    private AreaDao areaDao;

    public Page<Company> findRcCountSetPage(Page<Company> page, Company company) {

        company.setCompType(CompanyType.AGENCY);

        company.setPage(page);


        List<Company> companys = null;

        if (company.getArea() == null || company.getArea().getId().equals("")) {

            List<Area> subAreas = areaDao.findAreasByParentArea(new Area("1"));//搜索区域中国下的省

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(subAreas));

            companys = companyDao.findCompanyListByAreas(company);


            page.setList(companys);

        } else {

            companys = companyDao.findList2(company);

            page.setList(companys);
        }

        for(Company agencyCompany:companys){

            findCompanyCountSet(agencyCompany);

        }

        return page;

    }

    public Company getCompanyInfo(Company company) {
        return companyDao.get(company);
    }



    @Transactional(readOnly = false)
    public Condition save(Company company,CountSet countSet) throws
            CountSetSaveException, CountSetCountException, CountSetException {

        try {

            Condition condition = new Condition();

            //save
            if (countSet.getId()==null|| "".equals(countSet.getId())) {

                countSet.setId(IdGen.uuid());
                countSet.setCompany(company);
                countSet.setArea(company.getArea());
                countSet.setCreateBy(UserUtils.getUser());
                countSet.setUpdateBy(UserUtils.getUser());
                countSet.setUpdateDate(new Date());
                countSet.setCreateDate(new Date());
                if (countSetDao.insert(countSet) == 0) {

                    throw new CountSetSaveException(countSet.getStampShape().getValue()+"数量控制保存失败!");
                }

            } else {
                //update

                CountSet oldCountSet = countSetDao.get(countSet);

                countSet.setUpdateBy(UserUtils.getUser());

                countSet.setUpdateDate(new Date());

                if (countSetDao.update(countSet) == 0) {

                    throw new CountSetSaveException(countSet.getStampShape().getValue()+"数量控制保存失败!");
                }


            }

            return new Condition(Condition.SUCCESS_CODE, "保存"+countSet.getStampShape().getValue()+"数量控制成功!");

        } catch (CountSetSaveException e) {

            e.printStackTrace();

            throw e;

        } catch (CountSetCountException e) {

            e.printStackTrace();

            throw e;

        } catch (CountSetException e) {

            e.printStackTrace();

            throw e;

        } catch (Exception e) {

            e.printStackTrace();

            throw new CountSetException("error!");

        }


    }

}
