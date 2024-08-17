package kr.co.kjc.settlement.global.enums;

import java.util.Arrays;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumSpringAICode implements BaseEnum {

  OPEN_AI("open-ai", "Open AI"),
  GEMINI_AI("gemini-ai", "Vertex Gemini AI"),
  ;

  private final String code;
  private final String name;

  @Override
  public EnumSpringAICode getValue(String key) {
    return Arrays.stream(EnumSpringAICode.values())
        .filter(f -> f.getValue(key).equals(key))
        .findFirst()
        .orElseThrow(() -> new BaseAPIException(EnumResponseCode.NOT_FOUND_ENUM));
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getName() {
    return this.name;
  }

}
