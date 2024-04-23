package kr.co.kjc.settlement.domain.redis;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken")
public class Token {

  @Id
  private String uuid;
  private String refreshToken;
  private LocalDateTime createdAt;
  @TimeToLive
  private Long expiredTime;

  @Builder
  public Token(String uuid, String refreshToken, LocalDateTime createdAt, Long expiredTime) {
    this.uuid = uuid;
    this.refreshToken = refreshToken;
    this.createdAt = createdAt;
    this.expiredTime = expiredTime;
  }

  public static Token of(String uuid, String refreshToken, LocalDateTime createdAt,
      long expiredTime) {
    Token result = new Token();
    result.uuid = uuid;
    result.refreshToken = refreshToken;
    result.createdAt = createdAt;
    result.expiredTime = expiredTime;
    return result;
  }
}
