package kr.co.kjc.settlement.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

  List<String> uploadFile(MultipartFile multipartFile);

}
