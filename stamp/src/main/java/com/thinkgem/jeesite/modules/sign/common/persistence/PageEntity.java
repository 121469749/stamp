package com.thinkgem.jeesite.modules.sign.common.persistence;


import com.thinkgem.jeesite.modules.sign.common.page.Page;

/**
 * Created by Administrator on 2017/9/13.
 */
public class PageEntity<T> extends BaseEntity<T>{


    private Page<T> page;


    public PageEntity() {
    }

    public PageEntity(String id) {
        super(id);
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }
}
