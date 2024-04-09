package kr.co.kjc.settlement.global.config.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import kr.co.kjc.settlement.global.config.gson.GsonLocalDateConfig;
import kr.co.kjc.settlement.global.config.gson.GsonLocalDateTimeConfig;
import kr.co.kjc.settlement.global.config.gson.GsonMultipartFileAdaptorConfig;
import kr.co.kjc.settlement.global.config.gson.GsonPageConfig;
import kr.co.kjc.settlement.global.config.gson.GsonProblemDetailConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.http.ProblemDetail;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@ConditionalOnClass(Gson.class)
@EnableAutoConfiguration(exclude = JacksonAutoConfiguration.class)
@EnableConfigurationProperties(GsonProperties.class)
public class GsonConfig {

  @Bean
  @ConditionalOnMissingBean
  public GsonBuilder gsonBuilder(List<GsonBuilderCustomizer> customizers) {
    GsonBuilder builder = new GsonBuilder();
    /**
     * Custom Gson configuration
     */
    builder.registerTypeHierarchyAdapter(LocalDate.class, new GsonLocalDateConfig());
    builder.registerTypeHierarchyAdapter(LocalDateTime.class, new GsonLocalDateTimeConfig());
    builder.registerTypeHierarchyAdapter(Page.class, new GsonPageConfig());
    builder.registerTypeHierarchyAdapter(ProblemDetail.class, new GsonProblemDetailConfig());
    builder.registerTypeHierarchyAdapter(MultipartFile.class, new GsonMultipartFileAdaptorConfig());

    customizers.forEach((c) -> c.customize(builder));
    return builder;
  }

  @Bean
  @ConditionalOnMissingBean
  public Gson gson(GsonBuilder gsonBuilder) {
    return gsonBuilder.create();
  }

}
