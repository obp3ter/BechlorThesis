package com.obpeter.thesis.dbm.endpoint;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.obpeter.thesis.dbm.entity.Command;
import com.obpeter.thesis.dbm.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command")
public class CommandsEndpoint {

    @Autowired
    CommandService service;

    @GetMapping
    ResponseEntity<List<Command>> getAll() {
    return ResponseEntity.ok(service.findAll().collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<Command> get(@PathVariable UUID id){
        return ResponseEntity.ok(service.find(id).orElseThrow());
    }

    @PostMapping
    ResponseEntity<Command>add(Command command){
        return ResponseEntity.ok(service.add(command));
    }

    @PostMapping("/issue")
    ResponseEntity<Command>issue(@RequestParam String freeText){
        return ResponseEntity.ok(service.add(Command.builder().defaults().freeText(freeText).build()));
    }

    @PostMapping("/dow")
    ResponseEntity<List<Command>> searchByDOW(@RequestParam String dayOfWeek){
        return ResponseEntity.ok(service.searchForDOW(DayOfWeek.valueOf(dayOfWeek.toUpperCase())).collect(Collectors.toList()));
    }
    @PostMapping("/hm")
    ResponseEntity<List<Command>> searchByHourAndMinute(@RequestParam Short hour, @RequestParam @Nullable Short minute){
        return ResponseEntity.ok(service.searchHourMinute(hour,minute).collect(Collectors.toList()));
    }

}
