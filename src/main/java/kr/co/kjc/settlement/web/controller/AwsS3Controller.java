package kr.co.kjc.settlement.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kr.co.kjc.settlement.global.dtos.response.BaseResponseDTO;
import kr.co.kjc.settlement.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "96. Aws-S3", description = "Aws S3 API 입니다.")
@RestController
@RequiredArgsConstructor
public class AwsS3Controller {

  private final AwsS3Service awsS3Service;

  @Operation(summary = "S3 파일 업로드", description = "S3 파일을 업로드 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @PostMapping(value = "/excels/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public BaseResponseDTO<List<String>> upload(@RequestPart MultipartFile multipartFile) {
    return new BaseResponseDTO<>(awsS3Service.uploadFile(multipartFile));
  }

}
