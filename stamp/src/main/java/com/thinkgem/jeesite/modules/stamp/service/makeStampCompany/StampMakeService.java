package com.thinkgem.jeesite.modules.stamp.service.makeStampCompany;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.businessUtils.BusinessValidateUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.log.entity.ModifyInfoLog;
import com.thinkgem.jeesite.modules.log.service.ModifyInfoLogService;
import com.thinkgem.jeesite.modules.sign.entity.SinatureCerDTO;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.dao.AttachmentDao;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.dao.DeliverAttachmentDao;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.DeliverAttachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.dao.CountSet.CountSetDao;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.makeStampCompany.MakeCompanyStampDao;
import com.thinkgem.jeesite.modules.stamp.dao.moneySetting.MoneySettingDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.ElectronDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.MoulageDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.NewStampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampRecordDao;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.ElectronStampDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.MakeStampBusniessHanlerDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.StampMakeDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.StampWorkTypeDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Electron;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Moulage;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.StampMakeComp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.TempAgent;
import com.thinkgem.jeesite.modules.stamp.exception.moneySetting.MoneySettingNotFoundException;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.*;
import com.thinkgem.jeesite.modules.stamp.vo.makeStamp.ReceiptVo;
import com.thinkgem.jeesite.modules.stamp.vo.makeStamp.StampDeliveryVo;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.certificate.CertificateService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService.binaryImage;
import static com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService.transferAlpha;

/**
 * 制章公司的业务操作 服务层
 *
 * @author Locker
 *         <p>
 *         Created by Locker on 2017/5/20.
 */
@Service
public class StampMakeService {

    @Autowired
    private StampDao stampDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StampRecordDao stampRecordDao;

    @Autowired
    private MoulageDao moulageDao;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private MoneySettingDao moneySettingDao;

    @Autowired
    private ElectronDao electronDao;

    @Autowired
    private CountSetDao countSetDao;

    @Autowired
    private DeliverAttachmentDao deliverAttachmentDao;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private NewStampDao newStampDao;

    @Autowired
    private MakeCompanyStampDao makeCompanyStampDao;

    @Autowired
    private StampMakeService stampMakeService;

    @Autowired
    private ModifyInfoLogService modifyInfoLogService;

    /**
     * 根据 当前制章公司 查询印章列表
     *
     * @param page
     * @param stamp
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Stamp> findPage(Page<Stamp> page, Stamp stamp) {

        Company company = UserUtils.getUser().getCompanyInfo();

        /*设置查询条件 Start*/
        stamp.setNowMakeComp(company);

        stamp.setPage(page);
        /*设置查询条件 End*/
        if (stamp.getUseComp() != null) {
            if (StringUtils.isEmpty(stamp.getUseComp().getCompanyName())) {
                stamp.setUseComp(null);
            }
        }

        List<Stamp> stamps = stampDao.findListByCondition(stamp);

        //for (Stamp stamp1 : stamps) {
        //
        //    stamp1.setUseComp(companyDao.get(new Company(stamp1.getUseComp().getId(), CompanyType.USE)));
        //
        //    stamp1.setLastRecord(stampRecordDao.get(new StampRecord(stamp1.getLastRecord().getId(), stamp1.getRecordState())));
        //}

        page.setList(stamps);

        return page;
    }

    @Transactional(readOnly = true)
    public Page<Stamp> findPageDeveryList(Page<Stamp> page, Stamp stamp) {

        Company company = UserUtils.getUser().getCompanyInfo();

        /*设置查询条件 Start*/
        stamp.setNowMakeComp(company);

        stamp.setPage(page);
        /*设置查询条件 End*/
        if (stamp.getUseComp() != null) {
            if (StringUtils.isEmpty(stamp.getUseComp().getCompanyName())) {
                stamp.setUseComp(null);
            }
        }


        List<Stamp> stamps = new ArrayList<>();
        long startTime = System.currentTimeMillis();//获取开始时间

        if (stamp.getLastRecord() != null && !StringUtils.isEmpty(stamp.getLastRecord().getCompanyName())) {

            for (StampWorkType stampWorkType : StampWorkType.values()) {//遍历五次：因为RecordState有五个状态
                stamp.setRecordState(stampWorkType);
                List<Stamp> stamp2 = stampDao.findListByConditionDeveryList(stamp);
                stamps.addAll(stamp2);
                page.setCount(stamps.size());

            }

        } else {
            stamps = stampDao.findListByCondition(stamp);
            System.out.println("stamps长度：" + stamps.size());
            for (Stamp stamp1 : stamps) {
                stamp1.setLastRecord(stampRecordDao.get(new StampRecord(stamp1.getLastRecord().getId(), stamp1.getRecordState())));
            }
        }
        for (Stamp stamp1 : stamps) {
            // 根据公司id查询此印章绑定的公司是否有过修改记录
            ModifyInfoLog modifyInfoLog = new ModifyInfoLog();
            modifyInfoLog.setCompanyName(stamp1.getUseComp().getId());
            modifyInfoLog.setType("modifyCompanyInfo");
            List<ModifyInfoLog> modifyInfoLogList = modifyInfoLogService.findModifyInfoLog(modifyInfoLog);
            if (modifyInfoLogList != null && modifyInfoLogList.size() != 0) {
                stamp1.setModifyInfoLog(modifyInfoLogList.get(0));
            }
        }
        long endTime = System.currentTimeMillis(); //获取结束时间

        System.out.println("已交付列表运行时间： " + (endTime - startTime) + "ms");

        page.setList(stamps);

        return page;
    }


    /**
     * 通过印章公司名称
     * 查找该公司下正在使用中的印章
     *
     * @param page
     * @param useCompanyName
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Stamp> findStampPageByUseCompany(Page<Stamp> page, String useCompanyName, String stampShape) {

        if (useCompanyName == null) {
            return page;
        }

        User user = UserUtils.getUser();

        Area nowArea = UserUtils.getUser().getCompanyInfo().getArea();

        Company checkCompany = new Company(nowArea, useCompanyName, CompanyType.USE);

        Company useCompany = companyDao.get(checkCompany);

        if (useCompany == null) {

            return page;
        }

        Stamp checkStamp = new Stamp(useCompany, StampState.DELIVERY, OprationState.OPEN, SysState.USABLE, stampShape);

        checkStamp.setPage(page);

        List<Stamp> stamps = stampDao.findList(checkStamp);

        for (Stamp stamp : stamps) {
            stamp.setUseComp(useCompany);
        }

        page.setList(stamps);

        return page;
    }

    /**
     * 通过印章id查找印章信息
     *
     * @param stamp
     * @return
     */
    @Transactional(readOnly = true)
    public Stamp findStampInfo(Stamp stamp) {

        stamp = stampDao.get(stamp);

        StampRecord lastRecord = stampRecordDao.get(new StampRecord(stamp.getLastRecord().getId(), stamp.getRecordState()));
        lastRecord.setMakeComp(companyDao.get(new Company(lastRecord.getMakeComp().getId(), CompanyType.MAKE)));
        lastRecord.setUseComp(companyDao.get(new Company(lastRecord.getUseComp().getId(), CompanyType.USE)));

        //获得该印章的备案信息
        stamp.setLastRecord(lastRecord);
        //获得该印章使用公司的信息
        stamp.setUseComp(companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE)));

        //如果该印章是物理印章
        if ("1".equals(stamp.getStampShape())) {

            //补刻或者已经刻制完成
            if (stamp.getStampShapeId() != null && (!stamp.getStampShapeId().equals(""))) {

                stamp.setMoulage(moulageDao.getMoulageById(stamp.getStampShapeId()));
            }

        }

        //如果该印章是电子印章
        if ("2".equals(stamp.getStampShape())) {

            //补刻或者已经刻制完成
            if (stamp.getStampShapeId() != null && (!stamp.getStampShapeId().equals(""))) {

                Electron electron = electronDao.get(new Electron(stamp.getStampShapeId()));

                stamp.setElectron(electron);
            }

        }
        return stamp;
    }

    /**
     * 通过制章点id查找对应的印模信息
     *
     * @param makecompId
     * @return
     */
    @Transactional(readOnly = true)
    public String geteleModel(String makecompId) {
        String eleModel = makeCompanyStampDao.geteleModel(makecompId);
        System.out.println(eleModel);
        return eleModel;
    }

    @Transactional(readOnly = true)
    public StampDeliveryVo findDeliveryStampInfo(Stamp stamp) throws IOException {

        StampDeliveryVo deliveryVo = new StampDeliveryVo();

        stamp = stampDao.get(stamp);

        deliveryVo.setStamp(stamp);
        //获得该印章的备案信息
        stamp.setLastRecord(stampRecordDao.get(new StampRecord(stamp.getLastRecord().getId(), stamp.getRecordState())));
        //获得该印章使用公司的信息
        stamp.setUseComp(companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE)));

        String livePhotosJson = stamp.getLivePhoto();

        /*livephotos Json change start*/
        ObjectMapper mapper = new ObjectMapper();

        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Attachment.class);

        mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, Attachment.class);

        if (!"".equals(livePhotosJson) && livePhotosJson != null) {
            List<Attachment> attachments = (List<Attachment>) mapper.readValue(livePhotosJson, javaType);

        /*livephotos Json change end*/

            attachments = attachmentDao.findListByJsonList("99", attachments);

            deliveryVo.setAttachments(attachments);
        }

        return deliveryVo;
    }


    @Transactional(readOnly = true)
    public MakeStampBusniessHanlerDTO showBusniessHandlerInfo(String stampId, String stampShape) {

        //通过印章id 或者该印章信息
        Stamp stamp = new Stamp(stampId);
        stamp.setStampShape(stampShape);

        stamp = stampDao.get(stamp);


        StampRecord stampRecord = new StampRecord();

        stampRecord.setId(stamp.getLastRecord().getId());
        stampRecord.setWorkType(stamp.getRecordState());

        //通过印章信息获得该用章公司信息
        Company useCompany = companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE));


        MakeStampBusniessHanlerDTO dto = new MakeStampBusniessHanlerDTO(stamp, useCompany, stampRecord);


        return dto;
    }


    /**
     * @param stampMakeDTO
     * @author bb(update)
     * 备案登记操作
     * 1、校验用章单位是否存在（根据统一码和企业名称）
     * 2、印章与企业相互绑定
     * 3、
     */
    @Transactional
    public Condition<StampRecord> saveNewStamp(StampMakeDTO stampMakeDTO, MultipartFile[] files)
            throws SoleCodeException, AreaException, PhoneLoginNameException, StampMakeException {


        StampRecord stampRecord = stampMakeDTO.getStampRecord();
        List<Stamp> stamps = stampMakeDTO.getStamps();
        List<String> fileType = stampMakeDTO.getFileType();

        //当前的印章备案信息有
        // 经办人信息
        try {

            String soleCode = stampRecord.getUseComp().getSoleCode().trim();

            Company useCompany = stampRecord.getUseComp();

            Area area = null;

            StampWorkType applyStamp = StampWorkType.APPLY;

            //增加判断备案页面的用户是否选中统一码这个选项，再进行判断统一码的格式（规则）{1：统一码，2：其他码}
            //在超级管理员登录后，系统设置-字典管理-isSoleCode可以查询到该键值对并且修改相应的内容

            if ("1".equals(stampRecord.getIsSoleCode())) {
                //一个印章申请对应一个备案申请
//            List<stamp> stampRecords = new ArrayList<StampRecord>();

                //判断字符串长度为多少
                //正则表达式匹配规则
                //并且通过规则计算此唯一码是否正确
                //优先处理区域
                //从唯一码中获取区域
                if (soleCode.trim().length() == 18 && soleCode.matches(BusinessValidateUtil.newCodePattern1)) {

                    if (!BusinessValidateUtil.checkSoleCode(soleCode, 18)) {

                        throw new SoleCodeException("unified social credit code Error!");
                    }

                    //获取组织结构代码
                    String areaCode = soleCode.substring(2, 8);

                    Area areaByCode = areaDao.getAreaByCode(areaCode);

                    // area = areaDao.getAreaByCode(areaCode);
//                area = UserUtils.getUser().getCompanyInfo().getArea();
                    //当前用户的area
                    area = areaDao.get(new Area(UserUtils.getUser().getCompanyInfo().getArea().getId()));

                    if (areaByCode == null) {
                        throw new SoleCodeException("unified social credit code Error!");
                    }

                    //判断 areaBycode 与当前用章单位的area的 关系
                    //若为同一area 或者 当前area 为areabyCode的子级时，通过
                    //否则则抛出异常
                    /*if (!(area.judgeAreaCode(areaByCode) || area.containArea(areaByCode))) {

                        throw new AreaException("新统一码归属区域不符合规定!");

                    }*/

                    useCompany.setArea(area);

                } else if (soleCode.trim().length() == 10 && soleCode.matches(BusinessValidateUtil.newCodePattern2)) {

                    if (!BusinessValidateUtil.checkSoleCode(soleCode.replace("-", "").trim(), 9)) {

                        throw new SoleCodeException("unified social credit code Error!");
                    }

                    //获得当前 刻章点公司的地区
                    area = UserUtils.getUser().getCompanyInfo().getArea();


                } else if (soleCode.trim().length() == 9 && soleCode.matches(BusinessValidateUtil.newCodePattern3)) {

                    if (!BusinessValidateUtil.checkSoleCode(soleCode.replace("-", "").trim(), 9)) {

                        throw new SoleCodeException("unified social credit code Error!");

                    }
                    //获得当前 刻章点公司的地区
                    area = UserUtils.getUser().getCompanyInfo().getArea();


                    //15位正则匹配
                } else if (soleCode.trim().length() == 15 && soleCode.matches(BusinessValidateUtil.newCodePattern4)) {

                    if (!BusinessValidateUtil.checkSoleCode(soleCode.trim(), 15)) {

                        throw new SoleCodeException("unified social credit code Error!");

                    }

                    //到了这里说明码正确
                    //截取社会代码中的统一码
                    //0-5位
                    String areaCode = soleCode.substring(0, 6);

                    Area areaByCode = areaDao.getAreaByCode(areaCode);

                    // area = areaDao.getAreaByCode(areaCode);
                    // area = UserUtils.getUser().getCompanyInfo().getArea();
                    //当前用户的area
                    area = areaDao.get(new Area(UserUtils.getUser().getCompanyInfo().getArea().getId()));

                    if (areaByCode == null) {
                        throw new SoleCodeException("unified social credit code Error!");
                    }

                    //判断 areaBycode 与当前用章单位的area的 关系
                    //若为同一area 或者 当前area 为areabyCode的子级时，通过
                    //否则则抛出异常
                    /*if (!(area.judgeAreaCode(areaByCode) || area.containArea(areaByCode))) {

                        throw new AreaException("新统一码归属区域不符合规定!");

                    }*/

                    useCompany.setArea(area);

                } else {

                    throw new SoleCodeException("unified social credit code Error!");

                }

            } else {

                area = areaDao.get(new Area(UserUtils.getUser().getCompanyInfo().getArea().getId()));

                useCompany.setArea(area);

            }

            //以上是检验 企业唯一码是否正确

            //附件处理 start
            List<Attachment> attachments = null;

            if (stampMakeDTO.getFileType() != null && !stampMakeDTO.getFileType().isEmpty()) {
                attachments = attachmentService.setUUIDList(stampMakeDTO.getFileType());


                String attachJson = JsonMapper.toJsonString(attachments);

                stampRecord.setAttachs(attachJson);

                //attachmentService.saveAttachmentsInHosts(attachments, files, useCompany.getCompanyName());
                //注：重构上传文件的保存，从临时文件里复制文件到正式路径 入口
                attachmentService.copyFileFromTemporary(attachments, stampMakeDTO.getFileList(), useCompany.getCompanyName());

                if (attachments != null && !attachments.isEmpty()) {//如果为空值不插入附件表 xucaikai 2018.03.07
                    attachmentDao.insertAttachmentList(attachments);
                }
            }
            //附件处理 end

            Company makeCompany = UserUtils.getUser().getCompanyInfo();

            //如果该公司不存在,则创建用户和公司
            if (companyDao.checkCompanyBysoleCodeAndCompName(soleCode, useCompany.getCompanyName(), CompanyType.USE) == 0) {
                System.out.println("该企业不存在，正在创建...");

                // 创建新用户
                User newUser = createNewUser(stampRecord, area);

                //更新用户的userTypeId和CompanyId
                userDao.updateUseTypeIdAndComp(newUser);

                //
                stampRecord.setWorkType(applyStamp);
                stampRecord.setMakeComp(makeCompany);
                stampRecord.setUseComp(useCompany);

                //7.14增加 备案记录需要记录当前法人信息
                stampRecord.setLegalName(useCompany.getLegalName());
                stampRecord.setLegalPhone(useCompany.getLegalPhone());
                stampRecord.setLegalCertType(useCompany.getLegalCertType());
                stampRecord.setLegalCertCode(useCompany.getLegalCertCode());
                stampRecord.setCompanyName(useCompany.getCompanyName());
                stampRecord.setType1(useCompany.getType1());
                stampRecord.setCompPhone(useCompany.getCompPhone());
                stampRecord.setCompAddress(useCompany.getCompAddress());
                stampRecord.setSoleCode(soleCode);

                //对印章章型 做印章刻制列表的处理
                stamps = dealStampShades(stamps);

                //对印章做操作 配合json List
                Map<String, Integer> stampTypeMap1 = new HashMap<String, Integer>();
                Map<String, Integer> stampTypeMap2 = new HashMap<String, Integer>();

                for (Stamp stamp : stamps) {

                    stamp.setUUID();

                    switch (Integer.valueOf(stamp.getStampShape())) {
                        case 1:
                            //如果不包含
                            if (!stampTypeMap1.containsKey(stamp.getStampType())) {

                                stampTypeMap1.put(stamp.getStampType(), 1);
                            } else {
                                stampTypeMap1.put(stamp.getStampType(), stampTypeMap1.get(stamp.getStampType()) + 1);
                            }
                            //印章命名规范 xxx类印章-序号
                            stamp.setStampName(DictUtils.getDictLabels(stamp.getStampType(), "stampType", null)
                                    + "-" + stampTypeMap1.get(stamp.getStampType()));
                            break;
                        case 2:
                            //如果不包含
                            if (!stampTypeMap2.containsKey(stamp.getStampType())) {

                                stampTypeMap2.put(stamp.getStampType(), 1);
                            } else {
                                stampTypeMap2.put(stamp.getStampType(), stampTypeMap2.get(stamp.getStampType()) + 1);
                            }
                            //印章命名规范 xxx类印章-序号
                            stamp.setStampName(DictUtils.getDictLabels(stamp.getStampType(), "stampType", null)
                                    + "-" + stampTypeMap2.get(stamp.getStampType()));
                            break;
                        default:

                    }

                }

                stampRecord.setApplyInfos(JsonMapper.toJsonString(stamps));

                stampRecord.preInsert();

//                stampRecordDao.insert(stampRecord);

                int i = 0;


                StampRecord SR_Copy = new StampRecord();

//                List<String> record_id = new ArrayList<String>();

                //对印章做操作
                for (Stamp stamp : stamps) {

                    stamp.setNowMakeComp(makeCompany);
                    //设置制章公司
                    stamp.setMakeComp(makeCompany);
                    //设置用章公司
                    stamp.setUseComp(useCompany);
                    stamp.setNowMakeComp(makeCompany);
                    stamp.setSysState(SysState.USABLE);
                    // 设置印章状态
                    stamp.setStampState(StampState.RECEPT);
                    //设置印章 可操作类型
                    stamp.setUseState(OprationState.STOP);

                    stamp.setRecordDate(new Date());

                    StampRecord SR = new StampRecord(IdGen.uuid(), stampRecord);

                    //System.out.println("SR重新新建的ID信息（不存在公司信息）：" + SR.getId());
//                    record_id.add(SR.getId());

                    SR_Copy = SR;

                    /*把上面的stampRecord存入新的ID然后循环存放*/
                    stampRecordDao.insert(SR);

                    stamp.setLastRecord(SR);

                    setStampMoney(stamp);

                    i++;

                    //处理印章的编码问题
                    if (stamp.getStampShape().equals("1")) {
                        stamp.setStampCode(stampMakeService.phyStampCode());
                    }
                    if (stamp.getStampSubType() != null && !"".equals(stamp.getStampSubType())) {
                        if (stamp.getStampSubType().indexOf("1") != -1){
                            String[] MoulageStr = attachmentService.saveMoulage(files[0], "1", useCompany.getCompanyName(), stamp.getStampName());
                            stamp.setPhyModel(MoulageStr[0]);
                            stamp.setEleModel(MoulageStr[1]);
                            stamp.setEsEleModel(MoulageStr[2]);
                        } else if (stamp.getStampSubType().indexOf("2") != -1){
                            stamp.setScanModel(getScanModel(files[0], useCompany.getCompanyName(), stamp.getStampName()));
                        }
                        if (stamp.getStampCode() == null || "".equals(stamp.getStampCode())) {
                            stamp.setStampCode(stampMakeService.phyStampCode());
                        }
                    }

                    stamp.preInsert();
                    stampDao.insert(stamp);
                }

//                stampRecord.setRecord_id(record_id);

                //stampRecordDao.insert(stampRecord);

                //System.out.println("制章的stampRecord.serialNum：" + stampRecord.getSerialNum());

                Condition<StampRecord> condition = new Condition<StampRecord>(Condition.SUCCESS_CODE, "success", stampRecord);

                System.out.println("日期==" + (new SimpleDateFormat("yyyy年MM月dd日")).format(new Date()));

                SendMessageUtil.sendMessage(useCompany.getLegalPhone(), newUser.getName(), (new SimpleDateFormat("yyyy年MM月dd日")).format(new Date()),
                        makeCompany.getCompanyName(), newUser.getLoginName(), "123456", "");

                return condition;

            } else {
                //若公司存在，通过soleCode和companyName找出相应的公司
                useCompany = companyDao.getCompanyBysoleCodeAndCompName(soleCode, useCompany.getCompanyName(), CompanyType.USE);
                System.out.println("该企业已存在！");

                if (useCompany.getCompType() != CompanyType.USE) {

                    return new Condition(Condition.ERROR_CODE, "该社会统一码已经有公司注册,但不是用章单位!");
                }

                //申请操作
                stampRecord.setWorkType(applyStamp);
                stampRecord.setMakeComp(makeCompany);
                stampRecord.setUseComp(useCompany);

                //7.14增加 备案记录需要记录当前法人信息
                stampRecord.setLegalName(useCompany.getLegalName());
                stampRecord.setLegalPhone(useCompany.getLegalPhone());
                stampRecord.setLegalCertType(useCompany.getLegalCertType());
                stampRecord.setLegalCertCode(useCompany.getLegalCertCode());
                stampRecord.setCompanyName(useCompany.getCompanyName());
                stampRecord.setType1(useCompany.getType1());
                stampRecord.setCompPhone(useCompany.getCompPhone());
                stampRecord.setCompAddress(useCompany.getCompAddress());
                stampRecord.setSoleCode(soleCode);
                //对印章章型 做印章刻制列表的处理
                stamps = dealStampShades(stamps);

                //对印章做操作 配合json List
                Map<String, Integer> stampTypeMap = new HashMap<String, Integer>();

                //对印章做操作 配合json List
                for (Stamp stamp : stamps) {

                    stamp.setUUID();

                    stamp.setStampName(getRightStampName(stamp, useCompany, 0, stampTypeMap).toString());

                }

                //setApplyInfoList-toJson
                stampRecord.setApplyInfos(JsonMapper.toJsonString(stamps));

                stampRecord.preInsert();

                //保存备案信息
//                stampRecordDao.insert(stampRecord);

                StampRecord SR_Copy = new StampRecord();

//                List<String> record_id = new ArrayList<String>();

                //对印章做操作
                for (Stamp stamp : stamps) {
                    //设置制章公司
                    stamp.setMakeComp(makeCompany);
                    //设置用章公司
                    stamp.setUseComp(useCompany);
                    stamp.setNowMakeComp(makeCompany);
                    stamp.setSysState(SysState.USABLE);
                    // 设置印章状态
                    stamp.setStampState(StampState.RECEPT);
                    //设置印章 可操作类型
                    stamp.setUseState(OprationState.STOP);

                    stamp.setRecordDate(new Date());

                    StampRecord SR = new StampRecord(IdGen.uuid(), stampRecord);

                    SR_Copy = SR;

//                    record_id.add(SR.getId());

                    //System.out.println("SR重新新建的ID信息（存在公司信息）：" + SR.getId());

                    stamp.setLastRecord(SR);

                    /*把上面的stampRecord存入新的ID然后循环存放*/
                    stampRecordDao.insert(SR);

                    setStampMoney(stamp);


                    //处理印章的编码问题
                    if (stamp.getStampShape().equals("1")) {
                        stamp.setStampCode(stampMakeService.phyStampCode());
                    }
                    if (stamp.getStampSubType() != null && !"".equals(stamp.getStampSubType())) {
                        if (stamp.getStampSubType().indexOf("1") != -1){
                            String[] MoulageStr = attachmentService.saveMoulage(files[0], "1", useCompany.getCompanyName(), stamp.getStampName());
                            stamp.setPhyModel(MoulageStr[0]);
                            stamp.setEleModel(MoulageStr[1]);
                            stamp.setEsEleModel(MoulageStr[2]);
                        } else if (stamp.getStampSubType().indexOf("2") != -1){
                            stamp.setScanModel(getScanModel(files[0], useCompany.getCompanyName(), stamp.getStampName()));
                        }
                        if (stamp.getStampCode() == null || "".equals(stamp.getStampCode())) {
                            stamp.setStampCode(stampMakeService.phyStampCode());
                        }
                    }
                    /*if (stamp.getStampSubType() != null && !"".equals(stamp.getStampSubType())) {
                        stamp.setScanModel(getScanModel(files[0], useCompany.getCompanyName(), stamp.getStampName()));
                        if (stamp.getStampCode() == null || "".equals(stamp.getStampCode())) {
                            stamp.setStampCode(stampMakeService.phyStampCode());
                        }
                    }*/

                    stamp.preInsert();
                    stampDao.insert(stamp);
                }

                //stampRecord.setRecord_id(record_id);
                //stampRecordDao.insert(stampRecord);
                //System.out.println("制章的stampRecord.serialNum：" + stampRecord.getSerialNum());

                Condition<StampRecord> condition = new Condition<StampRecord>(Condition.SUCCESS_CODE, "success", stampRecord);

                return condition;

            }

        } catch (MoneySettingNotFoundException e) {

            e.printStackTrace();

            throw e;

        } catch (SoleCodeException e) {

            e.printStackTrace();

            throw e;

        } catch (AreaException e) {

            e.printStackTrace();

            throw e;

        } catch (PhoneLoginNameException e) {

            e.printStackTrace();

            throw e;
        } catch (Exception e) {

            e.printStackTrace();
            throw new StampMakeException("System error :" + e.getMessage());
        }


    }

    /**
     * 保存物理印章的制作
     * <p>
     * //TODO Exception
     * @param stamp,phyFile,eleFile
     * @author bb
     */
    @Transactional
    public Condition saveMakeStampMoulage(Stamp stamp, MultipartFile phyFile)
            throws StampMakeException {

        Condition condition = new Condition();

        try {

            Stamp stamp1 = new Stamp(stamp.getId());
            stamp1.setStampShape(stamp.getStampShape());
            stamp1 = stampDao.get(stamp1);

            Company useCompany = companyDao.get(new Company(stamp1.getUseComp().getId(), CompanyType.USE));

            condition.setCode(Condition.ERROR_CODE);

            condition.setMessage("系统繁忙!");

            Moulage moulage = stamp.getMoulage();

            moulage.setStampType(stamp1.getStampType());
            moulage.setMoulageName(stamp1.getStampName());
            moulage.setMakeCompany(UserUtils.getUser().getCompanyInfo());
            moulage.setUseCompany(stamp1.getUseComp());

            moulage.preInsert();

            String[] MoulageStr = attachmentService.saveMoulage(phyFile, "1", useCompany.getCompanyName(), stamp1.getStampName());
            //对物理印模的存储
            //String phyMoulageStr = attachmentService.saveMoulage(phyFile, "1", useCompany.getCompanyName(), stamp1.getStampName());
            String phyMoulageStr = MoulageStr[0];
            stamp1.setPhyModel(phyMoulageStr);

            //对电子印模的存储
            //String eleMoulageStr = attachmentService.saveMoulage(eleFile, "2", useCompany.getCompanyName(), stamp1.getStampName());
            String eleMoulageStr = MoulageStr[1];
            stamp1.setEleModel(eleMoulageStr);

            //对公安部电子印模的存储
            String eleMoulageStr2 = MoulageStr[2];
            stamp1.setEsEleModel(eleMoulageStr2);

            moulageDao.insertMoulage(moulage);

            //对电子印模图加水印处理
            String eleMoulageRealPath = Global.getConfig("attachment.absolutePath") + eleMoulageStr;

            //水印图
            String waterVirutalPath = WaterImageUtil.WaterImage(eleMoulageRealPath, "2", useCompany.getCompanyName(), stamp1.getStampName());

            stamp1.setWaterEleModel(waterVirutalPath);

            stampDao.saveWaterImage(stamp1);

            //物理印模绑定
            stamp1.setStampShapeId(moulage.getId());

            stamp1.setStampState(StampState.ENGRAVE);

            stamp1.setMakeDate(new Date());

            stamp1.preUpdate();

            stampDao.bindStampAndMoulage(stamp1);

            condition.setCode(Condition.SUCCESS_CODE);

            condition.setMessage("保存物理印模信息成功");

            //

        } catch (StampMakeException e) {

            e.printStackTrace();

            throw e;

        } catch (Exception e) {

            e.printStackTrace();

            throw new StampMakeException("Save Moulage Error!");
        }


        return condition;
    }

    /**
     * 检查将要制作的印章id 是否为真的需要制作
     *
     * @param stampId
     * @return
     */
    @Transactional(readOnly = true)
    public boolean chekcStampNeedMake(String stampId, String stampShape) {

        if (stampDao.checkStampNeedMake(stampId, stampShape) == 1) {

            return true;
        }

        return false;
    }

    /**
     * @param stamp
     * @return
     * @author bb
     * 更新物理印章写入芯片状态前作检查
     */
    @Transactional
    public String updateChip_pre(Stamp stamp) {
        stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        try {
            Stamp isExits = stampDao.getStampByAntiFakeId(stamp);
            if (isExits != null) {
                return isExits.getStampCode();
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * @param stamp
     * @return
     * @author bb
     * 更新物理印章写入芯片状态
     */
    @Transactional
    public boolean updateChip(Stamp stamp) {
        Stamp stamp1 = new Stamp();
        stamp1.setStampShape("1");
        stamp1.setStampCode(stamp.getStampCode());
        stamp1.setAntiFakeId(stamp.getAntiFakeId());
        stamp1.setAntiFakeWrite("1");
        try {
            int result = stampDao.updateChip(stamp1);
            if (result > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }


    /**
     * 通过Id返回Stamp对象（用来检查是否写入芯片）
     *
     * @param stamp
     * @return Stamp
     */
    @Transactional(readOnly = false)
    public Stamp getStamp(Stamp stamp) {

        Stamp stamp1 = new Stamp(stamp.getId());
        stamp1.setStampShape(stamp.getStampShape());
        return stampDao.get(stamp1);
    }

    /**
     * 通过StampId检查是否满足交付条件
     *
     * @param stampId
     * @return
     */
    @Transactional(readOnly = false)
    public List<DeliverAttachment> getDeliverAttachment(String stampId) {

        List<DeliverAttachment> list = deliverAttachmentDao.findListByStampId(stampId);

        return list;
    }

    /**
     * APP验证芯片
     *
     * @param stampCode,chipUID
     * @return Map
     */
    public Map<String, Object> chipVerify(String stampCode, String chipUID) {

        Stamp stamp = new Stamp();
        stamp.setStampCode(stampCode);
        stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        stamp = stampDao.getStampByStampCode(stamp);


        //获取用章单位信息
        stamp.setUseComp(companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE)));
        //获取现在所属制章单位信息
        stamp.setNowMakeComp(companyDao.get(new Company(stamp.getNowMakeComp().getId(), CompanyType.MAKE)));

        Map<String, Object> stampData = new HashMap<String, Object>();

        //若根据印章编码未找到印章信息 || 芯片的UID不匹配 则返回失败
        if (stamp == null || !stamp.getAntiFakeId().equals(chipUID)) {
            stampData.put("result", "Fail");
        } else {

            stampData.put("result", "Success");
            stampData.put("印章编码", stamp.getStampCode());
            stampData.put("印章类型", getstampTypeByCode(stamp.getStampType()));
            stampData.put("企业单位名称", stamp.getUseComp().getCompanyName());
            stampData.put("企业单位法人", stamp.getUseComp().getLegalName());
            stampData.put("制作单位名称", stamp.getNowMakeComp().getCompanyName());
            stampData.put("制作日期", new SimpleDateFormat("yyyy-MM-dd").format(stamp.getMakeDate()));
            stampData.put("印模", stamp.getEleModel());

        }

        return stampData;
    }

    //根据从数据库查到的印章类型代码，返回相应的印章类型
    private String getstampTypeByCode(String code) {
        if ("1".equals(code)) {
            return "公章";
        } else if ("2".equals(code)) {
            return "财务专用章";
        } else if ("3".equals(code)) {
            return "发票专用章";
        } else if ("4".equals(code)) {
            return "合同专用章";
        } else if ("5".equals(code)) {
            return "业务专用章";
        } else if ("6".equals(code)) {
            return "法定代表人名章";
        } else if ("7".equals(code)) {
            return "其它类型印章";
        } else {
            return "类型不明！";
        }
    }


    /**
     * 交付印章
     *
     * @param stamp
     *//*
    @Transactional
    public Condition deliveryStamp(Stamp stamp, MultipartFile[] photos, MultipartFile record)
            throws IOException, StampMakeException {

        Condition condition = null;

        try {

            Stamp stamp1 = new Stamp(stamp.getId());
            stamp1.setStampShape(stamp.getStampShape());

            stamp = stampDao.get(stamp1);

            Company company = companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE));

            List<Attachment> livePhotos = attachmentService.setUUIDList(photos);

            String livePhotoJson = JsonMapper.toJsonString(livePhotos);

            attachmentService.saveAttachmentsInHosts(livePhotos, photos, company.getCompanyName());

            attachmentDao.insertAttachmentList(livePhotos);

            stamp.setLivePhoto(livePhotoJson);

            String recordFile = attachmentService.saveAttachmentInHost(record, company.getCompanyName(), stamp.getStampName());

            stamp.setRecordPhoto(recordFile);

            stamp.setStampState(StampState.DELIVERY);


            stamp.setSysState(SysState.USABLE);

            stamp.setUseState(OprationState.OPEN);

            stamp.preUpdate();

            stamp.setDeliveryDate(new Date(System.currentTimeMillis()));

            stamp.setLastStateDate(new Date(System.currentTimeMillis()));

            stamp.setLastStateBy(UserUtils.getUser());

            stampDao.deliveryStamp(stamp);

            return new Condition(Condition.SUCCESS_CODE, "印章交付成功!");

        } catch (IOException e) {

            throw new StampMakeException("IOException !");

        } catch (Exception e) {

            e.printStackTrace();

            throw new StampMakeException("Delivery Stamp Error!");
        }
    }*/
    @Transactional
    public Condition deliveryStamp(Stamp stamp, String pdfPathReal) throws IOException, StampMakeException {

        Condition condition = null;

        try {

            Stamp stamp1 = new Stamp(stamp.getId());
            stamp1.setStampShape(stamp.getStampShape());

            stamp = stampDao.get(stamp1);

            Company company = companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE));

            stamp.setStampState(StampState.DELIVERY);

            stamp.setSysState(SysState.USABLE);

            stamp.setUseState(OprationState.OPEN);

            stamp.preUpdate();

            stamp.setDeliveryDate(new Date(System.currentTimeMillis()));

            stamp.setLastStateDate(new Date(System.currentTimeMillis()));

            stamp.setLastStateBy(UserUtils.getUser());


            if ("2".equals(stamp.getStampShape())) {
                //------------签章--------------------
                //1.企业
                User usecompUser = new User();
                Company useCompany = stamp.getUseComp();
                usecompUser.setUserTypeId(useCompany.getId());
                usecompUser.setUserType(UserType.USE);
                List<User> list = findUsersByUserTypeId(usecompUser);
                String usecompCerPath = list.get(0).getCertFilePath();
                String usecompCerPassword = list.get(0).getLoginName();

                String pdfPathReal_temp = pdfPathReal.replace(".pdf", "_temp.pdf");
                String pdfPathReal_sign = pdfPathReal_temp.replace("_temp.pdf", "_sign.pdf");

                SinatureCerDTO sinatureCerDTO = new SinatureCerDTO();
                sinatureCerDTO.setUndoneRealPath(pdfPathReal);
                sinatureCerDTO.setDoneRealPath(pdfPathReal_temp);
                sinatureCerDTO.setCerPath(usecompCerPath);
                sinatureCerDTO.setCerPassword(usecompCerPassword);
                sinatureCerDTO.setSealPath(Global.getConfig("attachment.absolutePath") + stamp.getEleModel());
                sinatureCerDTO.setLlx(270);
                sinatureCerDTO.setLly(210);
                sinatureCerDTO.setUrx(373);
                sinatureCerDTO.setUry(313);
                sinatureCerDTO.setPageSize(1);
                sinatureCerDTO.setSign_domain("sign1");
                SignPDF.sign(sinatureCerDTO);

                //2.刻章点
                StampMakeComp smc = stampDao.getMakeCompEleModel(UserUtils.getUser().getCompanyInfo().getId());
                String makeCompEleModel = smc.getEleModel();

                SinatureCerDTO sinatureCerDTO2 = new SinatureCerDTO();
                sinatureCerDTO2.setUndoneRealPath(pdfPathReal_temp);
                sinatureCerDTO2.setDoneRealPath(pdfPathReal_sign);
                sinatureCerDTO2.setCerPath(UserUtils.getUser().getCertFilePath());
                sinatureCerDTO2.setCerPassword(UserUtils.getUser().getLoginName());
                sinatureCerDTO2.setSealPath(Global.getConfig("attachment.absolutePath") + makeCompEleModel);
                sinatureCerDTO2.setLlx(415);
                sinatureCerDTO2.setLly(210);
                sinatureCerDTO2.setUrx(518);
                sinatureCerDTO2.setUry(313);
                sinatureCerDTO2.setPageSize(1);
                sinatureCerDTO2.setSign_domain("sign2");
                SignPDF.sign(sinatureCerDTO2);

                //签完章后删除临时文件
                new File(pdfPathReal).delete();
                new File(pdfPathReal_temp).delete();
                stamp.setRecordPDF(pdfPathReal_sign.replace("D:/stamp", ""));

            }
            stampDao.deliveryStamp(stamp);
            return new Condition(Condition.SUCCESS_CODE, "印章交付成功!");


        } catch (Exception e) {
            e.printStackTrace();
            throw new StampMakeException("Delivery Stamp Error!");
        }
    }

    /**
     * 通过回执id
     * 搜索刚刚制作的的印章信息 id
     */
    @Transactional(readOnly = true)
    public ReceiptVo getStampRecordReceiptInfo(String serialNum, StampWorkType stampWorkType) {


        //System.out.println("传递的流水号：" + serialNum + ";印章工作类型：" + stampWorkType);

        //获得此次印章备案记录
        List<StampRecord> stampRecordId = stampRecordDao.findRecordIdFromWorkType(new StampRecord(serialNum, stampWorkType, "根据流水号查找同一个备案stamp的record的id"));

//        List<String> stampRecordId = stampRecord.getRecord_id();

        List<Stamp> phyStamp = new ArrayList<Stamp>();

        List<Stamp> eleStamp = new ArrayList<Stamp>();

        StampRecord sr_copy = new StampRecord();

        String useCompId = "";

        //System.out.println("stampRecordId.size();" + stampRecordId.size());

        for (StampRecord sr_id : stampRecordId) {
            //System.out.println("sr_id:" + sr_id.getId());
            Stamp stamp = new Stamp();
            StampRecord sr = stampRecordDao.get(new StampRecord(sr_id.getId(), stampWorkType));
            stamp.setLastRecord(sr);
            if (stampDao.findEngStampFromRecordId(stamp) != null) {
                //System.out.println("eng_stamp" + stampDao.findEngStampFromRecordId(stamp).getId());
                phyStamp.add(stampDao.findEngStampFromRecordId(stamp));
            }
            if (stampDao.findEleStampFromRecordId(stamp) != null) {
                //System.out.println("ele_stamp" + stampDao.findEleStampFromRecordId(stamp).getId());
                eleStamp.add(stampDao.findEleStampFromRecordId(stamp));
            }
            useCompId = sr.getUseComp().getId();
            sr_copy = sr;
        }

//        Stamp stamp = new Stamp();
//
//        stamp.setLastRecord(stampRecord);

//        stamp.setStampShape(StampShape.PHYSTAMP.getKey());
//
//        List<Stamp> phyStamp = stampDao.findList(stamp);

//        stamp.setStampShape(StampShape.ELESTAMP.getKey());

//        List<Stamp> eleStamp = stampDao.findList(stamp);

        List<Stamp> stamps = new ArrayList<Stamp>();
        stamps.addAll(phyStamp);
        stamps.addAll(eleStamp);

        //获得此次备案的所制作的印章列表

        //System.out.println("用章公司的ID：" + useCompId);

        //获得用章公司信息
        Company useCompany = companyDao.get(new Company(useCompId, CompanyType.USE));

        //获得当前制章公司信息
        Company makeCompany = UserUtils.getUser().getCompanyInfo();


        return new ReceiptVo(sr_copy, stamps, useCompany, makeCompany);

    }


// /**
//     * 印章缴销
//     *
//             * @param stampDisposeDTO
//     * @param files
//     */
//
// @Transactional
//    public Condition logoutStamp(StampWorkTypeDTO stampDisposeDTO, MultipartFile[] files)
//            throws SoleCodeException, StampExistException, StampMakeException {
//
//        try {
//
//            Stamp stamp = stampDisposeDTO.getStamp();
//
//            StampRecord stampRecord = stampDisposeDTO.getStampRecord();
//
//            Company useCompany = stampDisposeDTO.getUseCompany();
//
//            Company makeCompany = UserUtils.getUser().getCompanyInfo();
//
//            //统一码
//            String soleCode = useCompany.getSoleCode().trim();
//
//            //如果信用统一代码不存在，就抛异常
//            //存在则继续
//            if (companyDao.checkSoleCode(soleCode) == 0) {
//
//                throw new SoleCodeException("查无此企业信用统一码!");
//            }
//
//            useCompany = companyDao.getCompanyBysoleCode(soleCode);
//
//            stamp.setUseComp(useCompany);
//
//            //印章处于启用状态以及交付状态
//            stamp.setStampState(StampState.DELIVERY);
//
//            stamp.setUseState(OprationState.OPEN);
//
//            //检查该印章是否存在
//            if (stampDao.checkStampExist(stamp) == 0) {
//
//                //TODO
//                throw new StampExistException("印章不存在!");
//            }
//
//            //存在 即更改状态
//            stamp = stampDao.getStampByShapesAndName(stamp);
//
//            stampRecord.setWorkType(StampWorkType.LOGOUT);
//
//            stampRecord.setMakeComp(makeCompany);
//
//            stampRecord.setUseComp(useCompany);
//
//            /* json化当前印章对象 start*/
//            Stamp jsonStamp = new Stamp();
//
//            jsonStamp.setStampName(stamp.getStampName());
//
//            jsonStamp.setId(stamp.getId());
//
//            jsonStamp.setStampShapes(stamp.getStampShapes());
//
//            jsonStamp.setStampTexture(stamp.getStampTexture());
//            /* json化当前印章对象 end*/
//
//            stampRecord.setApplyInfos(JsonMapper.toJsonString(jsonStamp));
//
//            stampRecord.preInsert();
//
//            //TODO  附件信息的保存
//
//            stampRecordDao.insert(stampRecord);
//
//            stamp.setUseState(OprationState.LOGOUT);
//
//            stamp.setLastRecord(stampRecord);
//
//            stamp.preUpdate();
//
//            stampDao.updateStampState(stamp);
//
//
//            return new Condition(Condition.SUCCESS_CODE, "Success", stamp);
//
//        } catch (SoleCodeException e) {
//
//            throw e;
//
//        } catch (StampExistException e) {
//
//            throw e;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            throw new StampMakeException("System error :" + e.getMessage());
//        }
//
//
//    }

    /**
     * 印章补刻Service
     * 先把原来有的印章 缴销
     * 然后再copy 原有的信息 进入代刻列表中。
     *
     * @param stampWorkTypeDTO
     * @param files
     * @return
     */
    @Transactional(readOnly = false)
    public Condition repairStamp(StampWorkTypeDTO stampWorkTypeDTO, MultipartFile[] files, MultipartFile permission)
            throws StampExistException, StampMakeException {

        //TODO rewrite
        try {
            //获得印章的信息
            Stamp stamp = new Stamp(stampWorkTypeDTO.getStamp().getId());
            stamp.setStampShape(stampWorkTypeDTO.getStamp().getStampShape());

            stamp = stampDao.get(stamp);

//            List<Attachment> attachments = attachmentService.setUUIDList(stampWorkTypeDTO.getFileType());
//
//            String attachJson = JsonMapper.toJsonString(attachments);

            StampRecord stampRecord = stampWorkTypeDTO.getStampRecord();


            //临时stampRecord 是为了该印章第一次备案的经办人相关信息
            StampRecord tempStampRecord = stampRecord;
            //默认设置为备案stampRecord类型
            tempStampRecord.setWorkType(StampWorkType.APPLY);

            stampRecord = tempStampRecord = stampRecordDao.get(tempStampRecord);

            Company useCompany = companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE));

            Company makeCompany = UserUtils.getUser().getCompanyInfo();

            //TODO 先对原印章进行缴销备案记录登记

/*            stampRecord.setCompanyName(useCompany.getCompanyName());
            stampRecord.setType1(useCompany.getType1());
            stampRecord.setCompPhone(useCompany.getCompPhone());
            stampRecord.setCompAddress(useCompany.getCompAddress());
            stampRecord.setSoleCode(useCompany.getSoleCode());
            stampRecord.setLegalCertCode(useCompany.getLegalCertCode());

            stampRecord.setLegalPhone(useCompany.getLegalPhone());

            stampRecord.setLegalName(useCompany.getLegalName());

            stampRecord.setLegalCertType(useCompany.getLegalCertType());*/

            //设置经办人相关信息
/*            stampRecord.setAgentCertCode(tempStampRecord.getAgentCertCode());

            stampRecord.setAgentPhone(tempStampRecord.getAgentPhone());

            stampRecord.setAgentName(tempStampRecord.getAgentName());

            stampRecord.setAgentCertType(tempStampRecord.getAgentCertType());*/

            stampRecord.setUseComp(useCompany);

            stampRecord.setMakeComp(makeCompany);

            stampRecord.setWorkType(StampWorkType.REPAIR);

            stampRecord.setApplyInfos(JsonMapper.toJsonString(jsonStamp(stamp).toArrayList()));

            stampRecord.preInsert();

            stampRecord.setAttachs(tempStampRecord.getAttachs());

//            attachmentService.saveAttachmentsInHosts(attachments, files, useCompany.getCompanyName());
//
//            if(!attachments.isEmpty()) {
//                attachmentDao.insertAttachmentList(attachments);
//            }

            //许可证处理
            String permissionPath = permissionUpload(stampRecord, permission, stamp);
            stampRecord.setPermissionPhoto(permissionPath);

            //TODO 对原有的印章做缴销状态更改

            //印章信息的状态更改
            stamp.setUseState(OprationState.LOGOUT);

            stamp.setSysState(SysState.UNUSABLE);

            stamp.setLastRecord(stampRecord);

            stamp.setNowMakeComp(makeCompany);

            stamp.setRecordState(StampWorkType.REPAIR);

            stamp.preUpdate();

            if (stampRecordDao.insert(stampRecord) == 0) {

                throw new StampExistException("create New stampRecord Error!");
            }


            if (stampDao.changeOldStampState(stamp) == 0) {

                throw new StampExistException("change Old StampState Error!");
            }


            //copy NewStamp
            Stamp newStamp = new Stamp(useCompany, makeCompany, makeCompany,
                    stamp.getStampName(), stamp.getStampSubType(), stamp.getStampType(), stamp.getStampTexture(),
                    StampState.RECEPT, stamp.getStampShape(), stamp.getStampShapeId());

            String newStampName = getRightStampName(newStamp, useCompany, 0).toString();

            newStamp.setStampName(newStampName);

            newStamp.setSysState(SysState.USABLE);

            newStamp.setUseState(OprationState.STOP);

            newStamp.setPhyModel(stamp.getPhyModel());

            newStamp.setEleModel(stamp.getEleModel());

            newStamp.setRecordDate(new Date());

            newStamp.setRecordState(StampWorkType.REPAIR);

            setStampMoney(newStamp);

            newStamp.preInsert();

            TempAgent tempAgent = stampWorkTypeDTO.getTempAgent();

            //新印章新的备案记录
            StampRecord newStampRecord = new StampRecord(stampRecord.getSerialNum(), tempAgent.getAgentName(),
                    tempAgent.getAgentCertType(), tempAgent.getAgentCertCode(),
                    tempAgent.getAgentPhone(), StampWorkType.REPAIR, stampRecord.getWorkRemakrs());

            newStampRecord.setCompanyName(useCompany.getCompanyName());
            newStampRecord.setType1(useCompany.getType1());
            newStampRecord.setCompPhone(useCompany.getCompPhone());
            newStampRecord.setCompAddress(useCompany.getCompAddress());
            newStampRecord.setSoleCode(useCompany.getSoleCode());

            newStampRecord.setLegalCertCode(useCompany.getLegalCertCode());

            newStampRecord.setLegalPhone(useCompany.getLegalPhone());

            newStampRecord.setLegalName(useCompany.getLegalName());

            newStampRecord.setLegalCertType(useCompany.getLegalCertType());


            newStampRecord.setMakeComp(makeCompany);

            newStampRecord.setUseComp(useCompany);

            newStampRecord.setApplyInfos(JsonMapper.toJsonString(jsonStamp(newStamp).toArrayList()));

            //Attachment deal

            List<Attachment> attachments = attachmentService.setUUIDList(stampWorkTypeDTO.getFileType());

            String attachJson = JsonMapper.toJsonString(attachments);

            newStampRecord.setAttachs(attachJson);

            attachmentService.saveAttachmentsInHosts(attachments, files, useCompany.getCompanyName());

            if (!attachments.isEmpty()) {
                attachmentDao.insertAttachmentList(attachments);
            }


            //处理物理印章的编码问题
            if (newStamp.getStampShape().equals("1")) {

                newStamp.setStampCode(stampMakeService.phyStampCode());

            }

            newStampRecord.preInsert();

            newStamp.setLastRecord(newStampRecord);

            if (stampDao.insert(newStamp) == 0) {

                throw new StampMakeException("create New RepairStamp Error!");

            }

            if (stampRecordDao.insert(newStampRecord) == 0) {
                throw new StampMakeException("create New RepairStampRecord Error!");
            }


            return new Condition(Condition.SUCCESS_CODE, "Success", newStamp);

        } catch (StampExistException e) {

            throw e;

        } catch (StampMakeException e) {

            throw e;

        } catch (Exception e) {

            e.printStackTrace();
            throw new StampMakeException("System error :" + e.getMessage());
        }


    }


    /**
     * 印章的缴销与挂失
     *
     * @param stampWorkTypeDTO
     * @param files
     * @param businessState
     * @return
     * @throws SoleCodeException
     * @throws StampExistException
     * @throws StampMakeException
     */
    @Transactional(readOnly = false)
    public Condition reportOrLogoutStamp(StampWorkTypeDTO stampWorkTypeDTO, MultipartFile[] files, StampWorkType businessState, MultipartFile permission)
            throws StampExistException, StampMakeException {
        try {

            Stamp stamp = new Stamp();
            stamp.setId(stampWorkTypeDTO.getStamp().getId());
            stamp.setStampShape(stampWorkTypeDTO.getStamp().getStampShape());
            //获得该印章信息
            stamp = stampDao.get(stamp);

            StampRecord stampRecord = stampWorkTypeDTO.getStampRecord();

            //临时stampRecord 是为了该印章第一次备案的经办人相关信息
            StampRecord tempStampRecord = stampRecord;
            //默认设置为备案stampRecord类型
            tempStampRecord.setWorkType(StampWorkType.APPLY);

            stampRecord = tempStampRecord = stampRecordDao.get(tempStampRecord);

            Company useCompany = companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE));

            Company makeCompany = UserUtils.getUser().getCompanyInfo();

            //TODO 备案处理

/*            stampRecord.setCompanyName(useCompany.getCompanyName());
            stampRecord.setType1(useCompany.getType1());
            stampRecord.setCompPhone(useCompany.getCompPhone());
            stampRecord.setCompAddress(useCompany.getCompAddress());
            stampRecord.setSoleCode(useCompany.getSoleCode());
            stampRecord.setLegalCertCode(useCompany.getLegalCertCode());

            stampRecord.setLegalPhone(useCompany.getLegalPhone());

            stampRecord.setLegalName(useCompany.getLegalName());

            stampRecord.setLegalCertType(useCompany.getLegalCertType());*/

            //设置经办人相关信息
   /*         stampRecord.setAgentCertCode(tempStampRecord.getAgentCertCode());

            stampRecord.setAgentPhone(tempStampRecord.getAgentPhone());

            stampRecord.setAgentName(tempStampRecord.getAgentName());

            stampRecord.setAgentCertType(tempStampRecord.getAgentCertType());*/

            stampRecord.setUseComp(useCompany);

            stampRecord.setMakeComp(makeCompany);

            stampRecord.setWorkType(businessState);

            stampRecord.setApplyInfos(JsonMapper.toJsonString(jsonStamp(stamp).toArrayList()));

            stampRecord.preInsert();

            //TODO 印章处理
            // 判断当前的业务类型 选择 印章要改变的状态
            stamp.setUseState(businessState == StampWorkType.LOGOUT ?
                    OprationState.LOGOUT : OprationState.REPORT);

            stamp.setSysState(SysState.UNUSABLE);

            stamp.setLastRecord(stampRecord);

            stamp.setNowMakeComp(makeCompany);

            stamp.setRecordState(businessState);

            stamp.preUpdate();

            //TODO 附件处理
            //1、附件的本地化处理
            //2、附件信息转对象处理
            //3、备案记录 附件信息保存处理 。

            //附件处理 start

//            List<Attachment> attachments = attachmentService.setUUIDList(stampWorkTypeDTO.getFileType());
//
//            String attachJson = JsonMapper.toJsonString(attachments);

            stampRecord.setAttachs(tempStampRecord.getAttachs());

//            attachmentService.saveAttachmentsInHosts(attachments, files, useCompany.getCompanyName());

//            if(!attachments.isEmpty()) {
//                attachmentDao.insertAttachmentList(attachments);
//            }

            //附件处理 end

            //许可证处理
            String permissionPath = permissionUpload(stampRecord, permission, stamp);
            stampRecord.setPermissionPhoto(permissionPath);


          /*  if (stampDao.updateStampState(stamp) == 0) {

                throw new Exception("update StampState error!");
            }
*/
            if (stampDao.updateStampState2(stamp) == 0) {

                throw new Exception("update StampState error!");
            }

            if (stampRecordDao.insert(stampRecord) == 0) {

                throw new Exception("create StampRecord Error! ");
            }


            return new Condition(Condition.SUCCESS_CODE, "Success", null);

        } catch (StampExistException e) {

            throw e;

        } catch (StampMakeException e) {

            throw e;

        } catch (Exception e) {
            e.printStackTrace();
            throw new StampMakeException("System error :" + e.getMessage());
        }


    }

    /**
     * 印章变更业务 7.14增加
     *
     * @param stampWorkTypeDTO
     * @param files
     * @param businessState
     * @param permission
     * @return
     */
    @Transactional
    public Condition changeStamp(StampWorkTypeDTO stampWorkTypeDTO, MultipartFile[] files, StampWorkType businessState, MultipartFile permission) {
        //TODO rewrite
        StampRecord firstStampRecord = stampWorkTypeDTO.getStampRecord();
        try {

            //增加判断备案页面的用户是否选中统一码这个选项，再进行判断统一码的格式（规则）{1：统一码，2：其他码}
            //在超级管理员登录后，系统设置-字典管理-isSoleCode可以查询到该键值对并且修改相应的内容
            System.out.println("stampWorkTypeDTO.getStampRecord().getIsSoleCode())" + stampWorkTypeDTO.getStampRecord().getIsSoleCode());
            if ("1".equals(stampWorkTypeDTO.getStampRecord().getIsSoleCode())) {

                //统一码问题
                String newSoleCode = stampWorkTypeDTO.getUseCompany().getSoleCode().trim();

                if (newSoleCode.trim().length() == 18 && newSoleCode.matches(BusinessValidateUtil.newCodePattern1)) {

                    if (!BusinessValidateUtil.checkSoleCode(newSoleCode, 18)) {

                        throw new SoleCodeException("统一码错误!");
                    }

                    //获取组织结构代码
                    String areaCode = newSoleCode.substring(2, 8);

                    Area areaByCode = areaDao.getAreaByCode(areaCode);

                    // area = areaDao.getAreaByCode(areaCode);
//                area = UserUtils.getUser().getCompanyInfo().getArea();
                    //当前用户的area
                    Area area = areaDao.get(new Area(UserUtils.getUser().getCompanyInfo().getArea().getId()));

                    if (areaByCode == null) {
                        throw new SoleCodeException("统一码错误");
                    }

                    //判断 areaBycode 与当前用章单位的area的 关系
                    //若为同一area 或者 当前area 为areabyCode的子级时，通过
                    //否则则抛出异常
                    if (!(area.judgeAreaCode(areaByCode) || area.containArea(areaByCode))) {

                        throw new SoleCodeException("新统一码归属区域不符合规定!");

                    }


                } else if (newSoleCode.trim().length() == 10 && newSoleCode.matches(BusinessValidateUtil.newCodePattern2)) {

                    if (!BusinessValidateUtil.checkSoleCode(newSoleCode.replace("-", "").trim(), 9)) {

                        throw new SoleCodeException("统一码错误!");
                    }

                    //获得当前 刻章点公司的地区
                    Area area = UserUtils.getUser().getCompanyInfo().getArea();


                } else if (newSoleCode.trim().length() == 9 && newSoleCode.matches(BusinessValidateUtil.newCodePattern3)) {

                    if (!BusinessValidateUtil.checkSoleCode(newSoleCode.replace("-", "").trim(), 9)) {

                        throw new SoleCodeException("统一码错误!");

                    }
                    //获得当前 刻章点公司的地区
                    Area area = UserUtils.getUser().getCompanyInfo().getArea();


                    //15位正则匹配
                } else if (newSoleCode.trim().length() == 15 && newSoleCode.matches(BusinessValidateUtil.newCodePattern4)) {

                    if (!BusinessValidateUtil.checkSoleCode(newSoleCode.trim(), 15)) {

                        throw new SoleCodeException("统一码错误!");

                    }

                    //到了这里说明码正确
                    //截取社会代码中的统一码
                    //0-5位
                    String areaCode = newSoleCode.substring(0, 6);

                    Area areaByCode = areaDao.getAreaByCode(areaCode);

                    // area = areaDao.getAreaByCode(areaCode);
//                area = UserUtils.getUser().getCompanyInfo().getArea();
                    //当前用户的area
                    Area area = areaDao.get(new Area(UserUtils.getUser().getCompanyInfo().getArea().getId()));

                    if (areaByCode == null) {
                        throw new SoleCodeException("统一码错误!");
                    }

                    //判断 areaBycode 与当前用章单位的area的 关系
                    //若为同一area 或者 当前area 为areabyCode的子级时，通过
                    //否则则抛出异常
                    if (!(area.judgeAreaCode(areaByCode) || area.containArea(areaByCode))) {

                        throw new SoleCodeException("新统一码归属区域不符合规定!");

                    }

                } else {

                    throw new SoleCodeException("统一码错误!");

                }
            }//不作处理，原先useCompany.area已存在
//            }else{
//
//                Area area = areaDao.get(new Area(UserUtils.getUser().getCompanyInfo().getArea().getId()));
//
//                useCompany.setArea(area);
//
//            }


            //获得印章的信息
            Stamp stamp = new Stamp(stampWorkTypeDTO.getStamp().getId());

            stamp.setStampShape(stampWorkTypeDTO.getStamp().getStampShape());

            stamp = stampDao.get(stamp);

//            List<Attachment> attachments = attachmentService.setUUIDList(stampWorkTypeDTO.getFileType());
//
//            String attachJson = JsonMapper.toJsonString(attachments);

            StampRecord stampRecord = new StampRecord();

            stampRecord = stampWorkTypeDTO.getStampRecord();

            //  System.out.println("两个对象地址是否同一个："+(firstStampRecord==stampRecord));

            //临时stampRecord 是为了该印章第一次备案的经办人相关信息
            StampRecord tempStampRecord = stampRecord;
            //默认设置为备案stampRecord类型
            tempStampRecord.setWorkType(StampWorkType.APPLY);

            stampRecord = tempStampRecord = stampRecordDao.get(tempStampRecord);

            Company newCompany = stampWorkTypeDTO.getUseCompany();

            newCompany.setCompType(CompanyType.USE);

            Company useCompany = companyDao.get(new Company(stamp.getUseComp().getId(), CompanyType.USE));

            Company makeCompany = UserUtils.getUser().getCompanyInfo();

            //TODO 先对原印章进行缴销备案记录登记

         /*   stampRecord.setCompanyName(useCompany.getCompanyName());
            stampRecord.setType1(useCompany.getType1());
            stampRecord.setCompPhone(useCompany.getCompPhone());
            stampRecord.setCompAddress(useCompany.getCompAddress());
            stampRecord.setSoleCode(useCompany.getSoleCode());
            stampRecord.setLegalCertCode(useCompany.getLegalCertCode());

            stampRecord.setLegalPhone(useCompany.getLegalPhone());

            stampRecord.setLegalName(useCompany.getLegalName());

            stampRecord.setLegalCertType(useCompany.getLegalCertType());*/
            //设置经办人相关信息
     /*       stampRecord.setAgentCertCode(tempStampRecord.getAgentCertCode());

            stampRecord.setAgentPhone(tempStampRecord.getAgentPhone());

            stampRecord.setAgentName(tempStampRecord.getAgentName());

            stampRecord.setAgentCertType(tempStampRecord.getAgentCertType());*/

            stampRecord.setUseComp(useCompany);

            stampRecord.setMakeComp(makeCompany);

            stampRecord.setWorkType(StampWorkType.LOGOUT);

            stampRecord.setApplyInfos(JsonMapper.toJsonString(jsonStamp(stamp).toArrayList()));

            stampRecord.preInsert();

/*            stampRecord.setAttachs(tempStampRecord.getAttachs());*/


            //许可证处理
            String permissionPath = permissionUpload(stampRecord, permission, stamp);
            stampRecord.setPermissionPhoto(permissionPath);

            //TODO 对原有的印章做缴销状态更改

            //印章信息的状态更改
            stamp.setUseState(OprationState.LOGOUT);

            stamp.setSysState(SysState.UNUSABLE);

            stamp.setLastRecord(stampRecord);

            stamp.setRecordState(StampWorkType.LOGOUT);

            stamp.setNowMakeComp(makeCompany);

            stamp.preUpdate();

            if (stampRecordDao.insert(stampRecord) == 0) {

                throw new StampExistException("create New stampRecord Error!");
            }


            if (stampDao.changeOldStampState(stamp) == 0) {

                throw new StampExistException("change Old StampState Error!");
            }


            //copy NewStamp
            Stamp newStamp = new Stamp(useCompany, makeCompany, makeCompany,
                    stamp.getStampName(), stamp.getStampSubType(), stamp.getStampType(), stamp.getStampTexture(),
                    StampState.RECEPT, stamp.getStampShape(), stamp.getStampShapeId());

            String newStampName = getRightStampName(newStamp, useCompany, 0).toString();


            newStamp.setStampName(newStampName);

            newStamp.setSysState(SysState.USABLE);

            newStamp.setRecordState(StampWorkType.CHANGE);

            newStamp.setUseState(OprationState.STOP);

            newStamp.setPhyModel(stamp.getPhyModel());

            newStamp.setEleModel(stamp.getEleModel());

            newStamp.setRecordDate(new Date());

            //处理物理印章的编码问题
            if (newStamp.getStampShape().equals("1")) {

                newStamp.setStampCode(stampMakeService.phyStampCode());

            }

            setStampMoney(newStamp);

            newStamp.preInsert();

            //新印章新的备案记录
//            StampRecord stampRecord_input = stampWorkTypeDTO.getStampRecord();

            TempAgent tempAgent = stampWorkTypeDTO.getTempAgent();

            StampRecord newStampRecord = new StampRecord(firstStampRecord.getSerialNum(), tempAgent.getAgentName(),
                    tempAgent.getAgentCertType(), tempAgent.getAgentCertCode(),
                    tempAgent.getAgentPhone(), StampWorkType.CHANGE, firstStampRecord.getWorkRemakrs());

            newStampRecord.setCompanyName(newCompany.getCompanyName());
            newStampRecord.setType1(newCompany.getType1());
            newStampRecord.setCompPhone(newCompany.getCompPhone());
            newStampRecord.setCompAddress(newCompany.getCompAddress());
            newStampRecord.setSoleCode(newCompany.getSoleCode());
            newStampRecord.setLegalCertCode(newCompany.getLegalCertCode());

            newStampRecord.setLegalPhone(newCompany.getLegalPhone());

            newStampRecord.setLegalName(newCompany.getLegalName());

            newStampRecord.setLegalCertType(newCompany.getLegalCertType());

            newStampRecord.setMakeComp(makeCompany);

            newStampRecord.setUseComp(useCompany);

            newStampRecord.setApplyInfos(JsonMapper.toJsonString(jsonStamp(newStamp).toArrayList()));

            //Attachment deal

            List<Attachment> attachments = attachmentService.setUUIDList(stampWorkTypeDTO.getFileType());

            String attachJson = JsonMapper.toJsonString(attachments);

            newStampRecord.setAttachs(attachJson);

            attachmentService.saveAttachmentsInHosts(attachments, files, newCompany.getCompanyName());

            if (!attachments.isEmpty()) {
                attachmentDao.insertAttachmentList(attachments);
            }

            newStampRecord.preInsert();

            newStamp.setLastRecord(newStampRecord);

            //更新公司信息
            newCompany.setId(useCompany.getId());

            newCompany.setCompType(CompanyType.USE);

            if (stampDao.insert(newStamp) == 0) {

                throw new StampMakeException("create New RepairStamp Error!");

            }

            if (companyDao.changeCompanyInfo(newCompany) == 0) {

                throw new StampMakeException("change Old CompanyInfo Error!");
            }


            if (stampRecordDao.insert(newStampRecord) == 0) {
                throw new StampMakeException("create New RepairStampRecord Error!");
            }


            return new Condition(Condition.SUCCESS_CODE, "Success", newStamp);

        } catch (SoleCodeException e) {

            return new Condition(Condition.ERROR_CODE, e.getMessage());

        } catch (StampExistException e) {

            throw e;

        } catch (StampMakeException e) {

            throw e;

        } catch (Exception e) {

            e.printStackTrace();

            throw new StampMakeException("System error :" + e.getMessage());
        }
    }


    @Transactional(readOnly = true)
    public Moulage getMoulage(String stampId) {

        Stamp stamp = new Stamp();

        stamp.setId(stampId);
        stamp.setStampName(StampShape.PHYSTAMP.getKey());

        String moulageId = stampDao.getMoulageIdByStampId(stamp);

        Moulage moulage = moulageDao.getMoulageById(moulageId);

        return moulage;
    }

    /**
     * 检查 该印章是否存在
     *
     * @param stamp
     * @return
     */
    @Transactional(readOnly = true)
    public boolean checkStampExist(Stamp stamp) {

        if (stampDao.checkStampExist(stamp) == 0) {
            return false;
        }

        return true;
    }

    /**
     * 检查是否是补刻印章
     *
     * @param stampId
     * @return
     */
    @Transactional(readOnly = true)
    public boolean checkRepairStamp(String stampId, String stampShape) {

        Stamp stamp = new Stamp(stampId);
        stamp.setStampShape(stampShape);

        stamp = stampDao.get(stamp);

        if (stamp != null) {

            if (stamp.getStampShapeId() != null && (!"".equals(stamp.getStampShapeId()))
                    && stamp.getStampState() == StampState.RECEPT) {

                return true;
            }

        }

        return false;
    }

    /**
     * 补刻物理印章 提交备案
     *
     * @param stampId
     * @throws StampExistException
     * @throws StampMakeException
     */
    @Transactional(readOnly = false)
    public void repairPhyStamp(String stampId)
            throws StampExistException, StampMakeException {

        try {

            Stamp stamp = new Stamp(stampId);

            if (stamp == null) {

                throw new StampExistException("Stamp is not Exist!");
            }

            stamp.setStampState(StampState.ENGRAVE);

            stamp.setMakeDate(new Date());

            if (stampDao.updateRepairStamp(stamp) == 0) {

                throw new StampMakeException("repairPhyStamp Error!");
            }

        } catch (StampExistException e) {

            e.printStackTrace();

            throw e;

        } catch (StampMakeException e) {

            e.printStackTrace();

            throw e;

        } catch (Exception e) {

            throw new StampMakeException("repairPhyStamp Error!");
        }

    }

    /**
     * 查看对应类型的物理印章信息
     * 主要用于查找印模
     *
     * @param stamp
     * @return
     */
    @Transactional(readOnly = true)
    public List<Stamp> findPhyStampByStampType(Stamp stamp) {

        Stamp phyStamp = new Stamp();

        phyStamp.setUseComp(stamp.getUseComp());

        phyStamp.setStampShape("1");

        phyStamp.setStampState(StampState.DELIVERY);

        phyStamp.setSysState(SysState.USABLE);

        phyStamp.setUseState(OprationState.OPEN);

        phyStamp.setStampType(stamp.getStampType());

        List<Stamp> stamps = newStampDao.findDependPhyStamps(phyStamp);


        return stamps;
    }

    /**
     * 保存电子印模信息
     *
     * @param eleFile
     * @return
     */

    @Transactional(readOnly = false)
    public Condition saveElectron(ElectronStampDTO electronStampDTO, MultipartFile eleFile) {
        //todo
        Condition condition = new Condition();

        try {

            Electron electron = electronStampDTO.getElectron();

            Stamp phyStamp = new Stamp(electronStampDTO.getPhyStampId());
            phyStamp.setStampShape(StampShape.PHYSTAMP.getKey());
            phyStamp = stampDao.get(phyStamp);

            Stamp stamp = new Stamp();
            stamp.setId(electronStampDTO.getStamp().getId());
            stamp.setStampShape(StampShape.ELESTAMP.getKey());
            stamp = stampDao.get(stamp);

            electron.setId(IdGen.uuid());
            electron.setCreateBy(UserUtils.getUser());

            Company useCompany = stamp.getUseComp();
            useCompany.setCompType(CompanyType.USE);

            useCompany = companyDao.get(useCompany);

            User useCompanySysUser = userDao.getUseComapnySysUser(useCompany.getId(), UserType.USE);

            stamp.setUseComp(useCompany);

            /*String eleFileVitrualPath = attachmentService.saveSealFile(eleFile, stamp);
            electron.setSealPath(eleFileVitrualPath);*/

            electron.setUser(useCompanySysUser);

            stamp.setStampState(StampState.ENGRAVE);

            electron.setStampName(phyStamp.getStampName());

            electron.setCreateTime(new Date());

            electron.setSealEleModel(phyStamp.getEleModel());

            //默认为启用状态
            electron.setSysOprState(OprationState.OPEN);

            electronDao.insert(electron);

            stamp.setStampShapeId(electron.getId());

            stamp.setMakeDate(new Date());
            stamp.setStampName(phyStamp.getStampName());
            stamp.setPhyModel(phyStamp.getPhyModel());
            stamp.setEleModel(phyStamp.getEleModel());
            stamp.setEsEleModel(phyStamp.getEsEleModel());
            stamp.setStampCode(phyStamp.getStampCode());
            stampDao.bindStampAndMoulage(stamp);

            stamp.setBindStamp(phyStamp);

            phyStamp.setBindStamp(stamp);

            //为电子印章设置水印印模图
            stamp.setWaterEleModel(phyStamp.getWaterEleModel());
            stampDao.saveWaterImage(stamp);

            //11.14 增加电子印章与物理印章相互绑定
            if (newStampDao.bindPhyForEle(stamp) == 1 && newStampDao.bindEleForPhy(phyStamp) == 1) {
                condition.setCode(Condition.SUCCESS_CODE);
                condition.setMessage("保存电子印章信息成功");
            } else {
                throw new StampMakeException("电子印章与物理印章绑定失败!");
            }

        } catch (StampMakeException e) {

            e.printStackTrace();
            throw e;

        } catch (Exception e) {

            e.printStackTrace();
            throw new StampMakeException("Save Moulage Error!");
        }

        return condition;

    }

    /**
     * 保存存量电子印模信息
     *
     * @param eleFile
     * @return
     */
    @Transactional(readOnly = false)
    public Condition saveElectron_ES(ElectronStampDTO electronStampDTO, MultipartFile eleFile) {

        Condition condition = new Condition();

        try {

            Electron electron = electronStampDTO.getElectron();

            Stamp stamp = new Stamp();
            stamp.setId(electronStampDTO.getStamp().getId());
            stamp.setStampShape(StampShape.ELESTAMP.getKey());
            stamp = stampDao.get(stamp);

            electron.setId(IdGen.uuid());
            electron.setCreateBy(UserUtils.getUser());

            Company useCompany = stamp.getUseComp();
            useCompany.setCompType(CompanyType.USE);
            useCompany = companyDao.get(useCompany);

            User useCompanySysUser = userDao.getUseComapnySysUser(useCompany.getId(), UserType.USE);

            stamp.setUseComp(useCompany);

            /*String eleFileVitrualPath = attachmentService.saveSealFile(eleFile, stamp);
            electron.setSealPath(eleFileVitrualPath);*/

            electron.setUser(useCompanySysUser);

            stamp.setStampState(StampState.ENGRAVE);

            electron.setStampName(stamp.getStampName());

            electron.setCreateTime(new Date());

            electron.setSealEleModel(stamp.getEleModel());

            //默认为启用状态
            electron.setSysOprState(OprationState.OPEN);

            electronDao.insert(electron);

            stamp.setStampShapeId(electron.getId());

            stamp.setMakeDate(new Date());
            stamp.setStampName(stamp.getStampName());
            stampDao.bindStampAndMoulage(stamp);

            condition.setCode(Condition.SUCCESS_CODE);
            condition.setMessage("保存电子印章信息成功");

        } catch (StampMakeException e) {

            e.printStackTrace();
            throw e;

        } catch (Exception e) {

            e.printStackTrace();
            throw new StampMakeException("Save Moulage Error!");
        }

        return condition;

    }

    /**
     * 检查是否有刻制数量限制
     */
    @Transactional(readOnly = true)
    public Condition checkCountSet(StampMakeDTO stampMakeDTO) {

        //先检索当前刻章点的刻制 数量设定
        Company makeCompany = UserUtils.getUser().getCompanyInfo();

        //1 物理印章刻制设定
        CountSet phyCountSet = new CountSet();

        phyCountSet.setCompany(makeCompany);

        phyCountSet.setStampShape(StampShape.PHYSTAMP);

        phyCountSet = countSetDao.get(phyCountSet);

        int phyLimit = -1;

        if (phyCountSet != null && (!"".equals(phyCountSet.getId()))) {

            phyLimit = phyCountSet.getCount();

        }


        //检查电子印章的设定
        CountSet eleCountSet = new CountSet();

        eleCountSet.setStampShape(StampShape.ELESTAMP);

        eleCountSet.setCompany(makeCompany);

        eleCountSet = countSetDao.get(eleCountSet);

        int eleLimit = -1;

        if (eleCountSet != null && (!"".equals(eleCountSet.getId()))) {

            eleLimit = eleCountSet.getCount();

        }

        //end

        if (eleLimit == -1 && phyLimit == -1) {

            return new Condition(Condition.SUCCESS_CODE);

        }

        //获得 刻制列表
        List<Stamp> stamps = dealStampShades(stampMakeDTO.getStamps());

        //此次备案物理印章的数量
        int phyMakeCount = 0;

        //此次备案电子印章的数量
        int eleMakeCount = 0;

        // 统计此次刻制的印章数量
        for (Stamp stamp : stamps) {

            if (StampShape.PHYSTAMP.getKey().equals(stamp.getStampShape())) {

                phyMakeCount++;
            }

            if (StampShape.ELESTAMP.getKey().equals(stamp.getStampShape())) {

                eleMakeCount++;
            }

        }
        //end

        //获得当前刻章点已经进行备案的物理印章和电子印章数目
        Stamp checkStamp = new Stamp();

        checkStamp.setMakeComp(makeCompany);

        checkStamp.setNowMakeComp(makeCompany);


        //物理
        if (phyMakeCount > 0 && phyLimit != -1) {

            checkStamp.setStampShape(StampShape.PHYSTAMP.getKey());

            List<Stamp> phyStamps = stampDao.findList(checkStamp);

            int phyStampCount = phyStamps.size();

            if ((phyLimit - phyStampCount) < phyMakeCount) {


                return new Condition(Condition.ERROR_CODE, "物理印章刻制数量被管控,超出制作数量限制!");
            }


        }


        //电子
        if (eleMakeCount > 0 && eleLimit != -1) {

            checkStamp.setStampShape(StampShape.ELESTAMP.getKey());

            List<Stamp> eleStamps = stampDao.findList(checkStamp);

            int eleStampCount = eleStamps.size();

            if ((eleLimit - eleStampCount) < eleMakeCount) {


                return new Condition(Condition.ERROR_CODE, "电子印章刻制数量被管控,超出制作数量限制!");
            }


        }

        //end

        return new Condition(Condition.SUCCESS_CODE);
    }

    /**
     * 根据提交的备案信息
     * 1、创建一个新的用章单位 的法人用户
     * 2、创建一个公司结构 根部
     * 3、处理好各级关系
     *
     * @param stampRecord 新增的用章公司的信息
     * @Param nowArea 当前的地区
     */
    public User createNewUser(StampRecord stampRecord, Area nowArea)
            throws PhoneLoginNameException, StampMakeException {
        try {

            //创建组织结构
            Office office = new Office();

            office.setName(stampRecord.getUseComp().getCompanyName());
            office.setMaster(stampRecord.getUseComp().getLegalName());
            office.setAddress(stampRecord.getUseComp().getCompAddress());
            office.setZipCode(stampRecord.getUseComp().getPostcode());
            office.setPhone(stampRecord.getUseComp().getCompPhone());
            office.setGrade("1");
            office.setType("1");
            office.setUseable("1");
            office.setArea(nowArea);

            Office parent = new Office();
            parent.setId("0");
            office.setParent(parent);
            office.setParentIds("0,");
            office.preInsert();

            //保存组织结构
            officeDao.insert(office);
            //创建用户
            User newUser = new User();

            if (StringUtils.isNotBlank(office.getId())) {

                newUser.setOffice(office);//所属部门
                newUser.setIsSysrole("1");
                newUser.setName(stampRecord.getUseComp().getLegalName());
                newUser.setLoginFlag("1");
                newUser.setUserType(UserType.USE);
                newUser.setPhone(stampRecord.getUseComp().getLegalPhone());

                //检查该手机是否已经有用户名了
/*                if (!(0 == userDao.checkLoginName(stampRecord.getUseComp().getLegalPhone(), UserType.USE))) {

                    throw new PhoneLoginNameException("this legal Phone User exists!");
                }*/

                //如果存在该手机号码，则加上-2
                Company company = companyDao.getCompanyBysoleCodeAndCompName(stampRecord.getUseComp().getSoleCode(),
                        stampRecord.getUseComp().getCompanyName(),
                        CompanyType.USE);

                if (company == null || "".equals(company)) {//如果不存在该公司，则进行一下判断

                    String tempLegalPhone = stampRecord.getUseComp().getLegalPhone();

                    int count = userDao.checkLoginNameNumber(tempLegalPhone, UserType.USE);

                    if (tempLegalPhone != null && count > 0) {//如果不存在该公司且该手机号码已存在，则加-2
                        count++;
                        System.out.println("生成的登录名===" + tempLegalPhone + "-" + count);

                        newUser.setLoginName(tempLegalPhone + "-" + count);
                    } else {

                        //以法人手机作为登录名
                        newUser.setLoginName(stampRecord.getUseComp().getLegalPhone());

                    }
                } else {

                    //以法人手机作为登录名
                    newUser.setLoginName(stampRecord.getUseComp().getLegalPhone());
                }

                //默认密码暂时为123456
                newUser.setPassword(SystemService.entryptPassword("123456"));
                newUser.preInsert();

                //第一次保存用户
                userDao.insert(newUser);

                //生成证书并绑定
                certificateService.generateCert(newUser);
            }

            //保存 用户角色
            if (StringUtils.isNotBlank(newUser.getId())) {
                //设置角色
                //制章点用户
                //获得制章点用户角色
                Role role = roleDao.get("a92d24f1bc0f4b3daa5a66b3daa8cd4d");
                newUser.getRoleList().add(role);

                userDao.insertUserRole(newUser);

            }

            //建立好 Company useCompany --user.CompanyInfo
            Company useCompany = stampRecord.getUseComp();
            //此时的stampRecord信息 有 经办人 信息
            //stampRecord 中的useCompany 有
            // 1、申请单位名称
            // 2、单位类别123
            // 3、申请 刻章单位统一社会代码
            // 4、申请刻章单位联系电话
            // 5、申请刻章单位地址
            // 6、附件信息列表
            // 7、法人信息

            useCompany.setCompState(CompanyState.USING);
            useCompany.setSysOprState(OprationState.OPEN);
            useCompany.setCompType(CompanyType.USE);
            useCompany.setArea(nowArea);
            // 生成单位编码（行政区划+6位递增序号）
            String CodeCount = "000000" + String.valueOf(companyDao.getSequenceNextVal(nowArea.getCode(), CompanyType.USE.getKey()));
            CodeCount = CodeCount.substring(CodeCount.length() - 6);
            String companyCode = nowArea.getCode() + CodeCount;
            useCompany.setCompanyCode(companyCode);

            //插入预处理
            useCompany.preInsert();

            //保存公司信息
            companyDao.insert(useCompany);

            newUser.setCompany(useCompany);//所属公司
            newUser.setUserTypeId(useCompany.getId());

            return newUser;

        } catch (PhoneLoginNameException e) {

            e.printStackTrace();

            throw e;

        } catch (StampMakeException e) {

            e.printStackTrace();

            throw e;
        }


    }

    public User findUseUserByStamp(Stamp stamp) {

        stamp = stampDao.get(stamp);

        User manager = userDao.getUseComapnySysUser(stamp.getUseComp().getId(), UserType.USE);

        return manager;
    }

    /**
     * 递归获得已在该印章正确的名字
     *
     * @return
     */
    protected StringBuffer getRightStampName(Stamp stamp, Company useCompany, int count, Map<String, Integer> stampType) {

        StringBuffer stampName = new StringBuffer();

        stampName.append(DictUtils.getDictLabels(stamp.getStampType(), "stampType", null) + "-");

        if (count == 0) {

            if (stampType.containsKey(stamp.getStampType())) {

                count = stampType.get(stamp.getStampType());

                stampType.put(stamp.getStampType(), ++count);


            } else {

                count = stampDao.getStampTypeCountByUseCompanyAndStampType(stamp.getStampType(), useCompany, stamp.getStampShape());

                stampType.put(stamp.getStampType(), ++count);
            }

            stampName.append(stampType.get(stamp.getStampType()));

        } else {

            stampType.put(stamp.getStampType(), count);

            stampName.append(count);

        }


        System.out.println((0 == stampDao.checkStampNameExist(stampName.toString(), stamp.getStampShape(), useCompany)));
        if (!(0 == stampDao.checkStampNameExist(stampName.toString(), stamp.getStampShape(), useCompany))) {


            stampName = getRightStampName(stamp, useCompany, count + 1, stampType);

        }

        return stampName;
    }

    protected StringBuffer getRightStampName(Stamp stamp, Company useCompany, int count) {

        StringBuffer stampName = new StringBuffer();

        stampName.append(DictUtils.getDictLabels(stamp.getStampType(), "stampType", null) + "-");

        if (count == 0) {

            count = stampDao.getStampTypeCountByUseCompanyAndStampType(stamp.getStampType(), useCompany, stamp.getStampShape());

        }

        count++;

        stampName.append(count);

        if (!(0 == stampDao.checkStampNameExist(stampName.toString(), stamp.getStampShape(), useCompany))) {

            stampName = getRightStampName(stamp, useCompany, count);

        }

        return stampName;
    }

    /**
     * 处理印章列表
     *
     * @param stamps
     * @return
     */
    protected List<Stamp> dealStampShades(List<Stamp> stamps) {

        List<Stamp> stamps1 = new ArrayList<Stamp>();

        //对印章章型 做印章刻制列表的处理
        for (Stamp stamp : stamps) {

            //如果是多形式存在
            if (stamp.getStampShape().contains(",")) {
                //',' 分割字符串
                String[] stampShades = stamp.getStampShape().split(",");

                for (int i = 0; i < stampShades.length; i++) {

                    if ("".equals(stampShades[i]) || "on".equals(stampShades[i])) {
                        continue;
                    }

                    Stamp newStamp = new Stamp();

                    newStamp.setStampShape(stampShades[i]);

                    newStamp.setStampType(stamp.getStampType());

                    if ("1".equals(stampShades[i])) {
                        newStamp.setStampTexture(stamp.getStampTexture());
                    } else if ("2".equals(stampShades[i])) {
                        newStamp.setStampTexture("99");
                    }

                    if (stamp.getStampCode() != null && !"".equals(stamp.getStampCode())) {
                        newStamp.setStampCode(stamp.getStampCode().trim());
                    }

                    if (stamp.getStampSubType() != null && !"".equals(stamp.getStampSubType())) {
                        newStamp.setStampSubType(stamp.getStampSubType());
                    }

                    newStamp.setRecordState(StampWorkType.APPLY);

                    stamps1.add(newStamp);

                }

            } else {

                Stamp newStamp = new Stamp();

                newStamp.setStampShape(stamp.getStampShape());

                newStamp.setStampType(stamp.getStampType());

                if ("1".equals(stamp.getStampShape())) {

                    newStamp.setStampTexture(stamp.getStampTexture());

                } else if ("2".equals(stamp.getStampShape())) {
                    newStamp.setStampTexture("99");
                }

                if (stamp.getStampCode() != null && !"".equals(stamp.getStampCode())) {
                    newStamp.setStampCode(stamp.getStampCode().trim());
                }

                if (stamp.getStampSubType() != null && !"".equals(stamp.getStampSubType())) {
                    newStamp.setStampSubType(stamp.getStampSubType());
                }

                newStamp.setRecordState(StampWorkType.APPLY);

                stamps1.add(newStamp);

            }

        }

        return stamps1;
    }

    protected Stamp jsonStamp(Stamp stamp) {

        Stamp jsonStamp = new Stamp();

        jsonStamp.setStampName(stamp.getStampName());

        jsonStamp.setId(stamp.getId());

        jsonStamp.setStampShape(stamp.getStampShape());

        jsonStamp.setStampTexture(stamp.getStampTexture());

        return jsonStamp;
    }

    /**
     * 处理物理印章的编码问题
     */
    public String phyStampCode() {


        //没有印模 先初始化一个物理印章编码

        User currentUser = UserUtils.getUser();

        Company makeCompany = currentUser.getCompanyInfo();

        //获得当前区域
        Area area = areaDao.get(makeCompany.getArea());

        String tempCount = area.getCount();

        int count = Integer.parseInt(tempCount);

        count++;

        String reallyCode = area.getCode() + "";

        String countStr = count + "";

        for (int i = 0; i < 7 - countStr.length(); i++) {

            reallyCode = reallyCode + "0";

        }

        //更新印章编码的数量
        areaDao.updateCount(area.getId(), countStr);

        reallyCode = reallyCode + countStr;

        return reallyCode;

    }


    /**
     * @param companyName
     * @return
     * @author bb(update)
     */
    public Condition findUseCompany(String companyName) {

        Condition<Company> condition = new Condition<Company>(Condition.ERROR_CODE, "找不到此公司信息!");

        Company company = new Company();

        company.setCompType(CompanyType.USE);

        company = companyDao.getCompanyByCompName(companyName, CompanyType.USE);


        if (company != null) {

            //尝试根据用章公司的信息获取对应的印章信息
            int engrave = checkStampTypeCount("1", company, "1");     //公章物理

            int engrave_02 = checkStampTypeCount("1", company, "2");     //财务专用印章
            int engrave_03 = checkStampTypeCount("1", company, "3");     //发票专用印章
            int engrave_04 = checkStampTypeCount("1", company, "4");     //合同专用章
            int engrave_05 = checkStampTypeCount("1", company, "5");     //业务专用章
            int engrave_06 = checkStampTypeCount("1", company, "6");     //法定代表人专用章
            int engrave_07 = checkStampTypeCount("1", company, "7");     //其他类型印章

            int elestamp = checkStampTypeCount("2", company, "1");    //公章电子

            company.setCheckEngraveCount(engrave);

            company.setEngrave_02(engrave_02);
            company.setEngrave_03(engrave_03);
            company.setEngrave_04(engrave_04);
            company.setEngrave_05(engrave_05);
            company.setEngrave_06(engrave_06);
            company.setEngrave_07(engrave_07);

            company.setCheckEleStampCount(elestamp);

            //去掉法人手机号码-2
//            company.setLegalPhone(company.getLegalPhone().substring(0, 11));

            condition.setEntity(company);

            condition.setCode(Condition.SUCCESS_CODE);

            condition.setMessage("查找成功!");

        }

        return condition;

    }

    /*
     *@author hjw
     *@description  上传许可证
     *@param [stampRecord, permission]
     *@return void
     *@date 2017/7/11
     */
    protected String permissionUpload(StampRecord stampRecord, MultipartFile permission, Stamp stamp) throws Exception {


        //相对路径
        StringBuffer virtualPath = new StringBuffer(Global.getConfig("permission.virtualPath"));
        //实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("permission.realPath"));

        try {
            //获得网络文件名
            String netAttachName = permission.getOriginalFilename();

            //  获取该文件的后缀文件类型
            int lastIndex = netAttachName.lastIndexOf(".");

            String lastName = netAttachName.substring(lastIndex, netAttachName.length());

            //文件名
            StringBuffer fileName = new StringBuffer();

            Area area = stampRecord.getUseComp().getArea();

            String areasRealPath = null;

            areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));

            realPath.append(areasRealPath);
            //初始化路径
            createDir(realPath.toString());

            fileName.append(stampRecord.getUseComp().getCompanyName() + "_" + stamp.getStampName() + "_" + System.currentTimeMillis() + lastName);

            virtualPath.append(areasRealPath.toString() + fileName.toString());

            realPath.append(fileName.toString());


            permission.transferTo(new File(realPath.toString()));

            return virtualPath.toString();

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }

    }

    /**
     * 递归获取目录
     *
     * @param area
     */
    protected String getRealFilePath(Area area) throws Exception {

        try {


            StringBuffer realFilePath = new StringBuffer("");

            if (!"2".equals(area.getType())) {

                //realFilePath.append(area.getName() + "\\");

                realFilePath.append(getRealFilePath(areaDao.get(new Area(area.getParentId()))) + area.getName() + "/");

            } else {

                realFilePath.append(area.getName() + "/" + realFilePath.toString());
            }

            return realFilePath.toString();

        } catch (Exception e) {

            throw e;
        }
    }


    /**
     * 把文件保存到相印的路径中
     */
    public static void createDir(String realPath) throws Exception {

        try {


            File file = new File(realPath);

            //目录存在则不需要创建
            if (!file.exists()) {
                System.out.println("目录结构不存在!正在创建...");
                file.mkdirs();
                System.out.println("目录创建成功!\n路径为:" + file.getAbsolutePath());
            } else {
                System.out.println("目录已经存在...\n路径为:" + file.getAbsolutePath());
            }

        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 为印章设置章收费.
     * 刻章点收费、经销商（区）收费，润城经销商收费.
     */
    protected void setStampMoney(Stamp stamp) {

        Company makeCompany = UserUtils.getUser().getCompanyInfo();

        Area district = areaDao.get(makeCompany.getArea());

        Area city = areaDao.get(district.getParent());

        Company cityAgeny = new Company(); //市经销商
        cityAgeny.setArea(city);
        cityAgeny.setCompType(CompanyType.AGENCY);
        cityAgeny = companyDao.getAgenyCompanyByArea(cityAgeny);

        Area province = areaDao.get(city.getParent());
        Company provinceAgeny = new Company(); //省经销商
        provinceAgeny.setArea(province);
        provinceAgeny.setCompType(CompanyType.AGENCY);
        provinceAgeny = companyDao.getAgenyCompanyByArea(provinceAgeny);


        Company rcCompany = new Company();
        rcCompany.setCompType(CompanyType.AGENCY);
        rcCompany.setArea(new Area("1"));//中国 - 润城经销商
        rcCompany.setSoleCode(Global.getConfig("runcheng.soleCode"));
        rcCompany = companyDao.getAgenyCompanyByArea(rcCompany);

        if (StampShape.PHYSTAMP.getKey().equals(stamp.getStampShape())) {

            int defaultPhyMakeMoney = Integer.parseInt(Global.getConfig("default.phyStamp"));

            //物理印章的制作费用获取

            //moneySetting -start 收费规则寻找
            //获取当前制章点的公司

            //1该刻章点的收费 makeMoneySetting
            MoneySetting phyStampMakeCompanyMoneySetting = new MoneySetting(makeCompany, PaymentType.PHYSTAMP, stamp.getStampTexture());

            phyStampMakeCompanyMoneySetting = moneySettingDao.getMoneySetting(phyStampMakeCompanyMoneySetting);

            if (phyStampMakeCompanyMoneySetting == null || "".equals(phyStampMakeCompanyMoneySetting.getId())) {

                stamp.setMakeMoney(defaultPhyMakeMoney);

            } else {

                stamp.setMakeMoney(phyStampMakeCompanyMoneySetting.getMoney());

            }


            //2市经销商收费cityMoneySetting

            MoneySetting phyStampCityMoneySetting = new MoneySetting(cityAgeny, PaymentType.PHYSTAMP, stamp.getStampTexture());
            phyStampCityMoneySetting.setArea(district);

            phyStampCityMoneySetting = moneySettingDao.getMoneySetting(phyStampCityMoneySetting);

            if (phyStampCityMoneySetting == null || "".equals(phyStampCityMoneySetting.getId())) {

                stamp.setCityMoney(defaultPhyMakeMoney);

            } else {
                stamp.setCityMoney(phyStampCityMoneySetting.getMoney());
            }


            //3省经销商收费provinceMoneySetting


            MoneySetting phyStampProvinceMoneySetting = new MoneySetting(provinceAgeny, PaymentType.PHYSTAMP, stamp.getStampTexture());

            phyStampProvinceMoneySetting.setArea(city);

            phyStampProvinceMoneySetting = moneySettingDao.getMoneySetting(phyStampProvinceMoneySetting);

            if (phyStampProvinceMoneySetting == null || "".equals(phyStampProvinceMoneySetting.getId())) {

                stamp.setProvinceMoney(defaultPhyMakeMoney);

            } else {

                stamp.setProvinceMoney(phyStampProvinceMoneySetting.getMoney());

            }


            //4润城经销商收费 rcMoneySetting

            MoneySetting rcPhyStampMoneySetting = new MoneySetting(rcCompany, PaymentType.PHYSTAMP, stamp.getStampTexture());

            rcPhyStampMoneySetting.setArea(province);

            rcPhyStampMoneySetting = moneySettingDao.getMoneySetting(rcPhyStampMoneySetting);

            if (rcPhyStampMoneySetting == null || "".equals(rcPhyStampMoneySetting.getId())) {

                stamp.setRcMoney(defaultPhyMakeMoney);

            } else {

                stamp.setRcMoney(rcPhyStampMoneySetting.getMoney());
            }


            //end


        }


        if (StampShape.ELESTAMP.getKey().equals(stamp.getStampShape())) {


            int defaultEleStampMoney = Integer.parseInt(Global.getConfig("default.eleStamp"));

            int defaultUkeyMoney = Integer.parseInt(Global.getConfig("default.ukey"));
            //moneySetting -start 收费规则寻找
            //获取当前制章点的公司


            //电子印章的制作费用获取

            //1该刻章点的收费 makeMoneySetting
            MoneySetting eleMoneySettingMakeCompany = new MoneySetting(makeCompany, PaymentType.ELESTAMP);

            eleMoneySettingMakeCompany = moneySettingDao.getMoneySetting(eleMoneySettingMakeCompany);

            int ukeyMoneyMakeCompany = 0;

            int eleMoneyMakeCompany = 0;

            if (eleMoneySettingMakeCompany == null || "".equals(eleMoneySettingMakeCompany.getId())) {

                eleMoneyMakeCompany = defaultEleStampMoney;

            } else {

                eleMoneyMakeCompany = eleMoneySettingMakeCompany.getMoney();

            }

            MoneySetting ukeyMoneySettingMakeCompany = new MoneySetting(makeCompany, PaymentType.UKEY);

            ukeyMoneySettingMakeCompany = moneySettingDao.getMoneySetting(ukeyMoneySettingMakeCompany);

            if (ukeyMoneySettingMakeCompany == null || "".equals(ukeyMoneySettingMakeCompany.getId())) {


                ukeyMoneyMakeCompany = defaultUkeyMoney;

            } else {

                ukeyMoneyMakeCompany = ukeyMoneySettingMakeCompany.getMoney();
            }

            stamp.setMakeMoney(ukeyMoneyMakeCompany + eleMoneyMakeCompany);

            //2市经销商收费cityMoneySetting
            int ukeyMoneyCity = 0;

            int eleMoneyCity = 0;

            MoneySetting eleStampCityMoneySetting = new MoneySetting(cityAgeny, PaymentType.ELESTAMP);
            eleStampCityMoneySetting.setArea(district);

            eleStampCityMoneySetting = moneySettingDao.getMoneySetting(eleStampCityMoneySetting);

            if (eleStampCityMoneySetting == null || "".equals(eleStampCityMoneySetting.getId())) {

                eleMoneyCity = defaultEleStampMoney;

            } else {
                eleMoneyCity = eleStampCityMoneySetting.getMoney();
            }


            MoneySetting ukeyStampCityMoneySetting = new MoneySetting(cityAgeny, PaymentType.UKEY);

            ukeyStampCityMoneySetting.setArea(district);

            ukeyStampCityMoneySetting = moneySettingDao.getMoneySetting(ukeyStampCityMoneySetting);

            if (ukeyStampCityMoneySetting == null || "".equals(ukeyStampCityMoneySetting.getId())) {

                ukeyMoneyCity = defaultUkeyMoney;

            } else {

                ukeyMoneyCity = ukeyStampCityMoneySetting.getMoney();

            }

            stamp.setCityMoney(ukeyMoneyCity + eleMoneyCity);

            //3省经销商收费provinceMoneySetting


            int ukeyMoneyProvince = 0;

            int eleMoneyProvince = 0;

            MoneySetting eleStampProvinceMoneySetting = new MoneySetting(provinceAgeny, PaymentType.ELESTAMP);

            eleStampProvinceMoneySetting.setArea(city);

            eleStampProvinceMoneySetting = moneySettingDao.getMoneySetting(eleStampProvinceMoneySetting);

            if (eleStampProvinceMoneySetting == null || "".equals(eleStampProvinceMoneySetting.getId())) {

                eleMoneyProvince = defaultEleStampMoney;

            } else {
                eleMoneyProvince = eleStampProvinceMoneySetting.getMoney();
            }

            MoneySetting ukeyStampProvinceMoneySetting = new MoneySetting(provinceAgeny, PaymentType.UKEY);

            ukeyStampProvinceMoneySetting.setArea(city);

            ukeyStampProvinceMoneySetting = moneySettingDao.getMoneySetting(ukeyStampProvinceMoneySetting);

            if (ukeyStampProvinceMoneySetting == null || "".equals(ukeyStampProvinceMoneySetting.getId())) {

                ukeyMoneyProvince = defaultUkeyMoney;

            } else {

                ukeyMoneyProvince = ukeyStampProvinceMoneySetting.getMoney();

            }

            stamp.setProvinceMoney(eleMoneyProvince + ukeyMoneyProvince);

            //4润城经销商收费 rcMoneySetting

            int ukeyMoneyRc = 0;

            int eleMoneyRc = 0;


            MoneySetting rcEleStampMoneySetting = new MoneySetting(rcCompany, PaymentType.ELESTAMP);

            rcEleStampMoneySetting.setArea(province);

            rcEleStampMoneySetting = moneySettingDao.getMoneySetting(rcEleStampMoneySetting);

            if (rcEleStampMoneySetting == null || "".equals(rcEleStampMoneySetting.getId())) {

                eleMoneyRc = defaultEleStampMoney;

            } else {
                eleMoneyRc = rcEleStampMoneySetting.getMoney();
            }

            MoneySetting rcUkeyStampMoneySetting = new MoneySetting(rcCompany, PaymentType.ELESTAMP);

            rcUkeyStampMoneySetting.setArea(province);

            rcUkeyStampMoneySetting = moneySettingDao.getMoneySetting(rcUkeyStampMoneySetting);

            if (rcUkeyStampMoneySetting == null || "".equals(rcUkeyStampMoneySetting.getId())) {

                ukeyMoneyRc = defaultUkeyMoney;

            } else {

                ukeyMoneyRc = rcUkeyStampMoneySetting.getMoney();

            }

            stamp.setRcMoney(ukeyMoneyRc + eleMoneyRc);

            //end

        }


    }

    private int checkStampTypeCount(String StampShape, Company company, String stampType) {
        int cstp = 0;

        Stamp stamp = new Stamp();

        stamp.setUseComp(company);

        stamp.setStampShape(StampShape);   //1,物理;2.电子

        stamp.setStampType(stampType);   //公章

        //stamp.setStampState(StampState.ENGRAVE);    //已刻制

        cstp = stampDao.checkStampType01isOnly(stamp);

        System.out.println("检查" + StampShape + "类型" + stampType + "章" + "正在使用数量：" + cstp + "；公司法人名称：" + company.getLegalName());

        return cstp == 0 ? 0 : cstp;
    }


    /**
     * @param user
     * @author 许彩开
     * @TODO (注：)
     * @DATE: 2018\1\15 0015 14:47
     */
    public List<User> findUsersByUserTypeId(User user) {
        return userDao.findUsersByUserTypeId(user);
    }

    /**
     * @param fileName
     * @author 许彩开
     * @TODO (注：删除临时文件夹里的文件)
     * @DATE: 2018\8\24 0024 14:43
     */
    public void deleteTempFile(String fileName) {
        //临时文件夹实际路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachmentTemp.realPath"));

        realPath.append(fileName);

        File file = new File(realPath.toString());

        if (file.isFile() && file.exists()) {
            Boolean succeedDelete = file.delete();
            if (succeedDelete) {
                System.out.println("删除单个文件" + fileName + "成功！");

            } else {
                System.out.println("删除单个文件" + fileName + "失败！");

            }
        } else {
            System.out.println("删除单个文件" + fileName + "失败！");

        }

    }

    /**
     * 保存扫描的电子印模（存量2）
     *
     * @param moulageFile
     * @param companyName
     * @param stampName
     * @author bb
     */
    public String getScanModel(MultipartFile moulageFile, String companyName, String stampName) {

        //相对路径
        StringBuffer realPath = new StringBuffer(Global.getConfig("attachment.moulageScanEle"));
        //实际路径
        StringBuffer virtualPath = new StringBuffer(Global.getConfig("attachment.moulageScanEleVirtualPath"));

        try {
            //获得网络文件名
            String netAttachName = moulageFile.getOriginalFilename();
            //获取该文件的后缀文件类型
            int lastIndex = netAttachName.lastIndexOf(".");
            String lastName = netAttachName.substring(lastIndex, netAttachName.length());

            String areasRealPath = null;
            //获得当前用户
            User user = UserUtils.getUser();
            //文件名
            StringBuffer attachName = new StringBuffer();
            Area area = areaDao.get(new Area(user.getCompanyInfo().getArea().getId()));
            areasRealPath = getRealFilePath(areaDao.get(new Area(area.getParentId())));
            attachName.append(companyName + "_" + stampName + "_4" + "_" + System.currentTimeMillis() + lastName);
            virtualPath.append(areasRealPath.toString() + attachName.toString());

            if (!(areasRealPath == null)) {
                //拼接目录 获得完整物理路径
                realPath.append(areasRealPath);
                //检查路径是否存在,无则创建
                createDir(realPath.toString());
            }
            realPath.append(attachName.toString());

            System.out.println("realPath:" + realPath.toString());
            System.out.println("virtualPath:" + virtualPath.toString());

            moulageFile.transferTo(new File(realPath.toString()));

            return virtualPath.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 1.保存处理完的电子印章图像(BMP)
     * 2.转PNG
     * 3.压缩
     * @param stamp
     * @param file
     * @param c
     * @author bb
     */
    @Transactional(readOnly = false)
    public void saveESModel(Stamp stamp, MultipartFile file, Condition c) {

    try {
        // 校验图像格式
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        if (!fileType.matches("^[(bmp)|(BMP)]+$")) {
            // 返回状态
            c.setCode(Condition.ERROR_CODE);
            c.setMessage("请上传格式为BMP的图像文件！");
            return;
        }
        // 检验是否为图像文件
        InputStream inputStream = file.getInputStream();
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        if(bufferedImage == null){
            // 返回状态
            c.setCode(Condition.ERROR_CODE);
            c.setMessage("此文件不是图像文件！");
            return;
        }
        // 检验图片大小
        if (file.getSize() > 2097152l) {
            // 返回状态
            c.setCode(Condition.ERROR_CODE);
            c.setMessage("文件大小不能超过2MB！");
            return;
        }

        //相对路径
        String esEleModel_virtualPath = new String(stamp.getScanModel());
        esEleModel_virtualPath = esEleModel_virtualPath.replace(Global.getConfig("attachment.moulageScanEleVirtualPath")
                ,Global.getConfig("attachment.moulageESEleVirtualPath"));
        esEleModel_virtualPath = esEleModel_virtualPath.replace("_4","_3");
        esEleModel_virtualPath = esEleModel_virtualPath.split("\\.")[0] + ".bmp";

        String eleModel_virtualPath = new String(stamp.getScanModel());
        eleModel_virtualPath = eleModel_virtualPath.replace(Global.getConfig("attachment.moulageScanEleVirtualPath")
                ,Global.getConfig("attachment.moulageEleVirtualPath"));
        eleModel_virtualPath = eleModel_virtualPath.replace("_4","_2");
        eleModel_virtualPath = eleModel_virtualPath.split("\\.")[0] + ".png";
        //实际路径
        String esEleModel_realPath = Global.getConfig("attachment.absolutePath") + esEleModel_virtualPath;
        String esEleModelPNG_realPath = esEleModel_realPath.split("\\.")[0] + ".png";
        String eleModel_realPath = Global.getConfig("attachment.absolutePath") + eleModel_virtualPath;
        eleModel_realPath = eleModel_realPath.split("\\.")[0] + ".png";

        System.out.println("esEleModel_realPath:" + esEleModel_realPath.toString());
        System.out.println("esEleModelPNG_realPath:" + esEleModelPNG_realPath.toString());
        System.out.println("eleModel_realPath:" + eleModel_realPath.toString());


        // 保存BMP
        file.transferTo(new File(esEleModel_realPath));
        // 保存PNG
        BufferedImage bi = ImageIO.read(new File(esEleModel_realPath));
        ImageIO.write(bi,"png",new File(esEleModelPNG_realPath));
        bi.flush();// 清空缓冲区数据
        transferAlpha(esEleModelPNG_realPath,esEleModelPNG_realPath);// 将png的印模变透明
        binaryImage(esEleModelPNG_realPath,esEleModelPNG_realPath);// 二值化，非白即红
        // 压缩，保存实际尺寸的印模
        Thumbnails.of(esEleModelPNG_realPath).scale(0.267175572519084).outputQuality(1f).outputFormat("png").toFile(eleModel_realPath);

        // 更新stamp信息
        stamp.setEleModel(eleModel_virtualPath);
        stamp.setEsEleModel(esEleModel_virtualPath);
        stampDao.update(stamp);

        // 返回状态
        c.setCode(Condition.SUCCESS_CODE);
        c.setMessage(eleModel_virtualPath);

        } catch (Exception e) {
            e.printStackTrace();

            // 返回状态
            c.setCode(Condition.ERROR_CODE);
            c.setMessage("印章图像上传失败,请检查网络问题");
        }

    }

}
