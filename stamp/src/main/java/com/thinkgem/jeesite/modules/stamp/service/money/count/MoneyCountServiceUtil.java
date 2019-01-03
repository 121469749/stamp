package com.thinkgem.jeesite.modules.stamp.service.money.count;

import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.vo.moneyCount.CompanyMoneyCountVO;

import java.util.List;

/**
 * Created by Locker on 17/7/24.
 */
public class MoneyCountServiceUtil {

    /**
     * 统计 刻章点 收费
     *  stamp -MakeMoney
     * <p>
     * 计算 物理印章 的数量 和金额
     *
     * @param phyStamps
     * @param vo
     * @return
     */
    public static CompanyMoneyCountVO countPhyStampsMakeMoney(List<Stamp> phyStamps, CompanyMoneyCountVO vo) {

        if (phyStamps == null || phyStamps.size() == 0) {

            vo.setPhyStampCount(0);

            vo.setPhyStampCountMoney(0);
        } else {

            int moneyCount = 0;

            vo.setPhyStampCount(phyStamps.size());

            for (Stamp stamp : phyStamps) {

                moneyCount += stamp.getMakeMoney();

            }

            vo.setPhyStampCountMoney(moneyCount);
        }

        return vo;

    }

    /**
     * 统计 刻章点 收费
     * stamp-MakeMoney
     *
     * 计算电子印章的数量和金额
     * @param eleStamps
     * @param vo
     * @return
     */
    public static CompanyMoneyCountVO countEleStampMakeMoney(List<Stamp> eleStamps, CompanyMoneyCountVO vo) {

        if (eleStamps == null || eleStamps.size() == 0) {

            vo.setEleStampCount(0);
            vo.setEleStampCountMoney(0);
        } else {

            int moneyCount = 0;

            vo.setEleStampCount(eleStamps.size());

            for (Stamp stamp : eleStamps) {

                moneyCount += stamp.getMakeMoney();
            }
            vo.setEleStampCountMoney(moneyCount);
        }

        return vo;
    }

    /**
     * 统计 市经销商 收费
     * stamp-cityMoney
     * 计算物理印章的数量和金额
     *
     * @param phyStamps
     * @param vo
     * @return
     */
    public static CompanyMoneyCountVO countPhyStampsCityMoney(List<Stamp> phyStamps, CompanyMoneyCountVO vo) {

        if (phyStamps == null || phyStamps.size() == 0) {
            vo.setPhyStampCount(0);
            vo.setPhyStampCountMoney(0);
        } else {

            int moneyCount = 0;

            vo.setPhyStampCount(phyStamps.size());

            for (Stamp stamp : phyStamps) {

                moneyCount += stamp.getCityMoney();

            }

            vo.setPhyStampCountMoney(moneyCount);
        }

        return vo;
    }

    /**
     * 统计 市经销商 收费
     * stamp-cityMoney
     * 计算物理印章的数量和金额
     *
     * @param eleStamps
     * @param vo
     * @return
     */
    public static CompanyMoneyCountVO countEleStampsCityMoney(List<Stamp> eleStamps, CompanyMoneyCountVO vo) {

        if (eleStamps == null || eleStamps.size() == 0) {
            vo.setEleStampCount(0);
            vo.setEleStampCountMoney(0);
        } else {

            int moneyCount = 0;

            vo.setEleStampCount(eleStamps.size());

            for (Stamp stamp : eleStamps) {

                moneyCount += stamp.getCityMoney();

            }

            vo.setEleStampCountMoney(moneyCount);
        }

        return vo;
    }

    /**
     * 统计 省经销商 收费
     * stamp-provinceMoney
     * 计算物理印章的数量和金额
     *
     * @param phyStamps
     * @param vo
     * @return
     */
    public static CompanyMoneyCountVO countPhyStampsProvinceMoney(List<Stamp> phyStamps, CompanyMoneyCountVO vo) {

        if (phyStamps == null || phyStamps.size() == 0) {

            vo.setPhyStampCount(0);

            vo.setPhyStampCountMoney(0);
        } else {

            int moneyCount = 0;

            vo.setPhyStampCount(phyStamps.size());

            for (Stamp stamp : phyStamps) {
                moneyCount += stamp.getProvinceMoney();
            }

            vo.setPhyStampCountMoney(moneyCount);
        }

        return vo;
    }

    /**
     * 统计 省经销商收费
     *stamp-provinceMoney
     * 计算电子印章的数量和金额
     * @param eleStamps
     * @param vo
     * @return
     */
    public static CompanyMoneyCountVO countEleStampsProvinceMoney(List<Stamp> eleStamps, CompanyMoneyCountVO vo) {

        if (eleStamps == null || eleStamps.size() == 0) {

            vo.setEleStampCount(0);

            vo.setEleStampCountMoney(0);

        } else {

            int moneyCount = 0;

            vo.setEleStampCount(eleStamps.size());

            for (Stamp stamp : eleStamps) {

                moneyCount += stamp.getProvinceMoney();

            }

            vo.setEleStampCountMoney(moneyCount);
        }

        return vo;
    }

    /**
     * 统计 润城经销商 收费
     *  stamp-RcMoney
     * 计算物理印章的数量和金额
     * @param phyStamps
     * @param vo
     * @return
     */
    public static CompanyMoneyCountVO countPhyStampsRcMoney(List<Stamp> phyStamps ,CompanyMoneyCountVO vo){

        if (phyStamps == null || phyStamps.size() == 0) {

            vo.setPhyStampCount(0);

            vo.setPhyStampCountMoney(0);

        } else {

            int moneyCount = 0;

            vo.setPhyStampCount(phyStamps.size());

            for (Stamp stamp : phyStamps) {

                moneyCount += stamp.getRcMoney();

            }

            vo.setPhyStampCountMoney(moneyCount);
        }

        return vo;

    }

    /**
     * 统计 润城经销商 收费
     * stamp-RcMoney
     * 计算电子印章的数量和金额
     * @param eleStamps
     * @param vo
     * @return
     */
    public static CompanyMoneyCountVO countEleStampsRcMoney(List<Stamp> eleStamps ,CompanyMoneyCountVO vo){

        if (eleStamps == null || eleStamps.size() == 0) {

            vo.setEleStampCount(0);

            vo.setEleStampCountMoney(0);

        } else {

            int moneyCount = 0;

            vo.setEleStampCount(eleStamps.size());

            for (Stamp stamp : eleStamps) {

                moneyCount += stamp.getRcMoney();

            }

            vo.setEleStampCountMoney(moneyCount);
        }

        return vo;
    }

}
