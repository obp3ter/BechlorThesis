package com.obpeter.thesis.learn.entity.habit.properties;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class HabitPropertyDeserializer extends StdDeserializer<HabitProperty> {

    public HabitPropertyDeserializer() {
        this(null);
    }

    public HabitPropertyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HabitProperty deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String queryType = node.get("queryType").asText();
        String propertyName= node.get("propertyName").asText();
        String value1=node.get("value1").asText();

        switch (queryType){
        case ("EQUALS"):
            return new HabitEqualsProperty(propertyName,value1);
        case("RANGE"):
            String value2=node.get("value2").asText();
            return new HabitRangeProperty(propertyName,value1,value2);
        case("CONTAINS"):
            return new HabitContainsProperty(propertyName,value1);
        default:
            return null;
        }
    }
}