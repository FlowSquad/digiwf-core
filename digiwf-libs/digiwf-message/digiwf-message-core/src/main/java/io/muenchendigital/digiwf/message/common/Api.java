package io.muenchendigital.digiwf.message.common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Api {

  @AliasFor(annotation = Component.class)
  String value() default "";

}
