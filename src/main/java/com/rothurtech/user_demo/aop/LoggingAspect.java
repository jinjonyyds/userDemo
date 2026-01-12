package com.rothurtech.user_demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    // cut all methods from service layer
    @Pointcut("execution(* com.rothurtech.user_demo.service..*(..))")
    public void serviceLayer() {}

    @Around("serviceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();


        String methodName = joinPoint.getSignature().toShortString();


        Object[] args = joinPoint.getArgs();
        log.info("Entering method: {} | args={}", methodName, args);

        try {
            Object result = joinPoint.proceed();
            long time = System.currentTimeMillis() - start;

            log.info("Exiting method: {} | result={} | time={}ms",
                    methodName, result, time);

            return result;
        } catch (Throwable ex) {
            long time = System.currentTimeMillis() - start;

            log.error("Exception in method: {} | time={}ms | message={}",
                    methodName, time, ex.getMessage());

            throw ex;
        }
    }
}
