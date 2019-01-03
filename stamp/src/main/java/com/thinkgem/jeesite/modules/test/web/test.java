/*
package com.thinkgem.jeesite.modules.test.web;

import com.thinkgem.jeesite.common.utils.GetAddr;
import com.thinkgem.jeesite.common.utils.MacUtils;
import com.thinkgem.jeesite.modules.job.dao.DataSynchronizationDao;
import com.thinkgem.jeesite.modules.job.entity.DataExchange;
import com.thinkgem.jeesite.modules.job.service.DataSynchronizationService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

*/
/**
 * Created by Administrator on 2017/5/19.
 *//*

@Controller
@RequestMapping(value="${frontPath}/bb")
public class test {

    @Autowired
    private DataSynchronizationService dataSynchronizationService;
    @Autowired
    private DataSynchronizationDao dataSynchronizationDao;

    @RequestMapping(value="/stampMake")
    public String tostampMakeJsp(){

        */
/*List<DataExchange> dList = new ArrayList<DataExchange>();
        DataExchange d1 = new DataExchange();
        d1.setBusinessId("FFF");
        d1.setSealType("3");
        d1.setSealStyle("3");
        d1.setSealCount("1");
        d1.setBusinessType("2");
        d1.setMakeCompArea("441202");
        d1.setMakeCompSocialcreditcode("441202");
        d1.setUseCompArea("441202");
        d1.setUseCompSocialcreditcode("441202");
        d1.setUseCompName("今日头条");
        d1.setUseCompType("2");
        d1.setUseCompAddress("北京");
        d1.setLegalName("马一怒");
        d1.setLegalPhone("马一怒");
        d1.setLegalCerttype("2");
        d1.setLegalCertcode("马一怒");
        d1.setAgentName("马一怒");
        d1.setAgentPhone("马一怒");
        d1.setAgentCertcode("马一怒");
        d1.setAgentCerttype("2");
        d1.setCreateDate(new Date());
        d1.setExFlag("0");

        DataExchange d2 = new DataExchange();
        d2.setBusinessId("HHH");
        d2.setSealType("3");
        d2.setSealStyle("3");
        d2.setSealCount("1");
        d2.setBusinessType("2");
        d2.setMakeCompArea("441202");
        d2.setMakeCompSocialcreditcode("441202");
        d2.setUseCompArea("441202");
        d2.setUseCompSocialcreditcode("441202");
        d2.setUseCompName("今日头条");
        d2.setUseCompType("2");
        d2.setUseCompAddress("北京");
        d2.setLegalName("马一怒");
        d2.setLegalPhone("马一怒");
        d2.setLegalCerttype("2");
        d2.setLegalCertcode("马一怒");
        d2.setAgentName("马一怒");
        d2.setAgentPhone("马一怒");
        d2.setAgentCertcode("马一怒");
        d2.setAgentCerttype("2");
        d2.setCreateDate(new Date());
        d2.setExFlag("0");

        dList.add(d1);
        dList.add(d2);
        dataSynchronizationService.batchsave(dList);*//*

        */
/*List<Company> list = Lists.newArrayList();
        Company useComp1 = new Company();
        useComp1.setId(IdGen.uuid());
        useComp1.setArea(new Area("123123123"));
        useComp1.setCompType(CompanyType.USE);
        useComp1.setSoleCode("2222");
        useComp1.setCompanyName("2222");
        useComp1.setType1("2222");
        useComp1.setLegalName("2222");
        useComp1.setLegalPhone("2222");
        useComp1.setLegalCertType("2222");
        useComp1.setLegalCertCode("2222");
        useComp1.setCompAddress("2222");
        useComp1.setCompState(CompanyState.USING);
        useComp1.setCompCreatDate(new Date());
        useComp1.setSysOprState(OprationState.OPEN);
        useComp1.setCreateDate(new Date());
        useComp1.setUpdateDate(new Date());
        useComp1.setCreateBy(new User("qweqweqweq"));
        useComp1.setUpdateBy(new User("qweqweqweq"));

        Company useComp2 = new Company();
        useComp2.setId(IdGen.uuid());
        useComp2.setArea(new Area("123123123"));
        useComp2.setCompType(CompanyType.USE);
        useComp2.setSoleCode("2222");
        useComp2.setCompanyName("2222");
        useComp2.setType1("2222");
        useComp2.setLegalName("2222");
        useComp2.setLegalPhone("2222");
        useComp2.setLegalCertType("2222");
        useComp2.setLegalCertCode("2222");
        useComp2.setCompAddress("2222");
        useComp2.setCompState(CompanyState.USING);
        useComp2.setCompCreatDate(new Date());
        useComp2.setSysOprState(OprationState.OPEN);
        useComp2.setCreateDate(new Date());
        useComp2.setUpdateDate(new Date());
        useComp2.setCreateBy(new User("qweqweqweq"));
        useComp2.setUpdateBy(new User("qweqweqweq"));

        list.add(useComp1);
        list.add(useComp2);
        dataSynchronizationService.batchtest(list);*//*


        List<DataExchange> dList = new ArrayList<DataExchange>();
        DataExchange d1 = new DataExchange();
        d1.setId("35");
        d1.setExFlag("1");
        d1.setExFailReason("你妈啊啊啊啊啊啊");
        DataExchange d2 = new DataExchange();
        d2.setId("36");
        d2.setExFlag("1");
        d2.setExFailReason("你妈啊啊啊啊啊啊");
        DataExchange d3 = new DataExchange();
        d3.setId("37");
        d3.setExFlag("1");
        d3.setExFailReason("你妈啊啊啊啊啊啊");

        dList.add(d1);
        dList.add(d2);
        dList.add(d3);

        dataSynchronizationDao.updateExFlag(dList);


        return "modules/jsps/500";
    }

    @RequestMapping(value="/getAddr")
    @ResponseBody
    public Map getAddr(HttpServletRequest request) throws Exception {

        String mac = "";

        mac = MacUtils.getMac();
        Map<String,String> map = new HashMap<String,String>();
        map.put("IP", GetAddr.getIp(request));
        map.put("MAC",mac);
        return map;

    }

    @RequestMapping(value="/ele")
    public String toJSP(HttpServletRequest request) {
        return "modules/jsps/stampmaker/stampengrave-ES/stampengrave-elestamp-ES";
    }

    @RequestMapping(value = "/toViewer")
    public void toViewer(MultipartFile file, HttpServletResponse response){

        byte[] data = null;
        try {
            CommonsMultipartFile cf = (CommonsMultipartFile) file;//此处file 是MultipartFile
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            File tempfile = fi.getStoreLocation();//会在项目的根目录的临时文件夹下生成一个临时文件 *.tmp

            //File tempfile = new File("C:/0412_demo.pdf");
            FileInputStream is = new FileInputStream(tempfile);
            data = new byte[is.available()];
            is.read(data);
            response.getOutputStream().write(data);
            System.out.println(data);
            is.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
*/
