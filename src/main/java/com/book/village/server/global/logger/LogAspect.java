package com.book.village.server.global.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Profile("local")
@Component
public class LogAspect {

    // 해당 프로젝트 모두 실행시
    @Pointcut("execution(* com.book.village..*(..))")
    public void all() {}

    // Controller 실행시
    @Pointcut("execution(* com.book.village..*Controller.*(..))")
    public void controller() {}

    // Service 실행시
    @Pointcut("execution(* com.book.village.*Service.*(..))")
    public void service(){}


    // 메서드 호출 이전 이후 예외 발생등 모든 시점에서 동작
    @Around("all()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("log = {}" , joinPoint.getSignature());
            log.info("timeMs = {}", timeMs);
        }
    }

    // Controller / service 실행 전에 동작
    @Before("controller() || service()")
    public void beforeLogic(JoinPoint joinPoint) throws Throwable {
        // 메서드 이름 나타냄.
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("method = {}", method.getName());


        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg != null) {
                // 타입
                log.info("type = {}", arg.getClass().getSimpleName());
                //
                log.info("value = {}", arg);
            }

        }
    }


}