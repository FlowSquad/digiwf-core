package io.muenchendigital.digiwf.integration.core.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Represents an integration.
 */
@Getter
@AllArgsConstructor
public class Integration {

    private String type;

    private Long timeout;

    private Object instance;

    private Method method;

    private Class<?> inputType;

    private Class<?> outputType;

    /**
     * Executes the integration.
     *
     * @param data data to be processed
     * @return result of the integration
     * @throws InvocationTargetException if the underlying method throws an exception.
     * @throws IllegalAccessException    if this Method object is enforcing Java language access control and the underlying method is inaccessible.
     */
    public Object execute(final Object data) throws InvocationTargetException, IllegalAccessException {
        return this.getMethod().invoke(this.instance, data);
    }

}
