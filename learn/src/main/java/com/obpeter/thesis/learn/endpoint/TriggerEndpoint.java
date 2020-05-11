package com.obpeter.thesis.learn.endpoint;

import com.obpeter.thesis.learn.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trigger")
public class TriggerEndpoint {

    @Autowired
    LearnService learnService;

    @GetMapping
    public ResponseEntity trigger(){
        return ResponseEntity.ok(learnService.sometin());
    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(learnService.getProperties());
    }

}
