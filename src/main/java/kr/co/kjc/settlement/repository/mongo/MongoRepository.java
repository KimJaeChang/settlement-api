package kr.co.kjc.settlement.repository.mongo;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import kr.co.kjc.settlement.global.enums.EnumMongoDataBase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoRepository {

  @Qualifier("mongoCommonLogTemplate")
  private final MongoTemplate mongoCommonLogTemplate;

  private static Map<EnumMongoDataBase, MongoTemplate> mongoTemplateMap = new HashMap<>();

  @PostConstruct
  void init() {
    mongoTemplateMap.put(EnumMongoDataBase.COMMON_LOG, mongoCommonLogTemplate);
  }

  public MongoTemplate getMongoTemplate(EnumMongoDataBase enumMongoDataBase) {
    return mongoTemplateMap.get(enumMongoDataBase);
  }

}
