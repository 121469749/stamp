package com.thinkgem.jeesite.modules.stamp.service.makeStampCompany;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.ReflectUtils;
//import com.thinkgem.jeesite.modules.log.entity.TLogDetail;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampState;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.dao.AttachmentDao;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.NewStampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampRecordDao;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.EditAttachmentDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.StampMakeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 新增业务
 * <p>
 * Created by Locker on 2017/10/3.
 */
@Service
@Transactional(readOnly = false)
public class StampMakeEditService {


    @Autowired
    private StampDao stampDao;

    @Autowired
    private StampRecordDao stampRecordDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private NewStampDao newStampDao;

    /**
     * 修改备案信息
     *
     * @param stampRecord
     * @return
     */
    public Condition editCompanyAndStampRecordInfo(StampRecord stampRecord) {

        StampRecord oldRecord = new StampRecord(stampRecord.getId());


        oldRecord.setWorkType(stampRecord.getWorkType());

        oldRecord = stampRecordDao.get(oldRecord);
        oldRecord.setStamp(stampRecord.getStamp());
        //记录操作日志用
        Company oldCompany = new Company();

        oldCompany = oldRecord.getUseComp();
        oldCompany.setCompType(CompanyType.USE);
        oldCompany = companyDao.get(oldCompany);
        //
        //更新公司信息
        Company company = oldRecord.getUseComp();
        company.setCompType(CompanyType.USE);
        //法人
        company.setLegalPhone(stampRecord.getLegalPhone());
        company.setLegalName(stampRecord.getLegalName());
        company.setLegalCertType(stampRecord.getLegalCertType());
        company.setLegalCertCode(stampRecord.getLegalCertCode());
        //公司其他信息
        company.setCompAddress(stampRecord.getCompAddress());
        company.setType1(stampRecord.getType1());
        company.setCompPhone(stampRecord.getCompPhone());

        //修改信息操作日志保存入口
        ReflectUtils.packageModifyContent(company,oldCompany ,
                oldRecord,
                "t_company_"+company.getCompType().getKey());
        ReflectUtils.packageModifyContent(stampRecord,oldRecord ,
                oldRecord,
                "t_stamprecord_"+stampRecord.getWorkType().getKey());
        //
        if (companyDao.editCompanyInfo(company) != 1) {

            throw new StampMakeException("error");
        }

        if (stampRecordDao.editStampRecord(stampRecord) != 1) {

            throw new StampMakeException("error");

        }

        return new Condition(Condition.SUCCESS_CODE);
    }

    /**
     * 修改电子印模
     *
     * @param file
     * @param id
     * @param stampShape
     * @return
     */
    /*public Condition editEleModel(MultipartFile file, String id, String stampShape) {
        try {
            //印章信息
            Stamp stamp = new Stamp(id);
            stamp.setStampShape(stampShape);
            stamp = stampDao.get(stamp);
            //公司信息
            Company useCompany = stamp.getUseComp();
            useCompany.setCompType(CompanyType.USE);
            useCompany = companyDao.get(useCompany);
            //对电子印模的存储
            String eleMoulageStr = attachmentService.saveMoulage(file, "2", useCompany.getCompanyName(), stamp.getStampName());

            stamp.setEleModel(eleMoulageStr);

            if (stampDao.updateEleModel(stamp) != 1) {

                throw new StampMakeException("error");
            }

            return new Condition(Condition.SUCCESS_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StampMakeException("error");
        }


    }*/

    /**
     * 修改备案附件内容
     *
     * @param files
     * @param dto
     * @return
     */
    public Condition editAttachments(MultipartFile[] files, EditAttachmentDTO dto) {
        try {
            //获得原来有的stampRecord
            StampRecord stampRecord = new StampRecord();

            stampRecord.setId(dto.getRecordId());
            stampRecord.setWorkType(dto.getStampWorkType());
            stampRecord = stampRecordDao.get(stampRecord);
            //此处设置是为了日志记录判断是在哪里修改的记录：待刻、已制作
            stampRecord.setStamp(dto.getStamp());

            //获得useCompany
            Company useCompany = stampRecord.getUseComp();
            useCompany.setCompType(CompanyType.USE);
            useCompany = companyDao.get(useCompany);
            //end

            List<String> deleteIds = dto.getDeleteIds();
            //获得老附件Json
            String oldAttachmentStr = stampRecord.getAttachs();

            List<Attachment> oldCheckAttachments = attachmentService.jsonStrToAttachments(oldAttachmentStr);
            List<Attachment> oldAttachments = attachmentService.jsonStrToAttachments(oldAttachmentStr);
            //如果有要删除的Attachment;
            boolean deleteFlag = false;
            if (deleteIds != null && deleteIds.size() > 0) {
                deleteFlag = true;
                for (String id : deleteIds) {
                    for (int i = 0; i < oldAttachments.size(); i++) {
                        Attachment attachment = oldAttachments.get(i);
                        if (attachment.getId().equals(id)) {
                            //删除附件日志保存入口
                            ReflectUtils.deleteAttachsLog(attachment,stampRecord);

                            oldAttachments.remove(i);
                            break;
                        }

                    }
                }
            }
            //删除完成


            //新附件处理 start

            List<String> fileTypes = dto.getFileType();
            String newJsonAttachment = "";
            boolean addFlag = false;
            if (fileTypes != null) {
                if (fileTypes.size() > 0) {
                    addFlag = true;
                    List<Attachment> addAttachments = attachmentService.setUUIDList(fileTypes);

                    oldAttachments.addAll(addAttachments);

                    newJsonAttachment = JsonMapper.toJsonString(oldAttachments);

                    attachmentService.saveAttachmentsInHosts(addAttachments, files, useCompany.getCompanyName());
                    attachmentDao.insertAttachmentList(addAttachments);

                    //添加附件日志保存入口
                    for(Attachment attachment : addAttachments) {

                        ReflectUtils.addAttachsLog(attachment,stampRecord);

                    }

                }
            }

            //附件处理 end

            //有删除也有增加
            if (addFlag && deleteFlag) {

                stampRecord.setAttachs(newJsonAttachment);

            }

            //增加没删除
            if (addFlag == true && deleteFlag == false) {

                stampRecord.setAttachs(newJsonAttachment);

            }

            //删除没增加
            if (addFlag == false && deleteFlag == true) {

                stampRecord.setAttachs(JsonMapper.toJsonString(oldAttachments));

            }

            if (stampRecordDao.editAttachs(stampRecord) != 1) {

                throw new StampMakeException("error");
            }


            return new Condition(Condition.SUCCESS_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StampMakeException("error");
        }
    }

    /**
     * 作废印章
     *
     * @param id
     * @param stampShape
     * @return
     */
    public Condition cancelStamp(String id, String stampShape) {

        Stamp stamp = new Stamp(id);

        stamp.setStampShape(stampShape);

        if (stampDao.delete(stamp) == 1) {

            return new Condition(Condition.SUCCESS_CODE, "作废成功!");

        } else {

            return new Condition(Condition.ERROR_CODE, "作废失败!");
        }

    }

    public Condition returnRecept(String id, String stampShape) {

        Stamp stamp = new Stamp(id);
        stamp.setStampShape(stampShape);
        stamp = stampDao.get(stamp);

        if (stamp.getStampState() == StampState.ENGRAVE) {

            stamp.setStampState(StampState.RECEPT);

            if(1==newStampDao.rollBackStampState(stamp)){

                return new Condition(Condition.SUCCESS_CODE,"返还成功!请前去待刻列表重新刻制!");

            }else{

                return new Condition(Condition.ERROR_CODE,"系统繁忙!");
            }

        } else {

            return new Condition(Condition.ERROR_CODE, "此印章不允许反还状态!");

        }
    }

}
