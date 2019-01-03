package com.thinkgem.jeesite.modules.stamp.common.util.attachment.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Locker on 17/5/9.
 */
@MyBatisDao
public interface AttachmentDao {

//    public int saveAttachment(Attachment attachment);
//
//    public List<Attachment> findListByIds(List<String> ids);

    public int save(Attachment attachment);

    public List<Attachment> findList();

    public List<Attachment> findListByIdList(List<String> list);

    public int insertAttachmentList(List<Attachment> attachments);

    public List<Attachment> findListByJsonList(@Param(value = "type")String type,@Param(value="list") List<Attachment> attachments);

    //  public List<Attachment> test(Attachment attachment);

    public Attachment getAttachment(Attachment attachment);


    // 传入多个id,逗号隔开,只返回一条拼接好的路径记录,多个逗号隔开
    public String findAttachmentsPathByIds(@Param(value = "ids")String ids);
}
