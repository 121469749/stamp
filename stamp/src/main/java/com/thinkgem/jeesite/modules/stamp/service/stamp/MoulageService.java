package com.thinkgem.jeesite.modules.stamp.service.stamp;

import com.thinkgem.jeesite.modules.stamp.dao.stamp.MoulageDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Moulage;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Created by 物理印模信息 on 2017/8/17.
 */
@Service
@Transactional(readOnly = true)
public class MoulageService {

    @Autowired
    private MoulageDao moulageDao;


    public Moulage get(String id){

        Moulage moulage = moulageDao.getMoulageById(id);

        return moulage;
    }

    public Moulage get(Moulage moulage){

        return moulageDao.get(moulage);
    }


    public List<Moulage> findMoulageInMake(Company makeCompany, Company useCompany, Stamp stamp){

        Moulage moulage = new Moulage();

        moulage.setUseCompany(useCompany);

        moulage.setMakeCompany(makeCompany);

        moulage.setStampType(stamp.getStampType());

        List<Moulage> list = moulageDao.findListInMake(moulage);


        return list;
    }


}
