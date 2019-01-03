package com.thinkgem.jeesite.modules.stamp.service.money;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.stamp.common.Condition;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.dao.company.CompanyDao;
import com.thinkgem.jeesite.modules.stamp.dao.moneySetting.MoneySettingDao;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.stamp.exception.moneySetting.MoneySettingAddException;
import com.thinkgem.jeesite.modules.stamp.exception.moneySetting.MoneySettingException;
import com.thinkgem.jeesite.modules.stamp.exception.moneySetting.MoneySettingUpdateException;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Locker on 2017/7/17.
 */
@Service
public class MoneySettingService {

    @Autowired
    private MoneySettingDao moneySettingDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private DictDao dictDao;

    @Autowired
    private CompanyDao companyDao;

    public Company getCurrentArea(){

        User user = UserUtils.getUser();

        Company company = user.getCompanyInfo();

        return company;
    }


    public List<Dict> findStampTextureDict(){

        Dict dict = new Dict();

        dict.setType("stampTexture");

        List<Dict> dicts= dictDao.findList(dict);

        return dicts;
    }

    @Transactional(readOnly = false)
    public Condition saveMoneySettingList(List<MoneySetting> moneySettingList){

        Condition condition = new Condition();

        try {

            for(MoneySetting moneySetting :moneySettingList){

                System.out.println(moneySetting.getId() == null);

                System.out.println("".equals(moneySetting.getId()));

                if(moneySetting.getId()==null || "".equals(moneySetting.getId())){

                    moneySetting.setId(IdGen.uuid());

                    moneySetting.setCreateDate(new Date());

                    moneySetting.setUpdateDate(new Date());

                    moneySetting.setCreateUser(UserUtils.getUser());

                    moneySetting.setUpdateUser(UserUtils.getUser());

                    moneySettingDao.insert(moneySetting);
//                    if(moneySettingDao.insert(moneySetting) == 0){
//
//                        throw  new MoneySettingAddException("insert new MoneySetting Error!");
//                    }

                }else{

                    moneySetting.setUpdateUser(UserUtils.getUser());

                    moneySetting.setUpdateDate(new Date());

                    moneySettingDao.update(moneySetting);

                }


            }
            condition.setCode(Condition.SUCCESS_CODE);

            condition.setMessage("保存收费规则成功!");

        }catch (MoneySettingAddException e){

            e.printStackTrace();

            condition.setCode(Condition.ERROR_CODE);

            condition.setMessage("新增失败!\n请检查数据是否符合格式!");

        }catch (MoneySettingUpdateException e){

            e.printStackTrace();

            condition.setCode(Condition.ERROR_CODE);

            condition.setMessage("更新失败!\n请检查数据是否符合格式!");

        }catch (MoneySettingException e){

            e.printStackTrace();

            condition.setCode(Condition.ERROR_CODE);

            condition.setMessage("保存失败!\n请联系管理员修正!");

        }catch (Exception e){

            e.printStackTrace();

        }finally {


            return condition;
        }


    }


    /**
     *
     * 经销商对经销商
     *
     */
    @Transactional(readOnly = true)
    public List<MoneySetting> findMoneySettingByCompanyId(String companyId){

        Company payCompany = new Company(companyId);

        payCompany.setCompType(CompanyType.AGENCY);

        payCompany = companyDao.get(payCompany);

        Company tollCompany = UserUtils.getUser().getCompanyInfo();

        MoneySetting moneySetting = new MoneySetting();

        moneySetting.setCompany(tollCompany);

        moneySetting.setArea(payCompany.getArea());

        List<MoneySetting> currentAgenyMoneySettings = moneySettingDao.findList(moneySetting);

        if(currentAgenyMoneySettings.size() == 0){

            currentAgenyMoneySettings =new ArrayList<MoneySetting>();

            return currentAgenyMoneySettings;

        }else{

            return currentAgenyMoneySettings;
        }


    }

    /**
     *
     * 经销商对经销商
     *
     */
    @Transactional(readOnly = true)
    public List<MoneySetting> findMoneySettingByArea(Area area){

        Company currentAgeny = UserUtils.getUser().getCompanyInfo();

        MoneySetting moneySetting = new MoneySetting();

        moneySetting.setCompany(currentAgeny);

        moneySetting.setArea(area);

        List<MoneySetting> currentAgenyMoneySettings = moneySettingDao.findList(moneySetting);

        if(currentAgenyMoneySettings.size() == 0){

            currentAgenyMoneySettings =new ArrayList<MoneySetting>();

            return currentAgenyMoneySettings;

        }else{

            return currentAgenyMoneySettings;
        }


    }


    /**
     * 只有刻章点用户进入收费设定才会被调用
     * @return
     */
    public List<MoneySetting> findCurrentMakeCompanyMoneySetting(){

        Company makeCompany = UserUtils.getUser().getCompanyInfo();

        MoneySetting moneySetting = new MoneySetting();

        moneySetting.setCompany(makeCompany);

        List<MoneySetting> moneySettings = moneySettingDao.findList(moneySetting);

        if(moneySettings.size() == 0){

            moneySettings =new ArrayList<MoneySetting>();

            return moneySettings;

        }else{

            return moneySettings;
        }

    }

}
