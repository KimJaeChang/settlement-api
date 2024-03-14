package kr.co.kjc.settlement.domain.payment.toss_payments.service;

import java.util.Map;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.enums.EnumPaymentBroker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceFactory {

  private static Map<EnumPaymentBroker, PaymentService> serviceMap;

  public void save(EnumPaymentBroker enumPaymentBroker) {
    serviceMap.computeIfAbsent(enumPaymentBroker
        , (paymentBroker) -> {
          throw new RuntimeException(EnumErrorCode.NOT_FOUND_ENUM.getDetail());
        });
  }

}
