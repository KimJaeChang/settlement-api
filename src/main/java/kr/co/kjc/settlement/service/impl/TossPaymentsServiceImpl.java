package kr.co.kjc.settlement.service.impl;

import kr.co.kjc.settlement.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TossPaymentsServiceImpl implements PaymentService {

  @Value("${service.payments.toss-payments.keys.api.client-key}")
  private String clientKey;

  @Value("${service.payments.toss-payments.keys.api.secret-key}")
  private String secretKey;

  @Override
  public void payment() {

//    WebClientConfig.getBaseUri();

  }

  @Override
  public void cancelPayment() {

  }

  @Override
  public void unPayment() {

  }
}
