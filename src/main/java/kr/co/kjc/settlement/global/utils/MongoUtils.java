package kr.co.kjc.settlement.global.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import kr.co.kjc.settlement.global.dtos.mongo.MongoCommonReqDto;
import kr.co.kjc.settlement.global.dtos.mongo.MongoCriteriaDto;
import kr.co.kjc.settlement.global.dtos.mongo.MongoPaginationDto;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ObjectUtils;

@Slf4j
@UtilityClass
public class MongoUtils {

  public static Sort createSortByOrder(Order order) {
    return Sort.by(order);
  }

  public static Pageable createPageable(MongoPaginationDto mongoPagination, Sort sort) {
    if (Objects.nonNull(sort)) {
      return PageRequest.of(mongoPagination.getPage(), mongoPagination.getSize(), sort);
    } else {
      return PageRequest.of(mongoPagination.getPage(), mongoPagination.getSize());
    }
  }

  public static Criteria createAndOperateCriteria(Criteria beforeCriteria, Criteria addCriteria) {
    return beforeCriteria.andOperator(addCriteria);
  }

  public static Criteria createOrOperateCriteria(Criteria beforeCriteria, Criteria addCriteria) {
    return beforeCriteria.orOperator(addCriteria);
  }

  public static Query createQueryByPageableAndSort(Pageable pageable, Sort sort) {
    return new Query()
        .with(pageable)
        .with(sort)
        .limit(pageable.getPageSize());
  }

  public static Criteria createCriteriaById(String key, Object value) {
    return Criteria.where(key).is(value);
  }

  public static Query createQueryByCriteria(Criteria criteria) {
    return new Query(criteria);
  }

  public static Query createQueryByCriterias(List<MongoCriteriaDto> mongoCriteriaDtos) {
    Query query = new Query();

    mongoCriteriaDtos.forEach((mongoCriteriaDto) -> {
      Criteria criteria = createCriteriaById(mongoCriteriaDto.getId(), mongoCriteriaDto.getValue());
      query.addCriteria(criteria);
    });

    return query;
  }

  public static Update createUpdate() {
    return new Update();
  }

  @SneakyThrows
  public static Update createUpdateQuery(MongoCommonReqDto mongoCommonReqDto) {

    Update update = createUpdate();
    Field[] declaredFields = mongoCommonReqDto.getClass().getDeclaredFields();

    for (Field field : declaredFields) {
      Object value = null;
      field.setAccessible(true); // private Field일 경우 접근을 허용한다.

      try {
        // Field key 조회
        String key = field.getName();
        // Field Value 조회
        value = field.get(mongoCommonReqDto);
        if (!ObjectUtils.isEmpty(value)) {
          update.set(key, value);
        }
      } catch (IllegalArgumentException e) {
        log.error("MongoUtils createUpdateQuery Error ::", e);
        throw new IllegalAccessException(e.toString());
      }
    }

    return update;
  }

}
