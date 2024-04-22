package kr.co.kjc.settlement.service.impl;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.enums.EnumJwtCategory;
import kr.co.kjc.settlement.global.enums.EnumJwtRole;
import kr.co.kjc.settlement.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final SecretKey secretKey;

  // JWT 발급
  @Override
  public String createAccessToken(MemberDTO memberDTO, EnumJwtCategory jwtCategory,
      EnumJwtRole jwtRole, Long expiredMs) {
    return Jwts.builder()
        .claim("category", jwtCategory)
        .claim("role", jwtRole)
        .claim("user", memberDTO)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(secretKey)
        .compact();
  }

  @Override
  public String createRefreshToken(MemberDTO memberDTO, EnumJwtCategory jwtCategory,
      EnumJwtRole jwtRole, Long expiredMs) {
    return Jwts.builder()
        .claim("uuid", UUID.randomUUID().toString())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(secretKey)
        .compact();
  }

  // token 유효확인
  @Override
  public boolean isExpired(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
        .getExpiration().before(new Date());
  }

  @Override
  public MemberDTO getUser(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
        .get("user", MemberDTO.class);
  }

  // accessToken인지 refreshToken인지 확인
  @Override
  public EnumJwtCategory getCategory(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
        .get("category", EnumJwtCategory.class);
  }

  @Override
  public EnumJwtRole getRole(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
        .get("role", EnumJwtRole.class);
  }
}
