package kr.co.kjc.settlement.global.dtos.mongo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MongoCommonReqDto {

  private Object data;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createdAt;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime updatedAt;

  public static MongoCommonReqDto of(Object data) {
    MongoCommonReqDto result = new MongoCommonReqDto();
    result.data = data;
    result.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    result.updatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    return result;
  }

  public static MongoCommonReqDto createByUpdateDto(Object data, LocalDateTime updatedAt) {
    MongoCommonReqDto result = new MongoCommonReqDto();
    result.data = data;
    result.updatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    return result;
  }

}
