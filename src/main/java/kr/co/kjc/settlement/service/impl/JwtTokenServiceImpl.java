package kr.co.kjc.settlement.service.impl;

import javax.crypto.SecretKey;
import kr.co.kjc.settlement.domain.redis.Token;
import kr.co.kjc.settlement.domain.redis.TokenBody;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.request.JwtTokenRefreshReqDTO;
import kr.co.kjc.settlement.global.dtos.request.JwtTokenReqDTO;
import kr.co.kjc.settlement.global.dtos.response.JwtTokenResDTO;
import kr.co.kjc.settlement.global.utils.JwtUtils;
import kr.co.kjc.settlement.repository.redis.TokenRedisRepository;
import kr.co.kjc.settlement.service.JwtTokenService;
import kr.co.kjc.settlement.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

  private static final long EXPIRED_MS = 60000;
  private final SecretKey secretKey;
  private final MemberService memberService;
  private final TokenRedisRepository tokenRedisRepository;

  @Override
  public JwtTokenResDTO create(JwtTokenReqDTO dto) {
//    return redisTemplate.opsForHash().putIfAbsent(table, key, value);

    String uuid = dto.getUuid();
    MemberDTO memberDTO = memberService.findByUuid(uuid);

    String accessToken = JwtUtils.createAccessToken(secretKey, memberDTO, dto.getJwtCategory(),
        dto.getJwtRole(), EXPIRED_MS);
    String refreshToken = JwtUtils.createRefreshToken(secretKey, EXPIRED_MS);

    Token token = Token.of(refreshToken, TokenBody.of(uuid, EXPIRED_MS));
    Token saveToken = tokenRedisRepository.save(token);

    return JwtTokenResDTO.createByToken(saveToken, accessToken);
  }

  @Override
  public void update(JwtTokenRefreshReqDTO dto) {

  }

  @Override
  public boolean isExpired(String accessToken) {
//    return JwtUtils.isExpired(secretKey, accessToken);
    return tokenRedisRepository.existsById(accessToken);
  }
}
