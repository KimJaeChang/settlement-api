package kr.co.kjc.settlement.service.impl;

import javax.crypto.SecretKey;
import kr.co.kjc.settlement.domain.redis.Token;
import kr.co.kjc.settlement.domain.redis.TokenBody;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.response.JwtTokenDTO;
import kr.co.kjc.settlement.global.enums.EnumJwtCategory;
import kr.co.kjc.settlement.global.enums.EnumJwtRole;
import kr.co.kjc.settlement.global.utils.JwtUtils;
import kr.co.kjc.settlement.repository.redis.TokenRedisRepository;
import kr.co.kjc.settlement.service.JwtTokenService;
import kr.co.kjc.settlement.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

  private static final long EXPIRED_MS = 60000;
  private final SecretKey secretKey;
  private final MemberService memberService;
  private final RedisTemplate<String, Object> redisTemplate;
  private final TokenRedisRepository tokenRedisRepository;

  @Override
  public JwtTokenDTO create(String uuid, EnumJwtCategory jwtCategory,
      EnumJwtRole jwtRole) {
//    return redisTemplate.opsForHash().putIfAbsent(table, key, value);

    MemberDTO memberDTO = memberService.findByUuid(uuid);

    String accessToken = JwtUtils.createAccessToken(secretKey, memberDTO, jwtCategory, jwtRole,
        EXPIRED_MS);
    String refreshToken = JwtUtils.createRefreshToken(secretKey, EXPIRED_MS);

    Token token = Token.of(uuid, TokenBody.of(refreshToken, EXPIRED_MS));
    Token saveToken = tokenRedisRepository.save(token);

    return JwtTokenDTO.createByToken(saveToken, accessToken);
  }

  @Override
  public void update(String uuid) {

  }

  @Override
  public boolean isExpired(String accessToken) {
    return JwtUtils.isExpired(secretKey, accessToken);
  }
}
