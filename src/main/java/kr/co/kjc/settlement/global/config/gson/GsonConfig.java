package kr.co.kjc.settlement.global.config.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import kr.co.kjc.settlement.global.bean.EnumMapper;
import kr.co.kjc.settlement.global.dtos.CodeDTO;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.data.domain.Page;
import org.springframework.http.ProblemDetail;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Object;

//@Configuration
//@ConditionalOnClass(Gson.class)
//@EnableAutoConfiguration(exclude = JacksonAutoConfiguration.class)
//@EnableConfigurationProperties(GsonProperties.class)
public class GsonConfig {

  //  @Bean
//  @ConditionalOnMissingBean
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
    builder.registerTypeHierarchyAdapter(CodeDTO.Item.class,
        new GsonEnumDeserializer(new EnumMapper()));
    builder.registerTypeHierarchyAdapter(S3Object.class, new GsonS3BucketConfig());

    customizers.forEach((c) -> c.customize(builder));
    return builder;
  }

  //  @Bean
//  @ConditionalOnMissingBean
  public Gson gson(GsonBuilder gsonBuilder) {
    return gsonBuilder.create();
  }

}
