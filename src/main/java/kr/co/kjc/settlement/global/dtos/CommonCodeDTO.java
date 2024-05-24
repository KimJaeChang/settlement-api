package kr.co.kjc.settlement.global.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kjc.settlement.domain.jpa.CommonCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CommonCodeDTO {

//  @Schema(description = "공통 코드 목록 DTO",
//      name = "CommonCodeDTO.Items"
//  )
//  @Getter
//  @Setter
//  @AllArgsConstructor
//  @NoArgsConstructor
//  public static class Items {
//
//    @Schema(description = "공통코드 SEQ")
//    private Long id;
//
//    @Schema(description = "공통코드 부모코드")
//    private String parentCode;
//
//    @Schema(description = "공통코드 자식코드")
//    private String childCode;
//
//    public static Items of(Long id, String parentCode, String childCode) {
//      Items result = new Items();
//      result.id = id;
//      result.parentCode = parentCode;
//      result.childCode = childCode;
//      return result;
//    }
//
//    public static Items toDto(CommonCode commonCode) {
//      Items result = new Items();
//      result.id = commonCode.getId();
//      result.parentCode = commonCode.getParentCode();
//      result.childCode = commonCode.getChildCode();
//      return result;
//    }
//  }

  @Schema(description = "공통 코드 DTO",
      name = "CommonCodeDTO.Item"
  )
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  public static class Item {

    @Schema(description = "공통코드 SEQ")
    private Long id;

    @Schema(description = "공통코드 부모코드")
    private String parentCode;

    @Schema(description = "공통코드 자식코드")
    private String childCode;

    public static Item of(Long id, String parentCode, String childCode) {
      Item result = new Item();
      result.id = id;
      result.parentCode = parentCode;
      result.childCode = childCode;
      return result;
    }

    public static Item toDto(CommonCode commonCode) {
      Item result = new Item();
      result.id = commonCode.getId();
      result.parentCode = commonCode.getParentCode();
      result.childCode = commonCode.getChildCode();
      return result;
    }
  }
}
