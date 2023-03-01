package io.muenchendigital.digiwf.integration.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception to be thrown when an integration fails with a technical error that may be handled by the caller process.
 */
@AllArgsConstructor
@Getter
public class TechnicalError extends RuntimeException {

    private final String processInstanceId;
    private final String errorCode;
    private final String errorMessage;

}
