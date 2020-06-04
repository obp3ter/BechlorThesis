package com.obpeter.thesis.learn.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@JsonSubTypes({
        @JsonSubTypes.Type(value = HabitRangeProperty.class),
        @JsonSubTypes.Type(value = HabitContainsProperty.class),
        @JsonSubTypes.Type(value = HabitEqualsProperty.class)
})
@JsonDeserialize(using = HabitPropertyDeserializer.class)
@Data
public abstract class HabitProperty implements Serializable {
    String queryType;

    String propertyName;

    String value1;

    String value2;

    abstract boolean evaluate(String evaluated);
}

