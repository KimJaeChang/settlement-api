package kr.co.kjc.settlement.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.kjc.settlement.global.constants.CommonConstants;
import kr.co.kjc.settlement.global.dtos.MemberDTO;
import kr.co.kjc.settlement.global.enums.EnumErrorCode;
import kr.co.kjc.settlement.global.exception.BaseAPIException;
import kr.co.kjc.settlement.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

  private final JwtService jwtService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    //전처리 로직

    String authorization = request.getHeader(CommonConstants.REQ_HEADER_KEY_AUTH);

    if (StringUtils.hasText(authorization) && authorization.startsWith(
        CommonConstants.REQ_HEADER_KEY_AUTH_TOKEN_TYPE)) {

      String accessToken = authorization.substring(
          CommonConstants.REQ_HEADER_KEY_AUTH_TOKEN_TYPE.length());

      if (jwtService.isExpired(accessToken)) {
        throw new BaseAPIException(EnumErrorCode.EXPIRED_JWT_TOKEN);
      }

      MemberDTO memberDTO = jwtService.getUser(accessToken);
      if (!StringUtils.hasText(memberDTO.getUuid())) {
        throw new BaseAPIException(EnumErrorCode.INVALID_JWT_TOKEN);
      }

      return true;
    }

    throw new BaseAPIException(EnumErrorCode.UNAUTHORIZED_JWT_TOKEN);
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    //후처리 로직
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    //afterCompletion
  }

}
