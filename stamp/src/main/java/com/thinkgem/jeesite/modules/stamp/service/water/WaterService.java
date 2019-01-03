package com.thinkgem.jeesite.modules.stamp.service.water;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sign.common.utils.FileUtil;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.dao.water.WaterDao;
import com.thinkgem.jeesite.modules.stamp.entity.water.Water;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * 水印管理服务层
 *
 * Created by Administrator on 2017/11/18.
 */
@Service
public class WaterService {

    @Autowired
    private WaterDao waterDao;

    /**
     * 根据一定的条件搜索水印列表
     * @param water
     * @return
     */
    @Transactional(readOnly = true)
    public List<Water> findList(Water water){

        return waterDao.findList(water);
    }


    /**
     * 水印列表分页
     * @param water
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Water> findPage(Water water, Page<Water> page) {

        water.setPage(page);

        List<Water> list = waterDao.findList(water);

        page.setList(list);

        return page;
    }

    /**
     * 获得某一个水印的信息
     * @param water
     * @return
     */
    @Transactional(readOnly = true)
    public Water get(Water water) {
        return waterDao.get(water);
    }

    /**
     * 保存水印
     * @param water
     * @param file
     * @return
     */
    @Transactional(readOnly = false)
    public Condition save(Water water, MultipartFile file) {
        try {
            if (water.getId() == null || water.getId().equals("")) {
                water.setId(IdGen.uuid());
                water.setUpdateBy(UserUtils.getUser());
                water.setCreateBy(UserUtils.getUser());

                water.setFilePath(saveWaterImage(water, file));

                if (waterDao.insert(water) == 1) {

                    return new Condition(Condition.SUCCESS_CODE, "保存成功!");
                } else {

                    return new Condition(Condition.ERROR_CODE, "保存失败!");
                }

            } else {

                if(file==null||file.isEmpty()){
                    water.setFilePath(null);
                }else{
                    water.setFilePath(saveWaterImage(water, file));
                }

                water.setUpdateBy(UserUtils.getUser());

                if (waterDao.update(water) == 1) {
                    return new Condition(Condition.SUCCESS_CODE, "保存成功!");
                } else {

                    return new Condition(Condition.ERROR_CODE, "保存失败!");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

            return new Condition(Condition.ERROR_CODE,"系统繁忙!\n请联系管理员");
        }

    }

    /**
     * 删除
     * @param water
     * @return
     */
    @Transactional(readOnly = false)
    public Condition delete(Water water) {

        if (waterDao.delete(water) == 1) {
            return new Condition(Condition.SUCCESS_CODE, "删除成功!");
        } else {

            return new Condition(Condition.ERROR_CODE, "删除失败!");
        }

    }

    /**
     * 保存水印图片到本地硬盘
     * @param water
     * @param file
     * @return
     * @throws IOException
     */
    protected String saveWaterImage(Water water, MultipartFile file) throws IOException {
        //获得网络文件名
        String netAttachName = file.getOriginalFilename();

        //  获取该文件的后缀文件类型
        int lastIndex = netAttachName.lastIndexOf(".");

        String lastName = netAttachName.substring(lastIndex, netAttachName.length());

        String realPath = Global.getConfig("water.realPath") + water.getName() + lastName;

        FileUtils.createDirectory(Global.getConfig("water.realPath"));

        file.transferTo(new File(realPath));


        String virtualPath = Global.getConfig("water.virtualPath") + water.getName() + lastName;


        return virtualPath;
    }

}
