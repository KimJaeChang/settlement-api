package kr.co.kjc.settlement.global.config.gson;

import static kr.co.kjc.settlement.global.constants.CommonConstants.FIELD_BODY;
import static kr.co.kjc.settlement.global.constants.CommonConstants.FIELD_ORDERS;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class GsonPageConfig implements JsonSerializer<Page<?>>, JsonDeserializer<Page<?>> {

  private static final String FIELD_TOTAL_PAGES = "totalPages";
  private static final String FIELD_TOTAL_ELEMENTS = "totalElements";
  private static final String FIELD_PAGE_SIZE = "pageSize";
  private static final String FIELD_PAGE_NO = "pageNo";
  private static final String FIELD_HAS_CONTENT = "hasContent";
  private static final String FIELD_FIRST = "first";
  private static final String FIELD_LAST = "last";
  private static final String FIELD_NUMBER_OF_ELEMENTS = "numberOfElements";
  private static final String FIELD_HAS_NEXT = "hasNext";
  private static final String FIELD_HAS_PREVIOUS = "hasPrevious";
  private static final String FIELD_SORT = "sort";
  private static final String FIELD_CONTENT = "content";

  @Override
  public JsonElement serialize(Page<?> page, Type type, JsonSerializationContext context) {

    JsonObject jsonPage = new JsonObject();
    // Add more properties as needed

    // 전체 페이지 수
    jsonPage.addProperty(FIELD_TOTAL_PAGES, page.getTotalPages());
    // 전체 데이터 수
    jsonPage.addProperty(FIELD_TOTAL_ELEMENTS, page.getTotalElements());
    // 페이지 크기
    jsonPage.addProperty(FIELD_PAGE_SIZE, page.getSize());
    // 현재 페이지 : 응답에 노출할 페이지번호는 실제보다 1 증가시킨다.
    jsonPage.addProperty(FIELD_PAGE_NO, page.getNumber() + 1);
    // 조회된 데이터 존재 여부
    jsonPage.addProperty(FIELD_HAS_CONTENT, page.hasContent());
    // 현재 페이지가 첫 페이지 인지 여부
    jsonPage.addProperty(FIELD_FIRST, page.isFirst());
    // 현재 페이지가 마지막 페이지 인지 여부
    jsonPage.addProperty(FIELD_LAST, page.isLast());
    // 현재 페이지에 나올 데이터 수
    jsonPage.addProperty(FIELD_NUMBER_OF_ELEMENTS, page.getNumberOfElements());
    // 다음 페이지 존재 여부
    jsonPage.addProperty(FIELD_HAS_NEXT, page.hasNext());
    // 이전 페이지 존재 여부
    jsonPage.addProperty(FIELD_HAS_PREVIOUS, page.hasPrevious());
    // 사용된 정렬 정보 : Serialize sort
    jsonPage.add(FIELD_SORT, context.serialize(page.getSort()));

    // 컨텐츠 : Serialize content
    JsonElement content = context.serialize(page.getContent());
    jsonPage.add(FIELD_CONTENT, content);

//    // Serialize pageable
//    jsonPage.add("pageable", context.serialize(page.getPageable()));

    return jsonPage;
  }

  @Override
  public Page<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonElement jsonElement = json.getAsJsonObject().get(FIELD_BODY);
    JsonElement content = jsonElement.getAsJsonObject().get(FIELD_CONTENT);

    List<?> contentList = context.deserialize(content, new TypeToken<List<?>>() {
    }.getType());

    int pageSize = jsonElement.getAsJsonObject().get(FIELD_PAGE_SIZE).getAsInt();
    int pageNo = jsonElement.getAsJsonObject().get(FIELD_PAGE_NO).getAsInt();
    long totalElements = jsonElement.getAsJsonObject().get(FIELD_TOTAL_ELEMENTS).getAsLong();
    JsonElement sort = jsonElement.getAsJsonObject().get(FIELD_SORT);
    JsonElement orders = sort.getAsJsonObject().get(FIELD_ORDERS);

    List<Order> orderList = context.deserialize(orders, new TypeToken<List<Order>>() {
    }.getType());

    return new PageImpl<>(contentList, PageRequest.of(pageSize, pageNo, Sort.by(orderList)),
        totalElements);
  }
}
