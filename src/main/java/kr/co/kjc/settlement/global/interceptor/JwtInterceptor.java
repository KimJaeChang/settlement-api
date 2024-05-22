package kr.co.kjc.settlement.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.kjc.settlement.global.constants.CommonConstants;
import kr.co.kjc.settlement.global.utils.JwtUtils;
import kr.co.kjc.settlement.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

  //  private final JwtService jwtService;
  private final JwtTokenService jwtTokenService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    //전처리 로직

    String authorization = request.getHeader(CommonConstants.REQ_HEADER_KEY_AUTH);

    if (JwtUtils.isAuthorization(authorization)) {

      String accessToken = authorization.substring(
          CommonConstants.REQ_HEADER_KEY_AUTH_TOKEN_TYPE.length());

      // 만료 여부 체크
      jwtTokenService.isExpired(accessToken);

      return true;
    }

    return false;
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
