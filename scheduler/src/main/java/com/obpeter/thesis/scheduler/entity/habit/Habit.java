package com.obpeter.thesis.scheduler.entity.habit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.obpeter.thesis.scheduler.entity.Command;
import com.obpeter.thesis.scheduler.entity.habit.properties.HabitProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

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

    @Builder.Default
    private Boolean active=true;

    public boolean evaluate(Command currentConditions) {

        List<List<HabitProperty>> habitPropertiesSorted = new ArrayList<>();

        properties.forEach(property->{
            AtomicInteger index= new AtomicInteger(-1);
            IntStream.range(0,habitPropertiesSorted.size()).forEach(i-> {
                if(habitPropertiesSorted.get(i).get(0)
                        .getPropertyName().equals(property.getPropertyName()) && index.get() ==-1)
                {
                    index.set(i);
                    habitPropertiesSorted.get(i).add(property);
                }

            });
            if(index.get()==-1)
                habitPropertiesSorted.add(new ArrayList<>(Collections.singletonList(property)));
        });
        return habitPropertiesSorted.stream().allMatch(habitProperty -> habitProperty.stream().anyMatch(property -> {
                        try {
                            return property.evaluate(Command.class.getDeclaredMethod(
                                    "get" + property.getPropertyName().substring(0, 1).toUpperCase() + property.getPropertyName()
                                            .substring(1)).invoke(currentConditions).toString());
                        } catch (Exception e) {
                            return false;
                        }
                    }));
//        return properties.stream().allMatch(property -> {
//            try {
//                return property.evaluate(Command.class.getDeclaredMethod(
//                        "get" + property.getPropertyName().substring(0, 1).toUpperCase() + property.getPropertyName()
//                                .substring(1)).invoke(currentConditions).toString());
//            } catch (Exception e) {
//                return false;
//            }
//        });
    }

}
