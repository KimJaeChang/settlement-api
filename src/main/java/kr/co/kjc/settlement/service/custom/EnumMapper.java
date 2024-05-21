package kr.co.kjc.settlement.service.custom;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import kr.co.kjc.settlement.global.dtos.CodeDTO.Item;
import kr.co.kjc.settlement.global.dtos.CodeDTO.Items;
import kr.co.kjc.settlement.global.enums.BaseEnum;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.enums.EnumPaymentBroker;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class EnumMapper {

  private static final Map<String, Class<? extends BaseEnum>> enumMap;

  // NOTE - 코드 추가시 enumMap에 해당 enum class 추가 필요
  static {
    enumMap = new HashMap<>();
    enumMap.put(EnumPaymentBroker.class.getSimpleName(), EnumPaymentBroker.class);
  }

  public List<Items> findAll() {
    return enumMap.keySet().stream()
        .map(m -> {
          return Items.toEnumItems(enumMap.get(m), getDescription(enumMap.get(m)),
              Item.toEnumItem(enumMap.get(m)));
        })
        .collect(Collectors.toList());
  }

  public List<Items> findAllByEnums(List<Class<? extends BaseEnum>> classes) {
    return classes.stream()
        .filter(f -> f.equals(enumMap.get(f.getSimpleName())))
        .map(m -> {
          return Items.toEnumItems(enumMap.get(m.getSimpleName()), getDescription(enumMap.get(m)),
              Item.toEnumItem(enumMap.get(m.getSimpleName())));
        })
        .collect(Collectors.toList());
  }

  public Items findOne(String enumClass) {

    Class<? extends BaseEnum> enumType = Optional.ofNullable(enumMap.get(enumClass))
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM));

    return Items.toEnumItems(enumType, getDescription(enumType), Item.toEnumItem(enumType));
  }

  public Item findOneByValue(String enumValue) {
    Set<String> enumClasses = enumMap.keySet();
    for (String enumClassName : enumClasses) {
      Class<? extends BaseEnum> enumType = enumMap.get(enumClassName);

      Optional<? extends BaseEnum> e = Arrays.stream(enumType.getEnumConstants())
          .filter(f -> {
            try {
              return f.getValue(enumValue).name().equals(enumValue);
            } catch (ResponseStatusException ex) {
              return false;
            }
          })
          .findFirst();

      if (e.isPresent()) {
        return Item.createItem(e.get());
      }
    }

    throw new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM);
  }

  public Item findOneByCode(String code) {

    Set<String> enumClasses = enumMap.keySet();
    for (String enumClassName : enumClasses) {
      Class<? extends BaseEnum> enumType = enumMap.get(enumClassName);

      Optional<? extends BaseEnum> e = Arrays.stream(enumType.getEnumConstants())
          .filter(f -> f.getCode().equals(code))
          .findFirst();

      if (e.isPresent()) {
        return Item.createItem(e.get());
      }
    }

    throw new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM);
  }

  public Item findOneByEnumValue(String enumClass, String value) {

    Class<? extends BaseEnum> enumType = Optional.ofNullable(enumMap.get(enumClass))
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM));

    BaseEnum e = Arrays.stream(enumType.getEnumConstants())
        .filter(f -> f.getValue(value).name().equals(value))
        .findFirst()
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM));

    return Item.createItem(e);
  }

  public Item findOneByEnumCode(String enumClass, String code) {

    Class<? extends BaseEnum> enumType = Optional.ofNullable(enumMap.get(enumClass))
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM));

    BaseEnum e = Arrays.stream(enumType.getEnumConstants())
        .filter(f -> f.getCode().equals(code))
        .findFirst()
        .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM_CODE));

    return Item.createItem(e);
  }

  public String getDescription(Class<? extends BaseEnum> e) {
    try {
      return (String) Arrays.stream(e.getInterfaces())
          .findFirst()
          .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM))
          .getDeclaredMethod("getDescription")
          .invoke(Arrays.stream(e.getEnumConstants()).findFirst()
              .orElseThrow(() -> new BaseAPIException(EnumErrorCode.NOT_FOUND_ENUM)));
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
      log.error("EnumMapper_getDescription_error:: " + ex.getMessage());
      return null;
    }
  }
}
