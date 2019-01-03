package com.thinkgem.jeesite.modules.sign.web;

import com.thinkgem.jeesite.common.utils.GetAddr;
import com.thinkgem.jeesite.common.utils.MacUtils;
import com.thinkgem.jeesite.modules.sign.common.condition.Condition;
import com.thinkgem.jeesite.modules.sign.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.sign.dto.SingatureDTO;
import com.thinkgem.jeesite.modules.sign.dto.SingaturePageDTO;
import com.thinkgem.jeesite.modules.sign.entity.Audit;
import com.thinkgem.jeesite.modules.sign.entity.Document;
import com.thinkgem.jeesite.modules.sign.entity.Seal;
import com.thinkgem.jeesite.modules.sign.service.DocumentInfoService;
import com.thinkgem.jeesite.modules.sign.service.SealFindService;
import com.thinkgem.jeesite.modules.sign.service.SinatrueService;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Locker on 2017/8/31.
 */
@Controller
@RequestMapping(value="${adminPath}/singal")
public class SingalController {

    @Autowired
    private SealFindService sealFindService;

    @Autowired
    private SinatrueService sinatrueService;

    @Autowired
    private DocumentInfoService documentInfoService;

    /**
     * 进入到签章页面
     * @param sealId
     * @param fileId
     * @param model
     * @return
     */
    @RequestMapping(value="/index")
    public String index(@RequestParam(value="seal")String sealId, @RequestParam(value="document")String fileId, Model model){

        Seal seal = sealFindService.get(sealId);

        model.addAttribute("seal",seal);

        model.addAttribute("document",fileId);

        return "modules/sign";
    }

    /**
     * 进入选择印章列表页面
     * @param documentId
     * @param model
     * @return
     */
    @RequestMapping(value="/ready/singatrue")
    public String readySinatrue(@RequestParam(value="id")String documentId, Model model){

        model.addAttribute("id",documentId);
        //todo 跳转到准备签章页面
        return "modules/sign/seal_list";
    }

    /**
     * 跳转到真正签章的iframe页面
     * @param sinatureDTO
     * @param model
     * @return
     */
    @RequestMapping(value="/real/singatrue")
    public String real(SingaturePageDTO sinatureDTO, Model model){

        Document document = documentInfoService.get(sinatureDTO.getDocument());

        //Seal seal = sealFindService.get(sinatureDTO.getSeal().getId());
        Stamp stamp =sealFindService.findStampById(sinatureDTO.getSeal().getId());
        sinatureDTO.setDocument(document);

        //sinatureDTO.setSeal(seal);
        sinatureDTO.setStamp(stamp);

        model.addAttribute("dto",sinatureDTO);

        model.addAttribute("doc",document);

        //跳转到签章的页面
        return "modules/sign/sign_iframe";
    }

    /**
     * 真正的签章页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/singatruePage")
    public String singatruePage(@RequestParam(value="id") String id, Model model){

        Document document = documentInfoService.get(new Document(id));
        model.addAttribute("document",document);

        //todo 真正的签章页面
        return "../../static/sign/pdfjs/web/viewer";
    }

    /**
     * 签章
     * @param dto
     * @return
     */
    @RequestMapping(value="/signature")
    @ResponseBody
    public String signature(SingatureDTO dto, HttpServletRequest request){
        System.out.println(dto.getPoint());

        //审计相关
        Audit audit = new Audit();
        try {
            audit.setIp(GetAddr.getIp(request));
            audit.setMac(MacUtils.getMac());//此处获取的是服务端Mac地址
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(dto.getHexCode()==null||dto.getHexCode().equals("")){
            return JsonMapper.toJsonString(new Condition(Condition.ERROR_CODE,"出错了！"));
        }

        Condition condition = sinatrueService.singatureOne(dto.getSeal(),dto.getDocument(),dto.getPoint(),dto.getHexCode(),audit);

        return JsonMapper.toJsonString(condition);
    }

    /**
     * 保存签章数据
     * @param SignData
     * @return
     */
    @RequestMapping(value ="/updateSignData")
    @ResponseBody
    public String updateSignData(String SignData,String DocmentId,String AuditId){
        Condition condition = sinatrueService.updateSignData(SignData,DocmentId,AuditId);
        return JsonMapper.toJsonString(condition);
    }


}
