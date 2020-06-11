package com.obpeter.thesis.dbm.entity.habit;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.obpeter.thesis.dbm.entity.habit.properties.HabitProperty;
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
    private UUID id=UUID.randomUUID();

    private String freeText;

    private List<HabitProperty> properties;

    @Builder.Default
    private Boolean alreadyExecuted=false;

    @Builder.Default
    private Boolean active=true;

}
