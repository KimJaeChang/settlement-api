package kr.co.kjc.settlement.global.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import kr.co.kjc.settlement.global.constants.CommonConstants;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class JwtUtils {

  /**
   * @author : jcKim
   * @version : 0.0.1
   * @date : 2024-04-25T15:58:33.912
   * @description : JWT AccessToken 생성
   */
  public static String createAccessToken(SecretKey secretKey, Map<String, ?> claims,
      long expiredMs) throws InvalidKeyException {
    return Jwts.builder()
        .claims(claims)
        .issuedAt(DateTimeUtils.nowDate())
        .expiration(new Date(DateTimeUtils.nowDate().getTime() + expiredMs))
        .signWith(secretKey)
        .compact();
  }

  /**
   * @author : jcKim
   * @version : 0.0.1
   * @date : 2024-04-25T15:58:50.707
   * @description : JWT RefreshToken 생성
   */
  public static String createRefreshToken(SecretKey secretKey, long expiredMs)
      throws InvalidKeyException {
    return Jwts.builder()
        .expiration(new Date(DateTimeUtils.nowDate().getTime() + expiredMs))
        .signWith(secretKey)
        .compact();
  }

  /**
   * 토큰의 만료기간을 지정하는 함수
   *
   * @return Calendar
   */
  private static Date createExpiredDate(long expiredMs) {
    // 토큰 만료시간은 30일으로 설정
    Calendar c = Calendar.getInstance();
    c.add(Calendar.MILLISECOND, (int) expiredMs);
    // c.add(Calendar.DATE, 1);         // 1일
    return c.getTime();
  }

  public static boolean isExpired(SecretKey secretKey, String accessToken)
      throws ExpiredJwtException, SignatureException, IllegalArgumentException {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(accessToken)
        .getPayload()
        .getExpiration()
        .before(DateTimeUtils.nowDate());
  }

//  private Claims parseClaims(SecretKey secretKey, String accessToken) {
//    try {
//      return Jwts.builder()
//          .signWith(secretKey)
//          .claims()
//    } catch (ExpiredJwtException e) {
//      return e.getClaims();
//    }
//  }

  public static boolean isAuthorization(String authorization) {
    if (StringUtils.hasText(authorization)) {
      if (authorization.startsWith(CommonConstants.REQ_HEADER_KEY_AUTH_TOKEN_TYPE)) {
        return true;
      }
      throw new BaseAPIException(EnumResponseCode.INVALID_JWT_TOKEN_BODY);
    }
    throw new BaseAPIException(EnumResponseCode.INVALID_JWT_TOKEN_HEADER);
  }

  public static String convertAccessToken(String authorization) {
    return authorization.substring(CommonConstants.REQ_HEADER_KEY_AUTH_TOKEN_TYPE.length());
  }

}
