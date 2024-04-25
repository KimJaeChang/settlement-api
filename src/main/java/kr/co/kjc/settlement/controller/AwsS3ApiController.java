package kr.co.kjc.settlement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import kr.co.kjc.settlement.global.dtos.S3BucketDTO;
import kr.co.kjc.settlement.global.dtos.S3ObjectDTO;
import kr.co.kjc.settlement.global.dtos.response.BaseResponseDTO;
import kr.co.kjc.settlement.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "96. Aws-S3", description = "Aws S3 API 입니다.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AwsS3ApiController {

  private final AwsS3Service awsS3Service;

  @Operation(summary = "S3 Bucket 조회", description = "S3 Bucket을 조회 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @GetMapping(value = "/s3/buckets")
  public BaseResponseDTO<List<S3BucketDTO>> findAllByS3Buckets() {
    return new BaseResponseDTO<>(awsS3Service.getBuckits());
  }

  @Operation(summary = "S3 버킷 파일 조회", description = "S3 버킷 내의 파일을 조회 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @GetMapping(value = "/s3/buckets/objects")
  public BaseResponseDTO<List<S3ObjectDTO>> findAllByS3Buckets(
      @RequestParam("bucket") String bucketName) {
    return new BaseResponseDTO<>(awsS3Service.getObjects(bucketName));
  }

  @Operation(summary = "S3 파일 업로드", description = "S3 파일을 업로드 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @PostMapping(value = "/s3/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public BaseResponseDTO<String> upload(@RequestPart MultipartFile multipartFile) {
    return new BaseResponseDTO<>(awsS3Service.uploadObject(multipartFile));
  }

  @Operation(summary = "S3 파일 다운로드", description = "S3 파일을 다운로드 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @GetMapping(value = "/s3/downloads")
  public ResponseEntity<Void> download(@RequestParam("bucket") String bucketName,
      @RequestParam("path") String path, @RequestParam("fileName") String fileName) {
    awsS3Service.download(bucketName, path, fileName);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "S3 파일 삭제", description = "S3 파일을 삭제 합니다. ",
      responses = {
          @ApiResponse(responseCode = "200", description = "성공 응답(success)", useReturnTypeSchema = true),
          @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "403", description = "권한없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "404", description = "데이터 없음", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
          @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
      }
  )
  @DeleteMapping(value = "/s3/buckets/objects")
  public ResponseEntity<Boolean> delete(@RequestParam("bucket") String bucketName,
      @RequestParam("key") String key) {
    return new ResponseEntity<>(awsS3Service.delete(bucketName, key), HttpStatus.OK);
  }
}
