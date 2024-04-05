package kr.co.kjc.settlement.global.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.co.kjc.settlement.global.constants.TextConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class LoggingServletWrappingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain)
      throws ServletException, IOException {

    // 서블릿 요청의 request/response 로깅을 위해 wrapping
    var wrappingRequest = new ContentCachingRequestWrapper(request);
    var wrappingResponse = new ContentCachingResponseWrapper(response);

    long start = System.currentTimeMillis();
    chain.doFilter(wrappingRequest, wrappingResponse);
    long end = System.currentTimeMillis();
    // NOTE 로깅 처리
    printRequestApiLog(wrappingRequest, wrappingResponse, (end - start));

    wrappingResponse.copyBodyToResponse();
  }

  private void printRequestApiLog(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long elapsedTime) {

    log.info(TextConstants.LOGGING_FORMAT,
        request.getMethod(),
        request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""),
        response.getStatus(),
        elapsedTime,
        request.getContentAsString(),
        new String(response.getContentAsByteArray()));
  }
}