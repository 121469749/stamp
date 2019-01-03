package com.thinkgem.jeesite.modules.stamp.service.makeStampCompany;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CheckState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.dao.AttachmentDao;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.licence.LicenceDao;
import com.thinkgem.jeesite.modules.stamp.dao.makeStampCompany.MakeCompanyLicenseDao;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.LicenseApplyDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.exception.police.RegisterMakeComException;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 制章点用户
 * 关于 许可证的操作
 * <p>
 * Created by Locker on 2017/5/19.
 */
@Service
public class MakeCompanyLicenseService {

    @Autowired
    private MakeCompanyLicenseDao makeCompanyLicenseDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private LicenceDao licenceDao;

    /**
     * @return true - 到了年审时刻
     * false - 还没到
     * @userType-制章点 判断是否到了年审时刻
     * @author Locker
     */
    public boolean judgeYear() {

        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();

        //根据营业时间判断
        if (DateUtils.getDistanceOfTwoDate(company.getBusStartDate(), company.getBusEndDate()) > 365) {

            return true;
        }
        return false;
    }

    /**
     * 判断可否进行 变更或者撤销 操作
     * 当且只有许可证状态在可用的情况下才可以进行此操作
     *
     * @return true -- 可以进行变更操作
     * fasle -- 不可以进行变更操作
     * @author Locker
     */
    public boolean judgeChangeState() {

        User user = UserUtils.getUser();
        Company company = user.getCompanyInfo();
        WorkType workType = company.getLastLicenceState();

        //判断当前公司最新的许可证记录
        if (makeCompanyLicenseDao.checkOtherLicence(company.getId(), workType) == CheckState.CHECKSUCCESS) {
            return true;
        }

        return false;
    }

    /**
     * @param licenseApplyDTO
     * @param multipartFile
     * @userType-制章点
     * @action 提交 许可证开办申请、年审信息、变更申请、注销申请 接口
     * @author Locker
     */
    @Transactional(readOnly = false)
    public Condition saveLicence(LicenseApplyDTO licenseApplyDTO, MultipartFile[] multipartFile, Condition condition) {



        Licence licence = licenseApplyDTO.getLicence();

        try {
            //再次检查  OPEN在前面拦截
            if (licence.getWorkType() != WorkType.OPEN) {
                if (!judgeChangeState()) {
                    condition.setCode(Condition.NOALLOW_CODE);
                    condition.setMessage("已经有其他操作!");
                    return condition;
                }
            }

            List<Attachment> attachments = attachmentService.setUUIDList(licenseApplyDTO.getFileType());

            String jsonAttachment = JsonMapper.toJsonString(attachments);



            attachmentService.saveAttachmentsInHosts(attachments, multipartFile, licence.getCompName());

            attachmentDao.insertAttachmentList(attachments);



            //处理附件
            licence.setAttachs(jsonAttachment);


            licence.setCheckState(CheckState.CHECKING);
            licence.preInsert();
            licenceDao.insert(licence);

            condition.setCode(Condition.SUCCESS_CODE);

            condition.setMessage("操作成功!");
            condition.setUrl(Global.getAdminPath()+"/makeStampAction/licenseForm?workType=OPEN");

            //更新该刻章点最新workType
            Company company = new Company();
            company.setId(licence.getMakeComp().getId());
            companyDao.updateLastLicenseState(company, licence.getWorkType());

        } catch (Exception e) {

            e.printStackTrace();

            condition.setCode(Condition.ERROR_CODE);

            condition.setMessage("系统繁忙!");
            throw new RuntimeException();

        } finally {
            return condition;
        }
    }


    /**
     * 获得当前制章点用户的公司信息
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Company getMakeCompanyInfo() {

        User user = UserUtils.getUser();

        Company company = user.getCompanyInfo();

        return companyDao.get(new Company(company.getId()));
    }

    @Transactional(readOnly = true)
    public Licence getLicence(String comId, WorkType workType) {
        return makeCompanyLicenseDao.getLicence(comId, workType);
    }


    @Transactional(readOnly = true)
    public Licence checkNewestLicence(Company com) {
        Company company = companyDao.get(com);
        //没有licence
        if (company.getLastLicenceState() == null) {
            return null;
        }
        return makeCompanyLicenseDao.checkNewestLicence(com.getId(), com.getLastLicenceState());
    }

}
