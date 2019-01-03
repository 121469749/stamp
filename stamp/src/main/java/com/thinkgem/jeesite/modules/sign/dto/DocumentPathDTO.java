package com.thinkgem.jeesite.modules.sign.dto;

/**
 * Created by Administrator on 2017/8/31.
 */
public class DocumentPathDTO {

    private String undoneRealPath; // 未签章的实际路径

    private String doneRealPath; // 签章的实际路径

    private String doneVirtualPath; // 签章后的虚拟路径

    private String doneDir; //签章文件夹路径

    public String getUndoneRealPath() {
        return undoneRealPath;
    }

    public void setUndoneRealPath(String undoneRealPath) {
        this.undoneRealPath = undoneRealPath;
    }

    public String getDoneRealPath() {
        return doneRealPath;
    }

    public void setDoneRealPath(String doneRealPath) {
        this.doneRealPath = doneRealPath;
    }

    public String getDoneVirtualPath() {
        return doneVirtualPath;
    }

    public void setDoneVirtualPath(String doneVirtualPath) {
        this.doneVirtualPath = doneVirtualPath;
    }

    public String getDoneDir() {
        return doneDir;
    }

    public void setDoneDir(String doneDir) {
        this.doneDir = doneDir;
    }
}
