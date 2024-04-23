package kr.co.kjc.settlement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kjc.settlement.global.dtos.response.BaseResponseDTO;
import kr.co.kjc.settlement.global.dtos.response.JwtTokenDTO;
import kr.co.kjc.settlement.service.JwtService;
import kr.co.kjc.settlement.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "97. JWT", description = "JWT API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class JwtApiController {

  private final JwtService jwtService;
  private final RedisService redisService;

  @Operation(summary = "JWT AccessToken 발급", description = "JWT AccessToken을 발급 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @PostMapping(value = "/authorization")
  public BaseResponseDTO<JwtTokenDTO> createAccessToken(@RequestParam("table") String table,
      @RequestParam("key") String key,
      @RequestParam("value") String value) {
    Boolean result = redisService.save(table, key, value);
    return new BaseResponseDTO<>();
  }

  @Operation(summary = "JWT AccessToken 발급", description = "JWT AccessToken을 발급 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @PostMapping(value = "/authorization/refresh")
  public BaseResponseDTO<JwtTokenDTO> createRefreshToken() {
    return new BaseResponseDTO<>();
  }

}