package kr.co.kjc.settlement.global.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.services.s3.model.S3Object;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class S3ObjectDTO {

  private String key;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
  private LocalDateTime lastModified;
  private String eTag;
  private String storageClass;
  private Long size;

  public static S3ObjectDTO of(S3Object s3Object) {
    S3ObjectDTO result = new S3ObjectDTO();
    result.key = s3Object.key();
    result.lastModified = LocalDateTime.ofInstant(s3Object.lastModified(), ZoneId.of("Asia/Seoul"));
    result.eTag = s3Object.eTag().replaceAll("\"", "");
    result.storageClass = s3Object.storageClassAsString();
    result.size = s3Object.size();
    return result;
  }

}
