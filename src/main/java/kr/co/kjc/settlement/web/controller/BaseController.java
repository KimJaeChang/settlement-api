package kr.co.kjc.settlement.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Collectors;
import kr.co.kjc.settlement.global.annotation.JwtAuthorization;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.dtos.response.BaseResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BaseController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @ResponseBody
  @GetMapping("/health-check")
  public BaseResponseDTO<?> healthCheck(@JwtAuthorization MemberDTO memberDTO) {
    return new BaseResponseDTO<>("success");
  }

  @GetMapping("/v1/toss-payments")
  public String tossPayments() {
    return "/toss_payments/main";
  }

  @GetMapping("/v1/port-one")
  public String portOne() {
    return "/port_one/main";
  }

  @GetMapping("/v1/toss-payments/success")
  public String tossPaymentKeyReturnSuccess(HttpServletRequest request) {

    System.out.println("\t");
    System.out.println("success-request : " + request.getParameterNames());
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

  @GetMapping("/v1/toss-payments/fail")
  public String tossPaymentKeyReturnFail(HttpServletRequest request) {

    System.out.println("\t");
    System.out.println("fail-request : " + request.getParameterNames());
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
