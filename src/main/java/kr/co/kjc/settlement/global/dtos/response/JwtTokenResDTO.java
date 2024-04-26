package kr.co.kjc.settlement.global.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.time.ZoneId;
import kr.co.kjc.settlement.domain.redis.Token;
import lombok.Getter;

@Getter
public class JwtTokenResDTO {

  private String uuid;
  private String accessToken;
  private String refreshToken;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
  private LocalDateTime expiredAt;

  public static JwtTokenResDTO of(String uuid, String accessToken, String refreshToken,
      Long expiredMs) {
    JwtTokenResDTO result = new JwtTokenResDTO();
    result.uuid = uuid;
    result.accessToken = accessToken;
    result.refreshToken = refreshToken;
    result.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    result.expiredAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusSeconds(expiredMs);
    return result;
  }

  public static JwtTokenResDTO createByToken(Token token, String accessToken) {
    JwtTokenResDTO result = new JwtTokenResDTO();
    result.uuid = token.getUuid();
    result.accessToken = accessToken;
    result.refreshToken = token.getTokenBody().getRefreshToken();
    result.createdAt = token.getTokenBody().getCreatedAt();
    result.expiredAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        .plusSeconds(token.getTokenBody().getExpiredTime());
    return result;
  }
}
