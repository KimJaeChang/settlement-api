package kr.co.kjc.settlement.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kjc.settlement.global.dtos.ai.SpringAIReqDto;
import kr.co.kjc.settlement.global.dtos.common.BaseResponseDTO;
import kr.co.kjc.settlement.service.custom.SpringAIServiceRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "01. Spring AI 관리 API", description = "Spring AI 관리 API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SpringAiApiController {

  private final SpringAIServiceRouter springAIServiceRouter;

  @Operation(summary = "Spring AI 명령", description = "Spring AI에게 명령 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @PostMapping(value = "/spring-ai")
  public BaseResponseDTO<String> call(@RequestBody SpringAIReqDto dto) {
    return new BaseResponseDTO<>(
        springAIServiceRouter.get(dto.getSpringAICode()).call(dto.getMessage()));
  }

}