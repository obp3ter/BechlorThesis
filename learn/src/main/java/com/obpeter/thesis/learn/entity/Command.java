package com.obpeter.thesis.learn.entity;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.obpeter.thesis.learn.util.RandomForestMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "shm", type = "command")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Command implements Serializable {
    @Id
    @RandomForestMapping(strategy = RandomForestMapping.Strategy.IGNORE)
    private UUID id;

    @Field
    @RandomForestMapping(strategy = RandomForestMapping.Strategy.CONTAINS)
    private String freeText;

    @Field(type = FieldType.Date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @RandomForestMapping(strategy = RandomForestMapping.Strategy.RANGE)
    private LocalDateTime time;

    @Field
    @RandomForestMapping(strategy = RandomForestMapping.Strategy.EQUALS)
    private DayOfWeek dayOfWeek;

    @Field
    @RandomForestMapping(strategy = RandomForestMapping.Strategy.RANGE)
    private Long timeOfDay;

    public static class CommandBuilder {
        public CommandBuilder defaults() {
            this.time = LocalDateTime.now();
            this.id = UUID.randomUUID();
            this.dayOfWeek = time.getDayOfWeek();
            this.timeOfDay = (long) (time.getHour() * 60 + time.getMinute()) * 60 + time.getSecond();
            return this;
        }
    }

}
