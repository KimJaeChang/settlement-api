package kr.co.kjc.settlement.global.dtos.mongo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MongoCriteriaDto {

  private String id;
  private Object value;

  public static MongoCriteriaDto of(String id, Object value) {
    MongoCriteriaDto result = new MongoCriteriaDto();
    result.id = id;
    result.value = value;
    return result;
  }
}
