package kr.co.kjc.settlement.domain.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/toss-payments")
public class TossPaymentsApiController {

  @GetMapping("/error")
  public void error() {
    throw new RuntimeException("에러가 발생했습니다");
  }

}
