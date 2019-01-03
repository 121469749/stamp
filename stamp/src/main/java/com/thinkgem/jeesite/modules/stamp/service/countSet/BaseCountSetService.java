package com.thinkgem.jeesite.modules.stamp.service.countSet;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.dao.CountSet.CountSetDao;
import com.thinkgem.jeesite.modules.stamp.dto.countSet.CountSetDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 *
 *
 * Created by Locker on 2017/8/18.
 */
public class BaseCountSetService {

    @Autowired
    protected CountSetDao countSetDao;


    public void findCompanyCountSet(Company agencyCompany) {

        CountSet phyCountSet = new CountSet();

        phyCountSet.setCompany(agencyCompany);

        phyCountSet.setStampShape(StampShape.PHYSTAMP);

        phyCountSet = countSetDao.get(phyCountSet);

        agencyCompany.setPhyCountSet(phyCountSet);

        CountSet eleCountSet = new CountSet();

        eleCountSet.setStampShape(StampShape.ELESTAMP);
        eleCountSet.setCompany(agencyCompany);

        eleCountSet = countSetDao.get(eleCountSet);

        agencyCompany.setEleCountSet(eleCountSet);
    }

    protected CountSetDTO lastCountDTO(List<Company> companys,int eleCount,int phyCount){

        for (Company agencyCompany : companys) {
            findCompanyCountSet(agencyCompany);
            CountSet phyCountSet = agencyCompany.getPhyCountSet();
            CountSet eleCountSet = agencyCompany.getEleCountSet();

            if (phyCountSet != null && !("".equals(phyCountSet.getId()))) {
                phyCount -= phyCountSet.getCount();
            }

            if (eleCountSet != null && !("".equals(eleCountSet.getId()))) {
                eleCount -= eleCountSet.getCount();
            }
        }
        CountSet lastPhyCountSet = new CountSet(StampShape.PHYSTAMP, phyCount);

        CountSet lastEleCountSet = new CountSet(StampShape.ELESTAMP, eleCount);

        return new CountSetDTO(lastEleCountSet, lastPhyCountSet);
    }

}
