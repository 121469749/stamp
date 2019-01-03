package com.thinkgem.jeesite.modules.stamp.common.Convertor;

/**
 *
 * 时间转换器
 *
 * Created by hjw-pc on 2017/6/23.
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
public class StringToDateConverter implements Converter<String, Date> {

    private String dateFormatPattern;

    private String dateFormatPattern2;
    int i = 1;
    public StringToDateConverter(String dateFormatPattern, String dateFormatPattern2) {
        this.dateFormatPattern = dateFormatPattern;
        this.dateFormatPattern2 = dateFormatPattern2;
    }

    @Override
    public Date convert(String source) {

        if(!StringUtils.hasLength(source)) {
            return null;
        }
        DateFormat df = null;
        if (source.length() > dateFormatPattern2.length()) {
            df = new SimpleDateFormat(dateFormatPattern);
        } else {
             df = new SimpleDateFormat(dateFormatPattern2);
        }
        try {
            return df.parse(source);
        } catch (ParseException e) {
            System.out.println(e.toString());
            //③转化失败
            throw new IllegalArgumentException(String.format("类型转换失败，需要格式%s，但格式是[%s]", dateFormatPattern, source));
        }
    }



}