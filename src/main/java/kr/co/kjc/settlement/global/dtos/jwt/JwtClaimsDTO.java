package kr.co.kjc.settlement.global.dtos.jwt;

import kr.co.kjc.settlement.global.dtos.member.MemberDTO;
import kr.co.kjc.settlement.global.enums.EnumJwtRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtClaimsDTO {

  private EnumJwtRole role;
  private MemberDTO member;

  public static JwtClaimsDTO of(EnumJwtRole role, MemberDTO member) {
    JwtClaimsDTO result = new JwtClaimsDTO();
    result.role = role;
    result.member = member;
    return result;
  }
}
