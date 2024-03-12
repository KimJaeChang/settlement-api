package kr.co.kjc.settlement.global.enums;

import java.lang.Enum;
public interface BaseEnum {

  Enum<?> getValue(String key);

  String getCode();

  String getName();
}
