package kr.co.kjc.settlement.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import kr.co.kjc.settlement.domain.redis.Token;
import kr.co.kjc.settlement.global.dtos.response.JwtTokenDTO;
import kr.co.kjc.settlement.repository.redis.TokenRedisRepository;
import kr.co.kjc.settlement.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final TokenRedisRepository tokenRedisRepository;

  @Override
  public JwtTokenDTO save(String uuid) {
    return null;
  }

  @Override
  public JwtTokenDTO saveRefreshToken(String uuid) {
//    return redisTemplate.opsForHash().putIfAbsent(table, key, value);

    String accessToken = "";
    String refreshToken = "";

    Token token = Token.of(uuid, refreshToken, LocalDateTime.now(ZoneId.of("Asia/Seoul")), 60);
    Token saveToken = tokenRedisRepository.save(token);

    return JwtTokenDTO.createByToken(saveToken, accessToken);
  }

  @Override
  public void update(String uuid) {

  }
}
