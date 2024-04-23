package kr.co.kjc.settlement.global.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.time.ZoneId;
import kr.co.kjc.settlement.domain.redis.Token;
import lombok.Getter;

@Getter
public class JwtTokenDTO {

  private String uuid;
  private String accessToken;
  private String refreshToken;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
  private LocalDateTime expiredAt;

  public static JwtTokenDTO of(String uuid, String accessToken, String refreshToken,
      Long expiredMs) {
    JwtTokenDTO result = new JwtTokenDTO();
    result.uuid = uuid;
    result.accessToken = accessToken;
    result.refreshToken = refreshToken;
    result.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    result.expiredAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusSeconds(expiredMs);
    return result;
  }

  public static JwtTokenDTO createByToken(Token token, String accessToken) {
    JwtTokenDTO result = new JwtTokenDTO();
    result.uuid = token.getUuid();
    result.accessToken = accessToken;
    result.refreshToken = token.getRefreshToken();
    result.createdAt = token.getCreatedAt();
    result.expiredAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        .plusSeconds(token.getExpiredTime());
    return result;
  }
}
