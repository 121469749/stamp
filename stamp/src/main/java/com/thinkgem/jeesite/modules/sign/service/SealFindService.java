package com.thinkgem.jeesite.modules.sign.service;

import com.thinkgem.jeesite.modules.sign.dao.SealFindDao;
import com.thinkgem.jeesite.modules.sign.entity.Seal;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Service
@Transactional(readOnly = true)
public class SealFindService {

    @Autowired
    private SealFindDao sealFindDao;

    public List<Stamp> findList(String[] ids){

        //List<Seal> seals = new ArrayList<Seal>();
        List<Stamp> stamps =new ArrayList<Stamp>();

        for(String str:ids){

            Seal seal = new Seal(str);

            //seal=sealFindDao.get(seal);
            Stamp stamp=new Stamp();
            stamp=sealFindDao.findStamp(seal);

            if(stamp!=null){
                //seals.add(seal);
                stamps.add(stamp);
            }
        }

        //return seals;
        return stamps;
    }

    public Seal get(String id){

        Seal seal = new Seal(id);

        seal.setUser(UserUtils.getUser());

        return sealFindDao.get(seal);
    }

    public Stamp findStamp(String id){
        Seal seal = new Seal(id);
        seal.setUser(UserUtils.getUser());
        return sealFindDao.findStamp(seal);
    }

    public Stamp findStampById(String id){
        Seal seal = new Seal(id);
        seal.setUser(UserUtils.getUser());
        return sealFindDao.findStampById(seal);
    }

}
