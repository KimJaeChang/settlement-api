package kr.co.kjc.settlement.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity<ProblemDetail> handleIllegalArgumentException(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage()));
  }

}
