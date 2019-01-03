package com.thinkgem.jeesite.modules.stamp.web.company.page;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampAuth;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampOperation;
import com.thinkgem.jeesite.modules.stamp.service.stamp.StampService;
import com.thinkgem.jeesite.modules.stamp.service.stamprecord.StampAuthSerivce;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.stamp.service.stamprecord.StampOperationService;
import com.thinkgem.jeesite.modules.stamp.vo.useCompany.StampAuthVo;
import com.thinkgem.jeesite.modules.stamp.vo.useCompany.UserAndStampVo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by sjk on 2017-05-20.
 */
@Controller
@RequestMapping(value = "${adminPath}/useCompanyStampPage")
public class UseCompanyStampPageController {

    @Autowired
    private StampService stampService;

    @Autowired
    private StampAuthSerivce stampAuthSerivce;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StampOperationService stampOperationService;

    /**
     * 启停与转授权页面
     * @param stampAuth
     * @return
     */
    @RequestMapping(value = "/findStampInfo")
    public String stampManage(Model model, StampAuth stampAuth, HttpServletRequest request, HttpServletResponse response) {

        Page<StampAuth> page = stampAuthSerivce.showAuthState(new Page<StampAuth>(request, response, -1), stampAuth);

        Stamp stamp = stampService.getStampInfo(stampAuth.getStamp());

        StampAuthVo stampAuthVo = new StampAuthVo();

        stampAuthVo.setStamp(stamp);

        stampAuthVo.setPage(page);

        List<User> noAuthUser = stampAuthSerivce.findNoAuthUser(stampAuth);

        model.addAttribute("stampAuthVo", stampAuthVo);

        model.addAttribute("noAuthUser", noAuthUser);

        model.addAttribute("loginUser",UserUtils.getUser());

        return "modules/jsps/useUnit/useUnit-jurisdiction";
    }

    /**
     * 显示使用中的印章页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/showUsing")
    public String showUsing(Model model, HttpServletRequest request, HttpServletResponse response, Stamp stamp) {
//
//        if (stamp == null) {
//            stamp = new Stamp();
//        }
        if(stamp.getStampShape() == null ||"".equals(stamp.getStampShape())){

            //默认是物理
            stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        }


        stamp.setUseComp(new Company(UserUtils.getUser().getUserTypeId(), CompanyType.USE));

        stamp.setStampState(StampState.DELIVERY);

        stamp.setUseState(OprationState.OPEN);

        stamp.setSysState(SysState.USABLE);

        System.out.println(StampState.DELIVERY+","+OprationState.OPEN+","+SysState.USABLE);

        Page<Stamp> page = stampService.listStampByUseStatePage(new Page<Stamp>(request, response), stamp);

        model.addAttribute("page", page);

        return "modules/jsps/useUnit/useUnit-using";
    }

    /**
     * 显示停用的印章页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/showStop")
    public String showStop(Model model, HttpServletRequest request, HttpServletResponse response, Stamp stamp) {

        if(stamp.getStampShape() == null ||"".equals(stamp.getStampShape())){

            //默认是物理
            stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        }

        stamp.setUseComp(new Company(UserUtils.getUser().getUserTypeId(),CompanyType.USE));

        stamp.setStampState(StampState.DELIVERY);

        stamp.setUseState(OprationState.STOP);

        Page<Stamp> page = stampService.listStampByUseStatePage(new Page<Stamp>(request, response), stamp);

        model.addAttribute("page", page);

        return "modules/jsps/useUnit/useUnit-stopping";
    }

    /**
     * 显示缴销的印章页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/showDestroy")
    public String showDestroy(Model model, HttpServletRequest request, HttpServletResponse response, Stamp stamp) {

        if(stamp.getStampShape() == null ||"".equals(stamp.getStampShape())){

            //默认是物理
            stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        }

        stamp.setUseState(OprationState.LOGOUT);

        stamp.setUseComp(new Company(UserUtils.getUser().getUserTypeId(),CompanyType.USE));

        Page<Stamp> page = stampService.listStampByUseStatePage(new Page<Stamp>(request, response), stamp);

        model.addAttribute("page", page);

        return "/modules/jsps/useUnit/useUnit-destroy";
    }

    /**
     * 显示挂失的印章页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/showMissing")
    public String showMissing(Model model, HttpServletRequest request, HttpServletResponse response, Stamp stamp) {

        if(stamp.getStampShape() == null ||"".equals(stamp.getStampShape())){

            //默认是物理
            stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        }

        stamp.setUseState(OprationState.REPORT);

        stamp.setUseComp(new Company(UserUtils.getUser().getUserTypeId(),CompanyType.USE));

        Page<Stamp> page = stampService.listStampByUseStatePage(new Page<Stamp>(request, response), stamp);

        model.addAttribute("page", page);

        return "/modules/jsps/useUnit/useUnit-missing";
    }

    /**
     * 显示刻制中的印章页面

     * @param model
     * @return
     */
    @RequestMapping(value = "/showMaking")
    public String showMaking(Model model, HttpServletRequest request, HttpServletResponse response, Stamp stamp) {

        if(stamp.getStampShape() == null ||"".equals(stamp.getStampShape())){

            //默认是物理
            stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        }

        stamp.setStampState(StampState.RECEPT);

        stamp.setUseComp(new Company(UserUtils.getUser().getUserTypeId(),CompanyType.USE));

        Page<Stamp> page = stampService.listStampByStampStatePage(new Page<Stamp>(request, response), stamp);

        model.addAttribute("page", page);

        return "/modules/jsps/useUnit/useUnit-making";
    }

    /**
     * 显示待交付页面
     * @param model
     * @param request
     * @param response
     * @param stamp
     * @return
     */
    @RequestMapping(value = "/showMade")
    public String showMade(Model model, HttpServletRequest request, HttpServletResponse response, Stamp stamp) {

        if(stamp.getStampShape() == null ||"".equals(stamp.getStampShape())){

            //默认是物理
            stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        }

        stamp.setStampState(StampState.ENGRAVE);

        stamp.setUseComp(new Company(UserUtils.getUser().getUserTypeId(),CompanyType.USE));

        Page<Stamp> page = stampService.listStampByStampStatePage(new Page<Stamp>(request, response), stamp);

        model.addAttribute("page", page);

        return "/modules/jsps/useUnit/useUnit-waitdeliver";
    }

    /**
     * 显示可使用的电子印章页面
     */
    @RequestMapping(value = "/showUseful")
    public String showUseful(Model model, HttpServletRequest request, HttpServletResponse response, StampAuth stampAuth) {

        //筛选当前用户
        stampAuth.setUser(UserUtils.getUser());

        //筛选已授权的印章
        stampAuth.setStampAuthState(StampAuthState.AUTH_TRUE);

        //筛选启用中的印章
        if (stampAuth.getStamp() == null) {
            stampAuth.setStamp(new Stamp());
        }
        stampAuth.getStamp().setUseState(OprationState.OPEN);

        Page<StampAuth> page = stampService.showUsefulEleStampPage(new Page<StampAuth>(request, response), stampAuth);

        model.addAttribute("page", page);

        return "/modules/jsps/useUnit/useUnit-usable";
    }

    /**
     * 显示物理印章使用录入页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/showRecordInput")
    public String showRecordInput(Model model) {

        UserAndStampVo userAndStampVo = stampOperationService.showUserAndStamp(UserUtils.getUser().getUserTypeId());

        model.addAttribute("userAndStamp", userAndStampVo);

        return "/modules/jsps/useUnit/useUnit-input";
    }

    /**
     * 显示印章详细信息
     * @param stampId
     * @param model
     * @return
     */
    @RequestMapping(value = "/showStamp")
    public String showStamp(String stampId,String stampShape, Model model) {

        Stamp stamp = stampService.findStampInfo(stampId,stampShape);

        model.addAttribute("stamp", stamp);

        return "/modules/jsps/useUnit/useUnit-stampdetail";
    }
}
