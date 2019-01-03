package com.thinkgem.jeesite.modules.stamp.service.count.dealer;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dto.count.CompanyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.dealer.StampTypeCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.CountUseCompanyStampDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.StampStateStampCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.service.count.makeCompany.MakeCompanyCountService;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
@Service
@Transactional(readOnly = true)
public class DealerCountService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private StampDao stampDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private MakeCompanyCountService makeCompanyCountService;

    @Autowired
    private DictDao dictDao;

    /**
     * 经销商根据
     * 区域过滤、条件过滤
     * 统计刻章单位信息
     *
     * @param page
     * @param dto
     * @return
     */
    public Page<Company> countMakeCompany(Page<Company> page, CompanyCountDTO dto){

        Company checkCompany = dto.getCompany();

        checkCompany.setCompType(CompanyType.MAKE);

        //区域过滤
        if(dto.getCompany().getArea()!=null&&dto.getCompany().getArea().getId()!=null&&(!dto.getCompany().getArea().getId().equals(""))){
            checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(dto.getCompany().getArea()), "a3"));
        }else{
            Company company = UserUtils.getUser().getCompanyInfo();
            checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(company.getArea()), "a3"));
        }


        checkCompany.setPage(page);

        List<Company> list = companyDao.countCompanyPage(checkCompany);

        countMakeCompanyStamp(list);

        page.setList(list);

        return page;
    }

    /**
     * 获得某一个刻章点的统计信息
     * @param makeCompanyId
     * @return
     */
    public List<StampTypeCountDTO> makeCompanyCountDetail(String makeCompanyId){

        Company makeCompany = new Company(makeCompanyId);

        Stamp checkStamp = new Stamp();

        checkStamp.setMakeComp(makeCompany);
        checkStamp.setNowMakeComp(makeCompany);


        Dict check = new Dict();
        check.setType("stampType");

        List<Dict> dicts  = dictDao.findList(check);

        List<StampTypeCountDTO> dtos = new ArrayList<StampTypeCountDTO>();

        for(Dict dict:dicts ){

            StampTypeCountDTO countDTO = new StampTypeCountDTO();

            countDTO.setStampType(dict.getValue());
            checkStamp.setStampType(dict.getValue());
            //物理
            checkStamp.setStampShape(StampShape.PHYSTAMP.getKey());

            StampStateStampCountDTO phyStampCountDTO = new StampStateStampCountDTO();

            makeCompanyCountService.countOprationStateStamp(checkStamp,phyStampCountDTO);

//            //电子
            checkStamp.setStampShape(StampShape.ELESTAMP.getKey());

            StampStateStampCountDTO eleStampCountDTO = new StampStateStampCountDTO();

            makeCompanyCountService.countOprationStateStamp(checkStamp,eleStampCountDTO);

            int count = phyStampCountDTO.getCount()+eleStampCountDTO.getCount();
            countDTO.setCount(count);

            countDTO.setPhyStampCount(phyStampCountDTO);

            countDTO.setEleStampCount(eleStampCountDTO);

            dtos.add(countDTO);
        }


        return dtos;
    }

    /**
     * 获得某一个刻章点的信息
     * @param id
     * @return
     */
    public Company getMakeCompany(String id){

        Company company = new Company(id);
        company.setCompType(CompanyType.MAKE);

        return companyDao.get(company);
    }

    protected void countMakeCompanyStamp(List<Company> list){


        for(Company makeCompany :list){
            Stamp checkStamp = new Stamp();

            checkStamp.setNowMakeComp(makeCompany);
            checkStamp.setMakeComp(makeCompany);
            //物理印章
            checkStamp.setStampShape(StampShape.PHYSTAMP.getKey());
            int phyStampsCount = stampDao.countStamp(checkStamp);
            //电子印章
            checkStamp.setStampShape(StampShape.ELESTAMP.getKey());
            int eleStampsCount = stampDao.countStamp(checkStamp);

            int allCount = phyStampsCount+eleStampsCount;

            CountUseCompanyStampDTO useCompanyDTO = new CountUseCompanyStampDTO();

            useCompanyDTO.setAllCount(allCount);

            //物理印章统计
            StampStateStampCountDTO phyStampDTO = new StampStateStampCountDTO();

            phyStampDTO.setCount(phyStampsCount);

            checkStamp.setStampShape(StampShape.PHYSTAMP.getKey());

            makeCompanyCountService.countOprationStateStamp(checkStamp,phyStampDTO);

            //电子印章统计
            StampStateStampCountDTO eleStampDTO = new StampStateStampCountDTO();

            eleStampDTO.setCount(eleStampsCount);

            checkStamp.setStampShape(StampShape.ELESTAMP.getKey());

            makeCompanyCountService.countOprationStateStamp(checkStamp,eleStampDTO);

            useCompanyDTO.setPhyStamps(phyStampDTO);

            useCompanyDTO.setEleStamps(eleStampDTO);

            makeCompany.setCountUseCompanyStampDTO(useCompanyDTO);

        }




    }



}
