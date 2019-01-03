package com.thinkgem.jeesite.modules.stamp.web.stampMake;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.GetAddr;
import com.thinkgem.jeesite.common.utils.Html2PdfUtils;
import com.thinkgem.jeesite.common.utils.MacUtils;
import com.thinkgem.jeesite.common.utils.SM.SM2Utils;
import com.thinkgem.jeesite.common.utils.SM.Util;
import com.thinkgem.jeesite.modules.sign.entity.Audit;
import com.thinkgem.jeesite.modules.sign.service.AuditService;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.DeliverAttachment;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dto.countSet.CountSetDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.ElectronStampDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.StampMakeDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.StampWorkTypeDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.ES_.EPS_PERSONALIZE_INFO;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Electron;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.TempAgent;
import com.thinkgem.jeesite.modules.stamp.exception.moneySetting.MoneySettingNotFoundException;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.*;
import com.thinkgem.jeesite.modules.stamp.service.ES_.ESFuncService;
import com.thinkgem.jeesite.modules.stamp.service.countSet.DealerCountSetService;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.StampMakeEditService;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.StampMakeService;
import com.thinkgem.jeesite.modules.stamp.service.police.PoliceStampService;
import com.thinkgem.jeesite.modules.stamp.vo.makeStamp.ReceiptVo;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 制章业务控制层
 * Created by Locker on 2017/5/20.
 */
@Controller
@RequestMapping(value = "${adminPath}/stampMakeAction")
public class StampMakeActionController {

    /**
     * 日志对象
     */
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StampMakeService stampMakeService;

    @Autowired
    private StampMakeEditService editService;

    @Autowired
    private ESFuncService esFuncService;

    @Autowired
    private DealerCountSetService dealerCountSetService;

    @Autowired
    private PoliceStampService policeStampService;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AuditService auditService;

    //正式私钥
    private static final String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
    //正式公钥
    private static final String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";


//    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
//    @ResponseBody
//    public String test(){
//
//        return JsonMapper.toJsonString(new Condition(Condition.SUCCESS_CODE,Global.getAdminPath()+"/stampMakePage/toRemakeList"));
//    }

    /**
     * 制章单位
     * 刻制备案
     *
     * @param stampMakeDTO
     */
    @RequestMapping(value = "/makeStampRecord", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String makeStampRecord(StampMakeDTO stampMakeDTO, @RequestParam(value = "file") MultipartFile[] files,HttpServletRequest request) {

        Condition<StampRecord> condition = new Condition();

        try {

            condition = stampMakeValidator(stampMakeDTO, files, request);

            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);

            }
            condition = stampMakeService.saveNewStamp(stampMakeDTO, files);

        } catch (MoneySettingNotFoundException e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, e.getMessage());

        } catch (SoleCodeException e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, "社会信用统一码错误!");

        } catch (AreaException e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, "该用章单位所属区域不正确!");

        }
        /*catch (PhoneLoginNameException e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "该手机已经被注册过，请勿重复注册!");

        } */ catch (Exception e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, "系统繁忙,请稍候再试!");

        } finally {

            System.out.println("finally Code:" + condition.getCode());


            if (condition.getCode() == Condition.SUCCESS_CODE) {

                // condition.setUrl(Global.getConfig("adminPath") + "/stampMakeAction/makeStamp/Receipt?stampRecordId=" + condition.getMessage());

                condition.setMessage("刻制备案成功!");

            }

            System.out.println(condition.toString());

            return JsonMapper.toJsonString(condition);
        }
    }

    /**
     * 刻制备案的回执
     *
     * @param serialNum
     * @param model
     * @return
     */
    @RequestMapping(value = "/makeStamp/Receipt")
    public String makeStampReceipt(String serialNum, Model model) {

        System.out.println("Success!");

        ReceiptVo receiptVo = stampMakeService.getStampRecordReceiptInfo(serialNum, StampWorkType.APPLY);

        model.addAttribute("receiptInfo", receiptVo);

        return "modules/jsps/stampmaker/stampengrave/stampengrave-receipt";
    }


    /**
     * 缴销印章Action
     *
     * @param stampDisposeDTO
     * @param files
     * @return
     */
    @RequestMapping(value = "/disposeStamp", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String disposeStamp(StampWorkTypeDTO stampDisposeDTO,
                               @RequestParam(value = "file") MultipartFile[] files,
                               @RequestParam(value = "permission", required = false) MultipartFile permission) {

        Condition condition = new Condition();


        try {

            if (!stampMakeService.checkStampExist(new Stamp(stampDisposeDTO.getStamp().getId(), StampState.DELIVERY,
                    OprationState.OPEN, SysState.USABLE, stampDisposeDTO.getStamp().getStampShape()))) {

                condition.setMessage("该印章不符合缴销条件!");

                condition.setCode(Condition.ERROR_CODE);

                return JsonMapper.toJsonString(condition);
            }

            condition = stampBusValidator(stampDisposeDTO, files, permission);

            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);
            }

            condition = stampMakeService.reportOrLogoutStamp(stampDisposeDTO, files, StampWorkType.LOGOUT, permission);

            String rCode = (int) ((Math.random() * 9 + 1) * 10000000) + policeStampService.getCharAndNumr(9);

            Stamp stamp = new Stamp(stampDisposeDTO.getStamp().getId());

            stamp.setStampShape(stampDisposeDTO.getStamp().getStampShape());

            stamp.setReturnCode(rCode);
            //保存回执编码
            policeStampService.updateReturnCode(stamp);

            condition.setMessage("缴销印章成功，请前往'已交付印章'列表查看状态!");
            //设置跳转url
            condition.setUrl(Global.getConfig("adminPath") + "/stampMakePage/toDisposeList");

        } catch (SoleCodeException e) {

            e.printStackTrace();
            condition = new Condition(Condition.NOALLOW_CODE, "信息填写有误,请检查!");
        } catch (StampExistException e) {

            e.printStackTrace();
            condition = new Condition(Condition.NOALLOW_CODE, "信息填写有误,请检查!");

        } catch (StampMakeException e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, "系统繁忙,请稍候再试!");

        } finally {

            return JsonMapper.toJsonString(condition);
        }

    }

    /**
     * 挂失印章Action
     *
     * @param stampDisposeDTO
     * @param files
     * @return
     */
    @RequestMapping(value = "/reportStamp", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String reportStamp(StampWorkTypeDTO stampDisposeDTO, @RequestParam(value = "file") MultipartFile[] files,
                              @RequestParam(value = "permission", required = false) MultipartFile permission) {


        Condition condition = new Condition();



        try {

            condition = stampBusValidator(stampDisposeDTO, files, permission);

            //去掉第一条空的
            stampDisposeDTO.getFileType().remove(0);

            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);
            }

            condition = stampMakeService.reportOrLogoutStamp(stampDisposeDTO, files, StampWorkType.REPORT, permission);

            String rCode = (int) ((Math.random() * 9 + 1) * 10000000) + policeStampService.getCharAndNumr(9);

            Stamp stamp = new Stamp(stampDisposeDTO.getStamp().getId());

            stamp.setStampShape(stampDisposeDTO.getStamp().getStampShape());

            stamp.setReturnCode(rCode);
            //保存回执编码
            policeStampService.updateReturnCode(stamp);

            condition.setMessage("挂失印章成功，请前往'已交付印章'列表查看状态!!");
            //设置跳转url
            condition.setUrl(Global.getConfig("adminPath") + "/stampMakePage/stampView");

        } catch (SoleCodeException e) {

            e.printStackTrace();

            condition = new Condition(Condition.NOALLOW_CODE, "信息填写有误,请检查!");

        } catch (StampExistException e) {

            e.printStackTrace();

            condition = new Condition(Condition.NOALLOW_CODE, "信息填写有误,请检查!");

        } catch (StampMakeException e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "系统繁忙,请稍候再试!");

        } finally {

            return JsonMapper.toJsonString(condition);
        }


    }

    /**
     * 补刻申请
     *
     * @param repairStampDTO
     * @param files
     * @return
     */
    @RequestMapping(value = "/repairStamp", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String repairStamp(StampWorkTypeDTO repairStampDTO, @RequestParam(value = "file") MultipartFile[] files,
                              @RequestParam(value = "permission", required = false) MultipartFile permission) {

        Condition condition = new Condition();



        try {


            condition = stampBusValidator(repairStampDTO, files, permission);


            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);
            }

            condition = stampMakeService.repairStamp(repairStampDTO, files, permission);

            String rCode = (int) ((Math.random() * 9 + 1) * 10000000) + policeStampService.getCharAndNumr(9);

            Stamp stamp = new Stamp(repairStampDTO.getStamp().getId());

            stamp.setStampShape(repairStampDTO.getStamp().getStampShape());

            stamp.setReturnCode(rCode);
            //保存回执编码
            policeStampService.updateReturnCode(stamp);

            condition.setMessage("印章补刻申请成功，请前往'待刻印章'列表重新制作!");
            //设置跳转url
            condition.setUrl(Global.getConfig("adminPath") + "/stampMakePage/stampView");

        } catch (SoleCodeException e) {

            e.printStackTrace();

            condition = new Condition(Condition.NOALLOW_CODE, "信息填写有误,请检查!");

        } catch (StampExistException e) {

            e.printStackTrace();

            condition = new Condition(Condition.NOALLOW_CODE, "信息填写有误,请检查!");

        } catch (StampMakeException e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "系统繁忙,请稍候再试!");

        } finally {

            return JsonMapper.toJsonString(condition);
        }

    }


    /**
     * 物理印模 刻制信息提交
     */
    /*@RequestMapping(value = "/stampMoulage", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String makeStampMoulage(Stamp stamp, @RequestParam(value = "moulageFile", required = false) MultipartFile[] files) {

        Condition condition = new Condition();

        Company currentCompany = UserUtils.getUser().getCompanyInfo();
        CountSet currentPhyCountSet;
        currentPhyCountSet = dealerCountSetService.getPhyCountByCompany(currentCompany);
        if (currentPhyCountSet == null || StringUtils.isEmpty(currentPhyCountSet.getCount()) || currentPhyCountSet.getCount() == 0) {
            condition = new Condition(Condition.ERROR_CODE, "物理印章数量不足");
            return JsonMapper.toJsonString(condition);
        }
        currentPhyCountSet.setCount(currentPhyCountSet.getCount() - 1);

        CountSetDTO countSetDTO = new CountSetDTO();

        countSetDTO.setCompanyId(currentCompany.getId());
        countSetDTO.setPhyCountSet(currentPhyCountSet);

        try {
            condition = stampMoulageValidator(files);

            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);
            }

            //默认第一个是物理印模，第二个是电子印模的方式来存储
            condition = stampMakeService.saveMakeStampMoulage(stamp, files[0], files[1]);

            if (condition.getCode() == Condition.SUCCESS_CODE) {

                condition.setUrl(Global.getAdminPath() + "/stampMakePage/toFinishList");

            }

            dealerCountSetService.updatePhyStampCountByCompanyId(countSetDTO);
        } catch (Exception e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "系统繁忙!请稍后再试");

        } finally {

            System.out.println(JsonMapper.toJsonString(condition));

            return JsonMapper.toJsonString(condition);

        }

    }*/


//    @RequestMapping(value="/getMoulageInfo", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
//    @ResponseBody
//    public String getMoulageInfo(@RequestParam(value="id") String id){
//
//        Company makeCompany = UserUtils.getUser().getCompanyInfo();
//        Moulage moulage = new Moulage();
//
//        moulage.setId(id);
//        moulage.setMakeCompany(makeCompany);
//
//        moulage = moulageService.get(moulage);
//
//        return JsonMapper.toJsonString(moulage);
//    }

    /**
     * 物理印模 刻制信息提交
     * @author bb
     * @param stamp
     * @param files
     * @return
     */
    @RequestMapping(value = "/stampMoulage", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String makeStampMoulage(Stamp stamp, @RequestParam(value = "moulageFile", required = false) MultipartFile[] files) {

        Condition condition = new Condition();

        Company currentCompany = UserUtils.getUser().getCompanyInfo();
        CountSet currentPhyCountSet;
        currentPhyCountSet = dealerCountSetService.getPhyCountByCompany(currentCompany);
        if (currentPhyCountSet == null || StringUtils.isEmpty(currentPhyCountSet.getCount()) || currentPhyCountSet.getCount() == 0) {
            condition = new Condition(Condition.ERROR_CODE, "物理印章数量不足");
            return JsonMapper.toJsonString(condition);
        }
        currentPhyCountSet.setCount(currentPhyCountSet.getCount() - 1);

        CountSetDTO countSetDTO = new CountSetDTO();

        countSetDTO.setCompanyId(currentCompany.getId());
        countSetDTO.setPhyCountSet(currentPhyCountSet);

        try {
            condition = stampMoulageValidator(files);

            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);
            }

            //默认第一个是物理印模，第二个是电子印模的方式来存储
            condition = stampMakeService.saveMakeStampMoulage(stamp, files[0]);

            if (condition.getCode() == Condition.SUCCESS_CODE) {

                condition.setUrl(Global.getAdminPath() + "/stampMakePage/toFinishList");

            }

            dealerCountSetService.updatePhyStampCountByCompanyId(countSetDTO);
        } catch (Exception e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "系统繁忙!请稍后再试");

        } finally {

            System.out.println(JsonMapper.toJsonString(condition));

            return JsonMapper.toJsonString(condition);

        }

    }


    /**
     * 电子印章刻制
     *
     * @param eleFile
     * @return
     */
    @RequestMapping(value = "/stampElectron", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String makeStampElectron(ElectronStampDTO electronStampDTO,
                                    @RequestParam(value = "eleFile", required = false) MultipartFile eleFile,
                                    HttpServletRequest request) {
        //审计相关
        Audit audit = new Audit();
        try {
            audit.setIp(GetAddr.getIp(request));
            audit.setMac(MacUtils.getMac());//此处获取的是服务端Mac地址
        } catch (Exception e) {
            e.printStackTrace();
        }
        audit.setUser(UserUtils.getUser());
        audit.setSeal(electronStampDTO.getStamp().getId());
        audit.setAuditType(Audit.AUDIT_MAKE);
        audit.setAuditDate(new Date());


        Condition condition = new Condition();
        Company currentCompany = UserUtils.getUser().getCompanyInfo();
        CountSet currentEleCountSet = dealerCountSetService.getEleCountByCompany(currentCompany);

        if (currentEleCountSet==null||StringUtils.isEmpty(currentEleCountSet.getCount()) || currentEleCountSet.getCount() == 0) {
            condition = new Condition(Condition.ERROR_CODE, "可制作的电子印章数量不足");
            audit.setAuditResult(Audit.FAIL);
            audit.setReason("可制作的电子印章数量不足");
            auditService.addAudit(audit);

            return JsonMapper.toJsonString(condition);
        }
        currentEleCountSet.setCount(currentEleCountSet.getCount() - 1);

        CountSetDTO countSetDTO = new CountSetDTO();
        countSetDTO.setCompanyId(currentCompany.getId());
        countSetDTO.setEleCountSet(currentEleCountSet);
        //condition = ElectronStampValidator(electronStampDTO, eleFile);
        try {

            if (condition.getCode() == Condition.ERROR_CODE) {

                audit.setAuditResult(Audit.FAIL);
                audit.setReason(condition.getMessage());
                auditService.addAudit(audit);

                return JsonMapper.toJsonString(condition);

            }

            // 判断是否为存量电子印章
            if (electronStampDTO.getStamp().getStampSubType() != null && !"".equals(electronStampDTO.getStamp().getStampSubType())) {
                condition = stampMakeService.saveElectron_ES(electronStampDTO, eleFile);
            }else {
                condition = stampMakeService.saveElectron(electronStampDTO, eleFile);
            }

            if (condition.getCode() == Condition.SUCCESS_CODE) {

                audit.setAuditResult(Audit.SUCCESS);
                audit.setReason("制作完成");

                condition.setUrl(Global.getAdminPath() + "/stampMakePage/toFinishList");

            }
            dealerCountSetService.updateEleStampCountByCompanyId(countSetDTO);
        } catch (Exception e) {
            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "系统繁忙!请稍后再试");

            audit.setAuditResult(Audit.FAIL);
            audit.setReason("系统繁忙");

        } finally {
            //审计
            auditService.addAudit(audit);

            return JsonMapper.toJsonString(condition);

        }
    }

    /**
     * @author bb
     * 上传处理完的电子印章图像
     * @param id
     * @param stampShape
     * @param file
     * @return JSON
     */
    @RequestMapping(value = "/uploadESModel")
    @ResponseBody
    public String uploadESModel(@RequestParam(value = "stampId")String id,
                                @RequestParam(value="stampShape")String stampShape,
                                @RequestParam(value="file")MultipartFile file){

        Condition condition = new Condition();

        Stamp stamp = new Stamp();
        stamp.setId(id);
        stamp.setStampShape(stampShape);
        stamp = stampMakeService.findStampInfo(stamp);

        //图像处理
        stampMakeService.saveESModel(stamp, file, condition);

        return JsonMapper.toJsonString(condition);
    }

    /**
     * @author bb
     * 公安部-制作电子印章-传给C端数据
     * @param id
     * @param stampShape
     * @return JSON
     */
    @RequestMapping(value = "/procPersonalize")
    @ResponseBody
    public String procPersonalize(@RequestParam(value = "stampId")String id,@RequestParam(value="stampShape")String stampShape){

        Condition condition = new Condition();

        Stamp stamp = new Stamp();
        stamp.setId(id);
        stamp.setStampShape(stampShape);
        stamp = stampMakeService.findStampInfo(stamp);

        EPS_PERSONALIZE_INFO epi = new EPS_PERSONALIZE_INFO();

        //检查并设置值
        esFuncService.funcPersonalize(stamp, epi, condition);

        return JsonMapper.toJsonString(condition);
    }


    /**
     * @author bb
     * 更新物理印章写入芯片状态
     * @param stampCode,antiFakeId
     * @return
     */
    @RequestMapping(value = "/writeInChip", method = RequestMethod.GET, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String writeChip(@RequestParam(value = "stampCode", required = true) String stampCode,
                            @RequestParam(value = "antiFakeId", required = true) String antiFakeId)
            throws StampMakeException, IOException {
        System.out.println("xieruxinpian");
        String decryptDate = new String(SM2Utils.decrypt(Util.hexToByte(prik), Util.hexToByte(stampCode)));//解密后的数据

        String result = null;
        String checkChipId = null;

        Stamp stamp = new Stamp();
        stamp.setStampCode(decryptDate);
        stamp.setAntiFakeId(antiFakeId);

        //写入前检查此芯片是否已写过，若写过，是否是第一次绑定的印章，是则更新，不是则更新失败
        checkChipId = stampMakeService.updateChip_pre(stamp);

        try {
            if (checkChipId == null || decryptDate.equals(checkChipId)){
                if (stampMakeService.updateChip(stamp)) {
                    result = ""+Condition.SUCCESS_CODE;
                }else {
                    result = ""+Condition.ERROR_CODE;
                }
            }else {
                result = ""+Condition.NOALLOW_CODE;
            }
        } catch (Exception e) {
            result = ""+Condition.ERROR_CODE;
        } finally {
            return result;
        }
    }

    /**
     * @author bb
     * 加密写入芯片的数据
     * @param datas
     * @return
     */
    @RequestMapping(value = "/encryptChipData", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String,String> encryptChipData(HttpServletRequest request, String[] datas) throws IOException {

        System.out.println(Arrays.toString(datas));

        Map<String,String> encryptData = new HashMap<String,String>();

        for (int i=0; i<datas.length; i++){
            byte[] sourceData = datas[i].getBytes();//原始数据
            String cipherData = SM2Utils.encrypt(Util.hexToByte(pubk), sourceData);//加密后的数据
            encryptData.put("encryptData"+ String.valueOf(i),cipherData);//设值
        }

        return encryptData;
    }

    /**
     * @author bb
     * APP验证芯片
     * @param stampCode,chipUID
     * @return
     */
    @RequestMapping(value = "/ChipVerifyAction", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String ChipVerify(@RequestParam(value = "stampCode", required = true) String stampCode,
                             @RequestParam(value = "chipUID", required = true)String chipUID){

        Map<String,Object> result = new HashMap<String, Object>();

        try {
            String decryptDate = new String(SM2Utils.decrypt(Util.hexToByte(prik), Util.hexToByte(stampCode.trim())));//解密后的数据
            result = stampMakeService.chipVerify(decryptDate, chipUID);

        } catch (IOException e) {
            result.put("result", "Fail");
        } catch (IllegalArgumentException e) {
            result.put("result", "Fail");
        }
        System.out.println(JsonMapper.toJsonString(result));
        return JsonMapper.toJsonString(result);

    }



    /**
    * 预览交付材料
    */
    @RequestMapping(value = "/previewPic")
    @ResponseBody
    public Map<String,String> previewPic(String stampId){

        Condition condition = new Condition();
        List<DeliverAttachment> list = stampMakeService.getDeliverAttachment(stampId);
        Map<String,String> map = new HashMap<String, String>();
        try {

            for (DeliverAttachment da : list) {
                if (da.getxFlag() == 1) {
                    map.put("tabAttachment1",da.getFileName());
                } else if (da.getxFlag() == 2) {
                    map.put("tabAttachment2",da.getFileName());
                } else if (da.getxFlag() == 3) {
                    map.put("tabAttachment3",da.getFileName());
                } else if (da.getxFlag() == 4) {
                    map.put("tabAttachment4",da.getFileName());
                } else if (da.getxFlag() == 10) {
                    map.put("tabAttachment10",da.getFileName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            return map;
        }


    }

    /**
     * 印章交付
     * @author bb
     * @param stamp
     * @return
     */
    @RequestMapping(value = "/deliveryStamp", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String deliveryStamp(Stamp stamp,
                                @RequestParam(value = "photo", required = false) MultipartFile[] photos,
                                @RequestParam(value = "record", required = false) MultipartFile record,
                                @RequestParam(value = "htmltempl") String str)
            throws StampMakeException {

        Condition condition = new Condition();
        //pdf相对路径
        String pdfPathRelative = null;
        //pdf真实路径
        String pdfPathReal = null;

        try {
            stamp = stampMakeService.getStamp(stamp);
            //检查是否为电子印章，如果是的话就先生成html，然后转为pdf
            if("2".equals(stamp.getStampShape())){
                try {
                   String htmltemplate1= URLDecoder.decode(str,"UTF-8");
                   String htmltemplate = htmltemplate1.replaceAll("123/img/","D:/stamp/");
                   String htmlPathTemp = stamp.getEleModel();
                   String htmlPathRelative = null;
                   if (htmlPathTemp.contains("png")) {
                       htmlPathRelative = "/test" + htmlPathTemp.replaceAll("moulageEle", "attachment")
                               .replaceAll("png", "html");
                   } else if (htmlPathTemp.contains("jpeg")){
                       htmlPathRelative = "/test" + htmlPathTemp.replaceAll("moulageEle", "attachment")
                               .replaceAll("jpeg", "html");
                   }
                   String htmlPathReal = "C:"+htmlPathRelative;
                   //pdf相对路径
                   pdfPathRelative = htmlPathRelative.replaceAll("html","pdf");
                   //pdf真实路径
                   pdfPathReal = htmlPathReal.replaceAll("html","pdf");
                   FileUtils.createFile(htmlPathReal);
                   FileUtils.writeToFile(htmlPathReal,htmltemplate,"UTF-8",false);
                   Html2PdfUtils.html2Pdf(htmlPathReal,pdfPathReal);
                   new File(htmlPathReal).delete();//生成pdf后将html删除
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            //判断是否满足交付条件
            //deliveryStampValidate(photos, record, stamp.getAntiFakeWrite(), stamp.getStampShape(), condition);
            deliveryStampValidate(stamp.getId(), stamp.getAntiFakeWrite(), stamp.getStampShape(), condition);

            //旧交付
            //condition = stampMakeService.deliveryStamp(stamp, photos, record);
            //新交付
            condition = stampMakeService.deliveryStamp(stamp, pdfPathReal);

            if (condition.getCode() == Condition.SUCCESS_CODE) {

                condition.setUrl(Global.getAdminPath() + "/stampMakePage/toDeliveryList");

            }

        } catch (StampValidateException e) {
            //后台验证不通过执行finally

        } catch (StampMakeException e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, "交付失败!");

        } catch (IOException e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "交付失败!");
        } catch (Exception e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE, "系统繁忙!");

        } finally {

            return JsonMapper.toJsonString(condition);
        }
    }

//    /**
//     * 通过印章id
//     * 获得原有印章物理印模的信息
//     *
//     * @param stampId
//     * @return
//     */
//    @RequestMapping(value = "/repairMoulage", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
//    @ResponseBody
//    public Moulage repairMoulage(@RequestParam(value = "id") String stampId) {
//
//        Moulage moulage = stampMakeService.getMoulage(stampId);
//
//        return moulage;
//    }


//    public String makeRepairStamp(@RequestParam(value = "id") String stampId,@RequestParam(value="stampShape")String stampShape) {
//
//        Condition condition = new Condition(Condition.SUCCESS_CODE);
//
//        try {
//
//            //检查该印章是否是补刻印章
//            if (!stampMakeService.checkRepairStamp(stampId,stampShape)) {
//
//                //todo
//                condition.setCode(Condition.ERROR_CODE);
//                condition.setMessage("该印章不是补刻印章!");
//
//                return JsonMapper.toJsonString(condition);
//            }
//
//            stampMakeService.repairPhyStamp(stampId);
//
//            condition.setMessage("补刻印章完成!");
//
//            condition.setUrl(Global.getAdminPath() + "/stampMakePage/toMakeList");
//
//        } catch (StampMakeException e) {
//
//            condition.setCode(Condition.ERROR_CODE);
//
//            condition.setMessage("系统繁忙!");
//
//
//        } finally {
//
//            return JsonMapper.toJsonString(condition);
//        }
//
//    }


    /**
     * 印章变更业务
     *
     * @param changeStampDTO
     * @param files
     * @param permission
     * @return
     */
    @RequestMapping(value = "/changeStamp", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String changeStamp(StampWorkTypeDTO changeStampDTO, @RequestParam(value = "file") MultipartFile[] files,
                              @RequestParam(value = "permission", required = false) MultipartFile permission) {


        Condition condition = new Condition();

        try {

            if (!stampMakeService.checkStampExist(new Stamp(changeStampDTO.getStamp().getId(), StampState.DELIVERY,
                    OprationState.OPEN, SysState.USABLE, changeStampDTO.getStamp().getStampShape()))) {

                condition.setMessage("该印章不符合变更条件!");

                condition.setCode(Condition.ERROR_CODE);

                return JsonMapper.toJsonString(condition);
            }

            condition = stampChangeBusValidator(changeStampDTO, files, permission);

            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);
            }

            condition = stampMakeService.changeStamp(changeStampDTO, files, StampWorkType.CHANGE, permission);

            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);
            }

            String rCode = (int) ((Math.random() * 9 + 1) * 10000000) + policeStampService.getCharAndNumr(9);

            Stamp stamp = new Stamp(changeStampDTO.getStamp().getId());

            stamp.setStampShape(changeStampDTO.getStamp().getStampShape());

            stamp.setReturnCode(rCode);
            //保存回执编码
            policeStampService.updateReturnCode(stamp);

            condition.setMessage("变更印章信息成功，请前往'待刻印章'列表重新制作!");
            //设置跳转url
            condition.setUrl(Global.getConfig("adminPath") + "/stampMakePage/stampView");

        } catch (SoleCodeException e) {

            e.printStackTrace();
            condition = new Condition(Condition.NOALLOW_CODE, "信息填写有误,请检查!");
        } catch (StampExistException e) {

            e.printStackTrace();
            condition = new Condition(Condition.NOALLOW_CODE, "信息填写有误,请检查!");

        } catch (StampMakeException e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, "系统繁忙,请稍候再试!");

        } finally {

            return JsonMapper.toJsonString(condition);
        }
    }


    /**
     * 作废印章
     *
     * @param id
     * @param stampShape
     * @return
     */
    @RequestMapping(value = "/cancelStamp", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String cancelStamp(@RequestParam(value = "stampId") String id, @RequestParam(value = "stampShape") String stampShape) {

        Company currentCompany = UserUtils.getUser().getCompanyInfo();
        CountSet currentEleCountSet = dealerCountSetService.getEleCountByCompany(currentCompany);
        CountSet currentPhyCountSet = dealerCountSetService.getPhyCountByCompany(currentCompany);

        currentEleCountSet.setCount(currentEleCountSet.getCount() + 1);
        currentPhyCountSet.setCount(currentPhyCountSet.getCount() + 1);

        //用于查找使用的实体
        CountSetDTO countSetDTO = new CountSetDTO();
        countSetDTO.setCompanyId(currentCompany.getId());
        //将已作废的章回收

        if ("1".equals(stampShape)) {
            countSetDTO.setPhyCountSet(currentPhyCountSet);
            dealerCountSetService.updatePhyStampCountByCompanyId(countSetDTO);
        }

        if ("2".equals(stampShape)) {
            countSetDTO.setEleCountSet(currentEleCountSet);
            dealerCountSetService.updateEleStampCountByCompanyId(countSetDTO);
        }

        Condition condition = editService.cancelStamp(id, stampShape);

        return JsonMapper.toJsonString(condition);
    }

    /**
     * 作废待刻印章
     *
     * @param id
     * @param stampShape
     * @return
     */
    @RequestMapping(value = "/cancelStampReady", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String cancelStampReady(@RequestParam(value = "stampId") String id, @RequestParam(value = "stampShape") String stampShape) {

        Condition condition = editService.cancelStamp(id, stampShape);

        return JsonMapper.toJsonString(condition);
    }


    @RequestMapping(value = "/findUseCompany", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getUserCompanyInfo(String companyName){

        Condition condition = stampMakeService.findUseCompany(companyName);

        return JsonMapper.toJsonString(condition);
    }

    /**
     * 对印章的刻制、缴销、挂失、补刻
     * 提交表单的动作做简单的表单验证
     *
     * @param stampMakeDTO
     */
    private Condition stampMakeValidator(StampMakeDTO stampMakeDTO, MultipartFile[] files, HttpServletRequest request) {

        Condition condition = new Condition();

        condition.setCode(Condition.SUCCESS_CODE);

        List<String> filetypes = stampMakeDTO.getFileType();

        List<String> requiredList = stampMakeDTO.getRequiredList();

        StringBuffer messageBuffer = new StringBuffer();

        StampRecord stampRecord = stampMakeDTO.getStampRecord();

        Company useCompany = stampMakeDTO.getStampRecord().getUseComp();

        List<Stamp> stamps = new ArrayList<Stamp>();

        List<Stamp> submitStamps = stampMakeDTO.getStamps();

        int count = 0;

        if (submitStamps.size() == 1) {

            if (submitStamps.get(0).getStampShape() == null || submitStamps.get(0).getStampShape().equals("") || "on".equals(submitStamps.get(0).getStampShape())) {

                messageBuffer.append("请选中制作的印章形式!\n");

                condition.setCode(Condition.ERROR_CODE);

            }

            stamps.add(submitStamps.get(0));

        } else {

            for (Stamp stamp : submitStamps) {

                if (stamp.getStampShape() == null || "on".equals(stamp.getStampShape())) {

                    count++;
                    continue;
                }

                stamps.add(stamp);

            }

        }

        if (count == submitStamps.size()) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("请正确填写印章信息!\n");

        }


        stampMakeDTO.setStamps(stamps);

        if (stampRecord.getSerialNum() == null || "".equals(stampRecord.getSerialNum())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("业务流水号不能为空!\n");

        }

//        if ("".equals(stampRecord.getWorkRemakrs()) || stampRecord.getWorkRemakrs() == null) {
//
//            condition.setCode(Condition.ERROR_CODE);
//
//            messageBuffer.append("刻制原因不能为空!\n");
//        }

        if ("".equals(useCompany.getSoleCode()) || useCompany.getSoleCode() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("单位统一社会信用代码不能为空!\n");
        }

        if ("".equals(useCompany.getCompanyName()) || useCompany.getCompanyName() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("单位名称不能为空\n");
        }


        if ("".equals(useCompany.getCompAddress()) || useCompany.getCompAddress() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("单位地址不能为空!\n");

        }

        if ("".equals(useCompany.getLegalName()) || useCompany.getLegalName() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("法人姓名不能为空!\n");

        }

        if ("".equals(useCompany.getLegalPhone()) || useCompany.getLegalPhone() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("法人手机号码不能为空!\n");
        }

        if ("".equals(useCompany.getLegalCertCode()) || useCompany.getLegalCertCode() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("法人证件号码不能为空!\n");

        }

        if ("".equals(useCompany.getCompAddress()) || useCompany.getCompAddress() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("公司地址不能为空!\n");

        }

//        if ("".equals(useCompany.getType1()) || useCompany.getType1() == null) {
//
//            condition.setCode(Condition.ERROR_CODE);
//
//            messageBuffer.append("单位类别不能为空!\n");
//
//        }

        /*if ("".equals(useCompany.getCompPhone()) || useCompany.getCompPhone() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("公司电话不能为空!\n");

        }*/

        if ("".equals(stampRecord.getAgentName()) || stampRecord.getAgentName() == null) {

            condition.setCode(Condition.ERROR_CODE);


            messageBuffer.append("经办人姓名不能为空!\n");
        }

        if ("".equals(stampRecord.getAgentPhone()) || stampRecord.getAgentPhone() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人手机号码不能为空!\n");
        }

        if ("".equals(stampRecord.getAgentCertCode()) || stampRecord.getAgentCertCode() == null) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人证件号码不能为空!\n");
        }


 /*       for (MultipartFile file : files) {

            if (file.getSize() == 0) {

                condition.setCode(Condition.ERROR_CODE);

                messageBuffer.append("请上传完整的附件!\n");

                break;
            }

        }*/


        if (files.length < 0) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("附件材料不齐!\n");
        }

/*        if (files.length < filetypes.size()) {
            System.out.println(files.length);
            System.out.println(filetypes.size());
            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("附件不能为空!\n");

        }*/
        if(requiredList !=null && !requiredList.isEmpty()) {
            for (int i = 0; i < requiredList.size(); i++) {
                if (!requiredList.get(i).equals("0")) {
                    if (!filetypes.contains(requiredList.get(i))) {//如果不存在，有“必填”未完成
                        condition.setCode(Condition.ERROR_CODE);
                        messageBuffer.append("必填附件不能为空!\n");
                    }
                }
            }
        }else{
            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请先联系管理员，配置附件项!\n");
        }

        if (condition.getCode() == Condition.ERROR_CODE) {

            condition.setMessage(messageBuffer.toString());
        }

        return condition;
    }

    private Condition stampBusValidator(StampWorkTypeDTO stampWorkTypeDTO, MultipartFile[] files, MultipartFile permission) {

        Condition condition = new Condition();

        condition.setCode(Condition.SUCCESS_CODE);

        Stamp stamp = stampWorkTypeDTO.getStamp();

        StringBuffer messageBuffer = new StringBuffer();

        StampRecord stampRecord = stampWorkTypeDTO.getStampRecord();

        TempAgent tempAgent = stampWorkTypeDTO.getTempAgent();

        List<String> filetypes = stampWorkTypeDTO.getFileType();

        List<String> requiredList = stampWorkTypeDTO.getRequiredList();

        if (stampRecord.getSerialNum() == null || "".equals(stampRecord.getSerialNum())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("业务流水号不能为空!\n");

        }

        if (stampRecord.getWorkRemakrs() == null || "".equals(stampRecord.getWorkRemakrs())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("操作原因不能为空!\n");



        }

        if (tempAgent.getAgentName() == null || "".equals(tempAgent.getAgentName())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人姓名不能回空!\n");

        }

        if (tempAgent.getAgentName() == null || "".equals(tempAgent.getAgentName())) {

            condition.setCode(Condition.ERROR_CODE);


            messageBuffer.append("经办人姓名不能为空!\n");
        }

        if (tempAgent.getAgentPhone() == null || "".equals(tempAgent.getAgentPhone())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人联系电话不能为空!\n");
        }

        if (tempAgent.getAgentCertType() == null || "".equals(tempAgent.getAgentCertType())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人证件类型不能为空!\n");
        }

        if (tempAgent.getAgentCertCode() == null || "".equals(tempAgent.getAgentCertCode())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人证件号码不能为空!\n");
        }


        for (MultipartFile file : files) {

            if (file.getSize() == 0) {

                condition.setCode(Condition.ERROR_CODE);

                messageBuffer.append("请上传完整的附件!\n");

                break;
            }

        }

        // TODO
        // 附件验证
        if (files.length < 0) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("附件材料不齐!\n");
        }

      /*  if (files.length < filetypes.size()) {
            System.out.println(files.length);
            System.out.println(filetypes.size());
            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("附件不能为空!\n");

        }*/
        if(!filetypes.isEmpty()) {
            for (int i = 0; i < requiredList.size(); i++) {
                if (!requiredList.get(i).equals("0")) {
                    if (!filetypes.contains(requiredList.get(i))) {//如果不存在，有“必填”未完成
                        condition.setCode(Condition.ERROR_CODE);
                        messageBuffer.append("必填附件不能为空!\n");
                    }
                }
            }
        }else{
            messageBuffer.append("必填附件不能为空!\n");
        }

        //许可证验证
        if (permission == null || permission.getOriginalFilename() == "") {
            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("许可证不能为空!\n");
        }


        if (condition.getCode() == Condition.ERROR_CODE) {

            condition.setMessage(messageBuffer.toString());
        }


        return condition;
    }

    private Condition stampChangeBusValidator(StampWorkTypeDTO stampWorkTypeDTO, MultipartFile[] files, MultipartFile permission) {

        Condition condition = new Condition();

        condition.setCode(Condition.SUCCESS_CODE);

        Stamp stamp = stampWorkTypeDTO.getStamp();

        StringBuffer messageBuffer = new StringBuffer();

        Company useCompany = stampWorkTypeDTO.getUseCompany();

        StampRecord stampRecord = stampWorkTypeDTO.getStampRecord();

        TempAgent tempAgent = stampWorkTypeDTO.getTempAgent();

        List<String> filetypes = stampWorkTypeDTO.getFileType();

        List<String> requiredList = stampWorkTypeDTO.getRequiredList();

        if (stampRecord.getSerialNum() == null || "".equals(stampRecord.getSerialNum())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("业务流水号不能为空!\n");

        }


        if (stampRecord.getWorkRemakrs() == null || "".equals(stampRecord.getWorkRemakrs())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("变更原因不能为空!\n");

        }

        if (useCompany.getCompanyName() == null || "".equals(useCompany.getCompanyName())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("新公司名称不能为空!\n");

        }


        if (useCompany.getCompAddress() == null || "".equals(useCompany.getCompAddress())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("新公司地址不能为空!\n");

        }

//        if (useCompany.getCompPhone() == null || "".equals(useCompany.getCompPhone())) {
//
//            condition.setCode(Condition.ERROR_CODE);
//
//            messageBuffer.append("新公司联系电话不能为空!\n");
//
//        }

        if (useCompany.getType1() == null || "".equals(useCompany.getType1())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("新公司类型不能为空!\n");

        }

        if (useCompany.getSoleCode() == null || "".equals(useCompany.getSoleCode())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("新统一码不能为空!\n");

        }
        if (useCompany.getLegalName() == null || "".equals(useCompany.getLegalName())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("新法人姓名不能为空!\n");

        }
        if (useCompany.getLegalPhone() == null || "".equals(useCompany.getLegalPhone())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("新法人电话不能为空!\n");

        }
        if (useCompany.getLegalCertType() == null || "".equals(useCompany.getLegalCertType())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("新法人证件类型不能为空!\n");

        }

        if (useCompany.getLegalCertCode() == null || "".equals(stampRecord.getAgentName())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("新法人证件码不能为空!\n");

        }


        if (tempAgent.getAgentName() == null || "".equals(tempAgent.getAgentName())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人姓名不能为空!\n");

        }

        if (tempAgent.getAgentName() == null || "".equals(tempAgent.getAgentName())) {

            condition.setCode(Condition.ERROR_CODE);


            messageBuffer.append("经办人姓名不能为空!\n");
        }

        if (tempAgent.getAgentPhone() == null || "".equals(tempAgent.getAgentPhone())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人联系电话不能为空!\n");
        }

        if (tempAgent.getAgentCertType() == null || "".equals(tempAgent.getAgentCertType())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人证件类型不能为空!\n");
        }

        if (tempAgent.getAgentCertCode() == null || "".equals(tempAgent.getAgentCertCode())) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("经办人证件号码不能为空!\n");
        }

        for (MultipartFile file : files) {

            if (file.getSize() == 0) {

                condition.setCode(Condition.ERROR_CODE);

                messageBuffer.append("请上传完整的附件!\n");

                break;
            }

        }

        // TODO
        // 附件验证
        if (files.length < 0) {

            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("附件材料不齐!\n");
        }

       /* if (files.length < filetypes.size()) {

            System.out.println(files.length);
            System.out.println(filetypes.size());
            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("附件不能为空!\n");

        }*/

        if(!filetypes.isEmpty()) {
            for (int i = 0; i < requiredList.size(); i++) {
                if (!requiredList.get(i).equals("0")) {
                    if (!filetypes.contains(requiredList.get(i))) {//如果不存在，有“必填”未完成
                        condition.setCode(Condition.ERROR_CODE);
                        messageBuffer.append("必填附件不能为空!\n");
                    }
                }
            }
        }else{
            messageBuffer.append("必填附件不能为空!\n");
        }


        //许可证验证
        if (permission == null || permission.getOriginalFilename() == "") {
            condition.setCode(Condition.ERROR_CODE);

            messageBuffer.append("许可证不能为空!\n");
        }


        if (condition.getCode() == Condition.ERROR_CODE) {

            condition.setMessage(messageBuffer.toString());
        }


        return condition;


    }

    private Condition stampMoulageValidator(MultipartFile[] files) {

        Condition condition = new Condition();

        condition.setCode(Condition.SUCCESS_CODE);

        if (files.length < 1) {

            condition.setCode(Condition.ERROR_CODE);

            condition.setMessage("请上传印模之后再进行提交！");

            return condition;
        }

        for (MultipartFile file : files) {

            //System.out.println(file.getName());

            //System.out.println(file.getOriginalFilename());

            if (file.getOriginalFilename() == null || "".equals(file.getOriginalFilename())) {

                condition.setCode(Condition.ERROR_CODE);

                condition.setMessage("请上传印模之后再进行提交！");

                break;
            }

        }

        return condition;
    }

    private Condition ElectronStampValidator(ElectronStampDTO electronStampDTO, MultipartFile eleFile) {

        Condition condition = new Condition();

        condition.setCode(Condition.SUCCESS_CODE);

        Electron electron = electronStampDTO.getElectron();

        StringBuffer messageBuffer = new StringBuffer();

        Stamp stamp = electronStampDTO.getStamp();

        if (electron.getValidDateStart() == null) {


            messageBuffer.append("请填写有效开始时间\n");

            condition.setCode(Condition.ERROR_CODE);

        }

        if (electron.getValidDateEnd() == null) {

            messageBuffer.append("请填写有效结束时间\n");

            condition.setCode(Condition.ERROR_CODE);

        }

        // 用了公安部的电子印章，不需要上传此文件了
        /*if (eleFile == null || "".equals(eleFile.getOriginalFilename())) {

            messageBuffer.append("请上传生成的电子印章文件!\n");

            condition.setCode(Condition.ERROR_CODE);

        } else {

            String sealName = eleFile.getOriginalFilename();

            //  获取该文件的后缀文件类型
            int lastIndex = eleFile.getOriginalFilename().lastIndexOf(".");
            String lastName = eleFile.getOriginalFilename().substring(lastIndex, sealName.length());

            if (!".seal".equals(lastName)) {

                messageBuffer.append("请正确上传电子印章文件!\n");

                condition.setCode(Condition.ERROR_CODE);

            }

        }*/


        if (stamp.getEleModel() == null || "".equals(stamp.getEleModel())) {

            messageBuffer.append("请选择对应的物理印模！\n");

            condition.setCode(Condition.ERROR_CODE);
        }

        if (condition.getCode() == Condition.ERROR_CODE) {

            condition.setMessage(messageBuffer.toString());

        }

        return condition;
    }

    /*/**
     *@author bb
     *@description  交付印章验证
     *@param [photos, record, antiFakeWrite]
     *@return void
     *@date 2018/1/14
     */
    /*private void deliveryStampValidate(MultipartFile[] photos,
                                       MultipartFile record,
                                       String antiFakeWrite,
                                       String stampShape,
                                       Condition condition) throws StampValidateException {

        StringBuffer sb = new StringBuffer();
        if (photos == null || photos.length <= 0) {
            sb.append("请上传现场照片\n");
        }
        if (record == null || "".equals(record.getOriginalFilename())) {
            sb.append("请上传纸质备案登记表\n");
        }
        if ("1".equals(stampShape) && !"1".equals(antiFakeWrite)){
            sb.append("请完成防伪芯片写入\n");
        }
        //to sum up
        if (sb.length() > 0) {
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage(sb.toString());
            throw new StampValidateException();
        }
    }*/
    private void deliveryStampValidate(String stampId,
                                       String antiFakeWrite,
                                       String stampShape,
                                       Condition condition) throws StampValidateException {
        int flag1 = 0;
        int flag2 = 0;
        int flag3 = 0;
        int flag4 = 0;
        int flag5 = 0;
        List<DeliverAttachment> list = stampMakeService.getDeliverAttachment(stampId);
        for (DeliverAttachment da : list) {
            if (da.getxFlag() == 1) {
                flag1 = 1;
            } else if (da.getxFlag() == 2) {
                flag2 = 1;
            } else if (da.getxFlag() == 3) {
                flag3 = 1;
            } else if (da.getxFlag() == 4) {
                flag4 = 1;
            } else if (da.getxFlag() == 10) {
                flag5 = 1;
            }
        }

        StringBuffer sb = new StringBuffer();
        if (flag1 == 0) {
            sb.append("请上传身份证正面\n");
        }
        if (flag2 == 0) {
            sb.append("请上传身份证反面\n");
        }
        if (flag3 == 0) {
            sb.append("请上传经办人照片\n");
        }
        if (flag4 == 0) {
            sb.append("请上传纸质备案登记表\n");
        }
        if (flag5 == 0) {
            sb.append("请上传经办人签名\n");
        }

        if ("1".equals(stampShape) && !"1".equals(antiFakeWrite)) {
            sb.append("请完成防伪芯片写入\n");
        }
        //to sum up
        if (sb.length() > 0) {
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage(sb.toString());
            throw new StampValidateException();
        }
    }

    /**
     * @description: 电子印章备案提交
     * @auther: bb
     * @date: 2018-09-13
     * @param: [stampMakeDTO, files, request]
     * @return: json
     */
    @RequestMapping(value = "/doBARecordES", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String doBARecordES(StampMakeDTO stampMakeDTO, @RequestParam(value = "file") MultipartFile[] files,HttpServletRequest request) {

        Condition<StampRecord> condition = new Condition();

        try {
            condition = stampMakeValidator(stampMakeDTO,files,request);

            if (condition.getCode() == Condition.ERROR_CODE) {
                return JsonMapper.toJsonString(condition);

            }
            condition = stampMakeService.saveNewStamp(stampMakeDTO, files);

        } catch (MoneySettingNotFoundException e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, e.getMessage());

        } catch (SoleCodeException e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, "社会信用统一码错误!");

        } catch (AreaException e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, "该用章单位所属区域不正确!");

        } catch (Exception e) {

            e.printStackTrace();
            condition = new Condition(Condition.ERROR_CODE, "系统繁忙,请稍候再试!");

        } finally {

            System.out.println("finally Code:" + condition.getCode());
            if (condition.getCode() == Condition.SUCCESS_CODE) {

                // condition.setUrl(Global.getConfig("adminPath") + "/stampMakeAction/makeStamp/Receipt?stampRecordId=" + condition.getMessage());
                condition.setMessage("电子印章备案成功!");
            }
            System.out.println(condition.toString());
            return JsonMapper.toJsonString(condition);
        }
    }

}
