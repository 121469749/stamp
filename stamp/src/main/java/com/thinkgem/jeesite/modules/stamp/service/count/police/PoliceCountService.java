package com.thinkgem.jeesite.modules.stamp.service.count.police;


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
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.CompanyCountExcelByPoliceDTO;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.StampCountExcelByMakeCompanyDTO;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.StampCountExcelByPoliceDTO;
import com.thinkgem.jeesite.modules.stamp.service.count.makeCompany.MakeCompanyCountService;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/6.
 */
@Service
@Transactional(readOnly = true)
public class PoliceCountService {

    @Autowired
    private StampDao stampDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private MakeCompanyCountService makeCompanyCountService;

    /**
     *
     * 公安局对印章的统计查询
     *
     * @param dto
     * @param page
     * @return
     */
    public Page<Stamp> countPage(StampCountDTO dto, Page<Stamp> page){

        //设置查询条件
        Stamp stamp = dto.changeStamp();

        //区域过滤
        if(dto.getArea()!=null&&dto.getArea().getId()!=null&&(!dto.getArea().getId().equals(""))){
            stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(dto.getArea()), "sa"));
        }else{
            Police police = UserUtils.getUser().getPoliceInfo();
            stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "sa"));
        }

        //一开始默认读取物理印章
        if ("".equals(stamp.getStampShape()) || stamp.getStampShape() == null){
            stamp.setStampShape("1");
        }

        //end
        stamp.setPage(page);

        long a = System.currentTimeMillis();

        List<Stamp> stamps = stampDao.policeCountStampList2(stamp);
        long b = System.currentTimeMillis();
        System.out.println("印章数据统计:"+(b-a));
        //印章所属用章公司

        makeCompanyCountService.findStampListUseCompany(stamps);

        makeCompanyCountService.findStampListNowMakeCompany(stamps);
        long c = System.currentTimeMillis();
        System.out.println("印章所属用章公司:"+(c-b));
        page.setList(stamps);

        return page;
    }

    /**
     * 公安对企业信息的统计
     * @param page
     * @param dto
     * @return
     */
    public Page<Company> useCompanyCountPage(Page<Company> page,CompanyCountDTO dto){

        Company checkCompany = dto.getCompany();

        checkCompany.setCompType(CompanyType.USE);

        //区域过滤
        if(dto.getCompany().getArea()!=null&&dto.getCompany().getArea().getId()!=null&&(!dto.getCompany().getArea().getId().equals(""))){
            checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(dto.getCompany().getArea()), "a3"));
        }else{
            Police police = UserUtils.getUser().getPoliceInfo();
            checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "a3"));
        }

        checkCompany.setPage(page);

        List<Company> list = companyDao.countCompanyPage(checkCompany);

        makeCompanyCountService.useCompanyCountDeal(list);

        page.setList(list);

        return page;
    }



    /**
     * 根据查询条件、区域过滤获得导出到excel的数据
     * @param dto
     * @return
     */
    public List<StampCountExcelByPoliceDTO> countExcel(StampCountDTO dto){

        //设置查询条件
        Stamp stamp = dto.changeStamp();

        //区域过滤
        if(dto.getArea()!=null&&dto.getArea().getId()!=null&&(!dto.getArea().getId().equals(""))){
            stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(dto.getArea()), "sa"));
        }else{
            Police police = UserUtils.getUser().getPoliceInfo();
            stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "sa"));
        }
        //end

        List<Stamp> stamps = stampDao.policeCountStampList2(stamp);

        //印章所属用章公司
        makeCompanyCountService.findStampListUseCompany(stamps);

        makeCompanyCountService.findStampListNowMakeCompany(stamps);

        List<StampCountExcelByPoliceDTO> excelList = new ArrayList<StampCountExcelByPoliceDTO>();
        changeExcelExportDTO(excelList,stamps);

        return excelList;
    }

    public void changeExcelExportDTO(List<StampCountExcelByPoliceDTO> excelList, List<Stamp> list){

        for(Stamp stamp:list){

            StampCountExcelByPoliceDTO dto = new StampCountExcelByPoliceDTO();

            dto.setStampType(stamp.getStampType());
            dto.setStampShape(stamp.getStampShape());
            dto.setUseCompanyName(stamp.getUseComp().getCompanyName());
            dto.setMakeCompanyName(stamp.getNowMakeComp().getCompanyName());
            dto.setDeliveryDate(stamp.getDeliveryDate());
            dto.setRecordDate(stamp.getRecordDate());
            dto.setMakeDate(stamp.getMakeDate());
            dto.setStampCode(stamp.getStampCode());
            dto.setStampState(stamp.getStampState());

            excelList.add(dto);
        }

    }
}
