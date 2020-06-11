package com.obpeter.thesis.dbm.endpoint;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.obpeter.thesis.dbm.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/habit")
@CrossOrigin(origins = "*")
public class HabitsEndpoint {

    @Autowired
    HabitService habitService;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(habitService.findAll().collect(Collectors.toList()));
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestParam String UUID){
        habitService.delete(UUID);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pause")
    public ResponseEntity pause(@RequestParam String UUID){
        habitService.pause(UUID);
        return ResponseEntity.ok().build();
    }

}
