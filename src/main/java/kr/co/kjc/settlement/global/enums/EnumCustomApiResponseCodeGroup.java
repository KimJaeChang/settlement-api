package kr.co.kjc.settlement.global.enums;

import kr.co.kjc.settlement.global.annotation.CustomApiResponseCode;
import kr.co.kjc.settlement.global.annotation.CustomApiResponseCodes;

public enum EnumCustomApiResponseCodeGroup {

  @CustomApiResponseCodes(value = {
      @CustomApiResponseCode(EnumResponseCode.SUCCESS),
  })
  SUCCESS,

  @CustomApiResponseCodes(value = {
      @CustomApiResponseCode(EnumResponseCode.INVALID_SCHEMA),
      @CustomApiResponseCode(EnumResponseCode.INVALID_PARAMETER),
      @CustomApiResponseCode(EnumResponseCode.INVALID_JWT_TOKEN),
      @CustomApiResponseCode(EnumResponseCode.INVALID_JWT_TOKEN_HEADER),
      @CustomApiResponseCode(EnumResponseCode.INVALID_JWT_TOKEN_BODY),
  })
  INVALID,

  @CustomApiResponseCodes(value = {
      @CustomApiResponseCode(EnumResponseCode.UNAUTHORIZED_JWT_TOKEN),
  })
  UNAUTHORIZED,

  @CustomApiResponseCodes(value = {
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND),
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND_REFRESH_TOKEN),
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND_MEMBER),
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND_MEMBER_BY_JWT_ACCESS_TOKEN),
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND_JWT_ACCESS_TOKEN),
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND_UPLOAD_FILE),
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND_ENUM),
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND_ENUM_VALUE),
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND_ENUM_CODE),
      @CustomApiResponseCode(EnumResponseCode.NOT_FOUND_COMMON_CODE),
  })
  NOT_FOUND,

  @CustomApiResponseCodes(value = {
      @CustomApiResponseCode(EnumResponseCode.CONFLICT_JWT_REFRESH_TOKEN),
  })
  CONFLICT,

  @CustomApiResponseCodes(value = {
      @CustomApiResponseCode(EnumResponseCode.INTERNAL_SERVER_ERROR),
  })
  INTERNAL_SERVER_ERROR
}
