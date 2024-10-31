package kr.co.kjc.settlement.service.custom;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import kr.co.kjc.settlement.global.enums.EnumPaymentBroker;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.service.PaymentService;
import kr.co.kjc.settlement.service.impl.PortOneServiceImpl;
import kr.co.kjc.settlement.service.impl.TossPaymentsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceRouter {

  private static Map<EnumPaymentBroker, PaymentService> serviceMap = new HashMap<>();
  private final PortOneServiceImpl portOneService;
  private final TossPaymentsServiceImpl tossPaymentsService;

  @PostConstruct
  void init() {
    serviceMap.put(EnumPaymentBroker.PORT_ONE, portOneService);
    serviceMap.put(EnumPaymentBroker.TOSS_PAYMENTS, tossPaymentsService);
  }

  public PaymentService get(EnumPaymentBroker enumPaymentBroker) {
    return serviceMap.computeIfAbsent(enumPaymentBroker
        , (paymentBroker) -> {
          throw new BaseAPIException(EnumResponseCode.INVALID_PAYMENT_SERVICE);
        });
  }

}
