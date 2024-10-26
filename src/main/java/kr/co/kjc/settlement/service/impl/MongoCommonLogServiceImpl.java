package kr.co.kjc.settlement.service.impl;

import kr.co.kjc.settlement.global.dtos.mongo.MongoCommonReqDto;
import kr.co.kjc.settlement.global.enums.EnumMongoDataBase;
import kr.co.kjc.settlement.repository.mongo.MongoRepository;
import kr.co.kjc.settlement.service.MongoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoCommonLogServiceImpl implements MongoService {

  private final MongoRepository mongoRepository;

  @Override
  public void save(MongoCommonReqDto dto) {
    mongoRepository.getMongoTemplate(EnumMongoDataBase.COMMON_LOG).save(dto);
  }
}
