package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.request.BaseSearchDTO;
import org.springframework.data.domain.Page;

public interface MemberService {

  Page<MemberDTO> findAll(BaseSearchDTO dto);

  MemberDTO findById(Long id);

  MemberDTO findOneByUuid(String uuid);

}
