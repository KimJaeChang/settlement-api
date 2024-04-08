package kr.co.kjc.settlement.global.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * Local CSMS API 표준응답 DTO
 *
 * @param <T>
 */
@Getter
@Setter
@ToString
@Schema(description = "API 표준 응답 DTO")
public class BaseResponseDTO<T> {

  // 추후 필요할 때 정의하자.
  // private String result;
  private int code = HttpStatus.OK.value();
  private String message = "";
  private LocalDateTime timestamp;

  // 표준 응답 Body (DTO가 유동적이므로 제네릭을 사용한다.)
  // @SerializedName("body")
  private T body;

  public BaseResponseDTO() {
    this.message = "success";
    this.timestamp = LocalDateTime.now();
  }

  public BaseResponseDTO(T dataBody) {
    this.body = dataBody;
    this.timestamp = LocalDateTime.now();
  }

  public BaseResponseDTO(T dataBody, int code) {
    this.body = dataBody;
    this.code = code;
    this.timestamp = LocalDateTime.now();
  }

  public BaseResponseDTO(T dataBody, int code, String message) {
    this.body = dataBody;
    this.code = code;
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }

  public BaseResponseDTO(T dataBody, int code, String message, LocalDateTime timestamp) {
    this.body = dataBody;
    this.code = code;
    this.message = message;
    this.timestamp = timestamp;
  }

}
