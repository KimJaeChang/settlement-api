package kr.co.kjc.settlement.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.kjc.settlement.domain.jpa.CommonCode;
import kr.co.kjc.settlement.global.dtos.CommonCodeDTO.Item;
import kr.co.kjc.settlement.global.dtos.request.BaseSearchDTO;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.repository.jpa.CommonCodeJpaRepository;
import kr.co.kjc.settlement.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {

  private final CommonCodeJpaRepository commonCodeJpaRepository;

  @Override
  public Page<Item> findAll(BaseSearchDTO dto) {
    return commonCodeJpaRepository.findAll(PageRequest.of(dto.getPageNo(), dto.getPageSize()))
        .map(Item::toDto);
  }

  // .findBy(example, FetchableFluentQuery::all)
  @Override
  public Page<Item> findAllByParentCode(String parentCode) {

    Example<CommonCode> example = Example.of(
        CommonCode.createExampleByParentCode(parentCode));

    List<Item> result = commonCodeJpaRepository.findAll(example).stream()
        .map(Item::toDto)
        .collect(Collectors.toList());

    return new PageImpl<>(result);
  }

  @Override
  public Item findOneById(Long id) {

    CommonCode commonCode = commonCodeJpaRepository.findById(id)
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_COMMON_CODE));

    return Item.toDto(commonCode);
  }

  @Override
  public Item findOneByChildCode(String childCode) {

    Example<CommonCode> example = Example.of(
        CommonCode.createExampleByChildCode(childCode));

    CommonCode commonCode = commonCodeJpaRepository.findOne(example)
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_COMMON_CODE));

    return Item.toDto(commonCode);
  }
}
