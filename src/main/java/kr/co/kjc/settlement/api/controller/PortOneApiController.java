package kr.co.kjc.settlement.api.controller;

import kr.co.kjc.settlement.global.enums.EnumPaymentBroker;
import kr.co.kjc.settlement.service.PaymentService;
import kr.co.kjc.settlement.service.custom.PaymentServiceRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/port-one")
@RequiredArgsConstructor
public class PortOneApiController {

  private final PaymentServiceRouter paymentServiceRouter;

  @GetMapping("/create-payment-key")
  public void createPaymentKey() {
    PaymentService paymentService = paymentServiceRouter.get(EnumPaymentBroker.PORT_ONE);
  }

  @GetMapping("/error")
  public void error() {
    throw new RuntimeException("에러가 발생했습니다");
  }

}
