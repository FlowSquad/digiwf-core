package io.muenchendigital.digiwf.integration.core.impl;


import io.muenchendigital.digiwf.integration.core.api.DigiwfIntegration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class IntegrationInfoMapper {

    public Integration map(final DigiwfIntegration integration, final Object bean, final Method method) {
        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new IllegalArgumentException("Too many parameters");
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];
        return new Integration(integration.type(), integration.timeout(), bean, method, inputParameter, method.getReturnType());
    }

}
