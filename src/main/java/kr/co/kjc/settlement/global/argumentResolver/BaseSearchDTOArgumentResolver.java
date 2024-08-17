package kr.co.kjc.settlement.global.argumentResolver;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import kr.co.kjc.settlement.global.dtos.common.BaseSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class BaseSearchDTOArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getGenericParameterType().equals(BaseSearchDTO.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    String pageNo = Optional.ofNullable(request.getParameter("pageNo")).orElse("0");
    String pageSize = Optional.ofNullable(request.getParameter("pageSize")).orElse("10");
    return new BaseSearchDTO(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
  }
}
