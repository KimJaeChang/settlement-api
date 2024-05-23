package kr.co.kjc.settlement.global.config.common;


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

  @Value("${springdoc.version}")
  private String springdocVersion;

  @Bean
  public OpenAPI openAPI() {
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
        .info(info)
        .addSecurityItem(securityRequirement)
        .components(components);
  }

//  @Bean
//  public OperationCustomizer operationCustomizer() {
//    return (operation, handlerMethod) -> {
//      ApiResponses apiResponses = operation.getResponses();
//      if (apiResponses == null) {
//        apiResponses = new ApiResponses();
//        operation.setResponses(apiResponses);
//      }
//      apiResponses.putAll(getCommonResponses());
//      return operation;
//    };
//  }
//
//  /**
//   * 공통 응답 정보를 생성하여 맵으로 리턴한다.
//   *
//   * @return LinkedHashMap<String, ApiResponse> ApiResponse Map
//   */
//  private Map<String, ApiResponse> getCommonResponses() {
//    LinkedHashMap<String, ApiResponse> responses = new LinkedHashMap<>();
//    responses.put("404", notFoundResponse());
//    responses.put("500", internalServerResponse());
//    return responses;
//  }
//
//  /**
//   * 404 Response 를 생성하여 리턴
//   *
//   * @return ApiResponse 404 응답 객체
//   */
//  private ApiResponse notFoundResponse() {
//    ApiResponse apiResponse = new ApiResponse();
//    apiResponse.setDescription("""
//        Not Found
//        - 요청한 URI 가 올바른지 확인한다.
//        """);
//    addContent(apiResponse, 404, "Not Found");
//    return apiResponse;
//  }
//
//  /**
//   * 500 Response 를 생성하여 리턴
//   *
//   * @return ApiResponse 500 응답 객체
//   */
//  private ApiResponse internalServerResponse() {
//    ApiResponse apiResponse = new ApiResponse();
//    apiResponse.setDescription("""
//        Internal Server Error (Unchecked Exception)
//        - API 담당자에게 오류 확인을 요청한다.
//        """);
//    addContent(apiResponse, 500, "Internal Server Error");
//    return apiResponse;
//  }
//
//  /**
//   * ApiResponse 의 Content 정보를 추가
//   *
//   * @param apiResponse Api 응답 객체
//   * @param status      응답 상태 코드
//   * @param message     응답 메시지
//   */
//  @SuppressWarnings("rawtypes")
//  private void addContent(ApiResponse apiResponse, int status, String message) {
//    Content content = new Content();
//    MediaType mediaType = new MediaType();
//    Schema schema = new Schema<>();
//    schema.$ref("#/components/schemas/ErrorResponse");
//    mediaType.schema(schema).example(new BaseAPIException(EnumErrorCode.INVALID_SCHEMA));
//    content.addMediaType("application/json", mediaType);
//    apiResponse.setContent(content);
//  }
}
