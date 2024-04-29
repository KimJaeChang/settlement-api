package kr.co.kjc.settlement.global.dtos.request;

import kr.co.kjc.settlement.domain.redis.Token;
import lombok.Getter;

@Getter
public class JwtTokenRefreshReqDTO {

  private String uuid;
  private String accessToken;
  private String refreshToken;

  public static JwtTokenRefreshReqDTO of(String uuid, String accessToken, String refreshToken) {
    JwtTokenRefreshReqDTO result = new JwtTokenRefreshReqDTO();
    result.uuid = uuid;
    result.accessToken = accessToken;
    result.refreshToken = refreshToken;
    return result;
  }

  public static JwtTokenRefreshReqDTO createByToken(Token token, String accessToken) {
    JwtTokenRefreshReqDTO result = new JwtTokenRefreshReqDTO();
    result.uuid = token.getTokenBody().getUuid();
    result.accessToken = accessToken;
    result.refreshToken = token.getRefreshToken();
    return result;
  }
}
