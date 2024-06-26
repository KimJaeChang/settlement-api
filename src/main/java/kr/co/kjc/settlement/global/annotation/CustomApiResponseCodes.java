package kr.co.kjc.settlement.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kr.co.kjc.settlement.global.enums.EnumCustomApiResponseCodeGroup;

@Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomApiResponseCodes {

  CustomApiResponseCode[] value() default {};

  EnumCustomApiResponseCodeGroup[] groups() default {};

}
