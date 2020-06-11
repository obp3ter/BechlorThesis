package com.obpeter.thesis.gateway.endpoint;

import java.util.HashMap;

import com.google.gson.Gson;
import com.obpeter.thesis.gateway.util.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/habit")
public class HabitsEndpoint {
    @Autowired
    HttpClient client;

    @GetMapping
    ResponseEntity getAll() {
        return client.get("database-manager", "habit");
    }

    @PostMapping("/pause")
    ResponseEntity pause(String UUID) {

        HashMap<String, String> form = new HashMap<>();
        form.put("UUID",UUID);

        return client.post("database-manager", form, "habit","pause");
    }
    @PostMapping("/delete")
    ResponseEntity delete(String UUID) {

        HashMap<String, String> form = new HashMap<>();
        form.put("UUID",UUID);

        return client.post("database-manager", form, "habit","delete");
    }

}
