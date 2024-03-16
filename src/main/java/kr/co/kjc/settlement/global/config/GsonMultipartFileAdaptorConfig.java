package kr.co.kjc.settlement.global.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.springframework.web.multipart.MultipartFile;

public class GsonMultipartFileAdaptorConfig implements JsonSerializer<MultipartFile>,
    JsonDeserializer<MultipartFile> {

  @Override
  public JsonElement serialize(MultipartFile src, Type typeOfSrc,
      JsonSerializationContext context) {
    // MultipartFile을 원하는 형식으로 직렬화하는 코드 작성
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("filename", src.getOriginalFilename());
    jsonObject.addProperty("contentType", src.getContentType());
    // 필요한 경우 다른 필드 추가
    return jsonObject;
  }

  @Override
  public MultipartFile deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {
    return null;
  }
}
