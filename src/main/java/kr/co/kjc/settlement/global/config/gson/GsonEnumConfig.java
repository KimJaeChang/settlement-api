package kr.co.kjc.settlement.global.config.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import kr.co.kjc.settlement.global.bean.EnumMapper;
import kr.co.kjc.settlement.global.dtos.CodeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GsonEnumConfig implements JsonDeserializer<CodeDTO.Item> {

  private final EnumMapper enumMapper;

  @Override
  public CodeDTO.Item deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context)
      throws JsonParseException {
    String enumValue = String.valueOf(json.getAsJsonObject().get("enumValue").getAsString());
    return enumMapper.findOneByValue(enumValue);
  }
}
