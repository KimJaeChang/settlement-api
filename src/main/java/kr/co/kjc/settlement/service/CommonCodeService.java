package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.code.CommonCodeDTO;
import kr.co.kjc.settlement.global.dtos.common.BaseSearchDTO;
import org.springframework.data.domain.Page;

public interface CommonCodeService {

  Page<CommonCodeDTO.Item> findAll(BaseSearchDTO dto);

  Page<CommonCodeDTO.Item> findAllByParentCode(String parentCode);

  CommonCodeDTO.Item findOneById(Long id);

  CommonCodeDTO.Item findOneByChildCode(String childCode);
}
