/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.domain.exception;

/**
 * Exception occurring in ALW system call.
 */
public class AlwException extends Exception {

    private String statusCode = "";

    public AlwException(final String message, String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public AlwException(final String message) {
        super(message);
    }

}
