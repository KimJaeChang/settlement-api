package kr.co.kjc.settlement.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EnumErrorCode {

  /**
   * 400 - Bad Request
   */
  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),

  /**
   * 404 - Not Found
   */
  NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
  NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
  NOT_FOUND_ENUM(HttpStatus.NOT_FOUND, "Enum을 찾을 수 없습니다."),

  /**
   * 500 - Internal_server_error
   */
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에러가 발생했습니다.");

  private final HttpStatus httpStatus;
  private final String detail;

}
