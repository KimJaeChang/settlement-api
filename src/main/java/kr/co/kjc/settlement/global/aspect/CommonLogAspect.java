package kr.co.kjc.settlement.global.aspect;

import com.google.gson.Gson;
import java.lang.reflect.Method;
import kr.co.kjc.settlement.global.constants.TextConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CommonLogAspect {

  private final Gson gson;

  /**
   * printControllerLoc : Request에 매핑된 컨트롤러 위치 출력
   */
  @Before("execution(* kr.co.kjc.settlement..*(..)) && @within(org.springframework.web.bind.annotation.RestController)")
  public void printControllerLoc(JoinPoint joinPoint) {
    MethodSignature ms = (MethodSignature) joinPoint.getSignature();
    Method calledMethod = ms.getMethod();
    // Class<?> calledClass = ms.getMethod().getDeclaringClass();
    String className = calledMethod.getDeclaringClass().getSimpleName();
    String calledMethodName = calledMethod.getName();
    log.info(TextConstants.LOGGING_PREFIX_START, className, calledMethodName);
    // Request Parameter 출력
    log.info(TextConstants.LOGGING_PREFIX_REQUEST, gson.toJson(joinPoint.getArgs()));
  }

  /**
   * printReturnDataAndViewName NOTE 1) 컨트롤러가 리턴하는 값 로깅 정상응답 일 경우 2) 컨트롤러 명 과 응답객체 출력 예외발생일 경우 응답객체
   * 출력
   */
  @AfterReturning(pointcut = """
        (execution(* kr.co.kjc.settlement..*(..)) && @within(org.springframework.web.bind.annotation.RestController)) ||
         execution(* kr.co.kjc.settlement..BaseAPIControllerAdvice.*(..))
      """, returning = "returnValue")
  public void printReturnDataAndViewName(JoinPoint joinPoint, Object returnValue) {
    MethodSignature ms = (MethodSignature) joinPoint.getSignature();
    Method calledMethod = ms.getMethod();
    String className = calledMethod.getDeclaringClass().getSimpleName();
    String calledMethodName = calledMethod.getName();
    log.info(TextConstants.LOGGING_PREFIX_END, className, calledMethodName);
    // response logging
    log.info(TextConstants.LOGGING_PREFIX_RESPONSE, gson.toJson(returnValue));
  }

  /**
   * printServiceLoc : Service 레이어 위치 출력 및 변수 세팅
   *
   * @param pjp
   * @return ProceedingJoinPoint.proceed()
   * @throws Throwable 2019.01.29 spring-data-rest 를 쓰게 되면 에러가 나기때문에 execution package 규칙 추가 (기본 내장
   *                   클래스의 프록시를 만들 수 없어서 AOP 불가)
   */
//  @Around("execution(* kr.co.everon..*(..)) && @within(org.springframework.stereotype.Service)")
//  public Object printServiceLoc(ProceedingJoinPoint pjp) throws Throwable {
//    MethodSignature ms = (MethodSignature) pjp.getSignature();
//    Method calledMethod = ms.getMethod();
//    // Class<?> calledClass = ms.getMethod().getDeclaringClass();
//    String className = calledMethod.getDeclaringClass().getSimpleName();
//    String calledMethodName = calledMethod.getName();
//    // 수행 시간 측정 로직 추가
//    log.info(TextConstants.LOGGING_PREFIX_START, className, calledMethodName);
//    StopWatch stopWatch = new StopWatch();
//    stopWatch.start();
//
//    Object proceed = pjp.proceed();
//
//    stopWatch.stop();
//    log.info(TextConstants.LOGGING_PREFIX_END + TextConstants.EXECUTE_TIME_CHECK_MESSAGE, className,
//        calledMethodName, stopWatch.getTotalTimeSeconds());
//    return proceed;
//  }
}

