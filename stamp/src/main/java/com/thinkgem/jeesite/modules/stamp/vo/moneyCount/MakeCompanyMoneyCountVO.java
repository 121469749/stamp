package com.thinkgem.jeesite.modules.stamp.vo.moneyCount;

import com.thinkgem.jeesite.modules.stamp.entity.company.Company;

/**
 *
 * Created by Locker on 2017/7/24.
 *
 */
public class MakeCompanyMoneyCountVO {

    private int phyStampCount; //当前用章公司 在当前刻章点上的刻制物理印章的数目

    private int phyStampCountMoney; // 物理印章收费数目

    private int eleStampCount; //当前用章公司 在当前刻章点上的刻制电子印章的数目

    private int eleStampCountMoney; //电子印章收费


    public int getPhyStampCount() {
        return phyStampCount;
    }

    public void setPhyStampCount(int phyStampCount) {
        this.phyStampCount = phyStampCount;
    }

    public int getPhyStampCountMoney() {
        return phyStampCountMoney;
    }

    public void setPhyStampCountMoney(int phyStampCountMoney) {
        this.phyStampCountMoney = phyStampCountMoney;
    }

    public int getEleStampCount() {
        return eleStampCount;
    }

    public void setEleStampCount(int eleStampCount) {
        this.eleStampCount = eleStampCount;
    }

    public int getEleStampCountMoney() {
        return eleStampCountMoney;
    }

    public void setEleStampCountMoney(int eleStampCountMoney) {
        this.eleStampCountMoney = eleStampCountMoney;
    }
}
