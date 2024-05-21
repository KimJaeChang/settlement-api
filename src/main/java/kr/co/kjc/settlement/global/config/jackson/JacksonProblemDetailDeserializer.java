package kr.co.kjc.settlement.global.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import kr.co.kjc.settlement.global.exception.ProblemDetailHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@RequiredArgsConstructor
public class JacksonProblemDetailDeserializer extends JsonDeserializer<ProblemDetail> {

  private final ProblemDetailHelper helper;

  @Override
  public ProblemDetail deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    // Implement your deserialization logic here
    // This example assumes a simple case without nested objects
    // Adjust accordingly to your specific use case
    JsonNode node = p.getCodec().readTree(p);
    String type = node.get("type").asText();
    String title = node.get("title").asText();
    int status = node.get("status").asInt();
    String detail = node.get("detail").asText();
    String instance = node.get("instance").asText();

    return helper.build(HttpStatus.OK, "");
  }
}
