package kr.co.kjc.settlement.service;

import java.util.List;
import kr.co.kjc.settlement.global.dtos.S3ObjectDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

  List<S3ObjectDTO> getBuckits();

  List<String> uploadFile(MultipartFile multipartFile);

  String uploadObject(MultipartFile multipartFile);

  void download(String name);

}
