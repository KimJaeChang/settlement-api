package kr.co.kjc.settlement.global.config.common;

import java.util.List;
import kr.co.kjc.settlement.global.argumentResolver.BaseSearchDTOArgumentResolver;
import kr.co.kjc.settlement.global.argumentResolver.JwtArgumentResolver;
import kr.co.kjc.settlement.global.argumentResolver.JwtRefreshArgumentResolver;
import kr.co.kjc.settlement.global.interceptor.GlobalLoggingInterceptor;
import kr.co.kjc.settlement.global.interceptor.JwtInterceptor;
import kr.co.kjc.settlement.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class CommonWebConfig implements WebMvcConfigurer {

  private final static List<String> LOG_EXCLUDES = List.of("/css/**", "/*.ico", "/error",
      "/error-page/**");
  private final static List<String> JWT_EXCLUDES = List.of("/api-docs/**", "/swagger-ui/**",
      "/health-check", "/api/v1/authorization/**");

  //  private final JwtService jwtService;
  private final JwtTokenService jwtTokenService;
  private final JwtArgumentResolver jwtArgumentResolver;
  private final JwtRefreshArgumentResolver jwtRefreshArgumentResolver;
  private final BaseSearchDTOArgumentResolver baseSearchDTOArgumentResolver;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) { // 기본 resourceHandler 유지하면서 추가
    registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/templates/", "classpath:/static/") // '/'으로 끝나도록
        .setCachePeriod(20);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new GlobalLoggingInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns(LOG_EXCLUDES);
    registry.addInterceptor(new JwtInterceptor(jwtTokenService))
        .excludePathPatterns(JWT_EXCLUDES)
        .addPathPatterns("/**");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(jwtArgumentResolver);
    resolvers.add(jwtRefreshArgumentResolver);
    resolvers.add(baseSearchDTOArgumentResolver);
  }

  // NOTE : CORS Filter
//  @Bean
//  public FilterRegistrationBean<Filter> corsFilterRegistrationBean() {
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    CorsConfiguration config = new CorsConfiguration();
//    config.addAllowedOrigin("*"); // 허용할 Origin 설정
//    config.addAllowedMethod("*"); // 허용할 HTTP 메서드 설정
//    config.addAllowedHeader("*"); // 허용할 헤더 설정
//    config.addExposedHeader("*");
//    source.registerCorsConfiguration("/**", config);
//    FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();
//    filterBean.setFilter(new CorsFilter(source));
//    filterBean.setOrder(0);
//    return filterBean;
//  }

}
