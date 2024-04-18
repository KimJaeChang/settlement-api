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
    encryptor.setPassword("kjc_secret!@#$%");

    System.out.println(
        "widgetClientKey : " + encryptor.encrypt("test_ck_d26DlbXAaV0eaqLkoDbrqY50Q9RB"));
    System.out.println(
        "widgetSecretKey : " + encryptor.encrypt("test_sk_YyZqmkKeP8gQWd5OeyYVbQRxB9lG"));
    System.out.println(
        "apiClientKey : " + encryptor.encrypt("test_ck_d26DlbXAaV0eaqLkoDbrqY50Q9RB"));
    System.out.println(
        "apiSecretKey : " + encryptor.encrypt("test_sk_YyZqmkKeP8gQWd5OeyYVbQRxB9lG"));
    System.out.println("bucket : " + encryptor.encrypt("everon-test"));
    System.out.println("region : " + encryptor.encrypt("ap-northeast-2"));
    System.out.println(
        "path : " + encryptor.encrypt("/upload/v1"));
    System.out.println(
        "decrypt : " + encryptor.decrypt("jDLGE7NjYfXqdrYrRUDxHlPL7Gtlop8tsUbrJBwuX90="));
    System.out.println("decrypt : " + encryptor.decrypt(
        "zxcCkAjJABQViY4GZk5KtUA1IOem8M805smRek34/9wu8TrQfQMOkxPCllZtwenwAEFPpMYVyt8="));
    System.out.println("decrypt : " + encryptor.decrypt("uNUz/YWyDxH9jOUx69uetp3DwCQyKESp"));
  }

}