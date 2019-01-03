package com.thinkgem.jeesite.modules.stamp.service;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.areaattachment.dao.AreaAttachmentDao;
import com.thinkgem.jeesite.modules.areaattachment.entity.AreaAttachment;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentDirNotFoundException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.ServiceTypeEnum;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域附件 服务层
 * <p>
 * Created by Administrator on 2017/6/16.
 */
@Service
public class AreaAttachmentDirService {

    @Autowired
    private AreaAttachmentDao areaAttachmentDao;

    @Autowired
    private DictDao dictDao;

    @Autowired
    private UserDao userDao;


    @Transactional(readOnly = true)
    public List<Dict> getCurrentAreaAttachmentList(ServiceTypeEnum serviceTypeEnum) {

        try {

            Area area = null;

            User user = UserUtils.getUser();

            if (UserType.POLICE == user.getUserType()) {

                area = user.getPoliceInfo().getArea();

            } else if (UserType.POLICE != user.getUserType()) {

                area = user.getCompanyInfo().getArea();

            }

            AreaAttachment areaAttachment = areaAttachmentDao.get(new AreaAttachment(area, serviceTypeEnum.getKey()));

            List<String> attachList = new ArrayList<String>();

            List<Dict> dicts = null;

            if (areaAttachment != null && !"".equals(areaAttachment)) {

                attachList = (List<String>) JsonMapper.fromJsonString(areaAttachment.getJsonAttachList(), List.class);


                Dict dict = new Dict();

                dict.setType("file_type");

                dicts = dictDao.findDicts(attachList, dict);

            }

            return dicts;


        } catch (AreaAttachmentDirNotFoundException e) {

            throw e;

        } catch (AreaAttachmentException e) {

            throw e;

        } catch (Exception e) {

            throw new AreaAttachmentException("AreaAttachmentDir Error!");
        }

    }

    @Transactional(readOnly = true)
    public List<String> getCurrentAreaAttachmentList_copy(ServiceTypeEnum serviceTypeEnum) {

        try {

            Area area = null;

            User user = UserUtils.getUser();

            if (UserType.POLICE == user.getUserType()) {

                area = user.getPoliceInfo().getArea();

            } else if (UserType.POLICE != user.getUserType()) {

                area = user.getCompanyInfo().getArea();

            }

            AreaAttachment areaAttachment = areaAttachmentDao.get(new AreaAttachment(area, serviceTypeEnum.getKey()));

            List<String> requiredList = new ArrayList<String>();

            if (areaAttachment != null && !"".equals(areaAttachment)) {

                if (areaAttachment.getJsonRequiredList().substring(0, 1).equals("[")) {
                    requiredList = (List<String>) JsonMapper.fromJsonString(areaAttachment.getJsonRequiredList(), List.class);
                }

            }

            return requiredList;


        } catch (AreaAttachmentDirNotFoundException e) {

            throw e;

        } catch (AreaAttachmentException e) {

            throw e;

        } catch (Exception e) {

            throw new AreaAttachmentException("AreaAttachmentDir Error!");
        }

    }


    @Transactional(readOnly = true)
    public List<Dict> getCurrentAreaAttachmentList(ServiceTypeEnum serviceTypeEnum, Area area) {

        try {


            if (area == null) {
                User user = UserUtils.getUser();

                if (UserType.POLICE == user.getUserType()) {

                    area = user.getPoliceInfo().getArea();

                } else if (UserType.POLICE != user.getUserType()) {

                    area = user.getCompanyInfo().getArea();

                }
            }


            AreaAttachment areaAttachment = areaAttachmentDao.get(new AreaAttachment(area, serviceTypeEnum.getKey()));

            System.out.println(areaAttachment.toString());

            List<String> attachList = (List<String>) JsonMapper.fromJsonString(areaAttachment.getJsonAttachList(), List.class);

            Dict dict = new Dict();

            dict.setType("file_type");

            List<Dict> dicts = dictDao.findDicts(attachList, dict);

            return dicts;


        } catch (AreaAttachmentDirNotFoundException e) {

            throw e;

        } catch (AreaAttachmentException e) {

            throw e;

        } catch (Exception e) {

            throw new AreaAttachmentException("AreaAttachmentDir Error!");
        }

    }
}
