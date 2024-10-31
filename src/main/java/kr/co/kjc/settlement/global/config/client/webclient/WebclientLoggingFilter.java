package kr.co.kjc.settlement.global.config.client.webclient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@Slf4j
public class WebclientLoggingFilter {

  public static ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      logRequestHeader(clientRequest);
      return logRequestBody(clientRequest);
    });
  }

  public static ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      logResponseStatus(clientResponse);
      logResponseHeader(clientResponse);
      return logResponseBody(clientResponse);
    });
  }

  private static void logRequestHeader(ClientRequest clientRequest) {
    Map<String, Object> headers = new HashMap<>();
    clientRequest.headers().forEach((headerName, headerValue) -> {
      headers.put(headerName, clientRequest.headers().get(headerName));
    });
    log.info("Webclient_Request_Header : [{}]", headers);
  }

  private static Mono<ClientRequest> logRequestBody(ClientRequest clientRequest) {

    BodyInserter<?, ? super ClientHttpRequest> bodyInserter = clientRequest.body();
    if (bodyInserter instanceof CustomBodyInserter<?> customBodyInserter) {
      log.info("Webclient_Request_Body : [{}]", customBodyInserter.getBody());

      ClientRequest newClientRequest = ClientRequest.from(clientRequest)
          .body(bodyInserter)
          .build();

      return Mono.just(newClientRequest);
    }

    return Mono.just(clientRequest);
  }

  private static void logResponseStatus(ClientResponse clientResponse) {
    log.info("Webclient_Response_Status : [{}] [{}]", clientResponse.statusCode().value(),
        clientResponse.statusCode());
  }

  private static void logResponseHeader(ClientResponse clientResponse) {
    Map<String, Object> headers = new HashMap<>();
    clientResponse.headers().asHttpHeaders().forEach((headerName, headerValue) -> {
      headers.put(headerName, clientResponse.headers().asHttpHeaders().get(headerName));
    });
    log.info("Webclient_Response_Header : [{}]", headers);
  }

  private static Mono<ClientResponse> logResponseBody(ClientResponse clientResponse) {

    clientResponse.bodyToMono(String.class).flatMap((body) -> {
      byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
      String bodyToString = new String(bytes, StandardCharsets.UTF_8);

      log.info("Webclient_Response_Body : [{}]", bodyToString);

      ClientResponse newClientResponse = ClientResponse.create(clientResponse.statusCode())
          .body(bodyToString)
          .build();

      return Mono.just(newClientResponse);
    });

    return Mono.just(clientResponse);
  }

}
