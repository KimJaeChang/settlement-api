package kr.co.kjc.settlement.global.config.network;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

  private static final int CONNECTION_TIMEOUT = 60000;
  private static final int READ_TIMEOUT = 60000;
  private static final int WRITE_TIMEOUT = 60000;

  public static WebClient getBaseUri(final String uri) {

    ConnectionProvider provider = ConnectionProvider.builder("settlement")
        .maxIdleTime(Duration.ofMillis(3000))
        .maxLifeTime(Duration.ofMillis(3000))
        .build();

    HttpClient httpClient = HttpClient.create(provider)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECTION_TIMEOUT)
        .secure(t -> {
          try {
            SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
          } catch (SSLException e) {
            throw new RuntimeException(e);
          }
        })
        .doOnConnected(conn ->
            conn.addHandlerLast(new ReadTimeoutHandler(READ_TIMEOUT, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(WRITE_TIMEOUT, TimeUnit.MILLISECONDS))
        );

    return WebClient.builder()
        .baseUrl(uri)
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build()
        .mutate()
        .build();
  }
}
