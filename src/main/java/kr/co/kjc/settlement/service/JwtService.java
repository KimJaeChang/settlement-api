package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.enums.EnumJwtCategory;
import kr.co.kjc.settlement.global.enums.EnumJwtRole;

@Deprecated
public interface JwtService {

  String createAccessToken(MemberDTO memberDTO, EnumJwtCategory jwtCategory, EnumJwtRole jwtRole,
      Long expiredMs);

  String createRefreshToken(MemberDTO memberDTO, EnumJwtCategory jwtCategory, EnumJwtRole jwtRole,
      Long expiredMs);

  boolean isExpired(String token);

  MemberDTO findMemberByToken(String token);

  EnumJwtCategory getCategory(String token);

  EnumJwtRole getRole(String token);
}
