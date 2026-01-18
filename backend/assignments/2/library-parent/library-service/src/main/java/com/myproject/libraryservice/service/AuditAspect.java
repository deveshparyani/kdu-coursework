package com.myproject.libraryservice.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private static final Logger log =
            LoggerFactory.getLogger(AuditAspect.class);

    @AfterReturning("@annotation(auditLog)")
    public void audit(JoinPoint joinPoint, AuditLog auditLog) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username =
                auth != null ? auth.getName() : "anonymous";

        log.info(
                "AUDIT | user={} action={} method={} correlationId={}",
                username,
                auditLog.action(),
                joinPoint.getSignature().toShortString(),
                MDC.get("correlationId")
        );
    }
}
