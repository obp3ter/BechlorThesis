package com.obpeter.thesis.learn.endpoint;

import com.obpeter.thesis.learn.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trigger")
public class TriggerEndpoint {

    @Autowired
    LearnService learnService;

    @GetMapping
    public ResponseEntity trigger(){
        learnService.learnNewCommands();
        return ResponseEntity.ok().build();
    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(learnService.getProperties());
    }

    @PostMapping("/habitByFreeText")
    public ResponseEntity getHabitByFreeText(@RequestParam String freeText){
        return ResponseEntity.ok(learnService.getHabitByFreeText(freeText));
    }

}
