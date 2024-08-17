package kr.co.kjc.settlement.global.dtos.member;

import kr.co.kjc.settlement.domain.jpa.Member;
import lombok.Data;

@Data
public class MemberDTO {

  private Long id;
  private String uuid;
  private String userName;
  private String handPhone;

  public static MemberDTO toDto(Member member) {
    MemberDTO result = new MemberDTO();
    result.id = member.getId();
    result.uuid = member.getUuid();
    result.userName = member.getUserName();
    result.handPhone = member.getHandPhone();
    return result;
  }
}
