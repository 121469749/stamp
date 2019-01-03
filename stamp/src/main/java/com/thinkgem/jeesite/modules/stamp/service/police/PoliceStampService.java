package com.thinkgem.jeesite.modules.stamp.service.police;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampWorkType;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampRecordDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hjw-pc on 2017/5/25.
 * 公安单位对印章的操作
 */
@Service
public class PoliceStampService {
    @Autowired
    private  StampDao stampDao;

    @Autowired
    private StampRecordDao stampRecordDao;

    /**
     *
     * @param stamp
     * @return
     * 获取印章
     */
    public Stamp get(Stamp stamp) {

        return stampDao.getWithCom(stamp);
    }

    /**
     *
     * @param stamp
     * @return
     * 更新回执编码
     */
    public int updateReturnCode(Stamp stamp) {

        return stampDao.updateReturnCode(stamp);
    }

    /**
     *
     * @param stamp
     * @return
     * 更新印章临时使用状态标志
     */
    public int updateStateFlag(Stamp stamp) {

        return stampDao.updateStateFlag(stamp);
    }



    /**
     *
     * @param page
     * @param stamp
     * @return
     * 获取印章列表
     */
    public Page<Stamp> findList(Page<Stamp> page, Stamp stamp) {
        //查询使用中的印章
     //   stamp.setUseState(OprationState.OPEN);
        Police police = UserUtils.getUser().getPoliceInfo();
        stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "sa"));
        stamp.setPage(page);


        List<Stamp> stamps =new ArrayList<>();


        if(stamp.getLastRecord() !=null&&!StringUtils.isEmpty(stamp.getLastRecord().getCompanyName())){

            for (StampWorkType stampWorkType : StampWorkType.values()) {//遍历五次：因为RecordState有五个状态

                stamp.setRecordState(stampWorkType);

                List<Stamp> stamp2 = stampDao.findListByPoliceArea3(stamp);

                stamps.addAll(stamp2);
                page.setCount(stamps.size());

            }

        }else{
            long a = System.currentTimeMillis();

            stamps = stampDao.findListByPoliceArea(stamp);

            long b = System.currentTimeMillis();
            System.out.println("联合查询所用的时间："+(b-a)+"ms");
            System.out.println("stamps长度："+stamps.size());

            for(Stamp stamp1:stamps){

                stamp1.setLastRecord(stampRecordDao.get(new StampRecord(stamp1.getLastRecord().getId(), stamp1.getRecordState())));

            }

        }

        page.setList(stamps);

        return page;
    }


    /**
     * @author 许彩开
     * @TODO (注：获取印章列表)
      * @param stamp
     * @DATE: 2017\12\22 0022 11:01
     */

    public Page<Stamp> findList2(Page<Stamp> page, Stamp stamp) {
        //查询使用中的印章
        //   stamp.setUseState(OprationState.OPEN);
        //Police police = UserUtils.getUser().getPoliceInfo();
        //stamp.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "sa"));
        stamp.setPage(page);

        List<Stamp> list = stampDao.findListByPoliceArea2(stamp);
//        if (stamp.getStampShape() == null) {
//
//            for (StampShape stampShape : StampShape.values()) {
//                stamp.setStampShape(stampShape.getKey());
//                list.addAll(stampDao.findListByPoliceArea(stamp));
//            }
//        } else {
//        }
        page.setList(list);
        return page;
    }



    /**
     * @author 许彩开
     * @TODO (注：随机产生字母+数字组合)
     * @param length
     * @DATE: 2017\10\23 0023 14:40
     */

    public String getCharAndNumr(int length)
    {
        String val = "";

        Random random = new Random();
        for(int i = 0; i < length; i++)
        {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice =65 /*random.nextInt(2) % 2 == 0 ? 65 : 97*/; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            }
            else if("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }

    public StampWorkType getNameByType(String type){
        StampWorkType stampWorkType=null;
        if(type.equals("chang")){
            stampWorkType =  StampWorkType.CHANGE;
        }
        if(type.equals("reset")){
            stampWorkType =   StampWorkType.REPAIR;
        }
        if(type.equals("cancal")){
            stampWorkType =   StampWorkType.LOGOUT;
        }
        if(type.equals("miss")){
            stampWorkType =   StampWorkType.REPORT;
        }
        return  stampWorkType;
    }


}
