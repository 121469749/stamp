package com.thinkgem.jeesite.modules.sys.security.xss;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper{
    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(values != null){
            for(int i = 0;i < values.length;i++){
                values[i] = dealString(values[i]);
            }
        }
        return values;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return dealString(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return dealString(value);
    }

    private String dealString(String value){
        if(value != null){
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replace("\\)", "&#41;");
            value = value.replaceAll("'", "&#39;");
            value = value.replaceAll("eval\\((.*)\\)", "");
            value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
                    "\"\"");
            value = value.replace("script", "");
        }
        return value;
    }
}
