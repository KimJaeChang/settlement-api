package kr.co.kjc.settlement.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EnumResponseCode {

  /**
   * 200 - Success
   */
  SUCCESS(HttpStatus.OK, "성공"),

  /**
   * 400 - Bad Request
   */
  INVALID_SCHEMA(HttpStatus.BAD_REQUEST, "올바르지 않은 Schema 접근입니다."),
  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
  INVALID_JWT_TOKEN(HttpStatus.BAD_REQUEST, "올바르지 않은 JWT 토큰입니다."),
  INVALID_JWT_TOKEN_HEADER(HttpStatus.BAD_REQUEST,
      "올바르지 않은 JWT 토큰입니다. Authorization로 헤더를 설정해 주세요."),
  INVALID_JWT_TOKEN_BODY(HttpStatus.BAD_REQUEST, "올바르지 않은 JWT 토큰입니다. Bearer 토큰으로 인증해 주세요."),

  /**
   * 401 - Unauthorized
   */
  UNAUTHORIZED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "JWT 토근 인증이 필요합니다."),
  EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰 입니다. RefreshToken으로 JWT 토큰을 재발급 해주세요."),

  /**
   * 404 - Not Found
   */
  NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
  NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "RefreshToken을 찾을 수 없습니다."),
  NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
  NOT_FOUND_MEMBER_BY_JWT_ACCESS_TOKEN(HttpStatus.NOT_FOUND, "JWT 토큰 정보에 대한 회원 정보를 찾을 수 없습니다."),
  NOT_FOUND_JWT_ACCESS_TOKEN(HttpStatus.NOT_FOUND, "JWT 토큰 정보를 찾을 수 없습니다."),
  NOT_FOUND_UPLOAD_FILE(HttpStatus.NOT_FOUND, "업로드한 파일을 찾을 수 없습니다."),
  NOT_FOUND_ENUM(HttpStatus.NOT_FOUND, "Enum을 찾을 수 없습니다."),
  NOT_FOUND_ENUM_VALUE(HttpStatus.NOT_FOUND, "해당 Enum의 Value가 존재하지 않습니다. 올바른 값으로 조회해주세요."),
  NOT_FOUND_ENUM_CODE(HttpStatus.NOT_FOUND, "해당 Enum의 Code가 존재하지 않습니다. 올바른 값으로 조회해주세요."),
  NOT_FOUND_COMMON_CODE(HttpStatus.NOT_FOUND, "해당 공통 코드가 존재하지 않습니다. 올바른 값으로 조회해주세요."),
  NOT_FOUND_S3_BUCKET(HttpStatus.NOT_FOUND, "해당 S3 Bucket이 존재하지 않습니다. 올바른 값으로 조회해주세요."),

  /**
   * 409 - Conflict
   */
  CONFLICT_JWT_REFRESH_TOKEN(HttpStatus.CONFLICT, "RefreshToken이 유효합니다. 만료된 후 다시 시도해주세요."),

  /**
   * 500 - Internal_server_error
   */
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에러가 발생했습니다.");

  private final HttpStatus httpStatus;
  private final String detail;

}
