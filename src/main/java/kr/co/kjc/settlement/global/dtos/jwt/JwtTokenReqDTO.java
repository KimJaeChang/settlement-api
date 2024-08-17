package kr.co.kjc.settlement.global.dtos.jwt;

import kr.co.kjc.settlement.global.enums.EnumJwtRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtTokenReqDTO {

  private String uuid;
  private EnumJwtRole jwtRole;
}
