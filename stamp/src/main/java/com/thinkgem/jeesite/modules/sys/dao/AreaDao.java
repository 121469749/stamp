/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
    /**
     * 通过区域编码查找区域对象信息
     * @param areaCode
     * @return
     */
    public Area getAreaByCode(@Param(value="areaCode") String areaCode);

    /**
     * 通过区域名称查找区域对象信息
     * @param name
     * @return
     */
    public Area getAreaByName(@Param(value="name") String name);

    /**
     * 通过区域类型查找区域对象信息集合
     * @param type
     * @return
     */
    public List<Area> getAreaByType(@Param(value="type") String type);

    /**
     * 检查区域是否存在
     * @param areaId
     * @return
     */
    public int checkAreaExist(@Param(value="areaid")String areaId);


    /**
     * @author 许彩开
     * @TODO (注：更新印章编码的数量)
      * @param areaId
     * @DATE: 2018\4\13 0013 15:49
     */

    public void updateCount(@Param(value = "areaId")String areaId ,@Param(value = "count")String count);

    /**
     * 查询当前父区域下的所有子区域 --所有
     * @param parentId
     * @return
     */
    public List<Area> getSubAreaByPareanId(@Param(value="parentId")String parentId);

    /**
     * 查询出所有的省区域
     * @param area
     * @return
     */
    public List<Area> findCustomAreaListByAreaType(Area area);

    /**
     * 查询出所有的省区域
     * @return
     */
    public List<Area> findALLProvince();

    /**
     *  查询当前的区域的所有子区域 --所有
     * @param area
     * @return
     */
    public List<Area> findSubAreasByParentId(Area area);

    public Area findAreaBySubArea(Area area);

    /**
     *  查询当前的区域的所有子区域 --一层
     * @param area
     * @return
     */
    public List<Area> findAreasByParentArea(Area area);

    /**
     * 检查子区域 数量
     * @param area
     * @return
     */
    public int checkSubAreasExist(Area area);



}
