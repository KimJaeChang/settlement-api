package kr.co.kjc.settlement.service.custom;

import java.util.Map;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.enums.EnumPaymentBroker;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceFactory {

  private static Map<EnumPaymentBroker, PaymentService> serviceMap;

  public PaymentService get(EnumPaymentBroker enumPaymentBroker) {
    return serviceMap.computeIfAbsent(enumPaymentBroker
        , (paymentBroker) -> {
          throw new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM);
        });
  }

}
