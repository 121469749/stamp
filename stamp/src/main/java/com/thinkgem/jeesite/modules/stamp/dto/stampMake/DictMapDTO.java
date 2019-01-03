package com.thinkgem.jeesite.modules.stamp.dto.stampMake;

import com.thinkgem.jeesite.modules.sys.entity.Dict;

public class DictMapDTO {
    private Dict dict;
    private String key;

    public Dict getDict() {
        return dict;
    }

    public void setDict(Dict dict) {
        this.dict = dict;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
