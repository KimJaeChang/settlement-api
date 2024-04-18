package kr.co.kjc.settlement.global.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class CommonUtils {

  public static File convertFile(MultipartFile multipartFile) throws IOException {
    String ext = multipartFile.getOriginalFilename()
        .substring(multipartFile.getOriginalFilename().lastIndexOf('.'));
    String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;

    File file = new File(System.getProperty("user.dir") + saveFileName);
    multipartFile.transferTo(file);

    return file;
  }

}
