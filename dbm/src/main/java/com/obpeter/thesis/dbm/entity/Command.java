package com.obpeter.thesis.dbm.entity;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName ="shm",type = "command")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Command {
    @Id
    private UUID id;
    @Field
    private String freeText;
    @Field
    private long time;

    public static class CommandBuilder{
        public CommandBuilder defaults(){
            this.time=Instant.now().toEpochMilli();
            this.id=UUID.randomUUID();
            return this;
        }
    }

}
