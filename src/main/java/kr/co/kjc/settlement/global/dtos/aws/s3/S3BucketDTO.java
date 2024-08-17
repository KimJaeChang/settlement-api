package kr.co.kjc.settlement.global.dtos.aws.s3;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.services.s3.model.Bucket;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class S3BucketDTO {

  private String name;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
  private LocalDateTime creationDate;

  public static S3BucketDTO of(Bucket bucket) {
    S3BucketDTO result = new S3BucketDTO();
    result.name = bucket.name();
    result.creationDate = LocalDateTime.ofInstant(bucket.creationDate(), ZoneId.of("Asia/Seoul"));
    return result;
  }

}
