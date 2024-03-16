package kr.co.kjc.settlement.global.config.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GsonLocalDateConfig implements JsonSerializer<LocalDate>,
    JsonDeserializer<LocalDate> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

  @Override
  public JsonElement serialize(final LocalDate date, final Type typeOfSrc,
      final JsonSerializationContext context) {
    return new JsonPrimitive(formatter.format(date));
  }

  @Override
  public LocalDate deserialize(final JsonElement json, final Type typeOfT,
      final JsonDeserializationContext context) throws JsonParseException {
    return LocalDate.parse(json.getAsString(), formatter);
  }
}
