package kr.co.kjc.settlement.domain.redis;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("Token")
public class Token {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  private String email;
  private String accessToken;
  private String refreshToken;
}
