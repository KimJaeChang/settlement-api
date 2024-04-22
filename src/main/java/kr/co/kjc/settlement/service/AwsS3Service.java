package kr.co.kjc.settlement.service;

import java.util.List;
import kr.co.kjc.settlement.global.dtos.S3BucketDTO;
import kr.co.kjc.settlement.global.dtos.S3ObjectDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

  List<S3BucketDTO> getBuckits();

  List<S3ObjectDTO> getObjects(String bucketName);

  List<String> uploadFile(MultipartFile multipartFile);

  String uploadObject(MultipartFile multipartFile);

  void download(String bucketName, String path, String fileName);

  void delete(String bucketName, String path, String fileName);

}
