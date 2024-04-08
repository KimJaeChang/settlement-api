package kr.co.kjc.settlement.global.dtos;

import kr.co.kjc.settlement.domain.Member;
import lombok.Data;

@Data
public class MemberDTO {

  private Long id;
  private String userName;
  private String handPhone;

  public static MemberDTO toDto(Member member) {
    MemberDTO result = new MemberDTO();
    result.id = member.getId();
    result.userName = member.getUserName();
    result.handPhone = member.getHandPhone();
    return result;
  }
}
