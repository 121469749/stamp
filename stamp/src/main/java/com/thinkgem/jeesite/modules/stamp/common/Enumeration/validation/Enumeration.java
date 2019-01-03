package com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by hjw-pc on 2017/5/16.
 * 验证枚举类annotation
 */
@Documented
@Constraint(validatedBy = EnumerationValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, CONSTRUCTOR })
@Retention(RUNTIME)
public @interface Enumeration {

    String message() default "{NOT ALLOW}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum> enumClass();

}
