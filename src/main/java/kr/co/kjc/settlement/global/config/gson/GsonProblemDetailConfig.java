package kr.co.kjc.settlement.global.config.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collections;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ProblemDetail;

public class GsonProblemDetailConfig implements JsonSerializer<ProblemDetail> {

  private static final String FIELD_TYPE = "type";
  private static final String FIELD_TITLE = "title";
  private static final String FIELD_STATUS = "status";
  private static final String FIELD_DETAIL = "detail";
  private static final String FIELD_INSTANCE = "instance";

  @Override
  public JsonElement serialize(ProblemDetail detail, Type type, JsonSerializationContext context) {
    JsonObject json = new JsonObject();
    json.add(FIELD_TYPE, context.serialize(detail.getType()));
    json.addProperty(FIELD_TITLE, detail.getTitle());
    json.addProperty(FIELD_STATUS, detail.getStatus());
    json.addProperty(FIELD_DETAIL, detail.getDetail());
    json.add(FIELD_INSTANCE, context.serialize(detail.getInstance()));

    if (!CollectionUtils.isEmpty(Collections.singleton(detail.getProperties()))) {
      detail.getProperties().forEach((name, value) -> json.add(name, context.serialize(value)));
    }

    return json;
  }
}
