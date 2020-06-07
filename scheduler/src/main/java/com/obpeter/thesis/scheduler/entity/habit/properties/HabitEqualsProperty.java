package com.obpeter.thesis.scheduler.entity.habit.properties;

public class HabitEqualsProperty extends HabitProperty {

    public HabitEqualsProperty(String propertyName, String value1) {
        super.propertyName = propertyName;
        super.value1 = value1;
        super.queryType = "EQUALS";
    }

    @Override
    public boolean evaluate(String evaluated) {
        return value1.equals(evaluated);
    }
}
