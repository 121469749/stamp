package com.thinkgem.jeesite.modules.stamp.web.stampMake;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentDirNotFoundException;
import com.thinkgem.jeesite.modules.areaattachment.exception.AreaAttachmentException;
import com.thinkgem.jeesite.modules.log.entity.ModifyCompanyAttachment;
import com.thinkgem.jeesite.modules.log.service.ModifyCompanyAttachmentService;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.DeliverAttachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.DictMapDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.ElectronStampDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.MakeStampBusniessHanlerDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.StampMakeDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Electron;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Moulage;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.entity.water.Water;
import com.thinkgem.jeesite.modules.stamp.service.AreaAttachmentDirService;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.countSet.DealerCountSetService;
import com.thinkgem.jeesite.modules.stamp.service.countSet.MakeCompanyCountSetService;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.StampMakeService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceStampService;
import com.thinkgem.jeesite.modules.stamp.service.stamp.MoulageService;
import com.thinkgem.jeesite.modules.stamp.service.stamp.StampService;
import com.thinkgem.jeesite.modules.stamp.service.water.WaterService;
import com.thinkgem.jeesite.modules.stamp.vo.makeStamp.StampDeliveryVo;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.certificate.CertificateService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 制章点的印章办理
 * 页面跳转`
 * <p>
 * Created by Locker on 2017/5/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/stampMakePage")
public class StampMakePageController {

    @Autowired
    private StampMakeService stampMakeService;

    @Autowired
    private PoliceStampService policeStampService;

    @Autowired
    private AreaAttachmentDirService areaAttachmentDirService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private MoulageService moulageService;

    @Autowired
    private WaterService waterService;

    @Autowired
    private StampService stampService;

    @Autowired
    private MakeCompanyCountSetService makeCompanyCountSetService;

    @Autowired
    private DealerCountSetService dealerCountSetService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ModifyCompanyAttachmentService modifyCompanyAttachmentService;

    /**
     * 跳转到印章刻制申请的页面
     *
     * @return
     */
    @RequestMapping(value = "/toMakeStamp")
    public String toMakeStamp(Model model) {

        String serialNum = IdGen.uuid();

        //设定默认选中备案页面的统一码单选按钮的选项(start)

        StampRecord stampRecord = new StampRecord();

        stampRecord.setIsSoleCode("1");

        StampMakeDTO stampMakeDTO = new StampMakeDTO();

        stampMakeDTO.setStampRecord(stampRecord);

        //end

        // /设置当前业务流水号
        model.addAttribute("serialNum", serialNum);

        model.addAttribute("stampMakeDTO", stampMakeDTO);

        model.addAttribute("currentTime", new Date());

        setAttachmentDirInModel_copy(model);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-index";
    }

   /**
    * @author 许彩开
    * @TODO (注：跳转到印章查看)
     * @param stamp
    * @DATE: 2017\12\22 0022 10:17
    */

    @RequestMapping(value = "/stampView")
    public String stampView(Model model, Stamp stamp, HttpServletRequest request, HttpServletResponse response){

        Page<Stamp> page ;
        if (stamp.getReturnCode()==null||"".equals(stamp.getReturnCode())){
            page = new Page<Stamp>();
        }else{
            stamp.setStampShape(StampShape.ELESTAMP.getKey());
            page = policeStampService.findList2(new Page<Stamp>(request,response), stamp);
            if(page.getCount()==0){
                stamp.setStampShape(StampShape.PHYSTAMP.getKey());
                page = policeStampService.findList2(new Page<Stamp>(request,response), stamp);
            }
        }

        model.addAttribute(stamp);
        model.addAttribute(page);
        model.addAttribute("stampShape",StampShape.values());
        model.addAttribute("useStates", OprationState.values());

        return "modules/jsps/stampmaker/stampengrave/stampengrave-list-merge";
    }

    /**
     * 缴销印章 页面列表
     * 通过 公司名称搜索
     *
     * @param useCompanyName
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toDisposeList")
    public String toDisposeList(String useCompanyName, String stampShape, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (stampShape == null || "".equals(stampShape)) {
            //默认是物理印章
            stampShape = StampShape.PHYSTAMP.getKey();
        }

        Page<Stamp> page = stampMakeService.findStampPageByUseCompany(new Page<Stamp>(request, response), useCompanyName, stampShape);

        Area nowArea = UserUtils.getUser().getCompanyInfo().getArea();

        model.addAttribute("page", page);
        model.addAttribute("area", nowArea);
        model.addAttribute("useCompanyName", useCompanyName);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-list-dispose";
    }

    /**
     * 挂失印章页面列表
     * 通过公司名称搜索
     *
     * @param useCompanyName
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toReportList")
    public String toReportList(String useCompanyName, String stampShape, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (stampShape == null || "".equals(stampShape)) {
            //默认是物理印章
            stampShape = StampShape.PHYSTAMP.getKey();
        }

        Page<Stamp> page = stampMakeService.findStampPageByUseCompany(new Page<Stamp>(request, response), useCompanyName, stampShape);

        Area nowArea = UserUtils.getUser().getCompanyInfo().getArea();

        model.addAttribute("page", page);
        model.addAttribute("area", nowArea);
        model.addAttribute("useCompanyName", useCompanyName);
        return "modules/jsps/stampmaker/stampengrave/stampengrave-list-report";
    }


    /**
     * 重做印章页面列表
     * 通过公司名称搜索
     *
     * @param useCompanyName
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toRemakeList")
    public String toRemakeList(String useCompanyName, String stampShape, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (stampShape == null || "".equals(stampShape)) {
            //默认是物理印章
            stampShape = StampShape.PHYSTAMP.getKey();
        }

        Page<Stamp> page = stampMakeService.findStampPageByUseCompany(new Page<Stamp>(request, response), useCompanyName, stampShape);

        Area nowArea = UserUtils.getUser().getCompanyInfo().getArea();

        model.addAttribute("page", page);
        model.addAttribute("area", nowArea);
        model.addAttribute("useCompanyName", useCompanyName);
        return "modules/jsps/stampmaker/stampengrave/stampengrave-list-remake";
    }

    /**
     * 缴销印章 页面列表
     * 通过 公司名称搜索
     *
     * @param useCompanyName
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toChangeList")
    public String toChangeList(String useCompanyName, String stampShape, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (stampShape == null || "".equals(stampShape)) {
            //默认是物理印章
            stampShape = StampShape.PHYSTAMP.getKey();
        }

        Page<Stamp> page = stampMakeService.findStampPageByUseCompany(new Page<Stamp>(request, response), useCompanyName, stampShape);

        Area nowArea = UserUtils.getUser().getCompanyInfo().getArea();

        model.addAttribute("page", page);
        model.addAttribute("area", nowArea);
        model.addAttribute("useCompanyName", useCompanyName);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-list-change";
    }


    /**
     * 跳转到印章缴销页面
     *
     * @return
     */
    @RequestMapping(value = "/toDispose")
    public String toDispose(@RequestParam(value = "id") String stampId, @RequestParam(value = "stampShape") String stampShape, Model model) {

        //检查是否为真印章
        //否则去错误页面
        if (!stampMakeService.checkStampExist(new Stamp(stampId, StampState.DELIVERY,
                OprationState.OPEN, SysState.USABLE, stampShape))) {

            return "modules/jsps/search";
        }

        //业务流水号
        String serialNum = IdGen.uuid();

        MakeStampBusniessHanlerDTO dto = stampMakeService.showBusniessHandlerInfo(stampId, stampShape);

        model.addAttribute("busniessHandler", dto);

        // /设置当前业务流水号
        model.addAttribute("serialNum", serialNum);

        model.addAttribute("currentTime", new Date());

        setAttachmentDirInModel_copy(model);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-cancellation";
    }

    /**
     * 跳转到印章挂失页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/toReport")
    public String toReport(@RequestParam(value = "id") String stampId, @RequestParam(value = "stampShape") String stampShape, Model model) {

        //检查是否为真印章
        //否则去错误页面
        if (!stampMakeService.checkStampExist(new Stamp(stampId, StampState.DELIVERY, OprationState.OPEN, SysState.USABLE, stampShape))) {

            return "modules/jsps/search";
        }

        String serialNum = IdGen.uuid();


        MakeStampBusniessHanlerDTO dto = stampMakeService.showBusniessHandlerInfo(stampId, stampShape);

        // /设置当前业务流水号
        model.addAttribute("serialNum", serialNum);

        model.addAttribute("currentTime", new Date());

        model.addAttribute("busniessHandler", dto);

        setAttachmentDirInModel_copy(model);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-missing";

    }

    /**
     * 跳转到补刻页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/toRemake")
    public String toRemake(@RequestParam(value = "id") String stampId, @RequestParam(value = "stampShape") String stampShape, Model model) {

        //检查是否为真印章
        //否则去错误页面
        if (!stampMakeService.checkStampExist(new Stamp(stampId, StampState.DELIVERY, OprationState.OPEN, SysState.USABLE, stampShape))) {

            return "modules/jsps/search";
        }

        //业务流水号
        String serialNum = IdGen.uuid();

        MakeStampBusniessHanlerDTO dto = stampMakeService.showBusniessHandlerInfo(stampId, stampShape);

        model.addAttribute("busniessHandler", dto);

        // /设置当前业务流水号
        model.addAttribute("serialNum", serialNum);

        model.addAttribute("currentTime", new Date());

        setAttachmentDirInModel_copy(model);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-repaired";
    }

    /**
     * 跳转到印章变更页面
     *
     * @return
     */
    @RequestMapping(value = "/toChange")
    public String toChange(@RequestParam(value = "id") String stampId, @RequestParam(value = "stampShape") String stampShape, Model model) {

        //检查是否为真印章
        //否则去错误页面
        if (!stampMakeService.checkStampExist(new Stamp(stampId, StampState.DELIVERY,
                OprationState.OPEN, SysState.USABLE, stampShape))) {

            return "modules/jsps/search";
        }

        //业务流水号
        String serialNum = IdGen.uuid();

        MakeStampBusniessHanlerDTO dto = stampMakeService.showBusniessHandlerInfo(stampId, stampShape);
        dto.getStampRecord().setIsSoleCode("1");
//        System.out.println("dto.getStampRecord().getIsSoleCode():"+dto.getStampRecord().getIsSoleCode());

        model.addAttribute("busniessHandler", dto);

        // /设置当前业务流水号
        model.addAttribute("serialNum", serialNum);

        model.addAttribute("currentTime", new Date());

        setAttachmentDirInModel_copy(model);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-change";
    }


    /**
     * 跳转到待刻印章的Page页面
     *
     * @return
     */
    @RequestMapping(value = "/toMakeList")
    private String toMakePage(Stamp stamp, Model model, HttpServletRequest request, HttpServletResponse response) {

        stamp.setStampState(StampState.RECEPT);

        Company currentAgencyCompany = UserUtils.getUser().getCompanyInfo();
        long startTime=System.currentTimeMillis();//获取开始时间
        makeCompanyCountSetService.findCompanyCountSet(currentAgencyCompany);

        Page<Stamp> page = stampMakeService.findPage(new Page<Stamp>(request, response), stamp);
        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("待刻列表运行时间： "+(endTime-startTime)+"ms");

        model.addAttribute("currentAgencyCompany",currentAgencyCompany);
        model.addAttribute("page", page);
        model.addAttribute("stamp", stamp);


        return "modules/jsps/stampmaker/stampengrave/stampengrave-list-wait";
    }

    /**
     * 跳转到已刻印章的Page页面
     *
     * @return
     */
    @RequestMapping(value = "/toFinishList")
    public String toFinishMakePage(Stamp stamp, Model model, HttpServletRequest request, HttpServletResponse response) {

        stamp.setStampState(StampState.ENGRAVE);
        long startTime=System.currentTimeMillis();//获取开始时间
        Page<Stamp> page = stampMakeService.findPage(new Page<Stamp>(request, response), stamp);
        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("已制作列表运行时间： "+(endTime-startTime)+"ms");

        model.addAttribute("page", page);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-list-finish";
    }

    /**
     * 跳转到已交付印章的Page页面
     *
     * @return
     */
    @RequestMapping(value = "/toDeliveryList")
    public String toDeliveryPage(Stamp stamp, Model model, HttpServletRequest request, HttpServletResponse response) {

        stamp.setStampState(StampState.DELIVERY);

//        if (stamp.getStampShape() == null || "".equals(stamp.getStampShape())) {
//            //默认是物理印章
//            stamp.setStampShape(StampShape.PHYSTAMP.getKey());
//        }

        Page<Stamp> page = stampMakeService.findPageDeveryList(new Page<Stamp>(request, response), stamp);

        model.addAttribute("page", page);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-list-devery";
    }


    /**
     * 获得一个印章的刻制信息(跳转到待刻详情)
     *
     * @param stamp
     * @param model
     * @return
     */
    @RequestMapping(value = "/oneStampMake")
    public String oneStampMake(Stamp stamp, Model model) {

        stamp = stampMakeService.findStampInfo(stamp);
        //去掉手机号码-2
        StampRecord lastRecord = stamp.getLastRecord();

        lastRecord.setLegalPhone(lastRecord.getLegalPhone());

        stamp.setLastRecord(lastRecord);

        model.addAttribute("stamp", stamp);

        List<Attachment> attachments = null;

        try {
            //工商传过来的数据无附件材料
            if (!"".equals(stamp.getLastRecord().getAttachs()) && stamp.getLastRecord().getAttachs() != null) {
                attachments = attachmentService.findAttachmentByJsonAttachs(stamp.getLastRecord().getAttachs());
            }
        } catch (Exception e) {

            e.printStackTrace();
            //解析失败则查无此信息
            return "modules/jsps/search";

        }

        model.addAttribute("attachments", attachments);

        setAttachmentDirInModel_copy(model);
        //待刻印章-选中 某一个印章-详情
        return "modules/jsps/stampmaker/stampengrave/stampengrave-detail-wait";
    }


    /**
     * 在详情页面-开始刻制
     * @author bb Update at 2018-08-30
     * @param stamp
     * @param model
     * @return
     */
    @RequestMapping(value = "/startMake")
    public String startMake(Stamp stamp, Model model, HttpServletResponse response) throws IOException {

        //检查 是否为真印章

        if (stampMakeService.chekcStampNeedMake(stamp.getId(), stamp.getStampShape())) {

            return "modules/jsps/404";
        }

        stamp = stampMakeService.findStampInfo(stamp);

        Company makeCompany = UserUtils.getUser().getCompanyInfo();

        Company useCompany = stamp.getUseComp();


        //物理印章
        if ("1".equals(stamp.getStampShape())) {


//            stampMakeService.phyStampCode(stamp);

            if (stamp.getRecordState() == StampWorkType.REPAIR) {

                Moulage moulage = moulageService.get(stamp.getStampShapeId());

                stamp.setMoulage(moulage);

                moulage = null;

            } else {

                stamp.setMoulage(new Moulage());

            }

            List<Moulage> list = moulageService.findMoulageInMake(makeCompany, useCompany, stamp);


            int makeCount = stampService.checkMakeStampCount(stamp);

            System.out.println("公司ID：" + stamp.getUseComp().getId() + "，物理印章：" + stamp.getStampShape()
                    + "，当前印章状态：" + stamp.getRecordState() + "，当前提交状态：" + stamp.getStampState() + "，当前印章类型：" + stamp.getStampType());

            model.addAttribute("makeCount", makeCount);

            model.addAttribute("list", list);
            list.clear();

            model.addAttribute("user", UserUtils.getUser());

            model.addAttribute("stamp", stamp);
            //新印章 ,跳去印模制作页面
            return "modules/jsps/stampmaker/stampengrave/stampengrave-engrave";

        }

        //电子印章
        if ("2".equals(stamp.getStampShape())) {

            CountSet currentEleCountSet;
            currentEleCountSet = dealerCountSetService.getEleCountByCompany(makeCompany);
            List<Stamp> phyStamps = stampMakeService.findPhyStampByStampType(stamp);

            List<Water> waters = waterService.findList(new Water());

            User useUser = stampMakeService.findUseUserByStamp(stamp);

            stamp.setElectron(new Electron());

            int makeCount = stampService.checkMakeStampCount(stamp);

            System.out.println("公司ID：" + stamp.getUseComp().getId() + "，电子印章：" + stamp.getStampShape()
                    + "，当前印章状态：" + stamp.getRecordState() + "，当前提交状态：" + stamp.getStampState() + "，当前印章类型：" + stamp.getStampType());

            model.addAttribute("makeCount", makeCount);

            model.addAttribute("stamp", stamp);

            model.addAttribute("phyStamps", phyStamps);

            model.addAttribute("user", UserUtils.getUser());

            model.addAttribute("useUser", useUser);

            model.addAttribute("electronStampDTO", new ElectronStampDTO());

            model.addAttribute("waters", waters);


            //新印章 ,跳去印模制作页面
            if (stamp.getStampSubType() != null && !"".equals(stamp.getStampSubType())) {
                if (stamp.getStampSubType().indexOf("1") != -1){
                    return "modules/jsps/stampmaker/stampengrave-ES/stampengrave-elestamp-ES3";
                } else if (stamp.getStampSubType().indexOf("2") != -1){
                    return "modules/jsps/stampmaker/stampengrave-ES/stampengrave-elestamp-ES2";
                }
            }else {
                return "modules/jsps/stampmaker/stampengrave-ES/stampengrave-elestamp-ES";
            }

        }

        return "modules/jsps/404";
    }


    /**
     * 单个印章 跳转到 交付页面(跳转到已制作详情)
     * @author bb
     * @param stamp
     * @param model
     * @return
     */
    @RequestMapping(value = "/toDelivery")
    public String toDelivery(Stamp stamp, Model model) {

        //检查 是否为真印章

        if (stampMakeService.chekcStampNeedMake(stamp.getId(), stamp.getStampShape())) {

            return "modules/jsps/search";
        }

        stamp = stampMakeService.findStampInfo(stamp);
        model.addAttribute("stamp", stamp);

        Company makeCompany = UserUtils.getUser().getCompanyInfo();
        String eleModel = stampMakeService.geteleModel(makeCompany.getId());
        model.addAttribute("eleModel", eleModel);

        List<Attachment> attachments = null;

        try {
            //工商传过来的数据无附件材料
            if (!"".equals(stamp.getLastRecord().getAttachs()) && stamp.getLastRecord().getAttachs() != null) {
                attachments = attachmentService.findAttachmentByJsonAttachs(stamp.getLastRecord().getAttachs());
            }
        } catch (Exception e) {

            e.printStackTrace();
            //解析失败则查无此信息
            return "modules/jsps/search";

        }

        model.addAttribute("attachments", attachments);

        setAttachmentDirInModel_copy(model);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-deatil-finish";

    }


    /**
     * 已交付印章的详细页面
     *
     * @param stamp
     * @param model
     * @return
     */
    @RequestMapping(value = "/toStampInfo")
    public String toStampInfo(Stamp stamp, Model model) throws IOException {

        StampDeliveryVo deliveryVo = stampMakeService.findDeliveryStampInfo(stamp);

        List<Attachment> attachments = null;

        //获取交付材料
        List<DeliverAttachment> deliverAttachmentList = stampMakeService.getDeliverAttachment(stamp.getId());
        Map<String,String> map = new HashMap<String, String>();
        getDeliveryAttachs(deliverAttachmentList, map);
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

        model.addAttribute("attachments", attachments);
        model.addAttribute("map", map);
        model.addAttribute("stampDeliveryVo", deliveryVo);
        setAttachmentDirInModel(model);
        return "modules/jsps/stampmaker/stampengrave/stampengrave-deatil";
    }


    /**
     * 功能描述:
     * @param: [stamp, model]
     * @return: java.lang.String
     * @auther: linzhibao
     * @date: 2018-08-29 14:27
     */
    @RequestMapping(value = "/toNewStampInfo")
    public String toNewStampInfo(Stamp stamp, Model model) throws Exception {

        // 1.获取更改过后的公司信息数据 t_company_2
        stamp.getUseComp().setCompType(CompanyType.USE);
        Company company = companyService.get(stamp.getUseComp());
        // 2.根据公司信息查找最新的代办人,代办人身份证,代办人证件号码以及最新附件信息 t_modify_company_attachment
        ModifyCompanyAttachment mca = new ModifyCompanyAttachment();
        mca.setCompany(company);
        List<ModifyCompanyAttachment> modifyCompanyAttachmentList = modifyCompanyAttachmentService.findList(mca);
        List<Attachment> attachments = null;
        if(modifyCompanyAttachmentList != null && modifyCompanyAttachmentList.size() != 0){
            //3. 获取更新过后的附件数据
            attachments = attachmentService.findAttachmentByJsonAttachs(modifyCompanyAttachmentList.get(0).getAttachs());
        }
        //4. 获取交付材料
        List<DeliverAttachment> deliverAttachmentList = stampMakeService.getDeliverAttachment(stamp.getId());
        Map<String,String> map = new HashMap<String, String>();
        getDeliveryAttachs(deliverAttachmentList, map);

        //5. 获取当前印章数据
        StampDeliveryVo deliveryVo = stampMakeService.findDeliveryStampInfo(stamp);
        model.addAttribute("attachments", attachments);
        model.addAttribute("map", map);
        model.addAttribute("stamp", deliveryVo.getStamp());
        model.addAttribute("mca", modifyCompanyAttachmentList.get(0));
        model.addAttribute("company", company);
        setAttachmentDirInModel(model);
        return "modules/jsps/stampmaker/stampengrave/stampengrave-new-deatil";
    }

    private void getDeliveryAttachs(List<DeliverAttachment> deliverAttachmentList, Map<String, String> map) {
        for (DeliverAttachment  deliverAttachment : deliverAttachmentList) {
            if ( deliverAttachment.getxFlag() == 1) {
                map.put("tabAttachment1", deliverAttachment.getFileName());
            } else if( deliverAttachment.getxFlag() == 2) {
                map.put("tabAttachment2", deliverAttachment.getFileName());
            } else if( deliverAttachment.getxFlag() == 3) {
                map.put("tabAttachment3", deliverAttachment.getFileName());
            } else if( deliverAttachment.getxFlag() == 4) {
                map.put("tabAttachment4", deliverAttachment.getFileName());
            } else if( deliverAttachment.getxFlag() == 10) {
                map.put("tabAttachment10", deliverAttachment.getFileName());
            }
        }
    }


    private void setAttachmentDirInModel(Model model) {

        try {

            List<Dict> list = areaAttachmentDirService.getCurrentAreaAttachmentList(ServiceTypeEnum.STAMPSERVICE);
            model.addAttribute("dir", list);


        } catch (AreaAttachmentDirNotFoundException e) {

            e.printStackTrace();

        } catch (AreaAttachmentException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void setAttachmentDirInModel_copy(Model model) {

        try {

            List<Dict> list = areaAttachmentDirService.getCurrentAreaAttachmentList(ServiceTypeEnum.STAMPSERVICE);

            List list2 = areaAttachmentDirService.getCurrentAreaAttachmentList_copy(ServiceTypeEnum.STAMPSERVICE);


            List list3 = null;

            if (list !=null && !"".equals(list)) {

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


    /**
     * @author 许彩开
     * @TODO (注：在刻章点下载用章单位证书)
     * @param response
     * @DATE: 2018\1\15 0015 14:30
     */
    @RequestMapping(value = "downloadCertInMakeStamp")
    public void downloadCertInMakeStamp(HttpServletResponse response ,Stamp stamp) throws IOException {

        User user = new User();

        stamp = stampMakeService.findStampInfo(stamp);

        Company useCompany = stamp.getUseComp();

        user.setUserTypeId(useCompany.getId());

        user.setUserType(UserType.USE);

        List<User> list = stampMakeService.findUsersByUserTypeId(user);

        user = list.get(0);

        File file = certificateService.getCertFile(user.getId());

        // 装载为下载文件
        String fileName = file.getName();

        try {
            InputStream in = new FileInputStream(file);
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            OutputStream out = response.getOutputStream();
            int len = 0;
            //5.创建数据缓冲区
            byte[] buffer = new byte[1024];
            //6.通过response对象获取OutputStream流
            //7.将FileInputStream流写入到buffer缓冲区
            while ((len = in.read(buffer)) > 0) {
                //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author 练浩文
     * @TODO (注：检查印章数量情况)
     * @param stamp
     * @DATE: 2018/2/28 9:12
     */
    @RequestMapping(value = "/validateStamp",method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String validateStamp(Stamp stamp){
        stamp = stampMakeService.findStampInfo(stamp);
        Company makeCompany = UserUtils.getUser().getCompanyInfo();
        //物理印章
        if ("1".equals(stamp.getStampShape())) {

            CountSet currentPhyCountSet=null;
            currentPhyCountSet = dealerCountSetService.getPhyCountByCompany(makeCompany);
            if (currentPhyCountSet == null || StringUtils.isEmpty(currentPhyCountSet.getCount()) || currentPhyCountSet.getCount() == 0) {
                return "1";
            }
        }
        //电子印章
        if ("2".equals(stamp.getStampShape())) {

            CountSet currentEleCountSet=null;
            currentEleCountSet = dealerCountSetService.getEleCountByCompany(makeCompany);
            if (currentEleCountSet == null || StringUtils.isEmpty(currentEleCountSet.getCount()) || currentEleCountSet.getCount() == 0) {
                return "2";
            }
        }
//        return "redirect:"+ Global.getAdminPath()+"/stampMakePage/startMake?stamp="+stamp;
        return "0";
    }

    private List<DictMapDTO> listMeger(List<Dict> listDict,List<String> listKey){
        List<DictMapDTO> dictMapDTOList = new ArrayList<DictMapDTO>();
        if(!listKey.isEmpty()) {
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
        }else{
            for (int i = 0; i < listDict.size(); i++) {

                DictMapDTO dictMapDTO = new DictMapDTO();

                dictMapDTO.setDict(listDict.get(i));

                dictMapDTO.setKey("0");//非必填

                dictMapDTOList.add(dictMapDTO);

            }
        }
        return  dictMapDTOList;
    }

    /**
     * @description: 跳转到电子印章备案页面
     * @auther: bb
     * @date: 2018-09-13
     */
    @RequestMapping(value = "/toBARecordES")
    public String toBARecordES(Model model) {

        String serialNum = IdGen.uuid();

        // 设定默认选中备案页面的统一码单选按钮的选项(start)
        StampRecord stampRecord = new StampRecord();
        stampRecord.setIsSoleCode("1");

        StampMakeDTO stampMakeDTO = new StampMakeDTO();
        stampMakeDTO.setStampRecord(stampRecord);

        // 设置当前业务流水号
        model.addAttribute("serialNum", serialNum);
        model.addAttribute("stampMakeDTO", stampMakeDTO);
        model.addAttribute("currentTime", new Date());

        setAttachmentDirInModel_copy(model);

        return "modules/jsps/stampmaker/stampengrave-ES/stampengrave-index-ES";
    }

    /**
     * @description:
     *   1.ukey_logout:跳转到电子印章Ukey修改PIN码页面跳转到电子印章Ukey注销页面
     *   2.ukey_changePIN:跳转到电子印章Ukey修改PIN码页面
     * @auther: bb
     * @date: 2018-10-18
     */
    @RequestMapping(value = "/ES/{toPage}")
    public String toUkeyEs(@PathVariable("toPage") String toPage,Model model) {

        model.addAttribute("type",toPage);
        return "modules/jsps/stampmaker/stampengrave-ES/ukey-ES";
    }


}
