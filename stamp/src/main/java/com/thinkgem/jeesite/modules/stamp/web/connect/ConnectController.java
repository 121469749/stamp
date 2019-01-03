package com.thinkgem.jeesite.modules.stamp.web.connect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/8/29.
 */
@Controller
@RequestMapping(value="${adminPath}/connect")
public class ConnectController {


    @RequestMapping(value="/index")
    public String index(){
        return "modules/jsps/contact";
    }

}
