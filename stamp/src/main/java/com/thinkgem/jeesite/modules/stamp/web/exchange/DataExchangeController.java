/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.web.exchange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JavaType;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.exchange.ExchangeResult;
import com.thinkgem.jeesite.modules.stamp.entity.exchange.SealEntity;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.AreaException;
import com.thinkgem.jeesite.modules.stamp.service.company.CompanyService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.utils.Object2Object;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.stamp.entity.exchange.DataExchange;
import com.thinkgem.jeesite.modules.stamp.service.exchange.DataExchangeService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 完成数据交换Controller
 *
 * @author ADD BY LINZHIBAO
 * @version 2018-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/stamp/exchange/dataExchange")
public class DataExchangeController extends BaseController {

    @Autowired
    private DataExchangeService dataExchangeService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CompanyService companyService;


    /**
     * 功能描述:接收印章商城订单数据,
     *
     * @param: [dataExchange, redirectAttributes]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @auther: linzhibao
     * @date: 2018-09-03 15:20
     */
    @RequestMapping(value = "/batchInsert", method = RequestMethod.POST)
    public ExchangeResult batchInsert(DataExchange dataExchange) {
        try {
            // 将 sealEntityListJson转换成list
            JsonMapper jsonMapper = new JsonMapper();
            // 后端对json字符串 HTML编码化后反编码
            dataExchange.setSealEntityListJson(StringEscapeUtils.unescapeHtml(dataExchange.getSealEntityListJson()));
            dataExchange.setSealEntityListJson(dataExchange.getSealEntityListJson());
            JavaType javaType = jsonMapper.createCollectionType(ArrayList.class, SealEntity.class);
            ArrayList<SealEntity> sealEntities = jsonMapper.fromJson(dataExchange.getSealEntityListJson(), javaType);
            dataExchange.setSealEntityList(sealEntities);
            // 保存订单数据
            dataExchangeService.save(dataExchange);
        } catch (Exception e) {
            return new ExchangeResult(-1, "下单失败", "描述一下");
        }
        return new ExchangeResult(0, "下单成功", "描述一下");
    }

    /**
     * 功能描述:刻章点查看来自印章商城的列表
     *
     * @param: [dataExchange, model]
     * @return: java.lang.String
     * @auther: linzhibao
     * @date: 2018-09-05 14:11
     */
    @RequestMapping(value = "/dataExchangeList")
    public String dataExchangeList(DataExchange dataExchange, Model model, HttpServletResponse response, HttpServletRequest request) {
        Page<DataExchange> dataExchangePage = dataExchangeService.findPage(new Page<DataExchange>(request, response), dataExchange);
        model.addAttribute("page", dataExchangePage);
        model.addAttribute("dataExchange", dataExchange);
        return "modules/jsps/stampmaker/stampengrave/stampengrave-stamp-store-order-list";
    }

    /**
     * 功能描述: 刻章点接受来自印章商城的订单,生成待刻印章
     *
     * @param: [dataExchange, model, response, request]
     * @return: void
     * @auther: linzhibao
     * @date: 2018-09-05 18:02
     */
    @RequestMapping(value = "/acceptOrder")
    @ResponseBody
    public String acceptOrder(DataExchange dataExchange) {

        // 1.根据前台传入Id查找要订单信息
        dataExchange = dataExchangeService.get(dataExchange.getId());

        // 2.判定刻章点与用章企业是否在同一个区域下,例如:端州区的刻章点只能刻制本区的企业
        Area makeArea = areaService.get(new Area(UserUtils.getUser().getCompanyInfo().getArea().getId()));// 获取刻章点所在区域
        Area useArea = areaService.getAreaByCode(dataExchange.getUseCompArea());  // 获用章企业所在的区域
        if (!(makeArea.judgeAreaCode(useArea) || makeArea.containArea(useArea))) {
            throw new AreaException("新统一码归属区域不符合规定!");
        }

        // 3.检查当前申请备案的用章企业在印章备案系统中是否已经存在此用户
        Company company = null;
        if (companyService.checkCompanyBysoleCodeAndCompName(dataExchange.getSoleCode(), dataExchange.getCompanyName(), CompanyType.USE) == 0) { // 不存在用户
            if("2".equals(dataExchange.getStampShape())){ //新公司不能直接刻制电子章
                return "刻制电子印章前请先刻制物理印章";
            }
            company = dataExchangeService.createUser(dataExchange, makeArea, new StampRecord()); // 创建用户和权限信息
            dataExchange.setUseCompany(company);
        }
        if (company == null) {
            // 4.校验是否可以刻此章,例如公章只能刻制一个,如已经刻制过公章,则不可再刻制公章,要刻制电子章就必须先刻制物理章
            Company com = Object2Object.convertPojo(dataExchange, Company.class);
            com.setCompType(CompanyType.USE);
            company = companyService.get(com);
            dataExchange.setUseCompany(company);
            String validResult = dataExchangeService.validCanMakeStamp(dataExchange);
            if(validResult != null){
                return validResult;
            }
        }
        // 5.插入 t_stamprecord_1 表
        dataExchange.setMakeCompany(UserUtils.getUser().getCompanyInfo());
        StampRecord stampRecord = dataExchangeService.insertStampRecord(dataExchange);
        // 6.插入 t_stamp_?表中 ,不能批量插入,因为不同的id分别要绑定stamprecord表
        List<Stamp> stampList = new ArrayList<Stamp>();
        for (int i = 0; i < dataExchange.getSealCount(); i++) {
            Stamp stamp = dataExchangeService.insertStamp(dataExchange, stampRecord); // 插入stamp表
            //7.将stamp与stampRecord进行绑定
            stampList.add(stamp);
        }
        dataExchangeService.stampBindRecord(dataExchange, stampList, stampRecord);
        // 8.逻辑删除此条订单数据delflag修改为1
        dataExchangeService.delete(dataExchange);
        return "刻制成功";
    }

}