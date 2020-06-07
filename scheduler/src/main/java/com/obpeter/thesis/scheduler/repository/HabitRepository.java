package com.obpeter.thesis.scheduler.repository;

import java.util.UUID;

import com.obpeter.thesis.scheduler.entity.habit.Habit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends ElasticsearchRepository<Habit, UUID> {
}
