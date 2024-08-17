package kr.co.kjc.settlement.global.dtos.code;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.kjc.settlement.global.enums.BaseEnum;
import kr.co.kjc.settlement.global.enums.EnumResponseCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CodeDTO {

  @Schema(description = "Enum 목록 DTO",
      name = "EnumDTO.Items"
  )
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Items {

    @Schema(description = "Enum Class 명")
    private String enumClass;

    @Schema(description = "Enum 설명")
    private String description;

    @Schema(description = "Enum 정보")
    private List<Item> items;

    public static Items toEnumItems(Class<? extends BaseEnum> e, String description,
        List<Item> items) {
      Items result = new Items();
      result.enumClass = e.getSimpleName();
      result.items = items;
      result.description = description;
      return result;
    }
  }

  @Schema(description = "Enum 상세 DTO",
      name = "EnumDTO.Item"
  )
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Item {

    @Schema(description = "Enum ClassName")
    private String enumClassName;

    @Schema(description = "Enum Value")
    private BaseEnum enumValue;

    @Schema(description = "Enum Code")
    private String code;

    @Schema(description = "Enum Name")
    private String name;

    public Item(BaseEnum e) {
      this.enumClassName = e.getClass().getSimpleName();
      this.enumValue = e;
      this.code = e.getCode();
      this.name = e.getName();
    }

    public static List<Item> toEnumItem(Class<? extends BaseEnum> e) {
      return Arrays.stream(e.getEnumConstants())
          .map(Item::new)
          .collect(Collectors.toList());
    }

    public static Enum<?> toEnum(BaseEnum e) {
      return Arrays.stream(e.getClass().getEnumConstants())
          .map(m -> m.getValue(String.valueOf(e)))
          .findFirst()
          .orElseThrow(() -> new BaseAPIException(EnumResponseCode.NOT_FOUND_ENUM));
    }

    public static Item createItem(BaseEnum e) {
      Item result = new Item(e);
      result.enumClassName = e.getClass().getSimpleName();
      result.enumValue = e;
      result.code = e.getCode();
      result.name = e.getName();
      return result;
    }

    public static Item createItemByJson(String enumClassName, BaseEnum enumValue, String code,
        String name) {
      Item result = new Item();
      result.enumClassName = enumClassName;
      result.enumValue = enumValue;
      result.code = code;
      result.name = name;
      return result;
    }
  }

}
