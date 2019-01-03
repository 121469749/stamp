package com.thinkgem.jeesite.modules.stamp.service.money.count;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dto.money.MakeCompanyMoneyCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.money.CompanyMoneyExcelDo;
import com.thinkgem.jeesite.modules.stamp.vo.moneyCount.CompanyMoneyCountVO;
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
 * Created by Locker on 2017/7/24.
 */
@Service
public class MakeCompanyMoneyCountService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private StampDao stampDao;

    @Autowired
    private AreaDao areaDao;

    @Transactional(readOnly = true)
    public Page<Company> countList(MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO, Page<Company> page) {

        Company currentMakeCompany = UserUtils.getUser().getCompanyInfo();

        Date startDate = makeCompanyMoneyCountDTO.getStartDate();

        Date endDate = makeCompanyMoneyCountDTO.getEndDate();

        //当前区域
        Area currentArea = areaDao.get(currentMakeCompany.getArea());

        //1先找出这个区域下的用章点公司 list and page
        Company useCompany = makeCompanyMoneyCountDTO.getCompany();

        useCompany.setCompType(CompanyType.USE);

        useCompany.setArea(currentArea);

        useCompany.setPage(page);

        List<Company> companyList = companyDao.findList(useCompany);

        //2再根据时间，一个个用章公司公司翻查，当前用章点刻制的印章
        //3再统计换算成对应的钱

        CompanyMoneyCountVO countVO;

        for (Company company : companyList) {

            countVO = new CompanyMoneyCountVO();

            //1统计物理 印章
            Stamp phyStamp = new Stamp();

            phyStamp.setStampShape(StampShape.PHYSTAMP.getKey());

            phyStamp.setUseComp(company);

            phyStamp.setMakeComp(currentMakeCompany);

            List<Stamp> phyStamps = stampDao.findStampByMakeAndUseCompany(phyStamp, startDate, endDate);

            countVO = MoneyCountServiceUtil.countPhyStampsCityMoney(phyStamps,countVO);

            //2统计电子

            Stamp eleStamp = new Stamp();

            eleStamp.setStampShape(StampShape.ELESTAMP.getKey());

            eleStamp.setUseComp(company);

            eleStamp.setMakeComp(currentMakeCompany);

            List<Stamp> eleStamps = stampDao.findStampByMakeAndUseCompany(eleStamp, startDate, endDate);


            countVO = MoneyCountServiceUtil.countEleStampMakeMoney(eleStamps,countVO);


            company.setCompanyMoneyCountVO(countVO);
        }


        page.setList(companyList);



        return page;
    }

    public List<CompanyMoneyExcelDo> excelExportService(MakeCompanyMoneyCountDTO makeCompanyMoneyCountDTO, Page<Company> page) {

        Company currentMakeCompany = UserUtils.getUser().getCompanyInfo();

        Date startDate = makeCompanyMoneyCountDTO.getStartDate();

        Date endDate = makeCompanyMoneyCountDTO.getEndDate();

        //当前区域
        Area currentArea = areaDao.get(currentMakeCompany.getArea());

        //1先找出这个区域下的用章点公司 list and page
        Company useCompany = makeCompanyMoneyCountDTO.getCompany();

        useCompany.setCompType(CompanyType.USE);

        useCompany.setArea(currentArea);

        useCompany.setPage(page);

        List<Company> companyList = companyDao.findList(useCompany);

        //2再根据时间，一个个用章公司公司翻查，当前用章点刻制的印章
        //3再统计换算成对应的钱

        List<CompanyMoneyExcelDo> list = new ArrayList<CompanyMoneyExcelDo>();

        CompanyMoneyCountVO countVO;

        for (Company company : companyList) {

            countVO = new CompanyMoneyCountVO();

            //1统计物理 印章
            Stamp phyStamp = new Stamp();

            phyStamp.setStampShape(StampShape.PHYSTAMP.getKey());

            phyStamp.setUseComp(company);

            phyStamp.setMakeComp(currentMakeCompany);

            List<Stamp> phyStamps = stampDao.findStampByMakeAndUseCompany(phyStamp, startDate, endDate);

            countVO = MoneyCountServiceUtil.countPhyStampsCityMoney(phyStamps, countVO);

            //2统计电子

            Stamp eleStamp = new Stamp();

            eleStamp.setStampShape(StampShape.ELESTAMP.getKey());

            eleStamp.setUseComp(company);

            eleStamp.setMakeComp(currentMakeCompany);

            List<Stamp> eleStamps = stampDao.findStampByMakeAndUseCompany(eleStamp, startDate, endDate);


            countVO = MoneyCountServiceUtil.countEleStampMakeMoney(eleStamps, countVO);

            CompanyMoneyExcelDo makeCompanyExcelDo = new CompanyMoneyExcelDo();

            makeCompanyExcelDo.setEleStampCount(countVO.getEleStampCount());

            makeCompanyExcelDo.setEleStampCountMoney(countVO.getEleStampCountMoney()*0.01);
            makeCompanyExcelDo.setPhyStampCountMoney(countVO.getPhyStampCountMoney()*0.01);
            makeCompanyExcelDo.setPhyStampCount(countVO.getPhyStampCount());

            makeCompanyExcelDo.setCompAddress(company.getCompAddress());
            makeCompanyExcelDo.setCompPhone(company.getCompPhone());
            makeCompanyExcelDo.setArea(company.getArea());
            makeCompanyExcelDo.setLegalName(company.getLegalName());
            makeCompanyExcelDo.setLegalPhone(company.getLegalPhone());
            makeCompanyExcelDo.setSoleCode(company.getSoleCode());
            makeCompanyExcelDo.setCompanyType(company.getCompType());
            makeCompanyExcelDo.setCompanyName(company.getCompanyName());

            list.add(makeCompanyExcelDo);
        }


        return list;
    }

}
