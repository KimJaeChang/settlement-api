package kr.co.kjc.settlement.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kjc.settlement.global.annotation.JwtRefreshAuthorization;
import kr.co.kjc.settlement.global.dtos.common.BaseResponseDTO;
import kr.co.kjc.settlement.global.dtos.jwt.JwtTokenReqDTO;
import kr.co.kjc.settlement.global.dtos.jwt.JwtTokenResDTO;
import kr.co.kjc.settlement.global.dtos.member.MemberDTO;
import kr.co.kjc.settlement.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "97. JWT", description = "JWT API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class JwtApiController {

  private final JwtTokenService jwtTokenService;

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
  public ResponseEntity<BaseResponseDTO<JwtTokenResDTO>> createAccessToken(
      @RequestBody JwtTokenReqDTO dto) {
    return ResponseEntity.ok()
        .body(new BaseResponseDTO<>(jwtTokenService.create(dto)));
  }

  @Operation(summary = "JWT AccessToken 재발급", description = "JWT AccessToken을 재발급 합니다. ",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          content = @Content(
              schema = @Schema(
                  allOf = JwtTokenReqDTO.class
              )
          ),
          required = true,
          useParameterTypeSchema = true
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @PutMapping(value = "/authorization")
  public ResponseEntity<BaseResponseDTO<JwtTokenResDTO>> createRefreshToken(
      @RequestHeader(value = "refreshToken") String beforeRefreshToken,
      @RequestBody JwtTokenReqDTO dto, @JwtRefreshAuthorization MemberDTO memberDTO) {
    return ResponseEntity.ok()
        .body(new BaseResponseDTO<>(jwtTokenService.update(beforeRefreshToken, dto, memberDTO)));
  }
}
