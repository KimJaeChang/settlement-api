package kr.co.kjc.settlement.domain.redis;

import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(value = "Token")
public class Token {

  @Id
  private String uuid;
  @Embedded
  private TokenBody tokenBody;


  public static Token of(String uuid, TokenBody tokenBody) {
    Token result = new Token();
    result.uuid = uuid;
    result.tokenBody = tokenBody;
    return result;
  }
}
