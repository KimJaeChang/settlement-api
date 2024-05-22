package kr.co.kjc.settlement.global.argumentResolver;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import kr.co.kjc.settlement.global.annotation.JwtAuthorization;
import kr.co.kjc.settlement.global.constants.CommonConstants;
import kr.co.kjc.settlement.global.utils.JwtUtils;
import kr.co.kjc.settlement.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class JwtArgumentResolver implements HandlerMethodArgumentResolver {

  //  private final JwtService jwtService;
  private final JwtTokenService jwtTokenService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(JwtAuthorization.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    String authorization = Objects.requireNonNull(request)
        .getHeader(CommonConstants.REQ_HEADER_KEY_AUTH);

    if (JwtUtils.isAuthorization(authorization)) {
      String accessToken = JwtUtils.convertAccessToken(authorization);

      return jwtTokenService.findMemberByToken(accessToken);
    }

    return null;
  }
}
