package kr.co.kjc.settlement.global.config.aws;

import io.awspring.cloud.core.region.StaticRegionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;

@Configuration
public class S3Config {

  @Value("${spring.cloud.aws.s3.credentials.access-key}")
  private String accessKey;

  @Value("${spring.cloud.aws.s3.credentials.secret-key}")
  private String secretKey;

  @Value("${spring.cloud.aws.s3.region.static}")
  private String region;

  @Bean
  public S3Client amazonS3Client() {
    return S3Client.builder()
        .credentialsProvider(awsCredentialsProvider())
        .region(Region.of(region))
        .build();
  }

  @Bean
  public StaticRegionProvider regionProvider() {
    return new StaticRegionProvider(Region.of(region).id());
  }

  @Bean
  public ListBucketsRequest listBucketsRequest() {
    return ListBucketsRequest.builder()
        .overrideConfiguration(AwsRequestOverrideConfiguration.builder()
            .credentialsProvider(
                StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
            .build())
        .build();
  }

  private AwsCredentialsProvider awsCredentialsProvider() {
    return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
  }

}