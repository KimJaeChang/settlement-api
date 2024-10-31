package kr.co.kjc.settlement.global.config.client.webclient;

import lombok.Getter;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import reactor.core.publisher.Mono;

public class CustomBodyInserter<T> implements BodyInserter<T, ReactiveHttpOutputMessage> {

  private final BodyInserter<T, ReactiveHttpOutputMessage> delegate;
  @Getter
  private final T body;

  public CustomBodyInserter(BodyInserter<T, ReactiveHttpOutputMessage> delegate, T body) {
    this.delegate = delegate;
    this.body = body;
  }

  @Override
  public Mono<Void> insert(ReactiveHttpOutputMessage outputMessage, Context context) {
    return this.delegate.insert(outputMessage, context);
  }
}