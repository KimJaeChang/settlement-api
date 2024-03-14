package kr.co.kjc.settlement.global.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
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

    public static WebClient getBaseUri(final String uri) {

        ConnectionProvider provider = ConnectionProvider.builder("settlement")
                .maxIdleTime(Duration.ofMillis(3000))
                .maxLifeTime(Duration.ofMillis(3000))
                .build();

        HttpClient httpClient = HttpClient.create(provider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECTION_TIMEOUT)
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(READ_TIMEOUT, TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .baseUrl(uri)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build()
                .mutate()
                .build();
    }
}
