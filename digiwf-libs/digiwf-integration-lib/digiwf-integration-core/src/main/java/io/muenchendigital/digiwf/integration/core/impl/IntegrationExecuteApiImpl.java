package io.muenchendigital.digiwf.integration.core.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.muenchendigital.digiwf.integration.core.api.IntegrationExecuteApi;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Implementation of the integration execute api. Follows the observer pattern to register and execute integrations {@see https://en.wikipedia.org/wiki/Observer_pattern#Java}.
 */
@Component
public class IntegrationExecuteApiImpl implements IntegrationExecuteApi {

    private final List<Integration> integrations = new ArrayList<>();

    /**
     * Registers an integration.
     *
     * @param integration integration to be registered
     */
    public void registerIntegration(final Integration integration) {
        this.integrations.add(integration);
    }

    /**
     * Executes an integration.
     * It iterates through all registered integrations and executes the first one that matches the given type.
     *
     * @param type type of the integration
     * @param data data to be processed
     * @return result of the integration
     */
    @Override
    public Object execute(final String type, final Object data) {
        final Optional<Integration> integrations = this.integrations.stream()
                .filter(integration -> integration.getType().equals(type))
                .findFirst();

        if (integrations.isEmpty()) {
            final String errorMessage = String.format("No integration for type %s exists", type);
            throw new RuntimeException(errorMessage);
        }

        try {
            final Object in = this.mapInput(integrations.get().getInputType(), data);
            return this.mapOutput(integrations.get().execute(in));
        } catch (final InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper function to map the input data to the expected type.
     * @param inputType
     * @param object
     * @return
     */
    private Object mapInput(final Class<?> inputType, final Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(object, inputType);
    }

    /**
     * Helper function to map the output data to a map.
     * @param output
     * @return
     */
    private Map<String, Object> mapOutput(final Object output) {
        if (Objects.isNull(output)) {
            return new HashMap<>();
        }

        final ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(output, new TypeReference<>() {});
    }

}
