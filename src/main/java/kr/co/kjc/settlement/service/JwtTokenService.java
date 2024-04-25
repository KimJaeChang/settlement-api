package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.response.JwtTokenDTO;
import kr.co.kjc.settlement.global.enums.EnumJwtCategory;
import kr.co.kjc.settlement.global.enums.EnumJwtRole;

public interface JwtTokenService {

  JwtTokenDTO create(String uuid, EnumJwtCategory jwtCategory, EnumJwtRole jwtRole);

  void update(String uuid);

  boolean isExpired(String accessToken);

}
