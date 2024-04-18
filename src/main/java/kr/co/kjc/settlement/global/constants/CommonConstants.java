package kr.co.kjc.settlement.global.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;

/**
 * 일반 비즈니스 로직에 필요한 상수 모음
 */
public class CommonConstants {

  // page 관련
  public static final String FIELD_BODY = "body";
  public static final String FIELD_TOTAL_PAGES = "totalPages";
  public static final String FIELD_TOTAL_ELEMENTS = "totalElements";
  public static final String FIELD_PAGE_SIZE = "pageSize";
  public static final String FIELD_PAGE_NO = "pageNo";
  public static final String FIELD_HAS_CONTENT = "hasContent";
  public static final String FIELD_FIRST = "first";
  public static final String FIELD_LAST = "last";
  public static final String FIELD_NUMBER_OF_ELEMENTS = "numberOfElements";
  public static final String FIELD_HAS_NEXT = "hasNext";
  public static final String FIELD_HAS_PREVIOUS = "hasPrevious";
  public static final String FIELD_SORT = "sort";
  public static final String FIELD_ORDERS = "orders";
  public static final String FIELD_CONTENT = "content";

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

  public static final String DEPRECATED_ENUM = "Deprecated 처리된 코드입니다.";

  public static final LocalTime endOfLocalTime = LocalTime.of(23, 59, 59, 999999000);

}
