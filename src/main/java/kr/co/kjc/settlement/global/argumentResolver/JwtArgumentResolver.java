package kr.co.kjc.settlement.global.argumentResolver;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.kjc.settlement.global.annotation.JwtAuthorization;
import kr.co.kjc.settlement.global.constants.CommonConstants;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.service.JwtService;
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
public class JwtArgumentResolver implements HandlerMethodArgumentResolver {

  private final JwtService jwtService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(JwtAuthorization.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    String authorization = request.getHeader(CommonConstants.REQ_HEADER_KEY_AUTH);

    if (StringUtils.hasText(authorization) && authorization.startsWith(
        CommonConstants.REQ_HEADER_KEY_AUTH_TOKEN_TYPE)) {

      String accessToken = authorization.substring(
          CommonConstants.REQ_HEADER_KEY_AUTH_TOKEN_TYPE.length());

      return jwtService.findMemberByToken(accessToken);
    }

    throw new BaseAPIException(EnumErrorCode.NOT_FOUND_MEMBER_BY_JWT_ACCESS_TOKEN);
  }
}
