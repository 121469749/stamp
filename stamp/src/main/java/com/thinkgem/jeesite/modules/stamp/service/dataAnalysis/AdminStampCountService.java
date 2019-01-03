package com.thinkgem.jeesite.modules.stamp.service.dataAnalysis;

import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dto.dataAnalysis.AreaMapDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\11\21 0021.
 */
@Service
public class AdminStampCountService {

    @Autowired
    private StampDao stampDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private AreaDao areaDao;
    /**
     * @author xucaikai
     * @TODO (注：辖区申请、已制作、已交付印章总数(管理员))
     * @param
     * @DATE: 2018\10\18 0018 9:14
     */
    public List<Integer> getTotalApplyCount(String areaName){
        Stamp stamp = new Stamp();
        List<Integer> list = new ArrayList<>();
        Company makeComp = new Company();

        int totalApplyCount =0;
        int totalEngraveCount =0;
        int totalDeliveryCount =0;
        int totalUseComCount =0;

//        Police police = UserUtils.getUser().getPoliceInfo();
        Area area = areaDao.getAreaByName(areaName);
        // 区域过滤，查询当前公安区域下所属的制章单位
        makeComp.setCompType(CompanyType.MAKE);
        makeComp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(area, "a3"));
        List<Company> makeCompsList = companyDao.findMakeComList(makeComp);
        if(makeCompsList.size()!=0) {
            //申请印章总数
            stamp.getSqlMap().put("stampStateFilter2", DataScopeFilterUtil.stampStateFilter2(StampState.DELIVERY));
            totalApplyCount = stampDao.getTotalCountForPolice(stamp, makeCompsList, makeCompsList.size());

            //已制作
            stamp.getSqlMap().put("stampStateFilter2", DataScopeFilterUtil.stampStateFilter2(StampState.ENGRAVE));
            totalEngraveCount = stampDao.getTotalCountForPolice(stamp, makeCompsList, makeCompsList.size());

            //已交付
            stamp.getSqlMap().put("stampStateFilter2", DataScopeFilterUtil.stampStateFilter2(StampState.DELIVERY));
            totalDeliveryCount = stampDao.getTotalCountForPolice(stamp, makeCompsList, makeCompsList.size());

            //辖区备案企业总数
            stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(area, "sa"));
            totalUseComCount = stampDao.getTotalUseComCount(stamp);
        }


        list.add(totalApplyCount);
        list.add(totalEngraveCount);
        list.add(totalDeliveryCount);
        list.add(totalUseComCount);

        return list;
    }


    /**
     * @author xucaikai
     * @TODO (注：辖区印章总数(管理员))
     * @param
     * @DATE: 2018\10\18 0018 9:14
     */
    public List<AreaMapDTO> getTotalApplyCountList(String areaName){
        Stamp stamp = new Stamp();
        List<AreaMapDTO> list = new ArrayList<AreaMapDTO>();
        Company makeComp = new Company();
        long a = System.currentTimeMillis();


        Area areaTemp = areaDao.getAreaByName(areaName);
        List<Area> areaList = areaDao.findAreasByParentArea(areaTemp);
        for(Area area:areaList){
            int totalApplyCount =0;
            // 区域过滤，查询当前区域下所属的制章单位
            makeComp.setCompType(CompanyType.MAKE);
            makeComp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(area, "a3"));
            List<Company> makeCompsList = companyDao.findMakeComList(makeComp);
            if(makeCompsList.size()!=0) {
                //申请印章总数
                stamp.getSqlMap().put("stampStateFilter2", DataScopeFilterUtil.stampStateFilter2(StampState.DELIVERY));
                totalApplyCount = stampDao.getTotalCountForPolice(stamp, makeCompsList, makeCompsList.size());

            }
            AreaMapDTO areaMapDTO = new AreaMapDTO(area.getCode(),area.getName(),String.valueOf(totalApplyCount));
            list.add(areaMapDTO);

        }
        long b = System.currentTimeMillis();
        System.out.println("用时："+(b-a));

        return list;
    }

}
