package com.obpeter.thesis.learn.repository;

import java.util.UUID;

import com.obpeter.thesis.learn.entity.Command;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends ElasticsearchRepository<Command, UUID> {
}
