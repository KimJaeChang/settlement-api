package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.CommonCodeDTO;
import kr.co.kjc.settlement.global.dtos.request.BaseSearchDTO;
import org.springframework.data.domain.Page;

public interface CommonCodeService {

  Page<CommonCodeDTO.Items> findAll(BaseSearchDTO dto);

  Page<CommonCodeDTO.Items> findAllByParentCode(String parentCode);

  CommonCodeDTO.Item findById(Long id);

  CommonCodeDTO.Item findByChildCode(String childCode);
}
