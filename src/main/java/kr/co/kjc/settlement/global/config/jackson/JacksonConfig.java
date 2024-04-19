package kr.co.kjc.settlement.global.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

  private static final String DATE_FORMATTER = "yyyy-MM-dd";
  private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss";

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    objectMapper.registerModule(javaTimeModule());
    return objectMapper;
  }

  private JavaTimeModule javaTimeModule() {
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(
        new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
    javaTimeModule.addSerializer(
        new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    javaTimeModule.addDeserializer(LocalDate.class,
        new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
    javaTimeModule.addDeserializer(LocalDateTime.class,
        new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    return javaTimeModule;
  }

}
