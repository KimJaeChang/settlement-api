package kr.co.kjc.settlement.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kr.co.kjc.settlement.global.constants.TextConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@RestControllerAdvice(basePackages = "kr.co.kjc.settlement")
@RequiredArgsConstructor
public class BaseAPIControllerAdvice extends ResponseEntityExceptionHandler {

  private final ProblemDetailHelper helper;

  /**
   * 새로 정의한 API Exception
   */
  @ExceptionHandler(BaseAPIException.class)
  public ProblemDetail handleBaseAPIException(BaseAPIException e) {
    log.error(TextConstants.EXCEPTION_PREFIX, e);

    if (e.isBaseErrorCodeType()) {
      // 고정된 메시지일 경우
      return helper.build(e.getEnumErrorCode());
    } else {
      // 메시지가 동적으로 만들어질 필요가 있을 경우
      return helper.build(HttpStatus.resolve(e.getStatusCode().value()), e.getLocalizedMessage());
    }
  }


  /**
   * Not Valid Exception
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    printRequestLogging();
    log.error(TextConstants.EXCEPTION_PREFIX, e);
    List<Map<String, String>> errors = new ArrayList<>();

    // TODO 고민해보기 : A/B 테스트 필요
    //       A : 기존 방식(필드에러와 글로벌에러 각각 불러오기)
    //       B : ex.getAllErrors()로 한번에 조회해도 결과가 같을까?
    // 필드 에러 정의
    e.getBindingResult().getFieldErrors().forEach(error -> {
      // FIXME 에러발생 필드를 key로 활용하는 게 좋을까?
      //      아니면 별도 오브젝트로 관리하는 게 좋을까?
      //      예시) {"pointer":"email","reason":"잘 구성된 이메일 주소여야 합니다."}
      errors.add(Collections.singletonMap(error.getField(), error.getDefaultMessage()));
    });
    // 글로벌 에러 정의
    e.getBindingResult().getGlobalErrors().forEach(error -> {
      errors.add(Collections.singletonMap(error.getObjectName(), error.getDefaultMessage()));
    });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(helper.build(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", errors));
  }

  @ExceptionHandler(ResponseStatusException.class)
  protected ProblemDetail handleResponseStatusException(final ResponseStatusException e) {
    printRequestLogging();
    log.error(TextConstants.EXCEPTION_PREFIX, e);
    return helper.build(HttpStatus.resolve(e.getStatusCode().value()), e.getMessage());
  }

  /*
   * 정의되지 않은 일반 에러 처리
   */
  @ExceptionHandler(Exception.class)
  protected ProblemDetail handleAllException(final Exception e, WebRequest request) {
    log.error(TextConstants.EXCEPTION_PREFIX, e);
    // return this.createProblemDetail(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null, null, request);
    return helper.build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request);
  }

  /**
   * handleMethodArgumentNotValid 인 경우에는 AOP 작동이 되지 않아 REQUEST 로깅 시 아래 로직이 필요
   */
  private void printRequestLogging() {
    HttpServletRequest servletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) servletRequest;

    String queryString = (cachingRequest.getQueryString() != null ? cachingRequest.getQueryString() + System.lineSeparator() : StringUtils.EMPTY);
    log.info(TextConstants.LOGGING_PREFIX_REQUEST, queryString + cachingRequest.getContentAsString());


  }

}
