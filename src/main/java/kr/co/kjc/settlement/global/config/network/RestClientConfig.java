package kr.co.kjc.settlement.global.config.network;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

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

  public static RestClient create(final String uri) {

    RestClient restClient = RestClient.builder().baseUrl(uri).build();
    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

    return factory.createClient(RestClient.class);
  }
}
