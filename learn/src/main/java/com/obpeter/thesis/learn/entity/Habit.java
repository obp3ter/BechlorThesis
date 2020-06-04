package com.obpeter.thesis.learn.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "habits", type = "habit")
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



}
