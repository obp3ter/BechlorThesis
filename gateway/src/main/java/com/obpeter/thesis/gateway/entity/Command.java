package com.obpeter.thesis.gateway.entity;

import java.time.Instant;
import java.util.UUID;

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
public class Command {
    private UUID id;
    private String freeText;
    private long time;

    public static class CommandBuilder{
        public CommandBuilder defaults(){
            this.time=Instant.now().toEpochMilli();
            this.id=UUID.randomUUID();
            return this;
        }
    }

}
