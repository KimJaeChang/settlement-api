package kr.co.kjc.settlement.service.impl;

import kr.co.kjc.settlement.domain.jpa.Member;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.request.BaseSearchDTO;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.repository.jpa.MemberJpaRepository;
import kr.co.kjc.settlement.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberJpaRepository memberJpaRepository;

  @Override
  public Page<MemberDTO> findAll(BaseSearchDTO dto) {
    return memberJpaRepository.findAll(PageRequest.of(dto.getPageNo(), dto.getPageSize()))
        .map(MemberDTO::toDto);
  }

  @Override
  public MemberDTO findById(Long id) {
    return MemberDTO.toDto(memberJpaRepository.findById(id)
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_MEMBER)));
  }

  @Override
  public MemberDTO findByUuid(String uuid) {
    Example<Member> example = Example.of(Member.createExampleByUuid(uuid));
    
    return MemberDTO.toDto(memberJpaRepository.findOne(example)
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_MEMBER)));
  }
}
