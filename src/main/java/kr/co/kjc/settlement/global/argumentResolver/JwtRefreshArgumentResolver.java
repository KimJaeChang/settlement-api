package kr.co.kjc.settlement.global.argumentResolver;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.kjc.settlement.global.annotation.JwtRefreshAuthorization;
import kr.co.kjc.settlement.global.constants.CommonConstants;
import kr.co.kjc.settlement.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class JwtRefreshArgumentResolver implements HandlerMethodArgumentResolver {

  //  private final JwtService jwtService;
  private final JwtTokenService jwtTokenService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(JwtRefreshAuthorization.class);
  }

  // Header validation 부분은 JwtInterceptor.class 에서 처리하고 있으므로 기본 로직만 작성함.
  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

    String refreshToken = request.getHeader(CommonConstants.REQ_HEADER_REFRESH_KEY_AUTH);

    if (StringUtils.hasText(refreshToken)) {
      return jwtTokenService.findMemberByRefreshToken(refreshToken);
    }

    return null;
  }
}
