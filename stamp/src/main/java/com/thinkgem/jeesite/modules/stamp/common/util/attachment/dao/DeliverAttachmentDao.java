package com.thinkgem.jeesite.modules.stamp.common.util.attachment.dao;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.DeliverAttachment;

import java.util.List;

/**
 * Created by bb on 2018/4/12.
 */
@MyBatisDao
public interface DeliverAttachmentDao {

    public List<DeliverAttachment> findListByStampId(String stampId);
}
