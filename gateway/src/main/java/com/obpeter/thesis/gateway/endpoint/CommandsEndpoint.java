package com.obpeter.thesis.gateway.endpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Response;

import com.obpeter.thesis.gateway.entity.Command;
import com.obpeter.thesis.gateway.util.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
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

    @GetMapping
    List<Command> getAll() {
        return client.get("database-manager","command").readEntity(List.class);
    }

    @GetMapping("/{id}")
    Command get(@PathVariable UUID id) {
        return client.get("database-manager","command",id.toString()).readEntity(Command.class);
    }

    @PostMapping
    Command add(Command command) {

        return client.post("database-manager",Map.of(),"command").readEntity(Command.class);
    }

    @PostMapping("/issue")
    Command issue(@RequestParam String freeText) {
        HashMap<String, String> assistantReq=new HashMap<>();
        assistantReq.put("name","obp3ter");
        assistantReq.put("command",freeText);
        assistantReq.put("converse","true");
        client.post("assistant-relay",assistantReq,"assistant");
        return client.post("database-manager",Map.of("freeText",freeText),"command","issue").readEntity(Command.class);
    }

}
