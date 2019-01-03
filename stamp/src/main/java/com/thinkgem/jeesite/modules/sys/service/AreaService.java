/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.crypto.Cipher;
import java.util.List;

/**
 * 区域Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

    @Autowired
    private AreaDao areaDao;

    public List<Area> findAll() {
        return UserUtils.getAreaList();
    }


    @Transactional(readOnly = false)
    public void save(Area area) {
        super.save(area);
        UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
        CacheUtils.remove(CacheUtils.CACHE_AREA_LIST);
    }

    @Transactional(readOnly = false)
    public void delete(Area area) {
        super.delete(area);
        UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
        CacheUtils.remove(CacheUtils.CACHE_AREA_LIST);
    }

    /*
     *@author hjw
     *@description spring启动回调方法，用于预加载AreaList
     *@param []
     *@return void
     *@date 2017/5/21
     */
    @PostConstruct
    public void initAreaData() throws Exception {
        List<Area> areaList = areaDao.findAllList(new Area());
        CacheUtils.put(CacheUtils.CACHE_AREA_LIST, areaList);
    }

    /*
     *@author hjw
     *@description spring销毁回调
     *@param []
     *@return void
     *@date 2017/5/21
     */
    @PreDestroy
    public void cleanUp() throws Exception {
        CacheUtils.remove(CacheUtils.CACHE_AREA_LIST);
    }

    /**
     * 从系统缓存中获取所有区域
     *
     * @return
     */
    public List<Area> getAllAreaList() {
        @SuppressWarnings("unchecked")
        List<Area> areaList = (List<Area>) CacheUtils.get(CacheUtils.CACHE_AREA_LIST);
        if (areaList == null) {
            areaList = areaDao.findAllList(new Area());
            CacheUtils.put(CacheUtils.CACHE_AREA_LIST, areaList);
        }
        return areaList;
    }

    /**
     * 查找所有省(只会搜索出一层)
     *
     * @return
     */
    public List<Area> findAllOnlyProvince() {

        List<Area> onlyProvinces = (List<Area>) CacheUtils.get("onlyProvinces");

        if (onlyProvinces == null) {
            Area checkArea = new Area();
            checkArea.setType("2");//省

            onlyProvinces = areaDao.findCustomAreaListByAreaType(checkArea);
            CacheUtils.put("onlyProvinces", onlyProvinces);
        }

        return onlyProvinces;
    }

    /**
     * 搜索所有省 包括剩下的市(两层)
     * @return
     */
    public List<Area> findAllProvince() {

        List<Area> provinces = (List<Area>) CacheUtils.get("Provinces");

        if (provinces == null) {

            provinces = areaDao.findALLProvince();

            CacheUtils.put("Provinces", provinces);
        }

        return provinces;

    }

    /**
     * 只限于公司用户去调用
     * @return
     */
    public List<Area> findCurrentAreaSubAreas(){

        Area currentArea = UserUtils.getUser().getCompanyInfo().getArea();

        List<Area> provinceSubAreas = areaDao.getSubAreaByPareanId(currentArea.getId());

        return provinceSubAreas;
    }

    /**
     * 只限于公司用户去调用
     * @return
     */
    public List<Area> findCurrentAreaSubAreasALayerByCompany(){

        Area currentArea = UserUtils.getUser().getCompanyInfo().getArea();

        List<Area> provinceSubAreas = areaDao.findAreasByParentArea(currentArea);

        return provinceSubAreas;
    }

    public List<Area> findCurrentAreaSubAreasALayerByPolice(){

        Area currentArea= UserUtils.getUser().getPoliceInfo().getArea();

        List<Area> provinceSubAreas = areaDao.findAreasByParentArea(currentArea);

        return provinceSubAreas;

    }

    public List<Area> findCurrentAreaSubAreasByPolice(){

        Area currentArea = UserUtils.getUser().getPoliceInfo().getArea();

        List<Area> provinceSubAreas = areaDao.getSubAreaByPareanId(currentArea.getId());

        return provinceSubAreas;

    }


    public int checkSubAreasExist(Area area){

        return areaDao.checkSubAreasExist(area);
    }

    public Area get(Area area){
        return areaDao.get(area);
    }

    public Area getAreaByCode(String areaCode){
        return areaDao.getAreaByCode(areaCode);
    }

}
