package com.thinkgem.jeesite.modules.stamp.service.licence;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.licence.LicenceDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hjw-pc on 2017/5/20.
 */
@Service
@Transactional(readOnly = true)
public class LicenceService extends CrudService<LicenceDao, Licence>{

    @Autowired
    private CompanyDao companyDao;
    public Licence get(String id) {
        return super.get(id);
    }

    public List<Licence> findList(Licence license) {
        return super.findList(license);
    }

    public Page<Licence> findPage(Page<Licence> page, Licence license) {
        return super.findPage(page, license);
    }

    @Transactional(readOnly = false)
    public void save(Licence license) {
        super.save(license);
        //更新该刻章点最新workType
        Company company = new Company();
        company.setId(license.getMakeComp().getId());
        companyDao.updateLastLicenseState(company, license.getWorkType());

    }

    @Transactional(readOnly = false)
    public void delete(Licence license) {
        super.delete(license);
    }
}
