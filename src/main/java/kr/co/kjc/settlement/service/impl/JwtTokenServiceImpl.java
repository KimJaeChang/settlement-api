package kr.co.kjc.settlement.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import javax.crypto.SecretKey;
import kr.co.kjc.settlement.domain.redis.Token;
import kr.co.kjc.settlement.domain.redis.TokenBody;
import kr.co.kjc.settlement.global.constants.CommonConstants;
import kr.co.kjc.settlement.global.constants.TextConstants;
import kr.co.kjc.settlement.global.dtos.JwtClaimsDTO;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.request.JwtTokenReqDTO;
import kr.co.kjc.settlement.global.dtos.response.JwtTokenResDTO;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.global.utils.JwtUtils;
import kr.co.kjc.settlement.repository.redis.TokenRedisRepository;
import kr.co.kjc.settlement.service.JwtTokenService;
import kr.co.kjc.settlement.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

  private static final long EXPIRED_MS = 1000 * 60 * 60; // 60분

  private final ObjectMapper om;
  private final SecretKey secretKey;
  private final MemberService memberService;
  private final TokenRedisRepository tokenRedisRepository;

  @Override
  public JwtTokenResDTO create(JwtTokenReqDTO dto) {
//    return redisTemplate.opsForHash().putIfAbsent(table, key, value);
    String uuid = dto.getUuid();
    MemberDTO memberDTO = memberService.findOneByUuid(uuid);
    Map<String, ?> claims = createClaims(dto, memberDTO);

    String accessToken = null;
    String refreshToken = null;

    try {
      accessToken =
          CommonConstants.REQ_HEADER_KEY_AUTH_TOKEN_TYPE + JwtUtils.createAccessToken(secretKey,
              claims, EXPIRED_MS);
      refreshToken = JwtUtils.createRefreshToken(secretKey, EXPIRED_MS);
    } catch (InvalidKeyException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new BaseAPIException(EnumResponseCode.SECRET_KEY_SERVER_ERROR);
    }

    if (StringUtils.hasText(refreshToken) && StringUtils.hasText(accessToken)) {
      Token token = Token.of(refreshToken, TokenBody.of(uuid, EXPIRED_MS));
      Token saveToken = tokenRedisRepository.save(token);
      return JwtTokenResDTO.createByToken(saveToken, accessToken);
    }

    return null;
  }

  @Override
  public JwtTokenResDTO update(JwtTokenReqDTO dto, MemberDTO memberDTO) {

    Map<String, ?> claims = createClaims(dto, memberDTO);
    String accessToken = null;
    String refreshToken = null;

    try {
      accessToken =
          CommonConstants.REQ_HEADER_KEY_AUTH_TOKEN_TYPE + JwtUtils.createAccessToken(secretKey,
              claims, EXPIRED_MS);
      refreshToken = JwtUtils.createRefreshToken(secretKey, EXPIRED_MS);
    } catch (InvalidKeyException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new BaseAPIException(EnumResponseCode.SECRET_KEY_SERVER_ERROR);
    }

    if (StringUtils.hasText(refreshToken) && StringUtils.hasText(accessToken)) {
      Token token = Token.of(refreshToken, TokenBody.of(memberDTO.getUuid(), EXPIRED_MS));
      Token saveToken = tokenRedisRepository.save(token);
      return JwtTokenResDTO.createByToken(saveToken, accessToken);
    }

    return null;
  }

  @Override
  public boolean isAccessTokenExpired(String accessToken) {
    try {
      return JwtUtils.isExpired(secretKey, accessToken);
    } catch (ExpiredJwtException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new BaseAPIException(EnumResponseCode.EXPIRED_JWT_ACCESS_TOKEN);
    } catch (SignatureException | IllegalArgumentException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new BaseAPIException(EnumResponseCode.INVALID_JWT_TOKEN);
    }
  }

  @Override
  public boolean isRefreshTokenExpired(String refreshToken) {

    Token findToken = tokenRedisRepository.findById(refreshToken)
        .orElseThrow(() -> new BaseAPIException(EnumResponseCode.NOT_FOUND_JWT_REFRESH_TOKEN));

    if (findToken.getTokenBody().getExpiredAt()
        .isAfter(LocalDateTime.now(ZoneId.of("Asia/Seoul")))) {
      throw new BaseAPIException(EnumResponseCode.CONFLICT_JWT_REFRESH_TOKEN);
    }

    return true;
  }

  @Override
  public MemberDTO findMemberByAccessToken(String accessToken) {
    try {
      Map<String, ?> payloads = Jwts.parser().verifyWith(secretKey).build()
          .parseSignedClaims(accessToken)
          .getPayload()
          .get("member", Map.class);
      return om.convertValue(payloads, MemberDTO.class);
    } catch (JwtException | IllegalArgumentException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new BaseAPIException(EnumResponseCode.NOT_FOUND_MEMBER_BY_JWT_ACCESS_TOKEN);
    }
  }

  @Override
  public MemberDTO findMemberByRefreshToken(String refreshToken) {

    Token token = tokenRedisRepository.findById(refreshToken)
        .orElseThrow(() -> new BaseAPIException(EnumResponseCode.NOT_FOUND_JWT_REFRESH_TOKEN));

    return memberService.findOneByUuid(token.getTokenBody().getUuid());
  }

  private Map<String, ?> createClaims(JwtTokenReqDTO dto, MemberDTO memberDTO) {
    JwtClaimsDTO jwtClaimsDTO = JwtClaimsDTO.of(dto.getJwtRole(), memberDTO);
    return om.convertValue(jwtClaimsDTO, new TypeReference<Map<String, ?>>() {
    });
  }
}
