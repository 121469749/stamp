package com.thinkgem.jeesite.modules.stamp.service.count.makeCompany;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.*;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampRecordDao;
import com.thinkgem.jeesite.modules.stamp.dto.count.CompanyCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.CountUseCompanyStampDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.StampCountDTO;
import com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany.StampStateStampCountDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.CompanyCountExcelByPoliceDTO;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.CompanyCountExcelDo;
import com.thinkgem.jeesite.modules.stamp.excelExportDo.count.StampCountExcelByMakeCompanyDTO;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * Created by Locker on 2017/7/25.
 */
@Service
@Transactional(readOnly = true)
public class MakeCompanyCountService {

    @Autowired
    private StampRecordDao stampRecordDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private StampDao stampDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private MakeCompanyCountService makeCompanyCountService;

    /**
     * 印章统计
     *
     * @param dto
     * @param page
     * @return
     */
    public Page<Stamp> count(StampCountDTO dto, Page<Stamp> page) {

        //设置查询条件
        Stamp stamp = dto.changeStamp();
        //当前的刻章点
        User user = UserUtils.getUser();
        Company makeCompany = user.getCompanyInfo();
        stamp.setMakeComp(makeCompany);
        stamp.setNowMakeComp(makeCompany);
        //end

        stamp.setPage(page);
        List<Stamp> list = stampDao.countStampList(stamp);

        //印章所属用章公司
        findStampListUseCompany(list);
        page.setList(list);

        return page;
    }

    /**
     * 导出 Excel
     *
     * @param dto
     * @return
     */
    public List<StampCountExcelByMakeCompanyDTO> countExcel(StampCountDTO dto) {

        //设置查询条件
        Stamp stamp = dto.changeStamp();
        //当前的刻章点
        User user = UserUtils.getUser();
        Company makeCompany = user.getCompanyInfo();
        stamp.setMakeComp(makeCompany);
        stamp.setNowMakeComp(makeCompany);
        //end

        List<Stamp> list = stampDao.countStampList(stamp);

        //印章所属用章公司
        findStampListUseCompany(list);

        List<StampCountExcelByMakeCompanyDTO> excelList = new ArrayList<StampCountExcelByMakeCompanyDTO>();

        changeExcelExportDTO(excelList, list);

        return excelList;
    }

    /**
     * 刻章点对企业信息的统计
     *
     * @param page
     * @param dto
     * @author bb
     */
    public Page<Company> makeCompanyCountPage(Page<Company> page, CompanyCountDTO dto) {

        Company checkCompany = dto.getCompany();
        Company currentMakeCompany = UserUtils.getUser().getCompanyInfo();
        Area currentArea = currentMakeCompany.getArea();
        checkCompany.setCompType(CompanyType.USE);
        checkCompany.setArea(currentArea);
        Date startDate = dto.getStartDate();
        Date endDate = dto.getEndDate();
        checkCompany.setPage(page);

        Stamp stamp = new Stamp();
        stamp.setStampShape(StampShape.PHYSTAMP.getKey());
        stamp.setNowMakeComp(currentMakeCompany);

        dto.setPage(page);
        dto.setCompany(checkCompany);
        dto.setStamp(stamp);
        // System.out.println(dto.getCompany().getCompType());

        long a1 = System.currentTimeMillis();
        List<Company> CompanyList = companyDao.findUseCompByArea(dto);
        long a2 = System.currentTimeMillis();
        System.out.println("企业统计时间:" + (a2 - a1));
        useCompanyCountDeal(CompanyList);
        long b = System.currentTimeMillis();
        System.out.println("印章统计时间:" + (b - a2));
        page.setList(CompanyList);

        return page;
    }


    /**
     * 对企业的备案详情做统计
     *
     * @param useCompanyId
     * @return
     * @throws IOException
     * @author bb
     */
    public List<StampRecord> findRecordDetailInfoByUseCompany(String useCompanyId) throws IOException {

        List<StampRecord> all = new ArrayList<StampRecord>();

        StampRecord check = new StampRecord();
        check.setUseComp(new Company(useCompanyId));

        //查出所有备案记录
        //1.若是刻章点查询
        if (UserUtils.getUser().getCompanyInfo().getCompType() == CompanyType.MAKE) {
            check.setMakeComp(UserUtils.getUser().getCompanyInfo());
            all = countStampRecordWorkType(check);
        }
        //2.若是公安查询(或者其他)
        all = countStampRecordWorkType(check);

        for (StampRecord stampRecord : all) {

            List<Stamp> list = dealStampRecordJsonStamp(stampRecord.getApplyInfos());

            stampRecord.setStamps(list);
        }

        return all;
    }


    /**
     * 整合信息导出excel
     *
     * @param page
     * @param dto
     * @return
     */
    public List<CompanyCountExcelDo> exportExcel(Page<Company> page, CompanyCountDTO dto) {

        Company checkCompany = dto.getCompany();

        Company currentMakeCompany = UserUtils.getUser().getCompanyInfo();

        Area currentArea = currentMakeCompany.getArea();

        checkCompany.setCompType(CompanyType.USE);

        checkCompany.setArea(currentArea);

        Date startDate = dto.getStartDate();

        Date endDate = dto.getEndDate();

        checkCompany.setPage(page);

        //查找这个区下的用章公司
        List<Company> makeCompanyList = companyDao.findCompanyByArea(checkCompany);

        List<CompanyCountExcelDo> list = new ArrayList<CompanyCountExcelDo>();

        for (Company useCompany : makeCompanyList) {


            StampRecord stampRecordCheck = new StampRecord();

            stampRecordCheck.setWorkType(StampWorkType.APPLY);

            stampRecordCheck.setMakeComp(currentMakeCompany);

            stampRecordCheck.setUseComp(useCompany);
            // 统计项目
            //1、印章备案数目
            //五个类型
            //1、apply
            int applyStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

            // 2、Logout
            stampRecordCheck.setWorkType(StampWorkType.LOGOUT);
            int logoutStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

            // 3、Report
            stampRecordCheck.setWorkType(StampWorkType.REPORT);
            int reportStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

            // 4、Repair
            stampRecordCheck.setWorkType(StampWorkType.REPAIR);
            int repairStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

            // 5、Change
            stampRecordCheck.setWorkType(StampWorkType.CHANGE);
            int changeStampRecordCount = stampRecordDao.countStampRecord(stampRecordCheck, startDate, endDate);

            //总数
            int stampRecordCount = applyStampRecordCount + logoutStampRecordCount
                    + reportStampRecordCount + repairStampRecordCount + changeStampRecordCount;

            //2、电子印章的刻制数量
            Stamp stamp = new Stamp();

            stamp.setStampShape(StampShape.ELESTAMP.getKey());

            stamp.setUseComp(useCompany);

            stamp.setMakeComp(currentMakeCompany);
            //总数
            int eleStampCount = stampDao.countStampNumber(stamp, startDate, endDate);


            //3、物理印章的刻制数量

            stamp.setStampShape(StampShape.PHYSTAMP.getKey());

            int phyStampCount = stampDao.countStampNumber(stamp, startDate, endDate);


            // 4、缴销印章的数量

            stamp.setUseState(OprationState.LOGOUT);

            int logoutPhyStampCount = stampDao.countStampNumber(stamp, startDate, endDate);

            stamp.setStampShape(StampShape.ELESTAMP.getKey());

            int logoutEleStampCount = stampDao.countStampNumber(stamp, startDate, endDate);
            //总数
            int logoutStampCount = logoutEleStampCount + logoutPhyStampCount;


            // 5、挂失印章的数量

            stamp.setUseState(OprationState.REPORT);

            int reportEleStampCount = stampDao.countStampNumber(stamp, startDate, endDate);

            stamp.setStampShape(StampShape.PHYSTAMP.getKey());

            int reportPhyStampCount = stampDao.countStampNumber(stamp, startDate, endDate);
            //总数
            int reportStampCount = reportEleStampCount + reportPhyStampCount;


            CompanyCountExcelDo excelDo = new CompanyCountExcelDo();

            excelDo.setStampRecordCount(stampRecordCount);
            excelDo.setPhyStampCount(phyStampCount);
            excelDo.setEleStampCount(eleStampCount);
            excelDo.setLogoutStampCount(logoutStampCount);
            excelDo.setReportStampCount(reportStampCount);

            excelDo.setCompAddress(useCompany.getCompAddress());
            excelDo.setCompPhone(useCompany.getCompPhone());
            excelDo.setArea(useCompany.getArea());
            excelDo.setLegalName(useCompany.getLegalName());
            excelDo.setLegalPhone(useCompany.getLegalPhone());
            excelDo.setSoleCode(useCompany.getSoleCode());
            excelDo.setCompanyType(useCompany.getCompType());
            excelDo.setCompanyName(useCompany.getCompanyName());

            list.add(excelDo);

        }

        return list;
    }


    public Company getUseCompany(String useCompangId) {

        Company company = new Company(useCompangId);
        company.setCompType(CompanyType.USE);

        return companyDao.get(company);
    }

    /**
     * 共用统计方法
     *
     * @param checkStamp
     * @param dto
     */
    public void countOprationStateStamp(Stamp checkStamp, StampStateStampCountDTO dto) {

        int sum = 0;

        for (OprationState oprationState : OprationState.values()) {

            checkStamp.setUseState(oprationState);

            int count = stampDao.countStamp(checkStamp);

            sum += count;
            if (oprationState == OprationState.OPEN) {
                dto.setUseCount(count);
            }

            if (oprationState == OprationState.LOGOUT) {
                dto.setLogoutCount(count);
            }

            if (oprationState == OprationState.REPORT) {
                dto.setReportCount(count);
            }

            if (oprationState == OprationState.STOP) {
                dto.setStopCount(count);
            }

        }

        dto.setCount(sum);

    }

    /**
     * 根据不同备案理性进行list 搜索
     *
     * @param stampRecord
     */
    public List<StampRecord> countStampRecordWorkType(StampRecord stampRecord) {

        List<StampRecord> all = new ArrayList<StampRecord>();

        for (StampWorkType workType : StampWorkType.values()) {

            stampRecord.setWorkType(workType);

            List<StampRecord> list = stampRecordDao.findCountList(stampRecord);

            all.addAll(list);
        }

        return all;
    }


    //将实体类转换成json格式
    public List<Stamp> dealStampRecordJsonStamp(String stampJsonStr) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        //List类型
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Stamp.class);
        //Map类型
        mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, Stamp.class);

        List<Stamp> list = (List<Stamp>) mapper.readValue(stampJsonStr, javaType);

        return list;
    }


    public void findStampListUseCompany(List<Stamp> list) {
        for (Stamp check : list) {
            Company useCompany = check.getUseComp();
            if (useCompany != null) {
                useCompany.setCompType(CompanyType.USE);
                useCompany = companyDao.get(useCompany);
                check.setUseComp(useCompany);
            }
        }

    }

    public void findStampListNowMakeCompany(List<Stamp> list) {

        for (Stamp check : list) {
            Company makeComp = check.getNowMakeComp();
            makeComp.setCompType(CompanyType.MAKE);
            makeComp = companyDao.get(makeComp);
            if (makeComp != null) {
                makeComp.setArea(areaDao.get(makeComp.getArea()));
                check.setNowMakeComp(makeComp);
            }


        }
    }

    /*public void useCompanyCountDeal(List<Company> list,Company currentMakeCompany){
        for(Company useCompany :list){
            Stamp checkStamp = new Stamp();

            if(currentMakeCompany!=null){
                checkStamp.setMakeComp(currentMakeCompany);
            }

            checkStamp.setUseComp(useCompany);
            //物理印章
            checkStamp.setStampShape(StampShape.PHYSTAMP.getKey());
            int phyStampsCount = stampDao.countStamp(checkStamp);
            //电子印章
            checkStamp.setStampShape(StampShape.ELESTAMP.getKey());
            int eleStampsCount = stampDao.countStamp(checkStamp);

            int allCount = phyStampsCount+eleStampsCount;

            CountUseCompanyStampDTO useCompanyDTO = new CountUseCompanyStampDTO();
            useCompanyDTO.setAllCount(allCount);

            //物理印章统计
            StampStateStampCountDTO phyStampDTO = new StampStateStampCountDTO();
            phyStampDTO.setCount(phyStampsCount);
            checkStamp.setStampShape(StampShape.PHYSTAMP.getKey());
            countOprationStateStamp(checkStamp,phyStampDTO);

            //电子印章统计
            StampStateStampCountDTO eleStampDTO = new StampStateStampCountDTO();
            eleStampDTO.setCount(eleStampsCount);
            checkStamp.setStampShape(StampShape.ELESTAMP.getKey());
            countOprationStateStamp(checkStamp,eleStampDTO);

            useCompanyDTO.setPhyStamps(phyStampDTO);
            useCompanyDTO.setEleStamps(eleStampDTO);
            useCompany.setCountUseCompanyStampDTO(useCompanyDTO);

        }
    }*/
    //统计印章数量细节
    public void useCompanyCountDeal(List<Company> list){
        for(Company useCompany : list){

            Stamp checkStamp = new Stamp();
            checkStamp.setUseComp(useCompany);

            //物理印章统计
            checkStamp.setStampShape(StampShape.PHYSTAMP.getKey());
            List<Map<String,String>> phyStampsStateCountList = stampDao.countStamp2(checkStamp);
            StampStateStampCountDTO phyStampDTO = new StampStateStampCountDTO();
            int phylogoutCount = 0;

            for (Map<String,String> phyStampsStateCount : phyStampsStateCountList){
                if ("OPEN".equals(phyStampsStateCount.get("useState"))){
                    phyStampDTO.setUseCount(Integer.parseInt(String.valueOf(phyStampsStateCount.get("COUNT"))));
                }
                if ("STOP".equals(phyStampsStateCount.get("useState"))){
                    phyStampDTO.setStopCount(Integer.parseInt(String.valueOf(phyStampsStateCount.get("COUNT"))));
                }
                if ("REPORT".equals(phyStampsStateCount.get("useState"))){
                    phyStampDTO.setReportCount(Integer.parseInt(String.valueOf(phyStampsStateCount.get("COUNT"))));
                }
                //补刻、变更、缴销都属于已缴销
                if ("LOGOUT".equals(phyStampsStateCount.get("useState"))
                        || "CHANGE".equals(phyStampsStateCount.get("useState"))
                        || "REPAIR".equals(phyStampsStateCount.get("useState"))){
                    phylogoutCount = phylogoutCount + Integer.parseInt(String.valueOf(phyStampsStateCount.get("COUNT")));
                }
            }

            phyStampDTO.setLogoutCount(phylogoutCount);
            int phyAllCount = phyStampDTO.getUseCount() + phyStampDTO.getStopCount() +
                    phyStampDTO.getReportCount() +phyStampDTO.getLogoutCount();
            phyStampDTO.setCount(phyAllCount);

            //电子印章统计
            checkStamp.setStampShape(StampShape.ELESTAMP.getKey());
            List<Map<String,String>> eleStampsStateCountList = stampDao.countStamp2(checkStamp);
            StampStateStampCountDTO eleStampDTO = new StampStateStampCountDTO();
            int elelogoutCount = 0;

            for (Map<String,String> eleStampsStateCount : eleStampsStateCountList){
                if ("OPEN".equals(eleStampsStateCount.get("useState"))){
                    eleStampDTO.setUseCount(Integer.parseInt(String.valueOf(eleStampsStateCount.get("COUNT"))));
                }
                if ("STOP".equals(eleStampsStateCount.get("useState"))){
                    eleStampDTO.setStopCount(Integer.parseInt(String.valueOf(eleStampsStateCount.get("COUNT"))));
                }
                if ("REPORT".equals(eleStampsStateCount.get("useState"))){
                    eleStampDTO.setReportCount(Integer.parseInt(String.valueOf(eleStampsStateCount.get("COUNT"))));
                }
                //补刻、变更、缴销都属于已缴销
                if ("LOGOUT".equals(eleStampsStateCount.get("useState"))
                        || "CHANGE".equals(eleStampsStateCount.get("useState"))
                        || "REPAIR".equals(eleStampsStateCount.get("useState"))){
                    elelogoutCount = elelogoutCount + Integer.parseInt(String.valueOf(eleStampsStateCount.get("COUNT")));
                }
            }

            eleStampDTO.setLogoutCount(elelogoutCount);
            int eleAllCount = eleStampDTO.getUseCount() + eleStampDTO.getStopCount() +
                    eleStampDTO.getReportCount() +eleStampDTO.getLogoutCount();
            eleStampDTO.setCount(eleAllCount);

            //总数量
            int AllCount = phyAllCount + eleAllCount;

            CountUseCompanyStampDTO useCompanyDTO = new CountUseCompanyStampDTO();
            useCompanyDTO.setPhyStamps(phyStampDTO);
            useCompanyDTO.setEleStamps(eleStampDTO);
            useCompanyDTO.setAllCount(AllCount);
            useCompany.setCountUseCompanyStampDTO(useCompanyDTO);

        }
    }


    protected void changeExcelExportDTO(List<StampCountExcelByMakeCompanyDTO> excelList, List<Stamp> list){

        for(Stamp stamp:list){

            StampCountExcelByMakeCompanyDTO dto = new StampCountExcelByMakeCompanyDTO();

            dto.setStampType(stamp.getStampType());
            dto.setStampShape(stamp.getStampShape());
            if(stamp.getUseComp()==null||"".equals(stamp.getUseComp())){
                dto.setCompanyName("无");
            }else {
                dto.setCompanyName(stamp.getUseComp().getCompanyName());
            }

            dto.setDeliveryDate(stamp.getDeliveryDate());
            dto.setRecordDate(stamp.getRecordDate());
            dto.setMakeDate(stamp.getMakeDate());
            dto.setStampCode(stamp.getStampCode());
            dto.setStampState(stamp.getStampState());

            excelList.add(dto);
        }



    }


    /**
     * 根据查询条件、区域过滤获得导出公司信息到excel的数据
     * @param dto
     * xucaikai 2018.06.01
     * @return
     */
    public List<CompanyCountExcelByPoliceDTO> exportCompany(Page<Company> page,CompanyCountDTO dto){

        Company checkCompany = dto.getCompany();

        checkCompany.setCompType(CompanyType.USE);

        //区域过滤
        if(dto.getCompany().getArea()!=null&&dto.getCompany().getArea().getId()!=null&&(!dto.getCompany().getArea().getId().equals(""))){
            checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(areaDao.get(dto.getCompany().getArea()), "a3"));
        }else{
            Police police = UserUtils.getUser().getPoliceInfo();
            checkCompany.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(police.getArea(), "a3"));
        }
        checkCompany.setPage(page);

        List<Company> companys = companyDao.countCompanyPage(checkCompany);

        makeCompanyCountService.useCompanyCountDeal(companys);

        List<CompanyCountExcelByPoliceDTO> excelList = new ArrayList<CompanyCountExcelByPoliceDTO>();

        setExcelExportDTO(excelList,companys);

        return excelList;
    }

    /**
     * @author 许彩开
     * @TODO (注：将数据写入导出工具类中)
     * @param excelList
     * @param companys
     * @DATE: 2018\6\1 0001 14:55
     */

    public void setExcelExportDTO(List<CompanyCountExcelByPoliceDTO> excelList, List<Company> companys){

        for(Company company:companys){

            CompanyCountExcelByPoliceDTO dto = new CompanyCountExcelByPoliceDTO();

            dto.setUseCompanyName(company.getCompanyName());
            dto.setCompanyPhone(company.getCompPhone());
            dto.setLegalName(company.getLegalName());
            dto.setLegalPhone(company.getLegalPhone());
            dto.setSoleCode(company.getSoleCode());
            //印章总数
            dto.setStampCount(String.valueOf(company.getCountUseCompanyStampDTO().getAllCount()));
            //物理印章
            dto.setPhysicsCount(String.valueOf(company.getCountUseCompanyStampDTO().getPhyStamps().getCount()));
            dto.setPhysicsUseCount(String.valueOf(company.getCountUseCompanyStampDTO().getPhyStamps().getUseCount()));
            dto.setPhysicsStopCount(String.valueOf(company.getCountUseCompanyStampDTO().getPhyStamps().getStopCount()));
            dto.setPhysicsReportCount(String.valueOf(company.getCountUseCompanyStampDTO().getPhyStamps().getReportCount()));
            dto.setPhysicsLogoutCount(String.valueOf(company.getCountUseCompanyStampDTO().getPhyStamps().getLogoutCount()));

            //电子印章
            dto.setEleCount(String.valueOf(company.getCountUseCompanyStampDTO().getEleStamps().getCount()));
            dto.setEleUseCount(String.valueOf(company.getCountUseCompanyStampDTO().getEleStamps().getUseCount()));
            dto.setEleStopCount(String.valueOf(company.getCountUseCompanyStampDTO().getEleStamps().getStopCount()));
            dto.setEleReportCount(String.valueOf(company.getCountUseCompanyStampDTO().getEleStamps().getReportCount()));
            dto.setEleLogoutCount(String.valueOf(company.getCountUseCompanyStampDTO().getEleStamps().getLogoutCount()));

            excelList.add(dto);
        }

    }



}
