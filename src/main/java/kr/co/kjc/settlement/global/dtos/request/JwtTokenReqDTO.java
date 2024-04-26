package kr.co.kjc.settlement.global.dtos.request;

import kr.co.kjc.settlement.global.enums.EnumJwtCategory;
import kr.co.kjc.settlement.global.enums.EnumJwtRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtTokenReqDTO {

  private String uuid;
  private EnumJwtCategory jwtCategory;
  private EnumJwtRole jwtRole;
}
