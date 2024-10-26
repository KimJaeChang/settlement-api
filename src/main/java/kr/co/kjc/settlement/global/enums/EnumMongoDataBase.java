package kr.co.kjc.settlement.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumMongoDataBase {

  COMMON_LOG("common-log", "공통 로그 DataBase"),
  ;

  private final String code;
  private final String name;

}
