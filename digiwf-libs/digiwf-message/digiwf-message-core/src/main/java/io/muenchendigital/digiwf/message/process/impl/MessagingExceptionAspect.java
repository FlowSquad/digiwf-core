package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.error.TechnicalError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class MessagingExceptionAspect {

    private final ProcessApi processApi;

    @Around(value = "@annotation(io.muenchendigital.digiwf.message.process.api.HandleTechnicalError)")
    public void handleTechnicalError(final ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            joinPoint.proceed();
        } catch (final TechnicalError ex) {
            log.info("Handling exception {} with message {}", ex.getClass().getSimpleName(), ex.getErrorMessage());
            this.processApi.handleTechnicalError(ex.getProcessInstanceId(), ex.getErrorCode(), ex.getErrorMessage());
        }
    }

    @Around(value = "@annotation(io.muenchendigital.digiwf.message.process.api.HandleIncident)")
    public void handleIncident(final ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        } catch (final Throwable e) {
            log.warn("Handling exception {} with message {}", e.getClass().getSimpleName(), e.getMessage());
            this.processApi.handleIncident("", "", e.getMessage());
        }
    }

}
