package kr.co.kjc.settlement.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
import software.amazon.awssdk.services.s3.model.AbortMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${spring.cloud.aws.s3.path}")
  private String path;

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

    long partSize = 5 * 1024 * 1024; // 5MB 파트 사이즈
    long fileLength = file.length();
    long numParts = (fileLength + partSize - 1) / partSize; // 필요한 파트 수 계산

    CreateMultipartUploadResponse response = multipartUpload(s3Client);
    for (int i = 1; i <= numParts; i++) {
      long startPos = (i - 1) * partSize;
      long curPartSize = Math.min(partSize, (fileLength - startPos));

      try (FileInputStream fis = new FileInputStream(file)) {
        fis.skip(startPos);
        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
            .bucket(bucket)
            .key(path)
            .uploadId(response.uploadId())
            .partNumber(i)
            .build();
        UploadPartResponse uploadPartResponse = s3Client.uploadPart(uploadPartRequest,
            RequestBody.fromInputStream(fis, curPartSize));
        list.add(uploadPartResponse.eTag());
      } catch (IOException e) {
        s3Client.abortMultipartUpload(AbortMultipartUploadRequest.builder()
            .bucket(bucket)
            .key(path)
            .uploadId(response.uploadId())
            .build());
        throw new RuntimeException("Failed to upload part: " + i, e);
      }
    }

    file.delete(); // 파일 정리

    return list;
  }

  private CreateMultipartUploadResponse multipartUpload(S3Client s3Client) {
    CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
        .bucket(bucket)
        .key(path)
        .build();

    return s3Client.createMultipartUpload(
        createMultipartUploadRequest);
  }

//  @Override
//  public PutObjectResponse uploadFile(MultipartFile multipartFile) {
//    if (multipartFile.isEmpty()) {
//      throw new BaseAPIException(EnumErrorCode.NOT_FOUND_UPLOAD_FILE);
//    }
//
//    File file = null;
//    try {
//      file = CommonUtils.convertFile(multipartFile);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//
//    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//        .bucket(bucket)
//        .key(multipartFile.getOriginalFilename())
//        .contentType(multipartFile.getContentType())
//        .build();
//
//    PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest,
//        RequestBody.fromFile(file));
//
//    file.delete();
//
//    return putObjectResponse;
//  }
}
