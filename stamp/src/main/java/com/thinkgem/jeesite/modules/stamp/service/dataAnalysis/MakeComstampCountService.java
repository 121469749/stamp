package com.thinkgem.jeesite.modules.stamp.service.dataAnalysis;

import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xucaikai on 2018\10\25 0025.
 */
@Service
public class MakeComstampCountService {
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private StampDao stampDao;
    /**
     * @author xucaikai
     * @TODO (注：统计印章类型的数量)
     * @param
     * @DATE: 2018\10\17 0017 14:53
     */

    public Map getcountStampFromStampType() {
        Map<String,Integer> map = new LinkedHashMap<String,Integer>();
        Stamp stamp = new Stamp();
        stamp.setStampShape(StampShape.PHYSTAMP.getKey());// 计算物理印章
        Company makeCom = UserUtils.getUser().getCompanyInfo();
        stamp.setMakeComp(new Company(makeCom.getId()));

        List<Dict> dictList = DictUtils.getDictList("stampType");

        List<Integer> countList = stampDao.countStampFromStampType(stamp,dictList,dictList.size());
        for (int i=0;i<countList.size();i++){
            map.put(dictList.get(i).getLabel(),countList.get(i));
        }
        return map;
    }

    /**
     * @author xucaikai
     * @TODO (注：申请印章总数(刻章点))
     * @param
     * @DATE: 2018\10\18 0018 9:14
     */
    public List<Integer> getTotalApplyCountForMakeCom(){
        Stamp stamp = new Stamp();
        List<Integer> list = new ArrayList<>();
        Company makeCom = new Company();

        int totalApplyCount =0;
        int totalEngraveCount =0;
        int totalDeliveryCount =0;
        int totalUseComCount =0;
        long a = System.currentTimeMillis();
        makeCom = UserUtils.getUser().getCompanyInfo();
        stamp.setMakeComp(new Company(makeCom.getId()));

        //申请印章总数
        stamp.getSqlMap().put("stampStateFilter2",DataScopeFilterUtil.stampStateFilter2(StampState.RECEPT));
        totalApplyCount = stampDao.getTotalCountForState(stamp);

       //已制作
        stamp.getSqlMap().put("stampStateFilter2",DataScopeFilterUtil.stampStateFilter2(StampState.ENGRAVE));
        totalEngraveCount = stampDao.getTotalCountForState(stamp);

        //已交付
        stamp.getSqlMap().put("stampStateFilter2",DataScopeFilterUtil.stampStateFilter2(StampState.DELIVERY));
        totalDeliveryCount = stampDao.getTotalCountForState(stamp);

        //备案企业总数
        totalUseComCount = stampDao.getUseComCount(stamp);

        list.add(totalApplyCount);
        list.add(totalEngraveCount);
        list.add(totalDeliveryCount);
        list.add(totalUseComCount);


        return list;
    }


    /**
     * @author xucaikai
     * @TODO (注：统计今天、本周、本月、今年中备案、已制作、已交付印章的数量(刻章点))
     * @param
     * @DATE: 2018\10\18 0018 9:14
     */
    public List<Integer> stampAndUseComCountForDate(String dateFlag){
        Stamp stamp = new Stamp();
        List<Integer> list = new ArrayList<>();
        Company makeCom = new Company();


        int twApplyCount =0;
        int twEngraveCount =0;
        int twDeliveryCount =0;
        int twUseComCount =0;

        stamp.setStampShape(StampShape.PHYSTAMP.getKey());// 计算物理印章
        makeCom = UserUtils.getUser().getCompanyInfo();
        stamp.setMakeComp(new Company(makeCom.getId()));

        //已承接（已备案）
        stamp.getSqlMap().put("stampStateFilter",DataScopeFilterUtil.stampStateFilter(StampState.RECEPT,dateFlag));
        twApplyCount = stampDao.stampStateCount(stamp);

        //已制作
        stamp.getSqlMap().put("stampStateFilter",DataScopeFilterUtil.stampStateFilter(StampState.ENGRAVE,dateFlag));
        twEngraveCount = stampDao.stampStateCount(stamp);

        //已交付
        stamp.getSqlMap().put("stampStateFilter",DataScopeFilterUtil.stampStateFilter(StampState.DELIVERY,dateFlag));
        twDeliveryCount = stampDao.stampStateCount(stamp);
        //备案企业的数量
        stamp.getSqlMap().put("dateFilter",DataScopeFilterUtil.dateFilter(dateFlag));
        twUseComCount =stampDao.UseComCountForDate(stamp);

        list.add(twApplyCount);
        list.add(twEngraveCount);
        list.add(twDeliveryCount);
        list.add(twUseComCount);

        return list;
    }

}
