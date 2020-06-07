package com.obpeter.thesis.learn.entity.habit.properties;

public class HabitRangeProperty extends HabitProperty {

    public HabitRangeProperty(String propertyName, String value1, String value2) {
        super.propertyName = propertyName;
        super.value1 = value1;
        super.value2 = value2;
        super.queryType = "RANGE";
    }

    @Override
    public boolean evaluate(String evaluated) {
        return value1.compareTo(evaluated) <= 0 && evaluated.compareTo(value2) <= 0;
    }
}
