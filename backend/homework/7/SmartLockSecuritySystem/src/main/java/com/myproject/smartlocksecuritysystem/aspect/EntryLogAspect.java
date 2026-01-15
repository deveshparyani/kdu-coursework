package com.myproject.smartlocksecuritysystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EntryLogAspect {

    private static final Logger log =
            LoggerFactory.getLogger(EntryLogAspect.class);

    @Before("execution(* com.myproject.smartlocksecuritysystem.service.SmartLockService.unlock(..))")
    public void logAccessAttempt(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String user = args.length > 0 ? String.valueOf(args[0]) : "UNKNOWN";

        log.info(
                "ACCESS ATTEMPT: User is approaching the door | user={} | method={}",
                user, method
        );
    }

    @AfterReturning("execution(* com.myproject.smartlocksecuritysystem.service.SmartLockService.unlock(..))")
    public void logSuccess(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String user = args.length > 0 ? String.valueOf(args[0]) : "UNKNOWN";

        log.info(
                "SUCCESS: User has entered the building | user={}",
                user
        );
    }
}
