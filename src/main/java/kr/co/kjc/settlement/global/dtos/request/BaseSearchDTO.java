package kr.co.kjc.settlement.global.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class BaseSearchDTO {

  @Schema(description = "페이지번호", example = "1")
  private Integer pageNo = 1;

  /**
   * 페이지사이즈
   */
  @Schema(description = "페이지사이즈", example = "10")
  private Integer pageSize = 10;

  /**
   * 검색 Keyword
   */
  @Schema(description = "검색키워드", example = "")
  private String searchKeyword = "";

  /**
   * 조회 시작일
   */
  @Schema(description = "조회시작일", example = "2024-01-01")
  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
  private LocalDate searchDateFrom;

  /**
   * 조회 종료일
   */
  @Schema(description = "조회종료일", example = "9999-12-31")
  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
  private LocalDate searchDateTo;

}
