package com.myproject.smartlocksecuritysystem.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1) // MUST run before logging
public class SecurityGuardAspect {

    private static final Logger log =
            LoggerFactory.getLogger(SecurityGuardAspect.class);

    @Around("execution(* com.myproject.smartlocksecuritysystem.service.SmartLockService.unlock(..))")
    public Object secureUnlock(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        String user = args.length > 0 ? String.valueOf(args[0]) : "UNKNOWN";

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        if ("Unknown".equalsIgnoreCase(user)) {
            log.warn(
                    "SECURITY ALERT: Unauthorized access blocked! | user={} | operation={}.{}",
                    user, className, methodName
            );
            return null;
        }

        return joinPoint.proceed();
    }


    @Around("execution(* com.myproject.smartlocksecuritysystem.service.SmartLockService.checkBattery(..))")
    public Object measureBatteryTime(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;

        log.info(
                "Battery check completed | method={} | duration={}ms",
                methodName, duration
        );

        return result;
    }
}
