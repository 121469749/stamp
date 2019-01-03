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
 * Created by Locker on 2017/8/18.
 */
@Service
@Transactional(readOnly = true)
public class DealerCountSetService extends BaseCountSetService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AreaDao areaDao;


    public Page<Company> findProvinceDealerPage(Company company, Page<Company> page) {

        company.setCompType(CompanyType.AGENCY);

        company.setPage(page);

        List<Company> companys = null;

        if (company.getArea() == null || company.getArea().getId().equals("")) {


            Area currentArea = UserUtils.getUser().getCompanyInfo().getArea();

            List<Area> subAreas = areaDao.findAreasByParentArea(currentArea);

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(subAreas));

            companys = companyDao.findCompanyListByAreas(company);

            page.setList(companys);

        } else {

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
    public CountSetDTO findProvinceLastCountSet(Company currentAgencyCompany) {

        findCompanyCountSet(currentAgencyCompany);

        CountSet currentPhyCountSet = currentAgencyCompany.getPhyCountSet();
        CountSet currentEleCountSet = currentAgencyCompany.getEleCountSet();
        //先检索该区域下的经销商区域
        List<Area> subAreas = areaDao.findAreasByParentArea(currentAgencyCompany.getArea());

        Company checkCompany = new Company();
        checkCompany.setCompType(CompanyType.AGENCY);
        checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(subAreas));

        //获得该区域下所有的经销商
        List<Company> companys = companyDao.findCompanyListByAreas(checkCompany);
        //电子印章和物理印章的剩余量
        int phyCount = currentPhyCountSet.getCount();
        int eleCount = currentEleCountSet.getCount();
        //找出 这些市经销商所有的印章生产控制

        return lastCountDTO(companys,eleCount,phyCount);
    }

//    /**
//     * 保存数量控制设定
//     * @param company
//     * @param countSet
//     * @param lastCountSet
//     * @return
//     * @throws CountSetSaveException
//     * @throws CountSetCountException
//     * @throws CountSetAbsenceException
//     * @throws CountSetException
//     */
//    @Transactional(readOnly = false)
//    public Condition save(Company company, CountSet countSet, CountSet lastCountSet) throws CountSetException {
//
//        try {
//
//            //save
//            if (countSet.getId() == null || "".equals(countSet.getId())) {
//
//                if (lastCountSet.getCount() < countSet.getCount()) {
//
//                    throw new CountSetAbsenceException(lastCountSet.getStampShape().getValue() + "余量不足!\n请重新正确的设定值!");
//                }
//
//                countSet.setId(IdGen.uuid());
//                countSet.setCompany(company);
//                countSet.setArea(company.getArea());
//                countSet.setCreateBy(UserUtils.getUser());
//                countSet.setUpdateBy(UserUtils.getUser());
//                countSet.setUpdateDate(new Date());
//                countSet.setCreateDate(new Date());
//
//                if (countSetDao.insert(countSet) == 0) {
//
//                    throw new CountSetSaveException(countSet.getStampShape().getValue() + "数量控制保存失败!");
//                }
//
//            } else {
//                //update
//                CountSet oldCountSet = countSetDao.get(countSet);
//                int newCountSet = countSet.getCount();
//
//
//                //如果在设定印章数则是在原有基础上增加
//                countSet.setCount(oldCountSet.getCount()+newCountSet);
//
//                if (countSet.getCount() > lastCountSet.getCount()) {
//
//                    throw new CountSetAbsenceException(lastCountSet.getStampShape().getValue() + "余量不足!\n请重新设定值!");
//
//                }
//
//                countSet.setUpdateBy(UserUtils.getUser());
//
//                countSet.setUpdateDate(new Date());
//
//                if (countSetDao.update(countSet) == 0) {
//
//                    throw new CountSetSaveException(countSet.getStampShape().getValue() + "数量控制保存失败!");
//                }
//
//            }
//
//            return new Condition(Condition.SUCCESS_CODE, "保存" + countSet.getStampShape().getValue() + "数量控制成功!");
//
//        } catch (CountSetSaveException e) {
//
//            e.printStackTrace();
//
//            throw e;
//
//        } catch (CountSetAbsenceException e) {
//
//            e.printStackTrace();
//
//            throw e;
//        } catch (CountSetCountException e) {
//
//            e.printStackTrace();
//
//            throw e;
//
//        } catch (CountSetException e) {
//
//            e.printStackTrace();
//
//            throw e;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            throw new CountSetException("error!");
//
//        }
//
//
//    }

   /**
     * @author 练浩文
     * @TODO (注：更新数据电子印章数量)
     * @param lastCountSet
     * @DATE: 2017/12/21 9:44
     */
    @Transactional(readOnly = false )
    public void updateEleStampCountByCompanyId( CountSetDTO lastCountSet) {
        countSetDao.updateEleStampCountByCompanyId(lastCountSet);
    }

    /**
     * @author 练浩文
     * @TODO (注：更新数据物理印章数量)
     * @param lastCountSet
     * @DATE: 2017/12/21 9:45
     */
    @Transactional(readOnly = false)
    public void updatePhyStampCountByCompanyId(CountSetDTO lastCountSet) {
        countSetDao.updatePhyStampCountByCompanyId(lastCountSet);
    }

    public CountSet getEleCountByCompany(Company currentCompany) {
        return countSetDao.getEleCountByCompany(currentCompany);
    }

    public CountSet getPhyCountByCompany(Company currentCompany) {
        return countSetDao.getPhyCountByCompany(currentCompany);
    }

    /**
     * @author 练浩文
     * @TODO (注：company中的印章数为分配的印章数，subCountSet中的印章数为被分配的下级中原有的印章数，parentCountSet为当前印章数)
     * @param company
     * @param subCountSet
     * @param parentCountSet
     * @DATE: 2017/12/22 9:18
     */
    @Transactional(readOnly = false)
    public Condition saveCount(Company company, CountSet subCountSet, CountSet parentCountSet) throws CountSetException {
        try {

            //save
            if (subCountSet.getId()==null || "null".equals(subCountSet.getId())) {
                if ("1".equals(parentCountSet.getStampShape().getKey())){
                    if (parentCountSet.getCount() < company.getPhyCountSet().getCount()) {
                        throw new CountSetAbsenceException(parentCountSet.getStampShape().getValue() + "余量不足!\n请重新正确的设定值!");
                    }
                    subCountSet.setCount(company.getEleCountSet().getCount());
                    subCountSet.setStampShape(parentCountSet.getStampShape());
                    parentCountSet.setCount(parentCountSet.getCount()-company.getPhyCountSet().getCount());
                }else{
                    if (parentCountSet.getCount() < company.getEleCountSet().getCount()){
                        throw new CountSetAbsenceException(parentCountSet.getStampShape().getValue() + "余量不足!\n请重新正确的设定值!");
                    }
                    subCountSet.setStampShape(parentCountSet.getStampShape());
                    subCountSet.setCount(company.getEleCountSet().getCount());
                    parentCountSet.setCount(parentCountSet.getCount()-company.getEleCountSet().getCount());
                }

                subCountSet.setId(IdGen.uuid());
                subCountSet.setCompany(company);
                subCountSet.setArea(company.getArea());
                subCountSet.setCreateBy(UserUtils.getUser());
                subCountSet.setUpdateBy(UserUtils.getUser());
                subCountSet.setUpdateDate(new Date());
                subCountSet.setCreateDate(new Date());

                if (countSetDao.insert(subCountSet) == 0 || countSetDao.update(parentCountSet)==0) {

                    throw new CountSetSaveException(subCountSet.getStampShape().getValue() + "数量控制保存失败!");
                }

            } else {
                //update
                //如果在设定印章数则是在原有基础上增加

                if ("1".equals(parentCountSet.getStampShape().getKey())){
                    if (parentCountSet.getCount() < company.getPhyCountSet().getCount()) {
                        throw new CountSetAbsenceException(parentCountSet.getStampShape().getValue() + "余量不足!\n请重新正确的设定值!");
                    }
                    subCountSet.setStampShape(parentCountSet.getStampShape());
                    subCountSet.setCount(company.getPhyCountSet().getCount()+subCountSet.getCount());
                    parentCountSet.setCount(parentCountSet.getCount()-company.getPhyCountSet().getCount());
                }else{
                    if (parentCountSet.getCount() < company.getEleCountSet().getCount()){
                        throw new CountSetAbsenceException(parentCountSet.getStampShape().getValue() + "余量不足!\n请重新正确的设定值!");
                    }
                    subCountSet.setStampShape(parentCountSet.getStampShape());
                    subCountSet.setCount(company.getEleCountSet().getCount()+subCountSet.getCount());
                    parentCountSet.setCount(parentCountSet.getCount()-company.getEleCountSet().getCount());
                }

                subCountSet.setUpdateBy(UserUtils.getUser());

                subCountSet.setUpdateDate(new Date());

                if (countSetDao.update(subCountSet) == 0||countSetDao.update(parentCountSet) == 0) {

                    throw new CountSetSaveException(subCountSet.getStampShape().getValue() + "数量控制保存失败!");
                }

            }

            return new Condition(Condition.SUCCESS_CODE, "保存" + subCountSet.getStampShape().getValue() + "数量控制成功!");

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

    /**
     * @author 练浩文
     * @TODO (注：润成后台进行分配,润成分配的印章数不进行记录)
     * @param company
     * @param subCountSet
     * @param parentCountSet
     * @DATE: 2017/12/22 9:18
     */
    @Transactional(readOnly = false)
    public Condition rcSaveCount(Company company, CountSet subCountSet) throws CountSetException {
        try {

            //save
            if (subCountSet.getId()==null || "null".equals(subCountSet.getId())) {
                if ("1".equals(subCountSet.getStampShape().getKey())){
                    subCountSet.setCount(company.getEleCountSet().getCount());
                    subCountSet.setStampShape(subCountSet.getStampShape());
                }else{
                    subCountSet.setStampShape(subCountSet.getStampShape());
                    subCountSet.setCount(company.getEleCountSet().getCount());
                }

                subCountSet.setId(IdGen.uuid());
                subCountSet.setCompany(company);
                subCountSet.setArea(company.getArea());
                subCountSet.setCreateBy(UserUtils.getUser());
                subCountSet.setUpdateBy(UserUtils.getUser());
                subCountSet.setUpdateDate(new Date());
                subCountSet.setCreateDate(new Date());

                if (countSetDao.insert(subCountSet) == 0) {

                    throw new CountSetSaveException(subCountSet.getStampShape().getValue() + "数量控制保存失败!");
                }

            } else {
                //update
                //如果在设定印章数则是在原有基础上增加


                if ("1".equals(subCountSet.getStampShape().getKey())){
                    subCountSet.setStampShape(subCountSet.getStampShape());
                    subCountSet.setCount(company.getPhyCountSet().getCount()+subCountSet.getCount());
                }else{
                    subCountSet.setStampShape(subCountSet.getStampShape());
                    subCountSet.setCount(company.getEleCountSet().getCount()+subCountSet.getCount());

                }

                subCountSet.setUpdateBy(UserUtils.getUser());

                subCountSet.setUpdateDate(new Date());

                if (countSetDao.update(subCountSet) == 0) {
                    throw new CountSetSaveException(subCountSet.getStampShape().getValue() + "数量控制保存失败!");
                }

            }

            return new Condition(Condition.SUCCESS_CODE, "保存" + subCountSet.getStampShape().getValue() + "数量控制成功!");

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
