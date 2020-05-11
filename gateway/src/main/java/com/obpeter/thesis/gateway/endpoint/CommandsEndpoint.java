package com.obpeter.thesis.gateway.endpoint;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.obpeter.thesis.gateway.entity.Command;
import com.obpeter.thesis.gateway.util.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    HttpClient client;

    private final Gson gson = new Gson();

    @GetMapping
    ResponseEntity getAll() {
        return client.get("database-manager", "command");
    }

    @GetMapping("/{id}")
    ResponseEntity get(@PathVariable UUID id) {
        return client.get("database-manager", "command", id.toString());
    }

    @PostMapping
    ResponseEntity add(Command command) {

        return client.post("database-manager", Map.of(), "command");
    }

    @PostMapping("/issue")
    ResponseEntity issue(@RequestParam String freeText) {
        HashMap<String, String> assistantReq = new HashMap<>();
        assistantReq.put("name", "obp3ter");
        assistantReq.put("command", freeText);
        assistantReq.put("converse", "true");
        ResponseEntity assistantResponseEntity = client.post("assistant-relay", assistantReq, "assistant");
        String response = assistantResponseEntity.getBody().toString();
        JsonObject assistantResponseObject = gson.fromJson(response, JsonObject.class);
        if (assistantResponseEntity.getStatusCode() == HttpStatus.OK &&
                !assistantResponseObject.get("response").getAsString().equals("")) {
            return client.post("database-manager", Map.of("freeText", freeText), "command", "issue");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PostMapping("/avgExecTOD")
    ResponseEntity executionTimeAverage(@RequestParam String freeText){
        ResponseEntity postResponse = client.post("database-manager", Map.of("freeText", freeText), "command", "avgExecTOD");
        if(postResponse.getStatusCode()!= HttpStatus.OK)
            return postResponse;
        JsonObject postBody = gson.fromJson(
                postResponse.getBody().toString(),
                JsonObject.class);
        JsonObject response = new JsonObject();
        response.addProperty("hours",postBody.get("first").getAsInt());
        response.addProperty("minutes",postBody.get("second").getAsInt());
        return ResponseEntity.ok(response.toString());
    }

}
