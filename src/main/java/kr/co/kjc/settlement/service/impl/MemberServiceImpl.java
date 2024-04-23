package kr.co.kjc.settlement.service.impl;

import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.request.BaseSearchDTO;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.repository.MemberRepository;
import kr.co.kjc.settlement.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  @Override
  public Page<MemberDTO> findAll(BaseSearchDTO dto) {
    return memberRepository.findAll(PageRequest.of(dto.getPageNo(), dto.getPageSize()))
        .map(MemberDTO::toDto);
  }

  @Override
  public MemberDTO findById(Long id) {
    return MemberDTO.toDto(memberRepository.findById(id)
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_MEMBER)));
  }
}
