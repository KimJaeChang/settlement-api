package kr.co.kjc.settlement.global.config.common;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import kr.co.kjc.settlement.global.annotation.CustomApiResponseCodes;
import kr.co.kjc.settlement.global.dtos.response.BaseResponseDTO;
import kr.co.kjc.settlement.global.enums.EnumCustomApiResponseCodeGroup;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;
import kr.co.kjc.settlement.global.exception.ProblemDetailHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationCustomizerConfig implements OperationCustomizer {

  private final ProblemDetailHelper helper;

  @SuppressWarnings("rawtypes")
  private final Schema errorEntitySchema = ModelConverters.getInstance()
      .readAllAsResolvedSchema(BaseResponseDTO.class).schema;

  @Override
  public Operation customize(Operation operation, HandlerMethod handlerMethod) {
    ApiResponses apiResponses = operation.getResponses();
    apiResponses.clear();
    Type dtoType = handlerMethod.getReturnType().getGenericParameterType();

    CustomApiResponseCodes apiResponseCodes = handlerMethod.getMethodAnnotation(
        CustomApiResponseCodes.class);
    if (apiResponseCodes != null) {
      Arrays.stream(apiResponseCodes.value()).forEach(
          code -> putApiResponseCode(apiResponses, code.value(), dtoType)
      );

      Arrays.stream(apiResponseCodes.groups()).forEach(
          group -> putApiResponseCodeGroup(apiResponses, group, dtoType)
      );
    }

    return operation;
  }

  private void putApiResponseCodeGroup(ApiResponses apiResponses,
      EnumCustomApiResponseCodeGroup group, Type dtoType) {
    try {
      Arrays.stream(EnumCustomApiResponseCodeGroup.class.getField(group.name())
          .getAnnotation(CustomApiResponseCodes.class).value()).forEach(apiResponseCode -> {
        putApiResponseCode(apiResponses, apiResponseCode.value(), dtoType);
      });
    } catch (NoSuchFieldException e) {
      log.error(String.format("Can not put ApiResponseCodeGroup [%s]", e));
    }
  }

  private void putApiResponseCode(ApiResponses apiResponses, EnumResponseCode code,
      Type dtoType) {
    if (code.getHttpStatus().value() < 0) {
      apiResponses.put(code.toString(), convertErrorResponse(code));
    } else {
      apiResponses.put(code.toString(), convertDataResponse(code, dtoType));
    }
  }

  private ApiResponse convertErrorResponse(EnumResponseCode code) {
    return convertResponseInner(
        errorEntitySchema.description(code.getDetail()),
        code,
        helper.build(code)
    );
  }

  private ApiResponse convertDataResponse(EnumResponseCode code, Type dtoType) {
    return convertResponseInner(
        customizeSchema(code, dtoType),
        code
    );
  }

  @SuppressWarnings("rawtypes")
  private Schema customizeSchema(EnumResponseCode responseCode, Type dtoType) {
    Schema schema = ModelConverters.getInstance().readAllAsResolvedSchema(dtoType).schema;
    @SuppressWarnings("unchecked") Map<String, Schema> properties = schema.getProperties();
    Boolean success = responseCode.getHttpStatus().is2xxSuccessful();
    Integer status = responseCode.getHttpStatus().value();
    Integer code = responseCode.getHttpStatus().value();
    String message = responseCode.getDetail();

    properties.get("success")
        .setDefault(status >= 200 && status < 300 ? Boolean.TRUE : Boolean.FALSE);
    properties.get("status").setDefault(status);
    properties.get("code").setDefault(code);
    properties.get("message").setDefault(message);

    return schema;
  }

  private ApiResponse convertResponseInner(@SuppressWarnings("rawtypes") Schema schema,
      EnumResponseCode code) {
    return convertResponseInner(schema, code, null);
  }

  private ApiResponse convertResponseInner(@SuppressWarnings("rawtypes") Schema schema,
      EnumResponseCode code, ProblemDetail example) {
    MediaType mediaType = new MediaType()
        .schema(schema);

    if (example != null) {
      mediaType.addExamples(code.name(), new Example().value(example));
    }

    return new ApiResponse()
        .content(
            new Content()
                .addMediaType(
                    APPLICATION_JSON_VALUE,
                    mediaType
                )
        )
        .description(code.getDetail());
  }

}
