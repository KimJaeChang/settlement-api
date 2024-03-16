package kr.co.kjc.settlement.global;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.co.kjc.settlement.global.annotation.EnumValid;
import org.springframework.stereotype.Component;

@Component
public class EnumValidator implements ConstraintValidator<EnumValid, Enum<?>> {

  private EnumValid annotation;

  @Override
  public void initialize(EnumValid constraintAnnotation) {
    this.annotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(Enum value, ConstraintValidatorContext context) {
    Object[] enumValues = this.annotation.enumClass().getEnumConstants();
    if (enumValues != null) {
      for (Object enumValue : enumValues) {
        if (value == enumValue) {
          return true;
        }
      }
    }
    return false;
  }
}


