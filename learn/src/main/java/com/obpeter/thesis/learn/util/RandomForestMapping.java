package com.obpeter.thesis.learn.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RandomForestMapping {

    Strategy strategy() default Strategy.EQUALS;

    enum Strategy {
        RANGE,
        EQUALS,
        CONTAINS,
        IGNORE
    }
}
