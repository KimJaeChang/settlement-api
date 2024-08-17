package kr.co.kjc.settlement.service.impl;

import kr.co.kjc.settlement.service.SpringAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAIService implements SpringAIService {

  private final OpenAiChatModel openAiChatModel;

  @Override
  public String call(String message) {
    return openAiChatModel.call(message);
  }
}
