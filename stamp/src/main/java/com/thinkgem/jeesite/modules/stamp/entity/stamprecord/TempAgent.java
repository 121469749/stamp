package com.thinkgem.jeesite.modules.stamp.entity.stamprecord;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * Created by Administrator on 2018\7\4 0004.
 */
public class TempAgent extends DataEntity<TempAgent> {

    private String agentName;        // 经办人姓名
    private String agentCertType;        // 经办人证件类型
    private String agentCertCode;        // 经办人证件号码
    private String agentPhone;        // 经办人电话号码

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentCertType() {
        return agentCertType;
    }

    public void setAgentCertType(String agentCertType) {
        this.agentCertType = agentCertType;
    }

    public String getAgentCertCode() {
        return agentCertCode;
    }

    public void setAgentCertCode(String agentCertCode) {
        this.agentCertCode = agentCertCode;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }
}
