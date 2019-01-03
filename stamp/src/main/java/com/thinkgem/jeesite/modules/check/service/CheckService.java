package com.thinkgem.jeesite.modules.check.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.check.dao.CheckDao;
import com.thinkgem.jeesite.modules.check.dto.QueryDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */
@Service
@Transactional(readOnly = true)
public class CheckService {


    @Autowired
    private CheckDao checkDao;


    public Page<Stamp> findPage(QueryDTO dto, HttpServletRequest request, HttpServletResponse response){

        Page<Stamp> page = new Page<Stamp>(request,response);

        //page.setPageSize(-1);
        page.setPageSizeAll();

        dto.setPage(page);

        List<Stamp> stamps = checkDao.findCheckStamp(dto);
        System.out.println("stampslist="+stamps.size());

        for(Stamp stamp :stamps){

            Company useCompany =  checkDao.getUseCompany(stamp.getUseComp().getId());

            stamp.setUseComp(useCompany);

            Company makeCompany = checkDao.getMakeCompany(stamp.getMakeComp().getId());

            stamp.setMakeComp(makeCompany);

            Company nowMakeCompany = checkDao.getMakeCompany(stamp.getNowMakeComp().getId());

            stamp.setNowMakeComp(nowMakeCompany);
        }

        page.setList(stamps);

        return page;
    }


    public Stamp findStamp(Stamp stamp){

        stamp = checkDao.findStamp(stamp);

        Company useCompany =  checkDao.getUseCompany(stamp.getUseComp().getId());

        stamp.setUseComp(useCompany);

        Company makeCompany = checkDao.getMakeCompany(stamp.getMakeComp().getId());

        stamp.setMakeComp(makeCompany);

        Company nowMakeCompany = checkDao.getMakeCompany(stamp.getNowMakeComp().getId());

        stamp.setNowMakeComp(nowMakeCompany);

        return stamp;
    }

}
