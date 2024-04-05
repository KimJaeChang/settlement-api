package kr.co.kjc.settlement.global.aspect;

import com.google.gson.Gson;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import kr.co.kjc.settlement.global.constants.TextConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CommonLogAspect {

  private static final List<Class<? extends Annotation>> requestMappingList = Arrays.asList(
      GetMapping.class, PutMapping.class, PostMapping.class,
      PatchMapping.class, DeleteMapping.class, RequestMapping.class);

  /**
   * printRestControllerStart : Request에 매핑된 컨트롤러 위치 출력
   */
  @Before("execution(* kr.co.kjc.settlement..*(..)) && @within(org.springframework.web.bind.annotation.RestController)")
  public void printRestControllerStart(JoinPoint joinPoint) {
    MethodSignature ms = (MethodSignature) joinPoint.getSignature();
    Method calledMethod = ms.getMethod();
    String className = calledMethod.getDeclaringClass().getSimpleName();
    String calledMethodName = calledMethod.getName();
    String url = requestMappingList.stream()
        .filter(calledMethod::isAnnotationPresent)
        .map(mappingClass -> {
          try {
            // 메소드 단위 매핑 어노테이션 주소를 가져온다.
            return ((String[]) mappingClass.getMethod("value").invoke(calledMethod.getAnnotation(mappingClass)))[0];
          } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return "";
          }
        })
        .findFirst()
        .orElse(null);

    log.info(TextConstants.LOGGING_PREFIX_START, url, className, calledMethodName);
    // Request body logging
    // log.info(TextConstants.LOGGING_PREFIX_REQUEST, gson.toJson(joinPoint.getArgs()));
  }

  /**
   * printRestControllerEnd
   */
  @AfterReturning(pointcut = """
        (execution(* kr.co.kjc.settlement..*(..)) && @within(org.springframework.web.bind.annotation.RestController)) ||
         execution(* kr.co.kjc.settlement..BaseAPIControllerAdvice.*(..))
      """, returning = "returnValue")
  public void printRestControllerEnd(JoinPoint joinPoint, Object returnValue) {
    MethodSignature ms = (MethodSignature) joinPoint.getSignature();
    Method calledMethod = ms.getMethod();
    String className = calledMethod.getDeclaringClass().getSimpleName();
    String calledMethodName = calledMethod.getName();
    log.info(TextConstants.LOGGING_PREFIX_END, className, calledMethodName);
    // Response body logging
    // log.info(TextConstants.LOGGING_PREFIX_RESPONSE, gson.toJson(returnValue));
  }

}

