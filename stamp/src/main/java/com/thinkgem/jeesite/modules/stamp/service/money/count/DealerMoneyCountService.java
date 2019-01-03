package com.thinkgem.jeesite.modules.stamp.service.money.count;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dto.money.DealerCompanyMoneyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.money.MakeCompanyMoneyCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.money.CompanyMoneyExcelDo;
import com.thinkgem.jeesite.modules.stamp.vo.moneyCount.CompanyMoneyCountVO;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Locker on 17/7/24.
 */
@Service
public class DealerMoneyCountService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private StampDao stampDao;

    /**
     * 市经销商统计->区下面的刻章点收费情况
     * stamp-count-CityMoney
     *
     * @param page
     * @param makeCompanyMoneyCountDTO
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Company> cityCountPage(Page<Company> page, MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO) {

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

                countCityDealerCompanyStamp(makeCompany, startDate, endDate);



            }

            page.setList(makeCompanyList);

        } else {
            //否则则是当前市经销商下的区域

            checkMakeCompany.setArea(checkAera);

            List<Company> makeCompanyList = companyDao.findCompanyByArea(checkMakeCompany);

            for (Company makeCompany : makeCompanyList) {

                countCityDealerCompanyStamp(makeCompany, startDate, endDate);

            }

            page.setList(makeCompanyList);
        }

        return page;
    }
    @Transactional(readOnly = true)
    public List<CompanyMoneyExcelDo> cityCountExcel(Page<Company> page, MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO){

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

        List<CompanyMoneyExcelDo> list = new ArrayList<CompanyMoneyExcelDo>();

        if (checkAera == null || "".equals(checkAera.getId()) || currentArea.getId().equals(checkAera.getId())) {

            checkAera = currentDealerCompany.getArea();

            //当前市下的所有区
            List<Area> districts = areaDao.findAreasByParentArea(checkAera);

            checkMakeCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(districts));
            //根据查询条件和区域找出相应的MakeCompany
            List<Company> makeCompanyList = companyDao.findCompanyListByAreas(checkMakeCompany);

            for (Company makeCompany : makeCompanyList) {

                countCityDealerCompanyStamp(makeCompany, startDate, endDate);

                CompanyMoneyExcelDo excelDo = exportMakeCompanyDeal(makeCompany);

                list.add(excelDo);
            }


        } else {
            //否则则是当前市经销商下的区域

            checkMakeCompany.setArea(checkAera);

            List<Company> makeCompanyList = companyDao.findCompanyByArea(checkMakeCompany);

            for (Company makeCompany : makeCompanyList) {

                countCityDealerCompanyStamp(makeCompany, startDate, endDate);

                CompanyMoneyExcelDo excelDo = exportMakeCompanyDeal(makeCompany);

                list.add(excelDo);

            }

        }

        return list;

    }

    /**
     * 省经销商统计->市经销商下面的收费情况
     *
     * @param page
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Company> provinceCountPage(Page<Company> page, DealerCompanyMoneyCountDTO dto) {

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

            countProvinceDealerCompanysStamp(cityDealerCompanys, startDate, endDate);

            page.setList(cityDealerCompanys);

        } else {
            //市
            // checkArea is city
            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            company.setArea(checkArea);

            company.setPage(page);

            List<Company> cityDealerCompanys = companyDao.findCompanyByArea(company);

            countProvinceDealerCompanysStamp(cityDealerCompanys, startDate, endDate);

            page.setList(cityDealerCompanys);

        }

        return page;
    }

    @Transactional(readOnly = true)
    public List<CompanyMoneyExcelDo> provinceExcelExport(Page<Company> page,DealerCompanyMoneyCountDTO dto){

        Area checkArea = dto.getArea();

        Date startDate = dto.getStartDate();

        Date endDate = dto.getEndDate();

        Area currentArea = UserUtils.getUser().getCompanyInfo().getArea();

        List<CompanyMoneyExcelDo> list = new ArrayList<CompanyMoneyExcelDo>();

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

            countProvinceDealerCompanysStamp(cityDealerCompanys, startDate, endDate);

            for(Company cityDealer : cityDealerCompanys){

                CompanyMoneyExcelDo excelDo = exportMakeCompanyDeal(cityDealer);

                list.add(excelDo);
            }

        } else {
            //市
            // checkArea is city
            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            company.setArea(checkArea);

            company.setPage(page);

            List<Company> cityDealerCompanys = companyDao.findCompanyByArea(company);

            countProvinceDealerCompanysStamp(cityDealerCompanys, startDate, endDate);

            for(Company cityDealer : cityDealerCompanys){

                CompanyMoneyExcelDo excelDo = exportMakeCompanyDeal(cityDealer);

                list.add(excelDo);
            }

        }

        return list;
    }

    /**
     * 润城最大经销商收费统计
     *
     * @param page
     * @param dto
     * @return
     */
    public Page<Company> rcCountPage(Page<Company> page, DealerCompanyMoneyCountDTO dto) {

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

            countRcDealerCompanyStamp(provinceDealerCompanys,startDate,endDate);

            page.setList(provinceDealerCompanys);

        }else{

            //说明当前区域等级是省

            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            company.setArea(checkArea);

            company.setPage(page);
            //当前查询 省的经销商
            List<Company> provinceDealerCompanys = companyDao.findCompanyByArea(company);

            countRcDealerCompanyStamp(provinceDealerCompanys,startDate,endDate);

            page.setList(provinceDealerCompanys);

        }

        return page;
    }

    public List<CompanyMoneyExcelDo> rcCountexcelExport(Page<Company> page, DealerCompanyMoneyCountDTO dto){

        Area checkArea = dto.getArea();

        Date startDate = dto.getStartDate();

        Date endDate = dto.getEndDate();

        //因为是润城经销商，润城=中国
        Area currentArea = new Area("1");

        List<CompanyMoneyExcelDo> list = new ArrayList<CompanyMoneyExcelDo>();

        if (checkArea == null || "".equals(checkArea.getId()) || currentArea.getId().equals(checkArea.getId())) {

            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            company.setPage(page);
            //各个省
            List<Area> provinces = areaDao.findAreasByParentArea(currentArea);

            company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(provinces));

            //找出了所有省经销商
            List<Company> provinceDealerCompanys = companyDao.findCompanyListByAreas(company);

            countRcDealerCompanyStamp(provinceDealerCompanys,startDate,endDate);

            for(Company dealCompany:provinceDealerCompanys){

                CompanyMoneyExcelDo excelDo = exportMakeCompanyDeal(dealCompany);

                list.add(excelDo);
            }

        }else{

            //说明当前区域等级是省

            Company company = new Company();

            company.setCompType(CompanyType.AGENCY);

            company.setArea(checkArea);

            company.setPage(page);
            //当前查询 省的经销商
            List<Company> provinceDealerCompanys = companyDao.findCompanyByArea(company);

            countRcDealerCompanyStamp(provinceDealerCompanys,startDate,endDate);

            for(Company dealCompany:provinceDealerCompanys){

                CompanyMoneyExcelDo excelDo = exportMakeCompanyDeal(dealCompany);

                list.add(excelDo);
            }
        }

        return list;
    }

    /**
     * 市经销商统计
     * <p>
     * 抽离方法 计算每个公司的物理和电子印章数目和金额
     *
     * @param makeCompany
     * @param startDate
     * @param endDate
     */
    protected void countCityDealerCompanyStamp(Company makeCompany, Date startDate, Date endDate) {

        CompanyMoneyCountVO countVO;

        countVO = new CompanyMoneyCountVO();

        //1物理章
        Stamp phyStamp = new Stamp();

        phyStamp.setStampShape(StampShape.PHYSTAMP.getKey());

        phyStamp.setMakeComp(makeCompany);


        List<Stamp> phyStamps = stampDao.findStampByMakeCompany(phyStamp, startDate, endDate);

        countVO = MoneyCountServiceUtil.countPhyStampsCityMoney(phyStamps, countVO);

        //2电子章

        Stamp eleStamp = new Stamp();

        eleStamp.setStampShape(StampShape.ELESTAMP.getKey());

        eleStamp.setMakeComp(makeCompany);

        List<Stamp> eleStamps = stampDao.findStampByMakeCompany(eleStamp, startDate, endDate);

        countVO = MoneyCountServiceUtil.countEleStampsCityMoney(eleStamps, countVO);

        makeCompany.setCompanyMoneyCountVO(countVO);

    }

    /**
     * 省经销商 统计
     *
     * @param makeCompany
     * @param startDate
     * @param endDate
     */
    protected void countProvinceDealerCompanyStamp(Company makeCompany, Date startDate, Date endDate) {

        CompanyMoneyCountVO countVO;

        countVO = new CompanyMoneyCountVO();

        //1物理章
        Stamp phyStamp = new Stamp();

        phyStamp.setStampShape(StampShape.PHYSTAMP.getKey());

        phyStamp.setMakeComp(makeCompany);


        List<Stamp> phyStamps = stampDao.findStampByMakeCompany(phyStamp, startDate, endDate);

        countVO = MoneyCountServiceUtil.countPhyStampsProvinceMoney(phyStamps, countVO);

        //2电子章

        Stamp eleStamp = new Stamp();

        eleStamp.setStampShape(StampShape.ELESTAMP.getKey());

        eleStamp.setMakeComp(makeCompany);

        List<Stamp> eleStamps = stampDao.findStampByMakeCompany(eleStamp, startDate, endDate);

        countVO = MoneyCountServiceUtil.countEleStampsProvinceMoney(eleStamps, countVO);

        makeCompany.setCompanyMoneyCountVO(countVO);

    }

    protected void countRcDealerCompanyStamp(Company makeCompany, Date startDate, Date endDate){

        CompanyMoneyCountVO countVO;

        countVO = new CompanyMoneyCountVO();

        //1物理章
        Stamp phyStamp = new Stamp();

        phyStamp.setStampShape(StampShape.PHYSTAMP.getKey());

        phyStamp.setMakeComp(makeCompany);


        List<Stamp> phyStamps = stampDao.findStampByMakeCompany(phyStamp, startDate, endDate);

        countVO = MoneyCountServiceUtil.countPhyStampsRcMoney(phyStamps, countVO);

        //2电子章

        Stamp eleStamp = new Stamp();

        eleStamp.setStampShape(StampShape.ELESTAMP.getKey());

        eleStamp.setMakeComp(makeCompany);

        List<Stamp> eleStamps = stampDao.findStampByMakeCompany(eleStamp, startDate, endDate);

        countVO = MoneyCountServiceUtil.countEleStampsRcMoney(eleStamps, countVO);

        makeCompany.setCompanyMoneyCountVO(countVO);

    }


    /**
     * 统计市经销商的省经销商收费
     * 省收费
     * @param cityDealerCompanys
     * @param startDate
     * @param endDate
     */
    protected void countProvinceDealerCompanysStamp(List<Company> cityDealerCompanys, Date startDate, Date endDate) {

        for (Company cityDealerCompany : cityDealerCompanys) {

            //找市下的区
            List<Area> districts = areaDao.findAreasByParentArea(cityDealerCompany.getArea());


            Company checkMakeCompany = new Company();

            checkMakeCompany.setCompType(CompanyType.MAKE);

            checkMakeCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(districts));
            //根据查询条件和区 找出相应的刻章点
            List<Company> makeCompanyList = companyDao.findCompanyListByAreas(checkMakeCompany);

            int phyStampCount = 0;

            int phyStampProvinceMoney = 0;

            int eleStampCount = 0;

            int eleStampProvinceMoney = 0;

            //计算每个刻章点的物理和电子印章.......
            for (Company makeCompany : makeCompanyList) {

                countProvinceDealerCompanyStamp(makeCompany, startDate, endDate);

                phyStampCount += makeCompany.getCompanyMoneyCountVO().getPhyStampCount();

                phyStampProvinceMoney += makeCompany.getCompanyMoneyCountVO().getPhyStampCountMoney();

                eleStampCount += makeCompany.getCompanyMoneyCountVO().getEleStampCount();

                eleStampProvinceMoney += makeCompany.getCompanyMoneyCountVO().getEleStampCountMoney();

            }

            CompanyMoneyCountVO companyMoneyCountVO = new CompanyMoneyCountVO(phyStampCount,phyStampProvinceMoney,eleStampCount,eleStampProvinceMoney);

            cityDealerCompany.setCompanyMoneyCountVO(companyMoneyCountVO);

        }

    }


    protected void countRcDealerCompanyStamp(List<Company> provinceDealerCompanys,Date startDate,Date endDate){

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

            int provincePhyStampCount = 0;
            int provincePhyStampMoney = 0;
            int provinceEleStampCount = 0;
            int provinceEleStampMoney = 0;
            //遍历 省下 所有市的经销商
            for (Company cityDealerCompany : cityDealerCompanys) {
                //获得当前市经销商区域
                Area city = cityDealerCompany.getArea();
                //获得市下所有区
                List<Area> districts = areaDao.findAreasByParentArea(city);
                Company makeCompany = new Company();
                makeCompany.setCompType(CompanyType.MAKE);
                makeCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(districts));
                List<Company> makeCompanys = companyDao.findCompanyListByAreas(makeCompany);
                //刻章点
                for (Company makeCompany1 : makeCompanys) {
                    countRcDealerCompanyStamp(makeCompany1,startDate,endDate);
                    provincePhyStampCount += makeCompany1.getCompanyMoneyCountVO().getPhyStampCount();
                    provincePhyStampMoney += makeCompany1.getCompanyMoneyCountVO().getPhyStampCountMoney();
                    provinceEleStampCount += makeCompany1.getCompanyMoneyCountVO().getEleStampCount();
                    provinceEleStampMoney += makeCompany1.getCompanyMoneyCountVO().getEleStampCountMoney();
                }

            }

            CompanyMoneyCountVO companyMoneyCountVO = new CompanyMoneyCountVO(provincePhyStampCount,provincePhyStampMoney,provinceEleStampCount,provinceEleStampMoney);

            provinceDealerCompany.setCompanyMoneyCountVO(companyMoneyCountVO);
        }

    }

    protected CompanyMoneyExcelDo exportMakeCompanyDeal(Company company){

        CompanyMoneyExcelDo excelDo = new CompanyMoneyExcelDo();

        excelDo.setEleStampCount(company.getCompanyMoneyCountVO().getEleStampCount());
        excelDo.setEleStampCountMoney(company.getCompanyMoneyCountVO().getEleStampCountMoney()*0.01);
        excelDo.setPhyStampCountMoney(company.getCompanyMoneyCountVO().getPhyStampCountMoney()*0.01);
        excelDo.setPhyStampCount(company.getCompanyMoneyCountVO().getPhyStampCount());
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

}
