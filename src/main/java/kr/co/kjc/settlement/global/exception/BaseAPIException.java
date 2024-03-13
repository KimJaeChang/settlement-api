package kr.co.kjc.settlement.global.exception;

import java.util.Objects;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * 기본 API Exception 처리
 */
@Getter
public class BaseAPIException extends ResponseStatusException {

  private EnumErrorCode enumErrorCode;

  // 표준 에러코드를 가지는지 확인
  public boolean isBaseErrorCodeType() {
    return !Objects.isNull(enumErrorCode);
  }

  // EnumErrorCode 형태 Exception
  public BaseAPIException(EnumErrorCode enumErrorCode) {
    super(enumErrorCode.getHttpStatus(), enumErrorCode.getDetail());
    this.enumErrorCode = enumErrorCode;
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
