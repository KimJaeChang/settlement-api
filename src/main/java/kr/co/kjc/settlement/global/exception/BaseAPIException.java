package kr.co.kjc.settlement.global.exception;

import java.util.Objects;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * 기본 API Exception 처리
 */
@Getter
public class BaseAPIException extends ResponseStatusException {

  private EnumResponseCode enumResponseCode;
  private BaseAPIExceptionDto dto;

  // 표준 에러코드를 가지는지 확인
  public boolean isBaseErrorCodeType() {
    return !Objects.isNull(enumResponseCode);
  }

  // EnumErrorCode 형태 Exception
  public BaseAPIException(EnumResponseCode enumResponseCode) {
    super(enumResponseCode.getHttpStatus(), enumResponseCode.getDetail());
    this.enumResponseCode = enumResponseCode;
  }

  public BaseAPIException(BaseAPIExceptionDto dto) {
    super(dto.getCode(), dto.getDetail());
    this.dto = dto;
  }

  public BaseAPIException(HttpStatusCode status) {
    super(status);
  }


  public BaseAPIException(HttpStatusCode status, String reason) {
    super(status, reason);
  }

  public BaseAPIException(int rawStatusCode, String reason, Throwable cause) {
    super(rawStatusCode, reason, cause);
  }

  public BaseAPIException(HttpStatusCode status, String reason, Throwable cause) {
    super(status, reason, cause);
  }

  protected BaseAPIException(HttpStatusCode status, String reason, Throwable cause,
      String messageDetailCode, Object[] messageDetailArguments) {
    super(status, reason, cause, messageDetailCode, messageDetailArguments);
  }
}
