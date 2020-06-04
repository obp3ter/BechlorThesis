package com.obpeter.thesis.learn.repository;

import java.util.UUID;

import com.obpeter.thesis.learn.entity.Habit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends ElasticsearchRepository<Habit, UUID> {
}
