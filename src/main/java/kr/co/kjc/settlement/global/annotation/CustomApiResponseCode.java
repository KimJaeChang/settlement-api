package kr.co.kjc.settlement.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;

@Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CustomApiResponseCodes.class)
public @interface CustomApiResponseCode {

  EnumResponseCode value();
}
