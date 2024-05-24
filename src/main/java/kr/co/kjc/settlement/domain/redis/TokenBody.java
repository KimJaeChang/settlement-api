package kr.co.kjc.settlement.domain.redis;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "TokenBody")
public class TokenBody implements Serializable {

  private String uuid;
  private LocalDateTime createdAt;
  private LocalDateTime expiredAt;
  @TimeToLive(unit = TimeUnit.MILLISECONDS)
  private Long expiredTime;

  public static TokenBody of(String uuid, long expiredTime) {
    TokenBody result = new TokenBody();
    result.uuid = uuid;
    result.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    result.expiredAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        .plus(expiredTime, ChronoUnit.MILLIS);
    result.expiredTime = expiredTime;
    return result;
  }
}
