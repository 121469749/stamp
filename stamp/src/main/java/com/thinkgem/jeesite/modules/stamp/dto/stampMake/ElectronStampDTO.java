package com.thinkgem.jeesite.modules.stamp.dto.stampMake;

import com.thinkgem.jeesite.modules.stamp.entity.stamp.Electron;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;

/**
 *
 * 刻制电子印模dto
 *
 * Created by Locker on 2017/7/16.
 */
public class ElectronStampDTO {

    private Stamp stamp;

    private Electron electron;

    private String phyStampId;

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public Electron getElectron() {
        return electron;
    }

    public void setElectron(Electron electron) {
        this.electron = electron;
    }

    public String getPhyStampId() {
        return phyStampId;
    }

    public void setPhyStampId(String phyStampId) {
        this.phyStampId = phyStampId;
    }
}
