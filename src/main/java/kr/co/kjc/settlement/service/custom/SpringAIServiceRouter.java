package kr.co.kjc.settlement.service.custom;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;
import kr.co.kjc.settlement.global.enums.EnumSpringAICode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.service.SpringAIService;
import kr.co.kjc.settlement.service.impl.OpenAIService;
import kr.co.kjc.settlement.service.impl.VertexGeminiAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringAIServiceRouter {

  private static Map<EnumSpringAICode, SpringAIService> serviceMap = new HashMap<>();

  private final OpenAIService openAIService;
  private final VertexGeminiAIService vertexGeminiAIService;

  @PostConstruct
  void init() {
    serviceMap.put(EnumSpringAICode.OPEN_AI, openAIService);
    serviceMap.put(EnumSpringAICode.GEMINI_AI, vertexGeminiAIService);
  }

  public SpringAIService get(EnumSpringAICode enumSpringAICode) {
    return serviceMap.computeIfAbsent(enumSpringAICode
        , (springAICode) -> {
          throw new BaseAPIException(EnumResponseCode.NOT_FOUND_SPRING_AI_SERVICE);
        });
  }

}
