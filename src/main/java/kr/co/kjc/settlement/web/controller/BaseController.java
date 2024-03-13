package kr.co.kjc.settlement.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/v1/toss-payments")
  public String tossPayments() {
    return "/toss_payments/checkout";
  }

}
