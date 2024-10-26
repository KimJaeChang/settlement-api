package kr.co.kjc.settlement.service;

import kr.co.kjc.settlement.global.dtos.mongo.MongoCommonReqDto;

public interface MongoService {

  public void save(MongoCommonReqDto dto);

}
