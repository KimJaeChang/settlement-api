package kr.co.kjc.settlement.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import javax.crypto.SecretKey;
import kr.co.kjc.settlement.domain.redis.Token;
import kr.co.kjc.settlement.domain.redis.TokenBody;
import kr.co.kjc.settlement.global.constants.TextConstants;
import kr.co.kjc.settlement.global.dtos.JwtClaimsDTO;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.request.JwtTokenReqDTO;
import kr.co.kjc.settlement.global.dtos.response.JwtTokenResDTO;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.global.utils.JwtUtils;
import kr.co.kjc.settlement.repository.redis.TokenRedisRepository;
import kr.co.kjc.settlement.service.JwtTokenService;
import kr.co.kjc.settlement.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

  private static final long EXPIRED_MS = 1000 * 60; // 60초
  private static final Logger log = LoggerFactory.getLogger(JwtTokenServiceImpl.class);

  private final ObjectMapper om;
  private final SecretKey secretKey;
  private final MemberService memberService;
  private final TokenRedisRepository tokenRedisRepository;

  @Override
  public JwtTokenResDTO create(JwtTokenReqDTO dto) {
//    return redisTemplate.opsForHash().putIfAbsent(table, key, value);

    String uuid = dto.getUuid();
    MemberDTO memberDTO = memberService.findByUuid(uuid);
    Map<String, ?> claims = createClaims(dto, memberDTO);

    String accessToken = JwtUtils.createAccessToken(secretKey, claims, EXPIRED_MS);
    String refreshToken = JwtUtils.createRefreshToken(secretKey, EXPIRED_MS);

    Token token = Token.of(refreshToken, TokenBody.of(uuid, EXPIRED_MS));
    Token saveToken = tokenRedisRepository.save(token);

    return JwtTokenResDTO.createByToken(saveToken, accessToken);
  }

  @Override
  public Token update(String refreshToken) {
    Token token = tokenRedisRepository.findById(refreshToken)
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_REFRESH_TOKEN));

    if (token.getTokenBody().getExpiredAt().isAfter(LocalDateTime.now(ZoneId.of("Asia/Seoul")))) {
      throw new BaseAPIException(EnumErrorCode.CONFLICT_JWT_REFRESH_TOKEN);
    }

    return tokenRedisRepository.save(Token.createTokenByRefreshToken(refreshToken));
  }

  @Override
  public boolean isExpired(String accessToken) {
    try {
      return JwtUtils.isExpired(secretKey, accessToken);
    } catch (ExpiredJwtException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new BaseAPIException(EnumErrorCode.EXPIRED_JWT_TOKEN);
    } catch (SignatureException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new BaseAPIException(EnumErrorCode.INVALID_JWT_TOKEN);
    }
  }

  @Override
  public MemberDTO findMemberByToken(String token) {
    Map<String, ?> payloads = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        .getPayload()
        .get("member", Map.class);
    return om.convertValue(payloads, MemberDTO.class);
  }

  private Map<String, ?> createClaims(JwtTokenReqDTO dto, MemberDTO memberDTO) {
    JwtClaimsDTO jwtClaimsDTO = JwtClaimsDTO.of(dto.getJwtCategory(), dto.getJwtRole(), memberDTO);
    return om.convertValue(jwtClaimsDTO, new TypeReference<Map<String, ?>>() {
    });
  }
}
