package com.obpeter.thesis.learn.entity;

public class HabitEqualsProperty extends HabitProperty {

    public HabitEqualsProperty(String propertyName, String value1) {
        super.propertyName = propertyName;
        super.value1 = value1;
        super.queryType = "EQUALS";
    }

    @Override
    boolean evaluate(String evaluated) {
        return value1.equals(evaluated);
    }
}
