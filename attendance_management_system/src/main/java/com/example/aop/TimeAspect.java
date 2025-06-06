package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect // AOP类
@Slf4j
public class TimeAspect {
    @Around("execution(* com.example.controller..*.*(..))") // 切入点表达式
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 方法开始时间
        long start = System.currentTimeMillis();

        Object res = joinPoint.proceed(); // 原始方法执行的返回值
        // 方法结束时间
        long end = System.currentTimeMillis();

        // 计算方法执行时间
        log.info("方法名：{}，执行时间：{}ms", joinPoint.getSignature().getName(), end - start);

        return res;
    }
}
