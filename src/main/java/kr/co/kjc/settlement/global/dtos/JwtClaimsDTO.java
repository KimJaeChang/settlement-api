package kr.co.kjc.settlement.global.dtos;

import kr.co.kjc.settlement.global.enums.EnumJwtCategory;
import kr.co.kjc.settlement.global.enums.EnumJwtRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtClaimsDTO {

  private EnumJwtCategory category;
  private EnumJwtRole role;
  private MemberDTO member;

  public static JwtClaimsDTO of(EnumJwtCategory category, EnumJwtRole role, MemberDTO member) {
    JwtClaimsDTO result = new JwtClaimsDTO();
    result.category = category;
    result.role = role;
    result.member = member;
    return result;
  }
}
