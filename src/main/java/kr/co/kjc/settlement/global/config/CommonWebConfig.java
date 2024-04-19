package kr.co.kjc.settlement.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CommonWebConfig implements WebMvcConfigurer {

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
        .excludePathPatterns(
            "/css/**", "/*.ico"
            , "/error", "/error-page/**" //오류 페이지 경로
        );
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
