package com.obpeter.thesis.gateway.entity;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Command implements Serializable {
    private UUID id;
    private String freeText;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;
    private DayOfWeek dayOfWeek;
    private Long timeOfDay;

    public static class CommandBuilder{
        public CommandBuilder defaults(){
            this.time=LocalDateTime.now();
            this.id=UUID.randomUUID();
            this.dayOfWeek=time.getDayOfWeek();
            this.timeOfDay=(long)(time.getHour()*60+time.getMinute())*60+time.getSecond();
            return this;
        }
    }

}
