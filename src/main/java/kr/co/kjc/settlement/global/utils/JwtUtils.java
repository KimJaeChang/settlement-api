package kr.co.kjc.settlement.global.utils;

import io.jsonwebtoken.Jwts;
import java.util.Map;
import javax.crypto.SecretKey;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.enums.EnumJwtCategory;
import kr.co.kjc.settlement.global.enums.EnumJwtRole;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUtils {

  /**
   * @author : jcKim
   * @version : 0.0.1
   * @date : 2024-04-25T15:58:33.912
   * @description : JWT AccessToken 생성
   */
  public static String createAccessToken(SecretKey secretKey, MemberDTO memberDTO,
      EnumJwtCategory jwtCategory, EnumJwtRole jwtRole, long expiredMs) {
    return Jwts.builder()
        .claims(Map.of(
            "category", jwtCategory,
            "role", jwtRole,
            "user", memberDTO
        ))
        .issuedAt(DateTimeUtils.nowDate())
        .expiration(DateTimeUtils.afterDateMs(expiredMs))
        .signWith(secretKey)
        .compact();
  }

  /**
   * @author : jcKim
   * @version : 0.0.1
   * @date : 2024-04-25T15:58:50.707
   * @description : JWT RefreshToken 생성
   */
  public static String createRefreshToken(SecretKey secretKey, long expiredMs) {
    return Jwts.builder()
        .expiration(DateTimeUtils.afterDateMs(expiredMs))
        .signWith(secretKey)
        .compact();
  }

  public static boolean isExpired(SecretKey secretKey, String accessToken) {
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

}
