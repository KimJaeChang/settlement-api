package kr.co.kjc.settlement.service.impl;

import kr.co.kjc.settlement.service.SpringAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VertexGeminiAIService implements SpringAIService {

  private final VertexAiGeminiChatModel vertexAiGeminiChatModel;

  @Override
  public String call(String message) {
    return vertexAiGeminiChatModel.call(message);
  }
}
