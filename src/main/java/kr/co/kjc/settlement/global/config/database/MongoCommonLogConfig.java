package kr.co.kjc.settlement.global.config.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

@Configuration
@EnableTransactionManagement // Spring의 @Transactional을 찾아서 트랜잭션 범위를 활성화하는 기능을 함.
public class MongoCommonLogConfig {

  @Value("${service.mongodb.host}")
  private String host;

  @Value("${service.mongodb.port}")
  private String port;

  @Value("${service.mongodb.username}")
  private String username;

  @Value("${service.mongodb.password}")
  private String password;

  @Value("${service.mongodb.database.common-log}")
  private String database;

  @Value("${service.mongodb.authentication-database}")
  private String authenticationDatabase;

  @Bean(name = "mongoCommonLogClient")
  public MongoClient mongoClient(
      @Qualifier("mongoCommonLogClientSettings") MongoClientSettings mongoClientSettings) {
    return MongoClients.create(mongoClientSettings);
  }

  @Bean(name = "mongoCommonLogTemplate")
  public MongoTemplate mongoTemplate(
      @Qualifier("mongoCommonLogDbFactory") MongoDatabaseFactory mongoDbFactory
      , @Qualifier("mongoCommonLogConverter") MongoConverter mongoConverter) {
    return new MongoTemplate(mongoDbFactory, mongoConverter);
  }

  @Bean(name = "mongoCommonLogDbFactory")
  public MongoDatabaseFactory mongoDbFactory(
      @Qualifier("mongoCommonLogClient") MongoClient mongoClient) {
    return new SimpleMongoClientDatabaseFactory(mongoClient, this.database);
  }

  @Bean(name = "mongoCommonLogMappingContext")
  public MongoMappingContext mongoMappingContext() {
    return new MongoMappingContext();
  }

  @Bean(name = "mongoCommonLogConverter")
  public MongoConverter mongoConverter(
      @Qualifier("mongoCommonLogDbFactory") MongoDatabaseFactory mongoDbFactory
      , @Qualifier("mongoCommonLogMappingContext") MongoMappingContext mongoMappingContext) {
    DbRefResolver resolver = new DefaultDbRefResolver(mongoDbFactory);

    MappingMongoConverter converter = new MappingMongoConverter(resolver, mongoMappingContext);
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    return converter;
  }

  @Bean(name = "mongoCommonLogConnectionString")
  public ConnectionString connectionString() {

    StringBuilder sb = new StringBuilder();
    sb.append("mongodb://");
    isAppendUserName(sb);
    isAppendPassword(sb);
    sb.append(this.host);
    sb.append(":");
    sb.append(this.port);
    sb.append("/");
    sb.append(this.database);
    isAppendAuthSource(sb);

    return new ConnectionString(sb.toString());
  }

  @Bean(name = "mongoCommonLogCredential")
  public MongoCredential mongoCredential() {
    return MongoCredential.createCredential(this.username, this.database,
        this.password.toCharArray());
  }

  @Bean(name = "mongoCommonLogClientSettings")
  public MongoClientSettings mongoClientSettings(
      @Qualifier("mongoCommonLogConnectionString") ConnectionString connectionString
      , @Qualifier("mongoCommonLogCredential") MongoCredential mongoCredential) {
    return MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .credential(mongoCredential)
        .build();
  }

//  @Bean("mongoCommonLogTransactionOptions")
//  public TransactionOptions transactionOptions() {
//    return TransactionOptions.builder()
//        .readPreference(ReadPreference.primary())
//        .readConcern(ReadConcern.LOCAL)
//        .writeConcern(WriteConcern.MAJORITY)
//        .build();
//  }
//
//  @Bean("mongoCommongLogTransactionManager")
//  public MongoTransactionManager mongoTransactionManager(
//      @Qualifier("mongoCommonLogDbFactory") MongoDatabaseFactory mongoDbFactory,
//      @Qualifier("mongoCommonLogTransactionOptions") TransactionOptions transactionOptions) {
//    return new MongoTransactionManager(mongoDbFactory, transactionOptions);
//  }

  private void isAppendUserName(StringBuilder sb) {
    if (StringUtils.hasText(this.username)) {
      sb.append(this.username);
      sb.append(":");
    }
  }

  private void isAppendPassword(StringBuilder sb) {
    if (StringUtils.hasText(this.username)) {
      sb.append(this.password);
      sb.append("@");
    }
  }

  private void isAppendAuthSource(StringBuilder sb) {
    if (StringUtils.hasText(this.username)) {
      sb.append("/?authSource=");
      sb.append(this.authenticationDatabase);
    }
  }
}
