package com.obpeter.thesis.dbm.entity.habit.properties;


public class HabitContainsProperty extends HabitProperty {

    public HabitContainsProperty(String propertyName, String value1) {
        super.propertyName = propertyName;
        super.value1 = value1;
        super.queryType = "CONTAINS";
    }

    @Override
    public boolean evaluate(String evaluated) {
        return value1.contains(evaluated);
    }
}
