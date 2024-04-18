package kr.co.kjc.settlement.global.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
  }

}