/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.areaattachment.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.areaattachment.entity.AreaAttachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域对应办事附件DAO接口
 * @author Locker
 * @version 2017-06-16
 */
@MyBatisDao
public interface AreaAttachmentDao  {

    /**
     *
     * 获得单个区域附件设定相关信息
     *
     * @param areaAttachment
     * @return
     */
    public AreaAttachment get(AreaAttachment areaAttachment);

    /**
     * 查询区域附件设定，返回列表
     * @param areaAttachment
     * @return
     */
    public List<AreaAttachment> findList(AreaAttachment areaAttachment);

    /**
     * 新增区域附件设定
     * @param areaAttachment
     * @return
     */
    public int insert(AreaAttachment areaAttachment );

    /**
     * 更新某一条区域附件设定
     * @param areaAttachment
     * @return
     */
    public int update(AreaAttachment areaAttachment);

    /**
     * 删除指定的区域附件设定
     * @param areaAttachmentId
     * @return
     */
    public int delete (@Param(value="id")String areaAttachmentId);

    /**
     * 查询区域附件是否存在
     * @param areaAttachment
     * @return
     */
    public int checkAreaAttachmentExist(AreaAttachment areaAttachment);

}