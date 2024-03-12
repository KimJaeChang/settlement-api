package kr.co.kjc.settlement.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import kr.co.kjc.settlement.global.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Value("${spring.application.name}")
  private String appName;

  @Bean
  public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
    Info info = new Info()
        .title(appName)
        .version(springdocVersion)
        .description(appName + " API Description");

    String schemeName = CommonConstants.REQ_HEADER_KEY_AUTH;
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(schemeName);
    Components components = new Components()
        .addSecuritySchemes(schemeName,
            new SecurityScheme().name(schemeName).type(Type.HTTP).scheme("Bearer")
                .bearerFormat("JWT"));

    return new OpenAPI()
        .components(new Components())
        .info(info)
        .addSecurityItem(securityRequirement)
        .components(components);
  }
}
