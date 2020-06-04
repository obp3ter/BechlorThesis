package com.obpeter.thesis.learn.entity;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = HabitPropertyDeserializer.class)
public abstract class HabitProperty implements Serializable {
    String queryType;

    String propertyName;

    String value1;

    String value2;

    abstract boolean evaluate(String evaluated);
}

