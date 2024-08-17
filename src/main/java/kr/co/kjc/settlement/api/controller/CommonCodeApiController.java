package kr.co.kjc.settlement.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kjc.settlement.global.dtos.code.CommonCodeDTO.Item;
import kr.co.kjc.settlement.global.dtos.common.BaseResponseDTO;
import kr.co.kjc.settlement.global.dtos.common.BaseSearchDTO;
import kr.co.kjc.settlement.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "98. 공통 코드 관리 API", description = "공통 코드 관리 API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommonCodeApiController {

  private final CommonCodeService commonCodeService;

  @Operation(summary = "공통 코드 전체 조회", description = "공통 코드를 전체 조회 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @GetMapping(value = "/common-codes")
  public BaseResponseDTO<Page<Item>> findAll(@ParameterObject BaseSearchDTO dto) {
    return new BaseResponseDTO<>(commonCodeService.findAll(dto));
  }

  @Operation(summary = "공통 코드 부모코드 기준 전체 조회", description = "공통 코드를 부모코드 기준으로 전체 조회 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @GetMapping(value = "/common-codes/parent-codes/{parentCode}")
  public BaseResponseDTO<Page<Item>> findAllByParentCode(
      @PathVariable("parentCode") String parentCode) {
    return new BaseResponseDTO<>(commonCodeService.findAllByParentCode(parentCode));
  }

}