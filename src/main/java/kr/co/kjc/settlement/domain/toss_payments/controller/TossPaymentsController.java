package kr.co.kjc.settlement.domain.toss_payments.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/toss-payments")
public class TossPaymentsController {

  @GetMapping("/")
  public String index() {
    return "toss_payments/index";
  }

}
