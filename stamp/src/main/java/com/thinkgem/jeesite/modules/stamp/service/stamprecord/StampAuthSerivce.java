package com.thinkgem.jeesite.modules.stamp.service.stamprecord;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampAuthState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.UserType;
import com.thinkgem.jeesite.modules.stamp.dao.stamp.StampDao;
import com.thinkgem.jeesite.modules.stamp.dao.stamprecord.StampAuthDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampAuth;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 用章单位-印章授权信息Service
 * Created by sjk on 2017-05-20.
 */
@Service
@Transactional(readOnly = true)
public class StampAuthSerivce extends CrudService<StampAuthDao, StampAuth> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StampDao stampDao;

    @Autowired
    private StampLogService stampLogService;

    /**
     * 更改授权状态
     * @param stampAuth
     * @return
     * true:修改成功
     * false:修改失败
     */
    @Transactional(readOnly = false)
    public Condition changeAuthState(HttpServletRequest request, StampAuth stampAuth, String state) throws Exception {

        stampAuth.getUser().setUserType(UserType.USE);

        stampAuth.getStamp().setStampShape(StampShape.ELESTAMP.getKey());

        String auth = null;

        if (state.equals("1")) {
            stampAuth.setStampAuthState(StampAuthState.AUTH_TRUE);
            auth = "授权";

        } else {
            stampAuth.setStampAuthState(StampAuthState.AUTH_FALSE);
            auth = "取消授权";
        }

        int result = -1;

        //判断授权还是取消授权
        //如果是取消授权
        if (stampAuth.getStampAuthState() == StampAuthState.AUTH_FALSE) {
            //记录取消授权的时间
            stampAuth.setEndTime(new Date());
            stampAuth.preUpdate();
            result = dao.changeAuthState(stampAuth);

        } else {
            /*Stamp stamp = new Stamp();
            stamp = stampAuth.getStamp();
            stamp.preUpdate();

            //一个印章只能授权一位用户使用
            //授权时需要先取消之前的授权
            dao.closeAllAuthByStamp(stamp);*/

            stampAuth.getUser().setUserType(UserType.USE);

            StampAuth temp = dao.findUsingByStamp(stampAuth);

            //如果temp不为空，则代表有人正在使用该印章
            if (temp != null) {
                return new Condition(Condition.ERROR_CODE, "授权失败！ " + temp.getUser().getName() + " 正在使用该印章");
            }

            //记录授权时间
            stampAuth.setStartTime(new Date());
            stampAuth.preUpdate();
            result = dao.changeAuthState(stampAuth);
        }

        if (result > 0) {

            User user = new User();

            user = userDao.get(stampAuth.getUser());

            Stamp stamp = new Stamp();
            stamp = stampDao.get(stampAuth.getStamp());

            String title = auth + " " + user.getName() + " 使用 " + stamp.getStampName();

            //记录日志
            stampLogService.insertLog(title);

            return new Condition(Condition.SUCCESS_CODE, "更改成功");

        } else {
            return new Condition(Condition.SUCCESS_CODE, "更改失败");
        }
    }

    /**
     * 根据印章id查询印章授权情况
     *
     * @param stampAuth
     * @return
     */
    public Page<StampAuth> showAuthState(Page<StampAuth> page, StampAuth stampAuth) {

        if (stampAuth.getStamp().getUseComp() == null) {
            stampAuth.getStamp().setUseComp(new Company(UserUtils.getUser().getUserTypeId(), CompanyType.USE));
        }

        User user = new User();

        user.setUserType(UserType.USE);

        stampAuth.setUser(user);

        stampAuth.setPage(page);

        List<StampAuth> stampAuthList = dao.findStampAuth(stampAuth);

        page.setList(stampAuthList);

        return page;
    }

    /**
     * 为印章权限列表添加用户
     * @param stampAuth
     * @return
     */
    @Transactional(readOnly = false)
    public Condition addUserToAuth(StampAuth stampAuth) {

        Condition condition = new Condition();

        //默认没有权限
        stampAuth.setStampAuthState(StampAuthState.AUTH_FALSE);

        //查询权限表中是否已有记录
        if (dao.judgeExitStampAndUser(stampAuth) == 0) {

            try {
                super.save(stampAuth);
                condition.setCode(Condition.SUCCESS_CODE);
                condition.setMessage("添加成功");
                return condition;

            } catch (Exception e) {
                e.printStackTrace();
                condition.setCode(Condition.ERROR_CODE);
                condition.setMessage("系统繁忙");
                return condition;
            }

        } else {
            condition.setCode(Condition.ERROR_CODE);
            condition.setMessage("用户已在列表中");
            return condition;
        }
    }

    /**
     * 查找未在授权表中的用户
     * @param stampAuth
     * @return
     */
    public List<User> findNoAuthUser(StampAuth stampAuth) {

        if (stampAuth.getUser() == null) {
            stampAuth.setUser(new User());
        }

        stampAuth.getUser().setUserTypeId(UserUtils.getUser().getUserTypeId());

        stampAuth.getUser().setUserType(UserType.USE);

        return dao.findNoAuthUser(stampAuth);
    }

}
