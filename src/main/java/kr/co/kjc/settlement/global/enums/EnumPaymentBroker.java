package kr.co.kjc.settlement.global.enums;

import java.util.Arrays;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumPaymentBroker implements BaseEnum {

  PORT_ONE("port-one", "아임포트(포트원)"),
  TOSS_PAYMENTS("toss-payments", "토스페이먼츠"),
  TOSS_PAY("toss-pay", "토스페이");

  private final String code;
  private final String name;

  @Override
  public EnumPaymentBroker getValue(String key) {
    return Arrays.stream(EnumPaymentBroker.values())
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
