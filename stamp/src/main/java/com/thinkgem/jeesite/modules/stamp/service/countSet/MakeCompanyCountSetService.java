package com.thinkgem.jeesite.modules.stamp.service.countSet;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dto.countSet.CountSetDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import com.thinkgem.jeesite.modules.stamp.exception.countSet.CountSetAbsenceException;
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
 * Created by Administrator on 2017/8/18.
 */
@Service
@Transactional(readOnly = true)
public class MakeCompanyCountSetService extends BaseCountSetService{

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AreaDao areaDao;

    /**
     *
     * 查询刻章单位
     *
     * @param company
     * @param page
     * @return
     */
    public Page<Company> findMakeCompanyPage(Company company,Page<Company> page){
        company.setCompType(CompanyType.MAKE);

        company.setPage(page);

        List<Company> companys = null;


        if(company.getArea()==null||"".equals(company.getArea().getId())){

            Area area = UserUtils.getUser().getCompanyInfo().getArea();

            List<Area> subAreas =  areaDao.findAreasByParentArea(area);

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(subAreas));

            companys = companyDao.findCompanyListByAreas(company);

            page.setList(companys);

        }else{

            companys = companyDao.findList2(company);

            page.setList(companys);

        }

        for (Company agencyCompany : companys) {

            findCompanyCountSet(agencyCompany);

        }

        return page;
    }

    /**
     * 查找当前省经销商剩余的印章可控制数量
     *
     * @param currentAgencyCompany
     * @return
     */
    public CountSetDTO findCityLastCountSet(Company currentAgencyCompany) {

        findCompanyCountSet(currentAgencyCompany);

        CountSet currentPhyCountSet = currentAgencyCompany.getPhyCountSet();
        CountSet currentEleCountSet = currentAgencyCompany.getEleCountSet();
        //先检索该区域下的 所有子区域
        List<Area> subAreas = areaDao.findAreasByParentArea(currentAgencyCompany.getArea());

        Company checkCompany = new Company();
        checkCompany.setCompType(CompanyType.MAKE);
        checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(subAreas));

        //获得该区域下所有的刻章点
        List<Company> companys = companyDao.findCompanyListByAreas(checkCompany);
        //电子印章和物理印章的剩余量
        int phyCount;
        int eleCount;
        if (currentPhyCountSet == null){
            phyCount = 0;

        }else {
            phyCount = currentPhyCountSet.getCount();
        }
        if (currentEleCountSet == null){
//            System.out.println("+++++++++++++++"+currentEleCountSet);
           eleCount = 0;
//            System.out.println("输出eleCount---------"+eleCount);
        }else {
           eleCount = currentEleCountSet.getCount();
        }

        //找出 这些市经销商所有的印章生产控制

        return lastCountDTO(companys,eleCount,phyCount);
    }


    @Transactional(readOnly = false)
    public Condition save(Company company, CountSet countSet, CountSet lastCountSet) throws
            CountSetSaveException, CountSetCountException, CountSetAbsenceException, CountSetException {

        try {

            //save
            if (countSet.getId() == null || "".equals(countSet.getId())) {

                if (lastCountSet.getCount() < countSet.getCount()) {

                    throw new CountSetAbsenceException(lastCountSet.getStampShape().getValue() + "余量不足!\n请重新正确的设定值!");
                }

                countSet.setId(IdGen.uuid());
                countSet.setCompany(company);
                countSet.setCreateBy(UserUtils.getUser());
                countSet.setUpdateBy(UserUtils.getUser());
                countSet.setUpdateDate(new Date());
                countSet.setCreateDate(new Date());

                if (countSetDao.insert(countSet) == 0) {

                    throw new CountSetSaveException(countSet.getStampShape().getValue() + "数量控制保存失败!");
                }

            } else {
                //update

                CountSet oldCountSet = countSetDao.get(countSet);

                if (oldCountSet.getCount() > countSet.getCount()) {

                    throw new CountSetCountException(countSet.getStampShape().getValue() + "数量控制更改不能比原有设定值" + oldCountSet.getCount() + "低!");
                }

                if ((countSet.getCount()-oldCountSet.getCount()) > lastCountSet.getCount()) {

                    throw new CountSetAbsenceException(lastCountSet.getStampShape().getValue() + "余量不足!\n请重新设定值!");

                }

                countSet.setUpdateBy(UserUtils.getUser());

                countSet.setUpdateDate(new Date());

                if (countSetDao.update(countSet) == 0) {

                    throw new CountSetSaveException(countSet.getStampShape().getValue() + "数量控制保存失败!");
                }

            }

            return new Condition(Condition.SUCCESS_CODE, "保存" + countSet.getStampShape().getValue() + "数量控制成功!");

        } catch (CountSetSaveException e) {

            e.printStackTrace();

            throw e;

        } catch (CountSetAbsenceException e) {

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
