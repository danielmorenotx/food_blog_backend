package com.example.foodblog.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect // allows this to spy on other classes
@Component // creates a bean of this class
public class LoggingAspect {

    @Pointcut("execution(* com.example.foodblog.controller..*.*(..))")
    public void controllerMethods() {}

    @Pointcut("execution(* com.example.foodblog.service..*.*(..))")
    public void serviceMethods() {}

    @Around("controllerMethods() || serviceMethods()")
    public Object logAroundControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // == BEFORE METHOD LOGGING ==
        long startTime = System.currentTimeMillis(); // setting the current time to a variable
        log.info("Before method: " + joinPoint.getSignature().toShortString() + "; Trigger time: " + startTime);

        // Proceed with the original method execution
        Object result =joinPoint.proceed();

        // == AFTER METHOD LOGGING ==
        long endTime = System.currentTimeMillis(); // sets time after method runs to variable
        log.info("After method: " + joinPoint.getSignature().toShortString() + "; Trigger time: " + endTime);

        // == METHOD EXECUTION TIME ==
        log.info("Method execution time: " + (endTime - startTime) + " milliseconds");

        return result;
    }
}
