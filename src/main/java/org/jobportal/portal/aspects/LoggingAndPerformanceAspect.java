package org.jobportal.portal.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAndPerformanceAspect {
//    @Around("@annotation(org.jobportal.portal.aspects.LogAspect)")

    @Around("execution(* org.jobportal.portal..*.*(..))")
    public Object logAndMeasureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();
        log.info("➡️ Entering method: {}", methodName);
        log.info("📥 Arguments: {}", Arrays.toString(methodArgs));
        // Proceed with actual business method
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        log.info("✅ Method executed successfully: {}", methodName);
        log.info("⏱ Execution time: {} ms", executionTime);
        return result;
    }
}
