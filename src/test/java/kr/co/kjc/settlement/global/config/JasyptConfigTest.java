package kr.co.kjc.settlement.global.config;

import java.util.Properties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//@SpringBootTest
//@ActiveProfiles("local")
class JasyptConfigTest {

//  @Value("${payments.toss-payments.keys.widget.client-key}")
//  private String widgetClientKey;
//
//  @Value("${payments.toss-payments.keys.widget.secret-key}")
//  private String widgetSecretKey;
//
//  @Value("${payments.toss-payments.keys.api.client-key}")
//  private String apiClientKey;
//
//  @Value("${payments.toss-payments.keys.api.secret-key}")
//  private String apiSecretKey;

  @Test
  @DisplayName("암호화 테스트")
  void testEncrypt() {

// Propertie 전체 조회
    System.out.println("============= Properties 전체 조회 ================");
    Properties properties = System.getProperties();
    for (Object propKey : System.getProperties().keySet()) {
      String key = (String) propKey;
      String value = properties.getProperty(key);
      System.out.println(key + "=" + value);
    }

    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
  }

}