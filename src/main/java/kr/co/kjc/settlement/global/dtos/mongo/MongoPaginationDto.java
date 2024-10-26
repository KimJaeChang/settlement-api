package kr.co.kjc.settlement.global.dtos.mongo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MongoPaginationDto {

  private Integer page;
  private Integer size;

  public static MongoPaginationDto of(int page, int size) {
    MongoPaginationDto result = new MongoPaginationDto();
    result.page = page;
    result.size = size;
    return result;
  }

}
