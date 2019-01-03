package com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Created by Locker on 2017/5/14.
 *
 * 枚举前端交互 转换器
 *
 */
public class MyEnumConverter implements ConverterFactory<String, Enum> {

    public <T extends Enum> Converter<String, T> getConverter(
            Class<T> targetType) {
        //System.out.println("I'm MyEnumConverter");
        //System.out.println("this enum class Type is "+targetType);
        return new StringToEnum(targetType);
    }


    private class StringToEnum<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(String source) {

            //System.out.println("I'm in covert!");

            //System.out.println("source is "+source);

            if (source.length()==0) {
                return null;
            }
            return (T) Enum.valueOf(this.enumType, source.trim());
        }
    }


}
