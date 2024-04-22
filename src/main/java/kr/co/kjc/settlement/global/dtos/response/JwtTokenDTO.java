package kr.co.kjc.settlement.global.dtos.response;

import lombok.Getter;

@Getter
public class JwtTokenDTO {

  private String accessToken;
  private String refreshToken;

  public static JwtTokenDTO of(String accessToken, String refreshToken) {
    JwtTokenDTO result = new JwtTokenDTO();
    result.accessToken = accessToken;
    result.refreshToken = refreshToken;
    return result;
  }
}
