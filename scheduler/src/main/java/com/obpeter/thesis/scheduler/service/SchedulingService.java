package com.obpeter.thesis.scheduler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.obpeter.thesis.scheduler.entity.Command;
import com.obpeter.thesis.scheduler.entity.habit.Habit;
import com.obpeter.thesis.scheduler.repository.HabitRepository;
import com.obpeter.thesis.scheduler.util.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@EnableAsync
@Component
@Service
public class SchedulingService {
    @Autowired
    HabitRepository repository;

    @Autowired
    HttpClient client;

    private final Gson gson = new Gson();

    List<Habit> latelyEvaluated = new ArrayList<>();

    List<Habit> toEvaluate = new ArrayList<>();

    @Scheduled(fixedRate = 10 * 1000)
    public void upcomingHabits() {

        Command current = Command.builder().defaults().build();

        StreamSupport.stream(repository.findAll().spliterator(), true).filter(Habit::getActive)
                .filter(habit -> habit.evaluate(current))
                .forEach(toEvaluate::add);

        latelyEvaluated.stream().parallel().filter(habit -> !habit.evaluate(current)).forEach(habit -> {
            habit.setAlreadyExecuted(false);
            repository.save(habit);
            latelyEvaluated.remove(habit);
        });

    }

    @Scheduled(fixedRate = 1000)
    public void executor() {
        toEvaluate.stream().parallel().forEach(habit -> {
            if (executeCommand(habit)) {
                habit.setAlreadyExecuted(true);
                latelyEvaluated.add(habit);
                toEvaluate.remove(habit);
            }
        });
    }

    boolean executeCommand(Habit habit) {
        String freeText = habit.getFreeText();
        HashMap<String, String> assistantReq = new HashMap<>();
        assistantReq.put("name", "obp3ter");
        assistantReq.put("command", freeText);
        assistantReq.put("converse", "true");
        ResponseEntity assistantResponseEntity = client.post("assistant-relay", assistantReq, "assistant");
        String response = assistantResponseEntity.getBody().toString();
        JsonObject assistantResponseObject = gson.fromJson(response, JsonObject.class);
        return assistantResponseEntity.getStatusCode() == HttpStatus.OK &&
                !assistantResponseObject.get("response").getAsString().equals("");
    }

}
