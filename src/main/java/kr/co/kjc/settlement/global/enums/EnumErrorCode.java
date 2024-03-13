package kr.co.kjc.settlement.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EnumErrorCode {

  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
  NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
  NOT_FOUND_ENUM(HttpStatus.NOT_FOUND, "Enum을 찾을 수 없습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에러가 발생했습니다.");

  private final HttpStatus httpStatus;
  private final String detail;

}
