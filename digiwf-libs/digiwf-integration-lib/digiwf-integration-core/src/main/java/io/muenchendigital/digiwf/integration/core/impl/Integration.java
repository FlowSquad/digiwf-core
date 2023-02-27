package io.muenchendigital.digiwf.integration.core.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public class Integration {

    private String type;

    private Long timeout;

    private Object instance;

    private Method method;

    private Class<?> inputType;

    private Class<?> outputType;

    public Object execute(final Object data) throws InvocationTargetException, IllegalAccessException {
        return this.getMethod().invoke(this.instance, data);
    }

}
