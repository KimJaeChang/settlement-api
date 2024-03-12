package kr.co.kjc.settlement.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/toss-payments")
  public String tossPayments() {
    return "toss_payments/index";
  }

}
