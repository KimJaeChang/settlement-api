package kr.co.kjc.settlement.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.global.utils.CommonUtils;
import kr.co.kjc.settlement.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

  @Value("${cloud.aws.s3.bucket")
  private String bucket;

  private final S3Client s3Client;

  @Override
  public List<String> uploadFile(MultipartFile multipartFile) {

    if (multipartFile.isEmpty()) {
      throw new BaseAPIException(EnumErrorCode.NOT_FOUND_UPLOAD_FILE);
    }

    File file = null;
    List<String> list = new LinkedList<>();

    try {
      file = CommonUtils.convertFile(multipartFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    RequestBody requestBody = RequestBody.fromFile(file);

    for (int i = 1; i < file.length(); i++) {
      UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
          .bucket(bucket)
          .key("/upload/v1")
          .partNumber(i)
          .uploadId(UUID.randomUUID().toString())
          .build();

      list.add(s3Client.uploadPart(uploadPartRequest, requestBody).eTag());
    }

    return list;
  }
}
