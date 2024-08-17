package kr.co.kjc.settlement.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseAPIExceptionDto {

  private HttpStatus code;

  private String detail;

  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
  private LocalDateTime timestamp;

  @Builder
  public BaseAPIExceptionDto(HttpStatus code, String detail) {
    this.code = code;
    this.detail = detail;
    this.timestamp = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
  }

  public static BaseAPIExceptionDto of(HttpStatus code, String detail) {
    return BaseAPIExceptionDto.builder()
        .code(code)
        .detail(detail)
        .build();
  }

}
