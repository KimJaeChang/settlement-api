package kr.co.kjc.settlement.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Collectors;
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

  @GetMapping("/v1/toss-payments/payment-key/return")
  public String tossPaymentKeyReturn(HttpServletRequest request) {

    System.out.println("\t");
    System.out.println("request : " + request.getParameterNames());
    System.out.println("\t");

    Arrays.stream(new Enumeration[]{request.getParameterNames()})
        .collect(Collectors.toList())
        .forEach((f) -> {
          System.out.println("\t");
          System.out.println("f : " + f);
          System.out.println("\t");
        });

    return null;
  }

}
