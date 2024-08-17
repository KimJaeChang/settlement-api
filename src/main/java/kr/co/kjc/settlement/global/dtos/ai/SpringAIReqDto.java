package kr.co.kjc.settlement.global.dtos.ai;

import kr.co.kjc.settlement.global.enums.EnumSpringAICode;
import lombok.Data;

@Data
public class SpringAIReqDto {

  private EnumSpringAICode springAICode;
  private String message;

}
