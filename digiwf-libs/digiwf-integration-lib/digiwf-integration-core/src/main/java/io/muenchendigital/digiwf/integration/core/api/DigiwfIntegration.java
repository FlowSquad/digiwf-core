package io.muenchendigital.digiwf.integration.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method as a digiwf integration.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DigiwfIntegration {
    /**
     * The type of the integration that is used to route the message to the according integration.
     * @return type
     */
    String type();

    long timeout() default 30000L;

}
