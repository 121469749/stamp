package com.thinkgem.jeesite.modules.stamp.service.stamprecord;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampOperationDao;
import com.thinkgem.jeesite.modules.stamp.dto.useCompany.InsertOperationDto;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampOperation;
import com.thinkgem.jeesite.modules.stamp.vo.useCompany.UserAndStampVo;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 印章操作历史Service
 * Created by sjk on 2017-05-22.
 */
@Service
@Transactional(readOnly = true)
public class StampOperationService {

    @Autowired
    private StampOperationDao stampOperationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StampDao stampDao;

    @Autowired
    private StampLogService stampLogService;

    /**
     * 物理印章使用录入
     *
     * @param condition
     * @param webData
     * @return
     */
    @Transactional(readOnly = false)
    public Condition insertPhyStampOpration(Condition condition, InsertOperationDto webData, HttpServletRequest request) {

        //校验印章
        if (StringUtils.isBlank(webData.getStampId())) {

            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("请选择物理印章名称");

            return condition;

        }

        //校验操作用户
        if (StringUtils.isBlank(webData.getUsername())) {

            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("请填写操作者名字");

            return condition;
        }

        //校验使用用户
        if (StringUtils.isBlank(webData.getApplyUsername())) {

            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("请填写使用者名字");

            return condition;
        }

        //校验使用时间
        if (StringUtils.isBlank(webData.getUseDate())) {

            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("请选择使用时间");

            return condition;
        }

        //校验使用用途
        if (StringUtils.isBlank(webData.getRemarks())) {

            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("请填写使用用途");

            return condition;
        }

        try {
            StampOperation stampOperation = new StampOperation();

            //物理印章
            Stamp stamp = new Stamp();
            stamp.setId(webData.getStampId());
            stamp.setStampShape(StampShape.PHYSTAMP.getKey());
            stamp = stampDao.get(stamp);

            stampOperation.setStamp(stamp);

            //操作用户
            User u = new User();
            u.setName(webData.getUsername());
            u.setCompany(new Company(webData.getCompanyId()));
            u.setUserType(UserType.USE);
            //根据操作用户名和操作企业id查找对应操作用户
            u = userDao.findUserByNameAndComPanyId(u);
            stampOperation.setUser(u);

            //使用用户
            stampOperation.setApplyUsername(webData.getApplyUsername());

            //操作类型
            stampOperation.setOperaType("1");

            //使用用途
            stampOperation.setRemarks(webData.getRemarks());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


            //使用时间
            stampOperation.setUseDate(df.parse(webData.getUseDate()));

            stampOperation.preInsert();
            stampOperationDao.insert(stampOperation);

            condition.setCode(Condition.SUCCESS_CODE);
            condition.setMessage("保存成功");

            //录入日志
            User user = new User();
            user.setId(webData.getUserId());
            user.setUserType(UserType.USE);

            user = userDao.get(user);


            //保存日志
          //  String title = "录入 " + user.getName() + " 使用了物理印章: " + stamp.getStampName();

           // stampLogService.insertLog(title);

        } catch (Exception e) {
            e.printStackTrace();
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("系统繁忙");
            return condition;
        }

        return condition;
    }

    /**
     * 查询公司用户和物理印章
     *
     * @param companyId
     * @return
     */
    @Transactional(readOnly = true)
    public UserAndStampVo showUserAndStamp(String companyId) {

        //todo
        User checkUser = new User();
        checkUser.setUserTypeId(companyId);
        checkUser.setUserType(UserType.USE);

        List<User> userList = userDao.findAllUserInCompany(checkUser);
        Stamp stamp = new Stamp();

        stamp.setUseComp(new Company(companyId, CompanyType.USE));

        //物理印章
        stamp.setStampShape(StampShape.PHYSTAMP.getKey());

        List<Stamp> stampList = stampDao.listStampByComId(stamp);

        UserAndStampVo userAndStamp = new UserAndStampVo();
        userAndStamp.setStampList(stampList);

        if (UserUtils.getUser().getIsSysrole().equals("1")) {
            userAndStamp.setUserList(userList);
        } else {
            List<User> user = new ArrayList<User>();
            user.add(UserUtils.getUser());
            userAndStamp.setUserList(user);
        }


        return userAndStamp;
    }

    /**
     * 查询印章使用日志
     * 用章公司员工仅能看到自己的操作历史
     * 用章公司管理员能看到所有人历史
     *
     * @param stampOperation
     * @return
     */
    @Transactional(readOnly = true)
    public Page<StampOperation> showStampHistory(Page<StampOperation> page, StampOperation stampOperation) {

        if (stampOperation.getUser() == null) {
            stampOperation.setUser(new User());
        }

        //判断当前登陆用户是否为用章公司员工
        if (UserUtils.getUser().getIsSysrole().equals("0")) {
            stampOperation.setUser(UserUtils.getUser());
        }

        stampOperation.getUser().setUserType(UserType.USE);

        stampOperation.setPage(page);

        List<StampOperation> historyList = stampOperationDao.findOperation(stampOperation);

        page.setList(historyList);

        return page;
    }

    /**
     * 根据印章类型（物理、电子）查询盖章列表
     */
    @Transactional(readOnly = true)
    public Page<StampOperation> showUseHistory(Page<StampOperation> page, StampOperation stampOperation) {

        // 设置默认时间范围，默认当前月
        if (stampOperation.getBeginDate() == null) {
            stampOperation.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (stampOperation.getEndDate() == null) {
            stampOperation.setEndDate(DateUtils.addMonths(stampOperation.getBeginDate(), 1));
        }

        stampOperation.getStamp().setUseComp(new Company(UserUtils.getUser().getUserTypeId()));

        stampOperation.setPage(page);

        if (stampOperation.getUser() == null) {
            stampOperation.setUser(new User());
        }

        stampOperation.getUser().setUserType(UserType.USE);

        List<StampOperation> stampOperationList = stampOperationDao.findUseHistoryByShape(stampOperation);

        page.setList(stampOperationList);

        return page;
    }
}
