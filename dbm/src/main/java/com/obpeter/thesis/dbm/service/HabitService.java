package com.obpeter.thesis.dbm.service;

import java.util.UUID;

import com.obpeter.thesis.dbm.entity.habit.Habit;
import org.springframework.stereotype.Service;

@Service
public class HabitService extends BaseService<Habit, UUID> {

    public void pause(String uuid){
        Habit habit = repository.findById(UUID.fromString(uuid)).get();
        habit.setActive(!habit.getActive());
        repository.save(habit);
    }

    public void delete(String uuid){
        repository.deleteById(UUID.fromString(uuid));
    }

}
