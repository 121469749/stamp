package com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by hjw-pc on 2017/5/16.
 */
public class EnumerationValidator implements ConstraintValidator<Enumeration, Enum> {

    private  Class<? extends Enum> enumC;

    @Override
    public void initialize(Enumeration enumeration) {
        enumC = enumeration.enumClass();
    }

    /**
     * 验证是否存在枚举类实例
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
       return Enum.valueOf(enumC, value.name()) != null;
    }

}
