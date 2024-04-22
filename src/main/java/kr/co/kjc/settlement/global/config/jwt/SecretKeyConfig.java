package kr.co.kjc.settlement.global.config.jwt;

import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretKeyConfig {

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Bean
  public SecretKey secretKey() {
    return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
  }

}
