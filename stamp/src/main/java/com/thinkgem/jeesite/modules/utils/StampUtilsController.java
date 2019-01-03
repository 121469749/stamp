package com.thinkgem.jeesite.modules.utils;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @Auther: linzhibao
 * @Date: 2018-08-27 14:29
 * @Description:
 */
@Controller
@RequestMapping(value="${adminPath}/stampUtil")
public class StampUtilsController extends BaseController{


    @RequestMapping(value="/getDictLabel")
    @ResponseBody
    public Dict getDictLabel(String value, String type, String defaultValue){
        Dict dirt = new Dict();
        dirt.setLabel(DictUtils.getDictLabel(value,type,defaultValue));
        return dirt;
    }

    @RequestMapping(value="/getDictList")
    @ResponseBody
    public List<Dict> getDictList(String type){
        return DictUtils.getDictList(type);
    }
}
