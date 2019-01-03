package com.thinkgem.jeesite.modules.stamp.entity.stamp;

/**
 * 刻章点对应的印模信息Entity
 * @author bb
 * @version 2018-03-25
 */
public class StampMakeComp {

    private String id;

    private String makecompId;//刻章点ID

    private String eleModel; //印模路径

    public StampMakeComp(){

    }

    public StampMakeComp(String id){

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMakecompId() {
        return makecompId;
    }

    public void setMakecompId(String makecompId) {
        this.makecompId = makecompId;
    }

    public String getEleModel() {
        return eleModel;
    }

    public void setEleModel(String eleModel) {
        this.eleModel = eleModel;
    }
}
