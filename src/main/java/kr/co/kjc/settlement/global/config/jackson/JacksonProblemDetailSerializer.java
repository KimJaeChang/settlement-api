package kr.co.kjc.settlement.global.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.http.ProblemDetail;

public class JacksonProblemDetailSerializer extends JsonSerializer<ProblemDetail> {

  private static final String FIELD_TYPE = "type";
  private static final String FIELD_TITLE = "title";
  private static final String FIELD_STATUS = "status";
  private static final String FIELD_DETAIL = "detail";
  private static final String FIELD_INSTANCE = "instance";

  @Override
  public void serialize(ProblemDetail value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField(FIELD_TYPE, value.getType().toString());
    gen.writeStringField(FIELD_TITLE, value.getTitle());
    gen.writeNumberField(FIELD_STATUS, value.getStatus());
    gen.writeStringField(FIELD_DETAIL, value.getDetail());
    gen.writeStringField(FIELD_INSTANCE, String.valueOf(value.getInstance()));
    gen.writeEndObject();
  }
}
