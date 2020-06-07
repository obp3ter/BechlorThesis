package com.obpeter.thesis.scheduler.entity.habit;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import com.obpeter.thesis.scheduler.entity.Command;
import com.obpeter.thesis.scheduler.entity.habit.properties.HabitProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "habits")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Habit implements Serializable {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    private String freeText;

    private List<HabitProperty> properties;

    @Builder.Default
    private Boolean alreadyExecuted = false;

    public boolean evaluate(Command currentConditions) {
        return properties.stream().allMatch(property -> {
            try {
                return property.evaluate(Command.class.getDeclaredMethod(
                        "get" + property.getPropertyName().substring(0, 1).toUpperCase() + property.getPropertyName()
                                .substring(1)).invoke(currentConditions).toString());
            } catch (Exception e) {
                return false;
            }
        });
    }

}
