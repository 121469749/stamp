package com.thinkgem.jeesite.modules.stamp.service.count;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampWorkType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampRecordDao;
import com.thinkgem.jeesite.modules.stamp.dto.count.DealerCompanyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.money.MakeCompanyMoneyCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.CompanyCountExcelDo;
import com.thinkgem.jeesite.modules.stamp.vo.count.CompanyCountVO;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 *  陈旧的统计业务，可以无视
 * Created by Locker on 2017/7/26.
 */
@Service
@Transactional(readOnly = true)
public class DealerCompanyCountService {

    @Autowired
    private StampRecordDao stampRecordDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private StampDao stampDao;

    @Autowired
    private AreaDao areaDao;

    /**
     * 市经销商->刻章点的简单统计
     *
     * @param page
     * @param makeCompanyMoneyCountDTO
     */
    public Page<Company> cityDealerCompanyCountPage(Page<Company> page, MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO) {

        Company checkMakeCompany = makeCompanyMoneyCountDTO.getCompany();

        Date startDate = makeCompanyMoneyCountDTO.getStartDate();

        Date endDate = makeCompanyMoneyCountDTO.getEndDate();

        checkMakeCompany.setCompType(CompanyType.MAKE);

        //如果查询区域为空 那么就用当前的经销商区域
        Company currentDealerCompany = UserUtils.getUser().getCompanyInfo();

        Area currentArea = currentDealerCompany.getArea();
        //获取当前的要查询的地区
        Area checkAera = checkMakeCompany.getArea();

        checkMakeCompany.setPage(page);

        if (checkAera == null || "".equals(checkAera.getId()) || currentArea.getId().equals(checkAera.getId())) {

            checkAera = currentDealerCompany.getArea();

            //当前市下的所有区
            List<Area> districts = areaDao.findAreasByParentArea(checkAera);

            checkMakeCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(districts));

            //根据查询条件和区域找出相应的MakeCompany
            List<Company> makeCompanyList = companyDao.findCompanyListByAreas(checkMakeCompany);

            for (Company makeCompany : makeCompanyList) {

                countCityDealerCompany(makeCompany, startDate, endDate);

            }

            page.setList(makeCompanyList);

        } else {

            checkMakeCompany.setArea(checkAera);

            List<Company> makeCompanyList = companyDao.findCompanyByArea(checkMakeCompany);

            for (Company makeCompany : makeCompanyList) {

                countCityDealerCompany(makeCompany, startDate, endDate);
            }

            page.setList(makeCompanyList);
        }

        return page;
    }

    @Transactional(readOnly = true)
    public List<CompanyCountExcelDo> cityDealerExcel(Page<Company> page, MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO) {

        Company checkMakeCompany = makeCompanyMoneyCountDTO.getCompany();

        Date startDate = makeCompanyMoneyCountDTO.getStartDate();

        Date endDate = makeCompanyMoneyCountDTO.getEndDate();

        checkMakeCompany.setCompType(CompanyType.MAKE);

        //如果查询区域为空 那么就用当前的经销商区域
        Company currentDealerCompany = UserUtils.getUser().getCompanyInfo();

        Area currentArea = currentDealerCompany.getArea();
        //获取当前的要查询的地区
        Area checkAera = checkMakeCompany.getArea();

        checkMakeCompany.setPage(page);

        List<CompanyCountExcelDo> list = new ArrayList<CompanyCountExcelDo>();

        if (checkAera == null || "".equals(checkAera.getId()) || currentArea.getId().equals(checkAera.getId())) {

            checkAera = currentDealerCompany.getArea();

            //当前市下的所有区
            List<Area> districts = areaDao.findAreasByParentArea(checkAera);

            checkAera.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(districts));

            checkMakeCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(districts));

            //根据查询条件和区域找出相应的MakeCompany
            List<Company> makeCompanyList = companyDao.findCompanyListByAreas(checkMakeCompany);

            for (Company makeCompany : makeCompanyList) {

                countCityDealerCompany(makeCompany, startDate, endDate);

                CompanyCountExcelDo excelDo = excelExportDeal(makeCompany);
                list.add(excelDo);
            }


        } else {

            checkMakeCompany.setArea(checkAera);

            List<Company> makeCompanyList = companyDao.findCompanyByArea(checkMakeCompany);

            for (Company makeCompany : makeCompanyList) {

                countCityDealerCompany(makeCompany, startDate, endDate);

                CompanyCountExcelDo excelDo = excelExportDeal(makeCompany);
                list.add(excelDo);
            }

        }

        return list;
    }

    @Transactional(readOnly = true)
    public Page<Company> provinceDealerCompanyCountPage(Page<Company> page, DealerCompanyCountDTO dto) {

        Area checkArea = dto.getArea();

        Date startDate = dto.getStartDate();

        Date endDate = dto.getEndDate();

        Area currentArea = UserUtils.getUser().getCompanyInfo().getArea();

        //当前区域---省
        if (checkArea == null || "".equals(checkArea.getId()) || currentArea.getId().equals(checkArea.getId())) {
            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            //当前省所有市区域
            List<Area> citys = areaDao.findAreasByParentArea(currentArea);

            company.setPage(page);
            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(citys));
            //找出了当前省的市经销商
            List<Company> cityDealerCompanys = companyDao.findCompanyListByAreas(company);

            countProvinceDealerCompany(cityDealerCompanys, startDate, endDate);

            page.setList(cityDealerCompanys);
        } else {

            // checkArea is city
            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            company.setArea(checkArea);

            company.setPage(page);

            List<Company> cityDealerCompanys = companyDao.findCompanyByArea(company);

            page.setList(cityDealerCompanys);
        }

        return page;
    }

    @Transactional(readOnly = true)
    public List<CompanyCountExcelDo> provinceDealerExcel(Page<Company> page, DealerCompanyCountDTO dto) {

        Area checkArea = dto.getArea();

        Date startDate = dto.getStartDate();

        Date endDate = dto.getEndDate();

        Area currentArea = UserUtils.getUser().getCompanyInfo().getArea();

        List<CompanyCountExcelDo> list = new ArrayList<CompanyCountExcelDo>();

        //当前区域---省
        if (checkArea == null || "".equals(checkArea.getId()) || currentArea.getId().equals(checkArea.getId())) {
            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            //当前省所有市区域
            List<Area> citys = areaDao.findAreasByParentArea(currentArea);

            company.setPage(page);

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(citys));
            //找出了当前省的市经销商
            List<Company> cityDealerCompanys = companyDao.findCompanyListByAreas(company);

            countProvinceDealerCompany(cityDealerCompanys, startDate, endDate);

            for (Company dealerCompangy : cityDealerCompanys) {

                CompanyCountExcelDo excelDo = excelExportDeal(dealerCompangy);
                list.add(excelDo);
            }

        } else {

            // checkArea is city
            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            company.setArea(checkArea);

            company.setPage(page);

            List<Company> cityDealerCompanys = companyDao.findCompanyByArea(company);

            for (Company dealerCompangy : cityDealerCompanys) {

                CompanyCountExcelDo excelDo = excelExportDeal(dealerCompangy);
                list.add(excelDo);
            }
        }

        return list;
    }

    @Transactional(readOnly = true)
    public Page<Company> rcDealerCompanyCountPage(Page<Company> page, DealerCompanyCountDTO dto) {

        Area checkArea = dto.getArea();

        Date startDate = dto.getStartDate();

        Date endDate = dto.getEndDate();

        //因为是润城经销商，润城=中国
        Area currentArea = new Area("1");

        if (checkArea == null || "".equals(checkArea.getId()) || currentArea.getId().equals(checkArea.getId())) {

            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            //各个省
            List<Area> provinces = areaDao.findAreasByParentArea(currentArea);

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(provinces));
            //找出了所有省经销商
            List<Company> provinceDealerCompanys = companyDao.findCompanyListByAreas(company);

            countRcCompanyStamp(provinceDealerCompanys, startDate, endDate);

            page.setList(provinceDealerCompanys);

        } else {

            //说明当前区域等级是省

            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            company.setArea(checkArea);

            company.setPage(page);
            //当前查询 省的经销商
            List<Company> provinceDealerCompanys = companyDao.findCompanyByArea(company);

            countRcCompanyStamp(provinceDealerCompanys, startDate, endDate);

            page.setList(provinceDealerCompanys);

        }
        return page;
    }

    @Transactional(readOnly = true)
    public List<CompanyCountExcelDo> rcDealerExcel(Page<Company> page, DealerCompanyCountDTO dto) {


        Area checkArea = dto.getArea();

        Date startDate = dto.getStartDate();

        Date endDate = dto.getEndDate();

        //因为是润城经销商，润城=中国
        Area currentArea = new Area("1");

        List<CompanyCountExcelDo> list = new ArrayList<CompanyCountExcelDo>();

        if (checkArea == null || "".equals(checkArea.getId()) || currentArea.getId().equals(checkArea.getId())) {

            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            //各个省
            List<Area> provinces = areaDao.findAreasByParentArea(currentArea);

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(provinces));

            //找出了所有省经销商
            List<Company> provinceDealerCompanys = companyDao.findCompanyListByAreas(company);

            countRcCompanyStamp(provinceDealerCompanys, startDate, endDate);

            for (Company dealerCompangy : provinceDealerCompanys) {

                CompanyCountExcelDo excelDo = excelExportDeal(dealerCompangy);
                list.add(excelDo);
            }

        } else {

            //说明当前区域等级是省

            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            company.setArea(checkArea);

            company.setPage(page);
            //当前查询 省的经销商
            List<Company> provinceDealerCompanys = companyDao.findCompanyByArea(company);

            countRcCompanyStamp(provinceDealerCompanys, startDate, endDate);

            for (Company dealerCompangy : provinceDealerCompanys) {

                CompanyCountExcelDo excelDo = excelExportDeal(dealerCompangy);

                list.add(excelDo);
            }

        }

        return list;

    }

    protected CompanyCountExcelDo excelExportDeal(Company company) {
        CompanyCountExcelDo excelDo = new CompanyCountExcelDo();

        excelDo.setStampRecordCount(company.getCompanyCountVO().getStampRecordCount());
        excelDo.setPhyStampCount(company.getCompanyCountVO().getPhyStampCount());
        excelDo.setEleStampCount(company.getCompanyCountVO().getEleStampCount());
        excelDo.setLogoutStampCount(company.getCompanyCountVO().getLogoutStampCount());
        excelDo.setReportStampCount(company.getCompanyCountVO().getReportStampCount());

        excelDo.setCompAddress(company.getCompAddress());
        excelDo.setCompPhone(company.getCompPhone());
        excelDo.setArea(company.getArea());
        excelDo.setLegalName(company.getLegalName());
        excelDo.setLegalPhone(company.getLegalPhone());
        excelDo.setSoleCode(company.getSoleCode());
        excelDo.setCompanyType(company.getCompType());
        excelDo.setCompanyName(company.getCompanyName());

        return excelDo;
    }

    protected void countCityDealerCompany(Company makeCompany, Date startDate, Date endDate) {

        CompanyCountVO companyCountVO = new CompanyCountVO();

        StampRecord stampRecordCheck = new StampRecord();

        stampRecordCheck.setWorkType(StampWorkType.APPLY);

        stampRecordCheck.setMakeComp(makeCompany);

        // 统计项目
        //1、印章备案数目
        //五个类型
        //1、apply
        int applyStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

        // 2、Logout
        stampRecordCheck.setWorkType(StampWorkType.LOGOUT);
        int logoutStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

        // 3、Report
        stampRecordCheck.setWorkType(StampWorkType.REPORT);
        int reportStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

        // 4、Repair
        stampRecordCheck.setWorkType(StampWorkType.REPAIR);
        int repairStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

        // 5、Change
        stampRecordCheck.setWorkType(StampWorkType.CHANGE);
        int changeStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

        //总数
        int stampRecordCount = applyStampRecordCount + logoutStampRecordCount
                + reportStampRecordCount + repairStampRecordCount + changeStampRecordCount;

        companyCountVO.setStampRecordCount(stampRecordCount);

        //2、电子印章的刻制数量
        Stamp stamp = new Stamp();

        stamp.setStampShape(StampShape.ELESTAMP.getKey());

        stamp.setMakeComp(makeCompany);
        //总数
        int eleStampCount = stampDao.countStampNumber(stamp, startDate, endDate);

        companyCountVO.setEleStampCount(eleStampCount);

        //3、物理印章的刻制数量

        stamp.setStampShape(StampShape.PHYSTAMP.getKey());

        int phyStampCount = stampDao.countStampNumber(stamp, startDate, endDate);

        companyCountVO.setPhyStampCount(phyStampCount);

        // 4、缴销印章的数量

        stamp.setUseState(OprationState.LOGOUT);

        int logoutPhyStampCount = stampDao.countStampNumber(stamp, startDate, endDate);

        stamp.setStampShape(StampShape.ELESTAMP.getKey());

        int logoutEleStampCount = stampDao.countStampNumber(stamp, startDate, endDate);
        //总数
        int logoutStampCount = logoutEleStampCount + logoutPhyStampCount;

        companyCountVO.setLogoutStampCount(logoutStampCount);

        // 5、挂失印章的数量

        stamp.setUseState(OprationState.REPORT);

        int reportEleStampCount = stampDao.countStampNumber(stamp, startDate, endDate);

        stamp.setStampShape(StampShape.PHYSTAMP.getKey());

        int reportPhyStampCount = stampDao.countStampNumber(stamp, startDate, endDate);
        //总数
        int reportStampCount = reportEleStampCount + reportPhyStampCount;

        companyCountVO.setReportStampCount(reportStampCount);

        makeCompany.setCompanyCountVO(companyCountVO);

    }

    protected void countProvinceDealerCompany(List<Company> cityDealerCompanyList, Date startDate, Date endDate) {

        for (Company cityDealerCompany : cityDealerCompanyList) {

            //找市下的区
            List<Area> districts = areaDao.findAreasByParentArea(cityDealerCompany.getArea());


            Company checkMakeCompany = new Company();

            checkMakeCompany.setCompType(CompanyType.MAKE);

            checkMakeCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(districts));

            //根据查询条件和区 找出相应的刻章点
            List<Company> makeCompanyList = companyDao.findCompanyListByAreas(checkMakeCompany);

            int stampRecordCount = 0; //印章备案数量(5种类型结合起来)

            int phyStampCount = 0;//物理印章刻制数量

            int eleStampCount = 0;// 电子印章刻制数量

            int logoutStampCount = 0;//缴销印章数量

            int reportStampCount = 0;//挂失印章数量


            for (Company makeCompany : makeCompanyList) {

                countCityDealerCompany(makeCompany, startDate, endDate);

                stampRecordCount += makeCompany.getCompanyCountVO().getStampRecordCount();
                phyStampCount += makeCompany.getCompanyCountVO().getPhyStampCount();
                eleStampCount += makeCompany.getCompanyCountVO().getEleStampCount();
                logoutStampCount += makeCompany.getCompanyCountVO().getLogoutStampCount();
                reportStampCount += makeCompany.getCompanyCountVO().getReportStampCount();
            }

            CompanyCountVO companyCountVO = new CompanyCountVO(stampRecordCount, phyStampCount, eleStampCount, logoutStampCount, reportStampCount);

            cityDealerCompany.setCompanyCountVO(companyCountVO);

        }

    }

    protected void countRcCompanyStamp(List<Company> provinceDealerCompanys, Date startDate, Date endDate) {

        //遍历省经销商
        for (Company provinceDealerCompany : provinceDealerCompanys) {
            //当前遍历到的省经销商的 地区 -省
            Area province = provinceDealerCompany.getArea();
            //找出省下的市
            List<Area> provinceSubAreas = areaDao.findAreasByParentArea(province);
            Company cityDealer = new Company();
            cityDealer.setCompType(CompanyType.AGENCY);

            cityDealer.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(provinceSubAreas));
            //找出该省下所有市的经销商 --- 市经销商
            List<Company> cityDealerCompanys = companyDao.findCompanyListByAreas(cityDealer);

            int stampRecordCount = 0; //印章备案数量(5种类型结合起来)

            int phyStampCount = 0;//物理印章刻制数量

            int eleStampCount = 0;// 电子印章刻制数量

            int logoutStampCount = 0;//缴销印章数量

            int reportStampCount = 0;//挂失印章数量

            countProvinceDealerCompany(cityDealerCompanys, startDate, endDate);

            for (Company cityDealerCompany : cityDealerCompanys) {

                stampRecordCount += cityDealerCompany.getCompanyCountVO().getStampRecordCount();
                phyStampCount += cityDealerCompany.getCompanyCountVO().getPhyStampCount();
                eleStampCount += cityDealerCompany.getCompanyCountVO().getEleStampCount();
                logoutStampCount += cityDealerCompany.getCompanyCountVO().getLogoutStampCount();
                reportStampCount += cityDealerCompany.getCompanyCountVO().getReportStampCount();

            }

            CompanyCountVO companyCountVO = new CompanyCountVO(stampRecordCount, phyStampCount, eleStampCount, logoutStampCount, reportStampCount);

            provinceDealerCompany.setCompanyCountVO(companyCountVO);
        }


    }
}
