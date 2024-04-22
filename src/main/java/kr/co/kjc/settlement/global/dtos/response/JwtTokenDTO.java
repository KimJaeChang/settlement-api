package kr.co.kjc.settlement.global.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Getter;

@Getter
public class JwtTokenDTO {

  private String accessToken;
  private String refreshToken;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
  private LocalDateTime expiredAt;

  public static JwtTokenDTO of(String accessToken, String refreshToken, Long expiredMs) {
    JwtTokenDTO result = new JwtTokenDTO();
    result.accessToken = accessToken;
    result.refreshToken = refreshToken;
    result.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    result.expiredAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusSeconds(expiredMs);
    return result;
  }
}
