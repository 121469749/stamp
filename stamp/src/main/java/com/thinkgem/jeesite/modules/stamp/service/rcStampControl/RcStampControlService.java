package com.thinkgem.jeesite.modules.stamp.service.rcStampControl;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.check.dao.CheckDao;
import com.thinkgem.jeesite.modules.check.dto.QueryDTO;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.ElectronDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Electron;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.exception.dealer.DealerSystemOprationException;
import com.thinkgem.jeesite.modules.stamp.exception.dealer.DealerSystemOprationOpenFailException;
import com.thinkgem.jeesite.modules.stamp.exception.dealer.DealerSystemOprationStopFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 润城 对印章的时效控制
 * Created by Administrator on 2017/11/19.
 */
@Service
public class RcStampControlService {

    @Autowired
    private CheckDao checkDao;

    @Autowired
    private ElectronDao electronDao;

    @Transactional(readOnly = true)
    public Page<Stamp> findPage(QueryDTO dto, HttpServletRequest request, HttpServletResponse response){

        Page<Stamp> page = new Page<Stamp>(request,response);

        page.setPageSize(10);

        dto.setPage(page);

        List<Stamp> stamps = checkDao.findCheckStamp2(dto);

        for(Stamp stamp :stamps){

            Company useCompany =  checkDao.getUseCompany(stamp.getUseComp().getId());

            stamp.setUseComp(useCompany);

            Company makeCompany = checkDao.getMakeCompany(stamp.getMakeComp().getId());

            stamp.setMakeComp(makeCompany);

            Company nowMakeCompany = checkDao.getMakeCompany(stamp.getNowMakeComp().getId());

            stamp.setNowMakeComp(nowMakeCompany);

            if(stamp.getStampShape().equals(StampShape.ELESTAMP.getKey())){

                if(stamp.getStampShapeId()!=null&&stamp.getStampShapeId()!="") {

                    Electron electron = new Electron(stamp.getStampShapeId());

                    stamp.setElectron(electronDao.get(electron));
                }

            }

        }

        page.setList(stamps);

        return page;
    }

    @Transactional(readOnly = false)
    public Condition controlStamp(Electron electron){

        if(electronDao.rcControl(electron)==1){
            return new Condition(Condition.SUCCESS_CODE,"控制成功!");
        }else{
            return new Condition(Condition.ERROR_CODE,"控制失败!");
        }

    }

    /**
     * 系统管控启用停用刻章点或者用章单位
     *
     * @param companyId
     * @param oprationState
     */
    @Transactional(readOnly = false)
    public Condition systemOpration(String companyId,  OprationState oprationState) {
        Condition condition = null;

        //启用
        if (oprationState == OprationState.OPEN) {
            //先启用公司的管控状态
            if (electronDao.systemOprationState(companyId, oprationState) != 0) {

                condition = new Condition(Condition.SUCCESS_CODE, oprationState.getValue()  + "成功!");

            }
        }

        //停用
        if (oprationState == OprationState.STOP) {
            //先启用公司的管控状态
            if (electronDao.systemOprationState(companyId, oprationState) != 0) {

                condition = new Condition(Condition.SUCCESS_CODE, oprationState.getValue()  + "成功!");

            }
        }

        return condition;


    }

}
