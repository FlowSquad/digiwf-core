package io.muenchendigital.digiwf.integration.core.api;

/**
 * Interface to execute an integration.
 */
public interface IntegrationExecuteApi {

    /**
     * Executes an integration.
     *
     * @param type type of the integration
     * @param data data to be processed
     * @return result of the integration
     */
    Object execute(final String type, final Object data);

}
