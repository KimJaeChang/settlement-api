package kr.co.kjc.settlement.global.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@EnableEncryptableProperties
public class JasyptConfig {

//  @Value("${jasypt.encryptor.password}")
//  private String encryptKey;

  private static Map<String, Object> MAP = new HashMap<>();

  static {
    String[] s = System.getenv("jasypt.encryptor.password").split(" ");

    Arrays.stream(s)
        .forEach(f -> {
          int index = f.indexOf("=");
          if (index != -1) {
            String key = f.substring(0, index);
            String value = f.substring(index + 1);
            System.out.println("key : " + key);
            System.out.println("value : " + value);
            MAP.put(key, value);
          } else {
            System.out.println("not : " + f);
            MAP.put("jasypt.encryptor.password", f);
          }
        });

    System.out.println("secretKeyMap 2: " + MAP);
  }

  /**
   * String encryptor string encryptor.
   *
   * @return the string encryptor
   */
//  @Bean("jasyptStringEncryptor")
//  public StringEncryptor stringEncryptor() {
//    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//    config.setPassword(encryptKey); // 암호화키
//    config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256"); // 알고리즘
//    config.setKeyObtentionIterations("1000"); // 반복할 해싱 회수
//    config.setPoolSize("1"); // 인스턴스 pool
//    config.setProviderName("SunJCE");
//    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스
//    config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
//    config.setStringOutputType("base64"); //인코딩 방식
//    encryptor.setConfig(config);
//    return encryptor;
//  }
  @Bean("jasyptStringEncryptor")
  public StringEncryptor stringEncryptor() {

    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(String.valueOf(MAP.get("jasypt.encryptor.password")));
    config.setPoolSize("1");
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setStringOutputType("base64");
    config.setKeyObtentionIterations("1000");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    config.setProviderName("SunJCE");
    encryptor.setConfig(config);
    return encryptor;
  }
}