package com.thinkgem.jeesite.modules.stamp.service.police;

import com.thinkgem.jeesite.common.businessUtils.BusinessValidateUtil;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DataScopeFilterUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.dao.AttachmentDao;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.service.AttachmentService;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dto.police.MakeComDTO;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.licence.Licence;
import com.thinkgem.jeesite.modules.stamp.entity.police.Police;
import com.thinkgem.jeesite.modules.stamp.exception.police.RegisterMakeComException;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.AreaException;
import com.thinkgem.jeesite.modules.stamp.exception.stampMake.SoleCodeException;
import com.thinkgem.jeesite.modules.stamp.service.licence.LicenceService;
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
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by hjw-pc on 2017/5/20.
 * 公安单位对刻章单位操作
 */
@Service
@Transactional(readOnly = true)
public class PoliceCompanyService {
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private SystemService systemService;
    @Autowired
    private OfficeDao officeDao;
    @Autowired
    private PoliceLicenseService policeLicenseService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private LicenceService licenceService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private CertificateService certificateService;


    //18位社会信用统一码表达式匹配
    private static String newCodePattern1 = "[1-9ABCDEFGV]{1}[1239]{1}[0-9]{6}[0-9A-Z]{9}[0-9ABCDEFGHJKLMNPQRTCUWXY]{1}";

    //10位组织结构代码正则匹配
    private static String newCodePattern2 = "[0-9A-Z]{8}-[0-9A-Z]{1}";

    //9位组织结构代码正则匹配
    private static String newCodePattern3 = "[0-9A-Z]{8}[0-9A-Z]{1}";

//    /**
//     * @param [company]
//     * @return java.util.List<com.thinkgem.jeesite.modules.stamp.units.entity.company.Company>
//     * @author hjw
//     * @description 查询mkcompanyList附带LICENSELIST
//     * @date 2017/5/20
//     */
//    public List<Company> findListWithLicenseList(Company company) {
//        return companyDao.findListWithLicenseList(company);
//    }

    /**
     * 更新企业运行状态
     *
     * @param company
     */
    @Transactional(readOnly = false)
    public void updateComState(Condition condition, Company company) {

        if (company.getCompType() == CompanyType.MAKE) {
            try {
                companyDao.updateComState(company);

                System.out.println("aaaa");

                systemService.updateLoginFlagByCompany(company);

                User checkUser = new User();

//                checkUser.setCompanyInfo(new Company(company.getId()));

                checkUser.setUserType(UserType.MAKE);
                checkUser.setUserTypeId(company.getId());


                // 清除当前用户缓存
                List<User> list = userDao.findUsersByUserTypeId(checkUser);
                for (User user : list) {
                    UserUtils.clearCache(user);
                }

                condition.setCode(Condition.SUCCESS_CODE);
                condition.setMessage("success");
            } catch (Exception e) {
                condition.setCode(Condition.ERROR_CODE);
                condition.setMessage("系统繁忙");
            }

        } else {
            condition.setCode(Condition.NOALLOW_CODE);

        }

    }


    /**
     *
     * @return void
     * @author hjw
     * @description 撤销企业
     * @date 2017/5/22
     */
    @Transactional(readOnly = false)
    public void deleteMakeCom(Condition condition, Company company) {
        if (company.getCompType() == CompanyType.MAKE) {

            try {
                companyDao.delete(company);

                User checkUser = new User();

                checkUser.setUserType(UserType.MAKE);

                checkUser.setUserTypeId(company.getId());

                userDao.deleteByUserTypeId(checkUser);


                // 清除当前用户缓存
                List<User> list = userDao.findUsersByUserTypeId(checkUser);
                for (User user : list) {
                    UserUtils.clearCache(user);
                }
                condition.setCode(Condition.SUCCESS_CODE);

                condition.setMessage("success");
            } catch (Exception e) {
                condition.setCode(Condition.ERROR_CODE);
                condition.setMessage("系统繁忙");
            }

        } else {
            condition.setCode(Condition.NOALLOW_CODE);

        }
    }

    /*
     *@author hjw
     *@description  刻章录入，验证统一码,系统生成user
     *@param [condition, company, multipartFile[]]
     *@return com.thinkgem.jeesite.modules.stamp.util.other.Condition
     *@date 2017/5/22
     */
    @Transactional(readOnly = false)
    public void saveMakeCom(Condition condition, MakeComDTO makeComDTO, MultipartFile[] multipartFile) {


        //make sure CompanyType is mk
        makeComDTO.getCompany().setCompType(CompanyType.MAKE);
        makeComDTO.getCompany().setCompPhone(makeComDTO.getCompany().getLegalPhone());
        makeComDTO.getCompany().setLegalCertType("1");
        //make sure companyState is checking
        makeComDTO.getCompany().setCompState(CompanyState.CHECKING);

        List<String> filetypes = makeComDTO.getFileType();

        String soleCode = makeComDTO.getCompany().getSoleCode().trim();


        Area area = null;

        try {
            //验证统一码
            Company company = companyDao.getCompanyBysoleCodeAndCompName(soleCode,makeComDTO.getCompany().getCompanyName(), CompanyType.MAKE);
            if (company != null) {
                throw new RegisterMakeComException("该制章单位已存在！");
            }

            //判断字符串长度为多少
            // 正则表达式匹配规则
            //并且通过规则计算此唯一码是否正确
            //优先处理区域
            //从唯一码中获取区域
            if (soleCode.trim().length() == 18 && soleCode.matches(newCodePattern1)) {

                if (!BusinessValidateUtil.checkSoleCode(soleCode, 18)) {

                    throw new SoleCodeException("unified social credit code Error!");

                }

                //获取组织结构代码
                String areaCode = soleCode.substring(2, 8);

                Area areaByCode = areaDao.getAreaByCode(areaCode);
                //   area = areaDao.getAreaByCode(areaCode);
                area = areaDao.get(new Area(UserUtils.getUser().getPoliceInfo().getArea().getId()));
                if (area == null) {

                    throw new SoleCodeException("unified social credit code Error!");

                }

                if (!(area.judgeAreaCode(areaByCode) || area.containArea(areaByCode))) {

                    throw new AreaException("Area isn't right!");

                }

                makeComDTO.getCompany().setArea(area);


            } else if (soleCode.trim().length() == 10 && soleCode.matches(newCodePattern2)) {

                if (!BusinessValidateUtil.checkSoleCode(soleCode.replace("-", "").trim(), 9)) {

                    throw new SoleCodeException("unified social credit code Error!");
                }

                area = UserUtils.getUser().getPoliceInfo().getArea();


            } else if (soleCode.trim().length() == 9 && soleCode.matches(newCodePattern3)) {

                if (!BusinessValidateUtil.checkSoleCode(soleCode.replace("-", "").trim(), 9)) {

                    throw new SoleCodeException("unified social credit code Error!");

                }

                area = UserUtils.getUser().getPoliceInfo().getArea();

            } else if (soleCode.trim().length() == 15 && soleCode.matches(BusinessValidateUtil.newCodePattern4)) {

                if (!BusinessValidateUtil.checkSoleCode(soleCode.trim(), 15)) {
                    throw new SoleCodeException("unified social credit code Error!");


                }

                //到了这里说明码正确
                //截取社会代码中的统一码
                //0-5位
                String areaCode = soleCode.substring(0, 6);

                Area areaByCode = areaDao.getAreaByCode(areaCode);
                //当前用户的area
                area = areaDao.get(new Area(UserUtils.getUser().getPoliceInfo().getArea().getId()));

                if (areaByCode == null) {
                    throw new SoleCodeException("unified social credit code Error!");
                }

                //判断 areaBycode 与当前用章单位的area的 关系
                //若为同一area 或者 当前area 为areabyCode的子级时，通过
                //否则则抛出异常
                if (!(area.judgeAreaCode(areaByCode) || area.containArea(areaByCode))) {

                    throw new AreaException("Area isn't right!");

                }
                //设置制章公司区域
                makeComDTO.getCompany().setArea(area);
            } else {

                throw new SoleCodeException("unified social credit code Error!");

            }

            // 生成单位编码（行政区划+6位递增序号）
            String CodeCount = "000000" + String.valueOf(companyDao.getSequenceNextVal(area.getCode(),CompanyType.MAKE.getKey()));
            CodeCount = CodeCount.substring(CodeCount.length()-6);
            String companyCode = area.getCode() + CodeCount;
            makeComDTO.getCompany().setCompanyCode(companyCode);

            //插入后才能确定id
            makeComDTO.getCompany().preInsert();
            //系统创建用户
            createNewUser(makeComDTO.getCompany(), area);
            companyDao.insert(makeComDTO.getCompany());


            //附件处理 start

            List<Attachment> attachments = attachmentService.setUUIDList(makeComDTO.getFileType());

            attachmentService.saveAttachmentsInHosts(attachments, multipartFile, makeComDTO.getCompany().getCompanyName());

            attachmentDao.insertAttachmentList(attachments);

            //附件处理 end


            //init许可申请
            Licence licence = policeLicenseService.newLicense(WorkType.OPEN, makeComDTO.getCompany());
            //处理附件
            String attachJson = JsonMapper.toJsonString(attachments);
            licence.setAttachs(attachJson);


            licence.setMakeComp(makeComDTO.getCompany());
            licenceService.save(licence);
            //更新公司最新状态


            condition.setCode(Condition.SUCCESS_CODE);

        } catch (SoleCodeException e) {
            condition.setMessage("社会统一码错误\n");
            condition.setCode(Condition.ERROR_CODE);
            //事务回滚
            throw new RegisterMakeComException();
        } catch (RegisterMakeComException re) {
            System.out.println(re.getMessage());
            condition.setMessage(re.getMessage());
            condition.setCode(Condition.ERROR_CODE);

            //事务回滚
            throw new RegisterMakeComException();
        }  catch (AreaException e) {

            e.printStackTrace();

            throw e;

        }catch (Exception e) {
            e.printStackTrace();

            //日志记录
            System.out.println(e.toString());
            //事务回滚
            throw new RuntimeException();
        }

    }

    /*
     *@author hjw
     *@description  系统生成user
     *@param [makeCom, nowArea]
     *@return com.thinkgem.jeesite.modules.sys.entity.User
     *@date 2017/5/22
     */
    private User createNewUser(Company makeCom, Area nowArea) throws RegisterMakeComException {


        //创建组织结构
        Office company = new Office();
        company.setName(makeCom.getCompanyName());
        company.setGrade("1");
        company.setMaster(makeCom.getLegalName());
        company.setAddress(makeCom.getCompAddress());
        company.setZipCode(makeCom.getPostcode());
        company.setPhone(makeCom.getCompPhone());
        company.setArea(nowArea);
        company.setUseable("1");
        Office parent = new Office();
        parent.setId("0");
        company.setParent(parent);
        company.setParentIds("0,");
        company.setType("1");

        company.preInsert();
        //保存组织结构
        officeDao.insert(company);
        //创建用户
        User newUser = new User();

        if (StringUtils.isNotBlank(company.getId())) {
            //绑定companyInfo
            newUser.setUserTypeId(makeCom.getId());
            newUser.setCompanyInfo(makeCom);
            newUser.setOffice(company);
            newUser.setCompany(makeCom);
            newUser.setName(makeCom.getLegalName());
            newUser.setLoginFlag("1");
            newUser.setIsSysrole("1");
            newUser.setUserType(UserType.MAKE);
            newUser.setPhone(makeCom.getLegalPhone());
            //以法人手机作为登录名
            newUser.setLoginName(makeCom.getLegalPhone());
            //判断是否该登录名是否已存在
            User user = systemService.getUserByLoginName(newUser.getLoginName(),UserType.MAKE);
            if (user != null) {

                throw new RegisterMakeComException("该用户名/法人手机号已存在");
            }
            //默认密码暂时为123456
            newUser.setPassword(SystemService.entryptPassword("123456"));


            systemService.saveUser(newUser);

            certificateService.generateCert(newUser);
        }
        //保存 用户角色
        if (StringUtils.isNotBlank(newUser.getId())) {
            //设置角色
            //制章点用户
            //获得制章点用户角色
            Role roleCondition = new Role();
            roleCondition.setEnname("makeCompany-sys");
            roleCondition = roleDao.getByEnname(roleCondition);
            newUser.getRoleList().add(roleCondition);
            userDao.insertUserRole(newUser);

        }

        return newUser;
    }

    /*
     *@author hjw
     *@description 查询companyList
     *@param [company]
     *@return java.util.List<com.thinkgem.jeesite.modules.stamp.entity.company.Company>
     *@date 2017/6/2
     */
    @Transactional(readOnly = true)
    public Page<Company> findMakeComList(Page<Company> page, Company company) {

        //area过滤
        Police police = UserUtils.getUser().getPoliceInfo();
        Area area = police.getArea();
        company.getSqlMap().put("areafilter", DataScopeFilterUtil.areaFilter(area, "a3"));
        company.setPage(page);
        page.setList(companyDao.findMakeComList(company));
        return page;
    }

    /*
      *@author hjw
      *@description 查询company
      *@param [company]
      *@return java.util.List<com.thinkgem.jeesite.modules.stamp.entity.company.Company>
      *@date 2017/6/2
      */
    @Transactional(readOnly = true)
    public Company getMakeCompany(Company company) {
        company.setCompType(CompanyType.MAKE);
        return companyDao.get(company);
    }

/**
 * @author 许彩开
 * @TODO (注：查看详情)
  * @param company
 * @DATE: 2018\1\2 0002 17:13
 */

    @Transactional(readOnly = true)
    public Company getMakeCompany2(Company company) {
        company.setCompType(CompanyType.MAKE);
        return companyDao.getDetails(company);
    }

    /**
     *
     * @param company
     * @return
     * 获取用章单位
     */
    @Transactional(readOnly = true)
    public Company getUseCompany(Company company) {
        company.setCompType(CompanyType.USE);
        return companyDao.get(company);
    }
}
