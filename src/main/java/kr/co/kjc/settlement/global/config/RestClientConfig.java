package kr.co.kjc.settlement.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

  private static final int CONNECTION_TIMEOUT = 60000;
  private static final int READ_TIMEOUT = 60000;

  public static RestClient getBaseUri(final String uri) {
    return RestClient.create(uri)
        .mutate()
        .build();
  }
}
