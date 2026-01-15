package com.myproject.smartlocksecuritysystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SystemAlarmAspect {

    private static final Logger log =
            LoggerFactory.getLogger(SystemAlarmAspect.class);

    @AfterThrowing(
            pointcut = "execution(* com.myproject.smartlocksecuritysystem.service.SmartLockService.*(..))",
            throwing = "ex"
    )
    public void triggerAlarm(JoinPoint joinPoint, Exception ex) {

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.error(
                "ALARM TRIGGERED: System error detected | operation={}.{} | reason={}",
                className, methodName, ex.getMessage()
        );

        log.error("Calling emergency maintenance service...");
    }
}
