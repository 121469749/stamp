package com.thinkgem.jeesite.modules.stamp.service.count.dealer;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dto.count.CompanyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.StampCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.StampCountExcelByPoliceDTO;
import com.thinkgem.jeesite.modules.stamp.service.count.makeCompany.MakeCompanyCountService;
import com.thinkgem.jeesite.modules.stamp.service.count.police.PoliceCountService;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
@Service
@Transactional(readOnly = true)
public class RcDealerCountService {


    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private StampDao stampDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private MakeCompanyCountService makeCompanyCountService;

    @Autowired
    private PoliceCountService policeCountService;

    /**
     *  印章统计页面搜索分页
     *
     * @param dto
     * @param page
     * @return
     */
    public Page<Stamp> countStampPage(StampCountDTO dto, Page<Stamp> page){

        //设置查询条件
        Stamp stamp = dto.changeStamp();

        //区域过滤
        if(dto.getArea()!=null&&dto.getArea().getId()!=null&&(!dto.getArea().getId().equals(""))){
            stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(dto.getArea()), "sa"));
        }else{
            Company company = UserUtils.getUser().getCompanyInfo();
            stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(company.getArea()), "sa"));
        }
        //end
        stamp.setPage(page);

        List<Stamp> stamps = stampDao.policeCountStampList(stamp);

        //印章所属用章公司
        makeCompanyCountService.findStampListUseCompany(stamps);

        makeCompanyCountService.findStampListNowMakeCompany(stamps);

        page.setList(stamps);

        return page;
    }

    /**
     * 获得全部数据导出报表
     * @param dto
     * @return
     */
    public List<StampCountExcelByPoliceDTO> countStampExcel(StampCountDTO dto){

        //设置查询条件
        Stamp stamp = dto.changeStamp();

        //区域过滤
        if(dto.getArea()!=null&&dto.getArea().getId()!=null&&(!dto.getArea().getId().equals(""))){
            stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(dto.getArea()), "sa"));
        }else{
            Company company = UserUtils.getUser().getCompanyInfo();
            stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(company.getArea()), "sa"));
        }
        //end

        List<Stamp> stamps = stampDao.policeCountStampList(stamp);

        //印章所属用章公司
        makeCompanyCountService.findStampListUseCompany(stamps);

        makeCompanyCountService.findStampListNowMakeCompany(stamps);


        List<StampCountExcelByPoliceDTO> excelList = new ArrayList<StampCountExcelByPoliceDTO>();
        policeCountService.changeExcelExportDTO(excelList,stamps);

        return excelList;
    }

    /**
     *  根据区域过滤
     *  条件搜索 搜索用章单位统计信息分页查询
     * @param page
     * @param dto
     * @return
     */
    public Page<Company> countUseCompanyPage(Page<Company> page,CompanyCountDTO dto){

        Company checkCompany = dto.getCompany();

        checkCompany.setCompType(CompanyType.USE);

        //区域过滤
        if(dto.getCompany().getArea()!=null&&dto.getCompany().getArea().getId()!=null&&(!dto.getCompany().getArea().getId().equals(""))){
            checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(dto.getCompany().getArea()), "a3"));
        }else{
            Company company = UserUtils.getUser().getCompanyInfo();
            checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(company.getArea()), "a3"));
        }

        checkCompany.setPage(page);

        List<Company> list = companyDao.countCompanyPage(checkCompany);

        makeCompanyCountService.useCompanyCountDeal(list);

        page.setList(list);

        return page;
    }

    /**
     * 统计获得用章单位信息
     * @param useCompanyId
     * @return
     * @throws IOException
     */
    public List<StampRecord> findRecordDetailInfoByUseCompany(String useCompanyId) throws IOException {

        StampRecord check = new StampRecord();
        check.setUseComp(new Company(useCompanyId));

        List<StampRecord>  all = makeCompanyCountService.countStampRecordWorkType(check);

        for(StampRecord stampRecord :all){

            List<Stamp> list = makeCompanyCountService.dealStampRecordJsonStamp(stampRecord.getApplyInfos());

            stampRecord.setStamps(list);
        }

        return all;
    }

    public Company getUseCompany(String id){

        return makeCompanyCountService.getUseCompany(id);
    }
}
