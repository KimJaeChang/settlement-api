package kr.co.kjc.settlement.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.kjc.settlement.global.constants.TextConstants;
import kr.co.kjc.settlement.global.dtos.S3BucketDTO;
import kr.co.kjc.settlement.global.dtos.S3ObjectDTO;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.global.utils.CommonUtils;
import kr.co.kjc.settlement.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.AbortMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
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
  private final ListBucketsRequest listBucketsRequest;

  @Override
  public List<S3BucketDTO> getBuckits() {
    return s3Client.listBuckets(listBucketsRequest).buckets().stream()
        .map(S3BucketDTO::of)
        .collect(Collectors.toList());
  }

  @Override
  public List<S3ObjectDTO> getObjects(String bucketName) {

    ListObjectsV2Request request = ListObjectsV2Request.builder()
        .bucket(bucketName)
        .build();

    try {
      return s3Client.listObjectsV2(request).contents().stream()
          .map(S3ObjectDTO::of)
          .collect(Collectors.toList());
    } catch (AwsServiceException | SdkClientException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new BaseAPIException(EnumResponseCode.NOT_FOUND_S3_BUCKET);
    }
  }

  @Override
  public List<String> uploadFile(MultipartFile multipartFile) {

    if (multipartFile.isEmpty()) {
      throw new BaseAPIException(EnumResponseCode.NOT_FOUND_UPLOAD_FILE);
    }

    File file = null;
    List<String> list = new LinkedList<>();

    try {
      file = CommonUtils.convertFile(multipartFile);
    } catch (IOException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
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
        log.error(TextConstants.EXCEPTION_PREFIX, e);
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

    return s3Client.createMultipartUpload(createMultipartUploadRequest);
  }

  @Override
  public String uploadObject(MultipartFile multipartFile) {
    if (multipartFile.isEmpty()) {
      throw new BaseAPIException(EnumResponseCode.NOT_FOUND_UPLOAD_FILE);
    }

    File file = null;
    try {
      file = CommonUtils.convertFile(multipartFile);
    } catch (IOException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new RuntimeException(e);
    }

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucket)
        .key(path + multipartFile.getOriginalFilename())  // 경로 + 파일명
        .contentType(multipartFile.getContentType())
        .build();

    PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest,
        RequestBody.fromFile(file));

    file.delete();

    return putObjectResponse.eTag();
  }

  @Override
  public void download(String bucketName, String path, String fileName) {
    try {
      byte[] data = s3Client.getObjectAsBytes(builder -> {
        builder
            .bucket(bucketName)
            .key(path + fileName)
            .build();
      }).asByteArray();

      File file = new File(System.getProperty("user.dir") + "/downloads/" + fileName);
      try (FileOutputStream os = new FileOutputStream(file);) {
        os.write(data);
      } catch (IOException e) {
        log.error(TextConstants.EXCEPTION_PREFIX, e);
        throw new RuntimeException(e);
      }
    } catch (AwsServiceException | SdkClientException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new RuntimeException(e);
    }
  }

  /**
   * @param bucketName
   * @param key        : ex) v1/uploads/파일명.txt
   */
  @Override
  public boolean delete(String bucketName, String key) {
    DeleteObjectRequest request = DeleteObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();

    try {
      s3Client.deleteObject(request);
      return true;
    } catch (AwsServiceException | SdkClientException e) {
      log.error(TextConstants.EXCEPTION_PREFIX, e);
      throw new RuntimeException(e);
    }
  }
}
