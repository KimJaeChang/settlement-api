package kr.co.kjc.settlement.global.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class S3ObjectDTO {

  private String key;
  private LocalDateTime lastModified;
  private String eTag;
  private String storageClass;
  private Integer size;
}
