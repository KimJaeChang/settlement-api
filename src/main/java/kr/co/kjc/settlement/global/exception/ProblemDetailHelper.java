package kr.co.kjc.settlement.global.exception;

import java.net.URI;
import java.util.List;
import java.util.Map;
import kr.co.kjc.settlement.global.enums.EnumMessageActionType;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * @implNote - ProblemDetail 알아가기(진행중)
 * @implSpec - ProblemDetail 생성을 도와주자
 * TODO - 더 우아하게1 ProblemDetail 살펴보기
 * TODO - 더 우아하게2 ErrorResponse 객체도 알아보기
 */
@Component
public class ProblemDetailHelper {

  /**
   * EnumErrorCode 로 에러 오브젝트 생성
   *
   * @param enumErrorCode
   * @return ProblemDetail
   */
  public ProblemDetail build(final EnumResponseCode enumErrorCode) {
    return build(enumErrorCode.getHttpStatus(), enumErrorCode.getDetail());
  }

  /**
   * HttpStatus, detail 로 에러 오브젝트 생성
   *
   * @param status
   * @param detail
   * @return ProblemDetail
   */
  public ProblemDetail build(final HttpStatus status, final String detail) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(status, StringUtils.normalizeSpace(detail));

    problemDetail.setProperty("notify", EnumMessageActionType.ALERT);
    return problemDetail;
  }

  /**
   * FieldError를 포함하여 에러 오브젝트 생성
   *
   * @param status
   * @param detail
   * @param errors
   * @return ProblemDetail
   */
  public ProblemDetail build(final HttpStatus status, final String detail,
      final List<Map<String, String>> errors) {

    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(status, StringUtils.normalizeSpace(detail));

    // Adds errors fields on validation errors, following RFC 9457 best practices.
    if (!CollectionUtils.isEmpty(errors)) {
      problemDetail.setProperty("errors", errors);
    }

    problemDetail.setProperty("notify", EnumMessageActionType.ALERT);

    return problemDetail;
  }

  /**
   * FieldError를 포함하여 에러 오브젝트 생성
   *
   * @param status
   * @param detail
   * @param request
   * @return ProblemDetail
   */
  public ProblemDetail build(final HttpStatus status, final String detail,
      final WebRequest request) {
    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(status, StringUtils.normalizeSpace(detail));

    if (request instanceof ServletWebRequest) {
      ServletWebRequest servletWebRequest = (ServletWebRequest) request;
      problemDetail.setInstance(URI.create(servletWebRequest.getRequest().getRequestURI()));
    }

    problemDetail.setProperty("notify", EnumMessageActionType.ALERT);

    return problemDetail;
  }

  public ProblemDetail build(final HttpStatus status, final String detail,
      final List<Map<String, String>> errors, final WebRequest request) {

    final ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(status, StringUtils.normalizeSpace(detail));

    if (request instanceof ServletWebRequest) {
      ServletWebRequest servletWebRequest = (ServletWebRequest) request;
      problemDetail.setInstance(URI.create(servletWebRequest.getRequest().getRequestURI()));
    }

    // Adds errors fields on validation errors, following RFC 9457 best practices.
    if (!CollectionUtils.isEmpty(errors)) {
      problemDetail.setProperty("errors", errors);
    }

    problemDetail.setProperty("notify", EnumMessageActionType.ALERT);

    return problemDetail;
  }
}
