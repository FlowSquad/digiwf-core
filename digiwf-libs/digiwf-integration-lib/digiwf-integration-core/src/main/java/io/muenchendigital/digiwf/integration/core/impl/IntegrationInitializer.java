package io.muenchendigital.digiwf.integration.core.impl;

import io.muenchendigital.digiwf.integration.core.api.DigiwfIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class IntegrationInitializer implements ApplicationContextAware {
    private ApplicationContext ctx;
    private final IntegrationExecuteApiImpl integrationExecuteApi;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeWorkerAfterStartup() {
        final List<Integration> integrations = this.getIntegrations();
        integrations.forEach(this.integrationExecuteApi::registerIntegration);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    private List<Integration> getIntegrations() {
        final List<Integration> integrations = new ArrayList<>();
        final String[] beanDefinitionNames = this.ctx.getBeanDefinitionNames();
        // Iterate over the list of spring beans and get all methods annotated with the specific annotation
        for (final String beanDefinitionName : beanDefinitionNames) {
            final Object bean = this.ctx.getBean(beanDefinitionName);
            final Class<?> beanClass = bean.getClass();

            ReflectionUtils.doWithMethods(beanClass, method -> {
                // Check if the method is annotated with the specific annotation
                if (method.isAnnotationPresent(DigiwfIntegration.class)) {
                    final DigiwfIntegration annotInstance = method.getAnnotation(DigiwfIntegration.class);
                    integrations.add(this.buildIntegration(annotInstance, bean, method));
                }
            });
        }
        return integrations;
    }

    private Integration buildIntegration(final DigiwfIntegration integration, final Object bean, final Method method) {
        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new IllegalArgumentException("Too many parameters");
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];
        return new Integration(integration.type(), integration.timeout(), bean, method, inputParameter, method.getReturnType());
    }

}
