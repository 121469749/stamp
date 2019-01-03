package com.thinkgem.jeesite.modules.stamp.web.police.page;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.ReflectUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentDirNotFoundException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.log.entity.ModifyCompanyAttachment;
import com.thinkgem.jeesite.modules.log.entity.ModifyInfoLog;
import com.thinkgem.jeesite.modules.log.service.ModifyCompanyAttachmentService;
import com.thinkgem.jeesite.modules.log.service.ModifyInfoLogService;
import com.thinkgem.jeesite.modules.police.entity.ModifyCompanyInfo;
import com.thinkgem.jeesite.modules.rcBackstage.service.RcCompanyService;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.DeliverAttachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampRecordDao;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.DictMapDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.service.AreaAttachmentDirService;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.StampMakeService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceCompanyService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceStampService;
import com.thinkgem.jeesite.modules.stamp.service.stamp.StampService;
import com.thinkgem.jeesite.modules.stamp.vo.makeStamp.StampDeliveryVo;
import com.thinkgem.jeesite.modules.stamp.vo.policeOperation.StampPermissionVO;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by hjw-pc on 2017/5/20.
 */
@Controller
@RequestMapping(value = "${adminPath}/policeStampPage")
public class PoliceStampPageController extends BaseController {

    @Autowired
    private PoliceStampService policeStampService;
    @Autowired
    private PoliceCompanyService policeCompanyService;
    @Autowired
    private AreaService areaService;

    @Autowired
    private StampMakeService stampMakeService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AreaAttachmentDirService areaAttachmentDirService;

    @Autowired
    private StampRecordDao stampRecordDao;

    @Autowired
    private RcCompanyService rcCompanyService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ModifyCompanyAttachmentService modifyCompanyAttachmentService;

    @Autowired
    private ModifyInfoLogService modifyInfoLogService;

    @Autowired
    private StampService stampService;

    /*
     *@author hjw
     *@description 跳转到印章查看
     *@param []
     *@return java.lang.String
     *@date 2017/5/20
     */
    @RequestMapping(value = "/stampView")
    public String stampView(Model model, Stamp stamp, HttpServletRequest request, HttpServletResponse response) {

        Police police = UserUtils.getUser().getPoliceInfo();
        long startTime = System.currentTimeMillis();//获取开始时间
        if (stamp.getStampShape() == null || "".equals(stamp.getStampShape())) {
            stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        }


        Page<Stamp> page = policeStampService.findList(new Page<Stamp>(request, response), stamp);
        model.addAttribute(stamp);
        model.addAttribute("page", page);
        model.addAttribute("stampShape", StampShape.values());
        model.addAttribute("useStates", OprationState.values());

        long endTime = System.currentTimeMillis(); //获取结束时间

        System.out.println("公安印章查看列表运行时间： " + (endTime - startTime) + "ms");

        return "modules/jsps/police/police-stampsee";
    }

    /**
     * 功能描述:查找需要变更的企业信息列表
     *
     * @param: [company, model, response, request]
     * @return: java.lang.String
     * @auther: linzhibao
     * @date: 2018-08-15 13:38
     */
    @RequestMapping(value = "/modifyCompanyList")
    public String modifyCompanyList(Company company, Model model, HttpServletResponse response, HttpServletRequest request) {
        company.setCompType(CompanyType.USE); // 设置只查找用章企业



        User user = UserUtils.getUser();

        // 设置搜索区域为当前登录公安所在区域,其他区域公司不展示
        Area area = new Area();
        area.setId(user.getPoliceInfo().getArea().getId());
        company.setArea(area);
        // 查找企业信息列表
        Page<Company> page = rcCompanyService.companyPage(new Page<Company>(request, response), company);
        // 2.根据查找出来的信息列表,判断公司名下是否有可用的印章,包含电子印章和物理印章
        StringBuilder sb = new StringBuilder();
        List<Company> companyList = page.getList();
        for(Company cp :companyList ){
            sb.append(cp.getId() + ",");
        }
        companyList = rcCompanyService.findCompanyInfoByIds(sb.toString());
        page.setList(companyList);
        model.addAttribute("page", page);
        model.addAttribute("company", company);
        return "modules/jsps/police/police-modify-company-list";
    }

    /**
     * 功能描述:点击变更企业信息,跳转修改页面
     *
     * @param: [company, model, response, request]
     * @return: java.lang.String
     * @auther: linzhibao
     * @date: 2018-08-15 13:38
     */
    @RequestMapping(value = "/forwardModifyPage")
    public String forwardModifyPage(ModifyCompanyInfo modifyCompanyInfo, Model model, HttpServletResponse response, HttpServletRequest request) {
        // 设置搜索范围为用章企业用户
        modifyCompanyInfo.getCompany().setCompType(CompanyType.USE);
        // 根据企业id查找对应企业信息
        Company company = companyService.get(modifyCompanyInfo.getCompany());
        modifyCompanyInfo.setCompany(company);
        // 设置流水号
        String serialNum = UUID.randomUUID().toString();
        model.addAttribute("serialNum", serialNum);
        model.addAttribute("modifyCompanyInfo", modifyCompanyInfo);
        setAttachmentDirInModel_copy(model, company.getCreateBy().getId());
        return "modules/jsps/police/police-modify-company-info";
    }

    /**
     * 功能描述:公安提交修改公司信息,保存修改后的信息
     *
     * @param: [modifyCompanyInfo, model, response, request]
     * @return: java.lang.String
     * @auther: linzhibao
     * @date: 2018-08-17 9:13
     */
    @RequestMapping(value = "/modifyCompanyInfo", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String modifyCompanyInfo(@RequestParam(value = "file") MultipartFile[] files, ModifyCompanyInfo modifyCompanyInfo, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
        List<MultipartFile> multipartFileList = new ArrayList<MultipartFile>(); // 过滤没有上传的文件
        for (MultipartFile multipartFile : files) {
            if (multipartFile.getSize() != 0) {
                multipartFileList.add(multipartFile);
            }
        }
        MultipartFile[] multipartFile = new MultipartFile[multipartFileList.size()];
        for (int i = 0; i < multipartFileList.size(); i++) {
            multipartFile[i] = multipartFileList.get(i);
        }
//        // 1.保存附件<新营业执照以及身份证照等附件信息>
        List<Attachment> attachments = attachmentService.setUUIDList(modifyCompanyInfo.getFileType());
        String attachJson = JsonMapper.toJsonString(attachments);
        if(attachJson.indexOf("\"attachType\":\"2\"") == -1 || attachJson.indexOf("\"attachType\":\"1\"")== -1 ){
            return "no_attachments";
        }
        attachmentService.saveAttachmentsInHosts(attachments, multipartFile, modifyCompanyInfo.getCompany().getCompanyName()); // 保存文件至后台服务器
        attachmentService.insertAttachmentList(attachments);// 数据库保存记录
//
//        //2.将旧的公司数据备份到t_company2_old_data中,方便以后进行新老数据进行对比
        Company condition = new Company();
        condition.setCompType(CompanyType.USE);
        condition.setId(modifyCompanyInfo.getCompany().getId());
        Company oldCompany = companyService.get(condition);
        companyService.saveOldCompany(oldCompany);

        // 3.更新公司信息 t_company_2表
        modifyCompanyInfo.getCompany().setCompType(CompanyType.USE);
        companyService.updateCompany(modifyCompanyInfo.getCompany());

        // 4.公司信息修改记录保存修改日志中 t_modify_info_log <法人,公司地址等信息>
        ModifyInfoLog modifyInfoLog = new ModifyInfoLog("t_company_2", "用章企业信息", "修改公司信息", "印章管理-企业信息变更", "modifyCompanyInfo",modifyCompanyInfo.getCompany().getId());
        ReflectUtils.modifyInfoRecord(modifyCompanyInfo.getCompany(), oldCompany,modifyInfoLog);


        // 5.修改附件的信息以及经办人等信息保存到修改日志中 t_modify_info_log  <营业执照法人身份证等信息>
        ModifyCompanyAttachment modifyCompanyAttachment = new ModifyCompanyAttachment();
        modifyCompanyAttachment.setAttachs(attachJson);
        modifyCompanyAttachment.setCompany(modifyCompanyInfo.getCompany());
        modifyCompanyAttachment.setCompanyType("2");
        modifyCompanyAttachment.setAgentName(modifyCompanyInfo.getAgentName());
        modifyCompanyAttachment.setAgentPhone(modifyCompanyInfo.getAgentPhone());
        modifyCompanyAttachment.setAgentCertType(modifyCompanyInfo.getAgentCertType());
        modifyCompanyAttachment.setAgentCertCode(modifyCompanyInfo.getAgentCertCode());
        modifyCompanyAttachment.setModifyReason(modifyCompanyInfo.getModifyReason());
        modifyCompanyAttachmentService.save(modifyCompanyAttachment);

        //6.解析修改过的附件列表
        processAttachment(modifyCompanyInfo, attachments);
        return "success + id="+ modifyCompanyAttachment.getId();
    }
    @RequestMapping(value = "/printNewCompanyInfo")
    public String printNewCompanyInfo(Model model,ModifyCompanyAttachment mca) {
        // 根据主键id查找对应的修改信息数据
        ModifyCompanyAttachment modifyCompanyAttachment = modifyCompanyAttachmentService.findModifyCompanyAttachment(mca);
        //查找修改公司对应章的信息
        Stamp stamp = new Stamp();
        Company company = new Company();
        company.setId(modifyCompanyAttachment.getCompany().getId());
        stamp.setStampState(StampState.DELIVERY);
        stamp.setUseComp(company);
        List<Stamp> stampList = stampService.findStampListByCompanyInfo(stamp);
        for(Stamp s:stampList){
            // 根据stampType字段查询数据字典中代表的印章类型label
            s.setStampName(DictUtils.getDictLabel(s.getStampType(),"stampType",null));
        }
        Stamp temp = new Stamp();// 增加请选择下拉框,并放在第一位
        temp.setId("");
        temp.setStampName("请选择");
        stampList.add(0,temp);
        model.addAttribute("mca",modifyCompanyAttachment);
        model.addAttribute("serialNum",UUID.randomUUID());
        model.addAttribute("stampList",stampList);
        model.addAttribute("stamp",new Stamp());
        return "modules/jsps/police/police-print-company-info";

    }

    @RequestMapping(value = "/findStampInfoById")
    @ResponseBody
    public Stamp findStampInfoById(String stampId)throws IOException{
        return stampService.findStampInfo(stampId,null);
    }


    private void processAttachment(ModifyCompanyInfo modifyCompanyInfo, List<Attachment> attachments) {
        ModifyInfoLog modifyInfoLog;
        StringBuilder newBusiness = new StringBuilder();
        StringBuilder newIdCard = new StringBuilder();
        StringBuilder newAgentPeople = new StringBuilder();
        StringBuilder newAgentBook = new StringBuilder();
        StringBuilder newOther = new StringBuilder();

        for (Attachment attachment : attachments) {
            if ("1".equals(attachment.getAttachType())) { // 营业执照
                newBusiness.append(attachment.getId() + ",");
            } else if ("2".equals(attachment.getAttachType())) { // 法人身份证
                newIdCard.append(attachment.getId() + ",");
            } else if ("3".equals(attachment.getAttachType())) { // 经办人身份证
                newAgentPeople.append(attachment.getId() + ",");
            } else if ("4".equals(attachment.getAttachType())) {// 委托书
                newAgentBook.append(attachment.getId() + ",");
            } else if ("5".equals(attachment.getAttachType())) {// 其他材料
                newOther.append(attachment.getId() + ",");
            }
        }
        // 查找之前附件,根据companyid从record表中查找
        String oldAttachs = stampRecordDao.findAttasByCompanyId(modifyCompanyInfo.getCompany().getId());
        oldAttachs =  oldAttachs == null?"":oldAttachs;
        String[] oldAttchsSplit = oldAttachs.replace("]","").replace("[","").split("}");
        modifyInfoLog = new ModifyInfoLog();
        modifyInfoLog.setTableName("用章企业信息");
        modifyInfoLog.setColumnName("attachs");
        modifyInfoLog.setBusinessName("印章管理-企业信息变更-修改附件");
        modifyInfoLog.setOperationType("修改");
        modifyInfoLog.setDelFlag("0");
        modifyInfoLog.setType("modifyCompanyInfo");
        modifyInfoLog.setOperationType("修改附件");
        modifyInfoLog.setCompanyName(modifyCompanyInfo.getCompany().getId());
        //营业执照被修改操作
        if (newBusiness.length() != 0) {
            String oldBusiness = "";
            for (String atta : oldAttchsSplit) {
                if (atta.indexOf("\"attachType\":\"1\"") != -1) {
                    oldBusiness += atta.substring(atta.indexOf(":\"") + 2, atta.indexOf("\",")) + ","; // 多个附件用逗号隔开,保存attachementId
                }
            }
            if(!"".equals(oldBusiness)){// 以前没上传其他材料
                oldBusiness = oldBusiness.substring(0,oldBusiness.length()-1);
                modifyInfoLog.setOldValue(attachmentService.findAttachmentsPathByIds(oldBusiness));
            }
            modifyInfoLog.setColumnText("工商营业执照");
            modifyInfoLog.setIsNewRecord(true);
            modifyInfoLog.setId(UUID.randomUUID().toString().replaceAll("-",""));
            modifyInfoLog.setNewValue(attachmentService.findAttachmentsPathByIds(newBusiness.toString().substring(0,newBusiness.toString().length()-1)));
            modifyInfoLogService.save(modifyInfoLog);

        }// 法人身份证被修改操作
        if (newIdCard.length() != 0) {
            String oldIdCard = "";
            for (String atta : oldAttchsSplit) {
                if (atta.indexOf("\"attachType\":\"2\"") != -1) {
                    oldIdCard += atta.substring(atta.indexOf(":\"") + 2, atta.indexOf("\",")) + ","; // 多个附件用逗号隔开,保存attachementId
                }
            }
            if(!"".equals(oldIdCard)){// 以前没上传其他材料
                oldIdCard = oldIdCard.substring(0,oldIdCard.length()-1);
                modifyInfoLog.setOldValue(attachmentService.findAttachmentsPathByIds(oldIdCard));
            }
            modifyInfoLog.setColumnText("法人身份证");
            modifyInfoLog.setNewValue(attachmentService.findAttachmentsPathByIds(newIdCard.toString()));
            modifyInfoLog.setIsNewRecord(true);
            modifyInfoLog.setId(UUID.randomUUID().toString().replaceAll("-",""));
            modifyInfoLogService.save(modifyInfoLog);
        }// 经办人身份证被修改
        if (newAgentPeople.length() != 0) {
            String oldAgentPeople = "";
            for (String atta : oldAttchsSplit) {
                if (atta.indexOf("\"attachType\":\"3\"") != -1) {
                    oldAgentPeople += atta.substring(atta.indexOf(":\"") + 2, atta.indexOf("\",")) + ","; // 多个附件用逗号隔开,保存attachementId
                }
            }
            if(!"".equals(oldAgentPeople)){// 以前没上传其他材料
                oldAgentPeople = oldAgentPeople.substring(0,oldAgentPeople.length()-1);
                modifyInfoLog.setOldValue(attachmentService.findAttachmentsPathByIds(oldAgentPeople));
            }
            modifyInfoLog.setColumnText("经办人身份证");
            modifyInfoLog.setNewValue(attachmentService.findAttachmentsPathByIds(newAgentPeople.toString()));
            modifyInfoLog.setId(UUID.randomUUID().toString().replaceAll("-",""));
            modifyInfoLog.setIsNewRecord(true);
            modifyInfoLogService.save(modifyInfoLog);
        }//委托书身份证被修改操作
        if (newAgentBook.length() != 0) {
            String oldAgentBook = "";
            for (String atta : oldAttchsSplit) {
                if (atta.indexOf("\"attachType\":\"4\"") != -1) {
                    oldAgentBook += atta.substring(atta.indexOf(":'") + 2, atta.indexOf("\",")) + ","; // 多个附件用逗号隔开,保存attachementId
                }
            }
            if(!"".equals(oldAgentBook)){// 以前没上传其他材料
                oldAgentBook = oldAgentBook.substring(0,oldAgentBook.length()-1);
                modifyInfoLog.setOldValue(attachmentService.findAttachmentsPathByIds(oldAgentBook));
            }
            modifyInfoLog.setColumnText("委托书");
            modifyInfoLog.setNewValue(attachmentService.findAttachmentsPathByIds(newAgentBook.toString()));
            modifyInfoLog.setIsNewRecord(true);
            modifyInfoLog.setId(UUID.randomUUID().toString().replaceAll("-",""));
            modifyInfoLogService.save(modifyInfoLog);

        }// 其他材料被修改操作
        if (newOther.length() != 0) {
            String oldOther = "";
            for (String atta : oldAttchsSplit) {
                if (atta.indexOf("\"attachType\":\"5\"") != -1) {
                    oldOther += atta.substring(atta.indexOf(":\"") + 2, atta.indexOf("\",")) + ","; // 多个附件用逗号隔开,保存attachementId
                }
            }
            if(!"".equals(oldOther)){// 以前没上传其他材料
                oldOther = oldOther.substring(0,oldOther.length()-1);
                modifyInfoLog.setOldValue(attachmentService.findAttachmentsPathByIds(oldOther));
            }
            modifyInfoLog.setColumnText("其他材料");
            modifyInfoLog.setNewValue(attachmentService.findAttachmentsPathByIds(newOther.toString()));
            modifyInfoLog.setIsNewRecord(true);
            modifyInfoLog.setId(UUID.randomUUID().toString().replaceAll("-",""));
            modifyInfoLogService.save(modifyInfoLog);
        }
    }
    /**
     * @param model, stamp
     * @return java.lang.String
     * @author bb
     * @description 印章查询详情
     * @date 2017/7/11
     */
    @RequestMapping(value = "/stampDetail")
    public String stampDetail(Model model, Stamp stamp, String useState1) throws IOException {
        stamp = policeStampService.get(stamp);
        if (StringUtils.isEmpty(useState1) || useState1.equals("1")) {
            useState1 = "1";
        }
        stamp.setMakeComp(policeCompanyService.getMakeCompany(stamp.getMakeComp()));
        model.addAttribute("stamp", stamp);
        model.addAttribute("useState", useState1);

        StampDeliveryVo deliveryVo = stampMakeService.findDeliveryStampInfo(stamp);
        List<Attachment> attachments = null;

        try {

            //工商传过来的数据无附件材料
            if (!"".equals(deliveryVo.getStamp().getLastRecord().getAttachs()) && deliveryVo.getStamp().getLastRecord().getAttachs() != null) {
                attachments = attachmentService.findAttachmentByJsonAttachs(deliveryVo.getStamp().getLastRecord().getAttachs());
            }

        } catch (Exception e) {

            e.printStackTrace();
            //解析失败则查无此信息
            return "modules/jsps/search";

        }

        //获取交付材料
        List<DeliverAttachment> list = stampMakeService.getDeliverAttachment(stamp.getId());
        Map<String, String> map = new HashMap<String, String>();
        for (DeliverAttachment da : list) {
            if (da.getxFlag() == 1) {
                map.put("tabAttachment1", da.getFileName());
            } else if (da.getxFlag() == 2) {
                map.put("tabAttachment2", da.getFileName());
            } else if (da.getxFlag() == 3) {
                map.put("tabAttachment3", da.getFileName());
            } else if (da.getxFlag() == 4) {
                map.put("tabAttachment4", da.getFileName());
            } else if (da.getxFlag() == 10) {
                map.put("tabAttachment10", da.getFileName());
            }
        }

        model.addAttribute("attachments", attachments);
        model.addAttribute("map", map);
        model.addAttribute("stampDeliveryVo", deliveryVo);

        setAttachmentDirInModel(model, stamp.getMakeComp().getArea());

        return "modules/jsps/police/police-stampsee-detailed";
    }

    @RequestMapping(value = "permission", method = RequestMethod.POST)
    public String permission(HttpServletRequest request, Stamp stamp, @RequestParam("agentName") String agentName, @RequestParam("agentId") String agentId, @RequestParam("type") String type, Model model) {
        Stamp stampComplete = policeStampService.get(stamp);
        //获得该印章的备案信息
        stampComplete.setLastRecord(stampRecordDao.get(new StampRecord(stampComplete.getLastRecord().getId(), stampComplete.getRecordState())));

        Company com = stampComplete.getUseComp();
        Company mcom = stampComplete.getMakeComp();
        StringBuffer stringBuffer = new StringBuffer();
        if (type == null || type == "") {
            stringBuffer.append("请选择操作类型\n");
        }
        if (agentName == null || agentName == "") {
            stringBuffer.append("请输入经办人姓名\n");

        }
        if (agentId == null || agentName == "") {
            stringBuffer.append("请输入经办人身份证号\n");
        }
        if (com == null || stringBuffer.length() > 0) {
            //数据非法
            stringBuffer.append("印章编码错误\n");
            return "modules/jsps/search";
        }
        if (mcom == null || stringBuffer.length() > 0) {
            //数据非法
            stringBuffer.append("印章编码错误\n");
            return "modules/jsps/search";
        }
        Company useCompany = policeCompanyService.getUseCompany(com);
        Company makeCompany = policeCompanyService.getMakeCompany(mcom);
        User currentUser = UserUtils.getUser();
        Police police = currentUser.getPoliceInfo();
        Area policeArea = police.getArea();
        if (policeArea.getName() == "") {
            policeArea = areaService.get(new Area(policeArea.getId()));
        }


        String typeId = request.getParameter("type_id");

        String rCode = (int) ((Math.random() * 9 + 1) * 10000000) + policeStampService.getCharAndNumr(9);
        //保存
        stamp.setReturnCode(rCode);
        if (typeId.equals("change")) {
            stamp.setStateFlag(StampWorkType.CHANGE);
        }
        if (typeId.equals("reset")) {
            stamp.setStateFlag(StampWorkType.REPAIR);
        }
        if (typeId.equals("cancal")) {
            stamp.setStateFlag(StampWorkType.LOGOUT);
        }
        if (typeId.equals("miss")) {
            stamp.setStateFlag(StampWorkType.REPORT);
        }

        //保存回执编码和印章临时状态标志
        policeStampService.updateStateFlag(stamp);
        policeStampService.updateReturnCode(stamp);

        //StampPermissionVO stampPermissionVO = new StampPermissionVO(stamp.getId(), stampComplete.getStampName(),agentName, agentId, useCompany, new Date(), type, policeArea.getName());

        StampPermissionVO stampPermissionVO = new StampPermissionVO(rCode,
                stampComplete.getStampName(),
                agentName,
                agentId,
                useCompany,
                makeCompany,
                new Date(),
                type,
                policeArea.getName(),
                stampComplete.getStampTexture(),
                currentUser.getName(),
                stampComplete.getLastRecord());
        model.addAttribute(stampPermissionVO);
        return "modules/jsps/police/police-receipt";
    }

    private void setAttachmentDirInModel(Model model, Area area) {

        try {

            List<Dict> list = areaAttachmentDirService.getCurrentAreaAttachmentList(ServiceTypeEnum.STAMPSERVICE, area);


            model.addAttribute("dir", list);


        } catch (AreaAttachmentDirNotFoundException e) {

            e.printStackTrace();

        } catch (AreaAttachmentException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    private void setAttachmentDirInModel_copy(Model model, String userId) {

        try {

            List<Dict> list = areaAttachmentDirService.getCurrentAreaAttachmentList(ServiceTypeEnum.STAMPSERVICE);

            List list2 = areaAttachmentDirService.getCurrentAreaAttachmentList_copy(ServiceTypeEnum.STAMPSERVICE);


            List list3 = null;

            if (list != null && !"".equals(list)) {

                list3 = listMeger(list, list2);

            }

            model.addAttribute("dir", list3);


        } catch (AreaAttachmentDirNotFoundException e) {

            e.printStackTrace();

        } catch (AreaAttachmentException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private List<DictMapDTO> listMeger(List<Dict> listDict, List<String> listKey) {
        List<DictMapDTO> dictMapDTOList = new ArrayList<DictMapDTO>();
        if (!listKey.isEmpty()) {
            for (int i = 0; i < listDict.size(); i++) {
                DictMapDTO dictMapDTO = new DictMapDTO();
                dictMapDTO.setDict(listDict.get(i));
                if (listKey.contains(listDict.get(i).getValue())) {
                    dictMapDTO.setKey(listDict.get(i).getValue());//必填
                } else {
                    dictMapDTO.setKey("0");//非必填
                }
                dictMapDTOList.add(dictMapDTO);
            }
        } else {
            for (int i = 0; i < listDict.size(); i++) {
                DictMapDTO dictMapDTO = new DictMapDTO();
                dictMapDTO.setDict(listDict.get(i));
                dictMapDTO.setKey("0");//非必填
                dictMapDTOList.add(dictMapDTO);
            }
        }
        return dictMapDTOList;
    }
}
