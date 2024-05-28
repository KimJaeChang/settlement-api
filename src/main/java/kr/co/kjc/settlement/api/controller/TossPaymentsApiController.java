package kr.co.kjc.settlement.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/toss-payments")
@RequiredArgsConstructor
public class TossPaymentsApiController {

  @GetMapping("/create-payment-key")
  public void createPaymentKey() {

  }

  @GetMapping("/error")
  public void error() {
    throw new RuntimeException("에러가 발생했습니다");
  }

}
