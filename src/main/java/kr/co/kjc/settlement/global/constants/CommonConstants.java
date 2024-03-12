package kr.co.kjc.settlement.global.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 일반 비즈니스 로직에 필요한 상수 모음
 */
public class CommonConstants {

  // 기본 캐릭터 셋
  public static final Charset STANDARD_CHARSET = StandardCharsets.UTF_8;

  // 관제 접속 용
  public static final String AUTH_KEY_SECRET = "LOCAL_SECRET_";

  public static final String OCPP_KEY_SECRET = "everOn_Ocpp_LocalCSMS101";


  public static final String REQ_HEADER_KEY_AUTH = "Authorization";

  public static final String REQ_HEADER_KEY_AUTH_TOKEN_TYPE = "Bearer ";


  public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
  public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

  public static final String yyyyMMdd = "yyyyMMdd";
  public static final String yyyyMMddHHmm = "yyyyMMddHHmm";

  public static final String HAND_PHONE_REG_EXP = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$";

  public static final String EMPTY_STRING = "";

}
