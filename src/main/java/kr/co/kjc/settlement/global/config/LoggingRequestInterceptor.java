package kr.co.kjc.settlement.global.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingRequestInterceptor implements HandlerInterceptor {

  private final Gson gson;

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
    final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

    String queryString = request.getQueryString();

    JsonObject requestJson = gson.fromJson(new String(cachingRequest.getContentAsByteArray()),
        JsonObject.class);
    JsonObject responseJson = gson.fromJson(new String(cachingResponse.getContentAsByteArray()),
        JsonObject.class);

    log.info(
        "\n[{}] {}" + "\n[PAYLOAD] {} " + " \n[RESPONSE] {} \n=================================",
        request.getMethod(),
        queryString == null ? request.getRequestURL()
            : request.getRequestURL() + "?" + queryString,
        requestJson, responseJson);
  }
}
