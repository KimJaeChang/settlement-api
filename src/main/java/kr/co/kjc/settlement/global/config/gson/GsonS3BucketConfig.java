package kr.co.kjc.settlement.global.config.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import kr.co.kjc.settlement.global.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.S3Object;

@Slf4j
@RequiredArgsConstructor
public class GsonS3BucketConfig implements JsonSerializer<S3Object> {

  @Override
  public JsonElement serialize(S3Object src, Type type,
      JsonSerializationContext jsonSerializationContext) {

    JsonArray jsonArray = new JsonArray();
    JsonObject jsonObject = new JsonObject();

//    s3Objects
//        .forEach(s3Object -> {
//          LocalDateTime localDateTime = LocalDateTime.ofInstant(s3Object.lastModified(),
//              ZoneId.of("Asia/Seoul"));
//
//          String lastModified = DateTimeUtils.formatLocalDateTime(localDateTime,
//              DateTimeFormatter.ISO_DATE_TIME.toString());
//
//          jsonObject.addProperty("key", s3Object.key());
//          jsonObject.addProperty("lastModified", lastModified);
//          jsonObject.addProperty("eTag", s3Object.eTag());
//          jsonObject.addProperty("storageClass", s3Object.storageClassAsString());
//          jsonArray.add(jsonObject);
//        });
//
//    return jsonArray;

    LocalDateTime localDateTime = LocalDateTime.ofInstant(src.lastModified(),
        ZoneId.of("Asia/Seoul"));

    String lastModified = DateTimeUtils.formatLocalDateTime(localDateTime,
        DateTimeFormatter.ISO_DATE_TIME.toString());

    jsonObject.addProperty("key", src.key());
    jsonObject.addProperty("lastModified", lastModified);
    jsonObject.addProperty("eTag", src.eTag());
    jsonObject.addProperty("storageClass", src.storageClassAsString());
    jsonObject.addProperty("size", src.size());

    return jsonObject;
  }
}
