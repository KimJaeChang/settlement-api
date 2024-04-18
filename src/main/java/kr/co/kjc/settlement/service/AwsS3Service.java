package kr.co.kjc.settlement.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public interface AwsS3Service {

  PutObjectResponse uploadFile(MultipartFile multipartFile);

}
