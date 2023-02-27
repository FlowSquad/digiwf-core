package io.muenchendigital.digiwf.integration.core.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.muenchendigital.digiwf.integration.core.api.IntegrationExecuteApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Component
@Slf4j
public class IntegrationExecuteApiImpl implements IntegrationExecuteApi {

    private final List<Integration> integrations = new ArrayList<>();

    public void registerIntegration(final Integration integrationInfo) {
        this.integrations.add(integrationInfo);
    }

    @Override
    public Object execute(final String type, final Object data) {
        final Optional<Integration> integrationInfo = this.integrations.stream()
                .filter(integration -> integration.getType().equals(type))
                .findFirst();

        if (integrationInfo.isEmpty()) {
            final String errorMessage = String.format("No integration for type %s exists", type);
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        try {
            final Object in = this.mapInput(integrationInfo.get().getInputType(), data);
            return this.mapOutput(integrationInfo.get().execute(in));
        } catch (final InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object mapInput(final Class<?> inputType, final Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(object, inputType);
    }


    private Map<String, Object> mapOutput(final Object output) {
        if (Objects.isNull(output)) {
            return new HashMap<>();
        }

        final ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(output, new TypeReference<>() {});
    }

}
