package com.thinkgem.jeesite.modules.stamp.web.stampMake;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.dto.countSet.CountSetDTO;
import com.thinkgem.jeesite.modules.stamp.dto.stampMake.EditAttachmentDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.countSet.CountSet;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.StampMakeException;
import com.thinkgem.jeesite.modules.stamp.service.countSet.DealerCountSetService;
import com.thinkgem.jeesite.modules.stamp.service.makeStampCompany.StampMakeEditService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/10/3.
 */
@Controller
@RequestMapping(value = "${adminPath}/stampMakeEdit")
public class StampMakeEditController {


    @Autowired
    private StampMakeEditService stampMakeEditService;

    @Autowired
    private DealerCountSetService dealerCountSetService;

    /**
     * stampRecord 可以从页面里面拿有这个属性没有用上去而已
     * <p>
     * 必须传入stampRecord的类型(workType)和Id
     * <p>
     * 做成什么样的效果，你觉得哪种好就哪种。
     * <p>
     * 同样的也要做数据校验
     * <p>
     * 企业名称和统一码不可以改。
     *
     * @param stampRecord
     * @return
     */
    @RequestMapping(value = "/editInfo", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String editStampRecordAndCompanyInfo(StampRecord stampRecord) {

        Condition condition = new Condition();

        try {

            //检查修改后的返回数据格式是否正确
            condition = stampMakeEditInfoValidator(stampRecord);

            if (condition.getCode() == Condition.ERROR_CODE) {

                return JsonMapper.toJsonString(condition);

            }

//            System.out.println("stampRecord.toString():"+stampRecord.toString());

            //传入修改后验证后的数据进行处理
            condition = stampMakeEditService.editCompanyAndStampRecordInfo(stampRecord);


        } catch (StampMakeException e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE);

        } catch (Exception e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE);

        } finally {

            return JsonMapper.toJsonString(condition);
        }


    }

    /**
     * 只能修改电子印模图
     * @param file
     * @param id 印章id
     * @param type 印章类型（电子/物理）
     * @return
     */
    /*@RequestMapping(value = "/editEleModel", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String editStampEleModel(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam(value = "id") String id, @RequestParam(value = "type") String type) {

        Condition condition = new Condition();

        try {


            if(file==null || file.isEmpty()){
                condition = new Condition(Condition.NOTFOUND_CODE,"请上传文件!");
                return JsonMapper.toJsonString(condition);
            }

            condition = stampMakeEditService.editEleModel(file,id,type);


        } catch (StampMakeException e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE);

        } catch (Exception e) {

            e.printStackTrace();

            condition = new Condition(Condition.ERROR_CODE);

        } finally {

            return JsonMapper.toJsonString(condition);
        }


    }*/

    /**
     *
     * 修改某个备案的附件
     *
     * @param files
     * @param dto
     * @return
     */
    @RequestMapping(value = "/editAttachments", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String editAttachments(@RequestParam(value="news",required = false) MultipartFile[] files, EditAttachmentDTO dto){

        Condition condition = null;


        try {

            condition = editAttachmentValidator(files,dto);

            if(condition.getCode()==Condition.ERROR_CODE){

                return JsonMapper.toJsonString(condition);
            }

            condition = stampMakeEditService.editAttachments(files,dto);
        }catch (Exception e){

            e.printStackTrace();

            condition= new Condition(Condition.ERROR_CODE);
        }

        return JsonMapper.toJsonString(condition);
    }

    /**
     * 返回待刻状态
     * @param id
     * @param stampShape
     * @return
     */
    @RequestMapping(value="/returnRecept", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String returnRecept(@RequestParam(value = "stampId")String id,@RequestParam(value="stampShape")String stampShape){

        Condition condition  = stampMakeEditService.returnRecept(id,stampShape);

        Company currentCompany = UserUtils.getUser().getCompanyInfo();
        CountSet currentEleCountSet = dealerCountSetService.getEleCountByCompany(currentCompany);
        CountSet currentPhyCountSet = dealerCountSetService.getPhyCountByCompany(currentCompany);

        currentEleCountSet.setCount(currentEleCountSet.getCount()+1);
        currentPhyCountSet.setCount(currentPhyCountSet.getCount()+1);

        //用于查找使用的实体
        CountSetDTO countSetDTO = new CountSetDTO();
        countSetDTO.setCompanyId(currentCompany.getId());

        //如果将已刻制的返回到待刻章状态，则将此章回收

        if ("1".equals(stampShape)) {
            countSetDTO.setPhyCountSet(currentPhyCountSet);
            dealerCountSetService.updatePhyStampCountByCompanyId(countSetDTO);
        }

        if ("2".equals(stampShape)){
            countSetDTO.setEleCountSet(currentCompany.getEleCountSet());
            dealerCountSetService.updateEleStampCountByCompanyId(countSetDTO);
        }

        return JsonMapper.toJsonString(condition);
    }

    protected Condition stampMakeEditInfoValidator(StampRecord stampRecord) {

        Condition condition = new Condition();

        condition.setCode(Condition.SUCCESS_CODE);

        StringBuffer messageBuffer = new StringBuffer();


        if (stampRecord.getLegalName() == null || "".equals(stampRecord.getLegalName())) {

            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请输入正确的法人姓名!\n");

        }

        if (stampRecord.getLegalPhone() == null || "".equals(stampRecord.getLegalPhone())) {

            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请输入正确的法人电话!\n");

        }

        if (stampRecord.getLegalCertType() == null || "".equals(stampRecord.getLegalCertType())) {

            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请输入正确的法人证件类型!\n");

        }

        if (stampRecord.getAgentName() == null || "".equals(stampRecord.getAgentName())) {

            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请输入正确的经办人姓名!\n");

        }

        if (stampRecord.getAgentPhone() == null || "".equals(stampRecord.getAgentPhone())) {

            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请输入正确的经办人手机!\n");

        }
        if (stampRecord.getAgentCertType() == null || "".equals(stampRecord.getAgentCertType())) {

            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请输入正确的经办人证件类型!\n");

        }
        if (stampRecord.getAgentCertCode() == null || "".equals(stampRecord.getAgentCertCode())) {

            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请输入正确的经办人证件号!\n");

        }

        if (stampRecord.getCompAddress() == null || "".equals(stampRecord.getCompAddress())) {

            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请输入正确的公司地址!\n");

        }

//        if (stampRecord.getCompPhone() == null || "".equals(stampRecord.getCompPhone())) {
//
//            condition.setCode(Condition.ERROR_CODE);
//            messageBuffer.append("请输入正确的公司电话!\n");
//
//        }

        if (stampRecord.getType1() == null || "".equals(stampRecord.getType1())) {

            condition.setCode(Condition.ERROR_CODE);
            messageBuffer.append("请输入正确的单位类别!\n");

        }

        return condition;
    }

    protected Condition editAttachmentValidator(MultipartFile[] files, EditAttachmentDTO dto){
        System.out.println(files!=null);
        System.out.println(dto.getDeleteIds()!=null);
        if(files != null&&files.length<1&&dto.getDeleteIds()==null){
            return new Condition(Condition.ERROR_CODE,"没有要修改的内容!");
        }

        if(files.length!=0) {
            if (files.length != dto.getFileType().size()) {

                return new Condition(Condition.ERROR_CODE, "请正确上传要更改的附件!");

            }
        }

        return new Condition(Condition.SUCCESS_CODE);
    }

}
