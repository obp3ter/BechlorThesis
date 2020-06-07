package com.obpeter.thesis.scheduler.util;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HttpClient {
    @Autowired
    DiscoveryClient eurekaClient;

    private final Client client = ClientBuilder.newClient();

    public ResponseEntity get(String app, String... path) {
        StringBuilder suffix = new StringBuilder();
        Arrays.asList(path).forEach(word -> suffix.append('/').append(word));
        ServiceInstance instanceInfo = eurekaClient.getInstances(app).get(0);
        Response response = client.target(instanceInfo.getUri())
                .path(suffix.toString())
                .request(MediaType.APPLICATION_JSON).get();
        return ResponseEntity.status(Objects.requireNonNull(HttpStatus.resolve(response.getStatus())))
                .body(response.readEntity(String.class));
    }

    public ResponseEntity post(String app, Map<String, String> body, String... path) {
        StringBuilder suffix = new StringBuilder();
        Form form = new Form();
        body.forEach(form::param);
        Arrays.asList(path).forEach(word -> suffix.append('/').append(word));
        ServiceInstance instanceInfo = eurekaClient.getInstances(app).get(0);
        Response response = client.target(instanceInfo.getUri())
                .path(suffix.toString())
                .request(MediaType.APPLICATION_JSON).post(Entity.form(form));
        return ResponseEntity.status(Objects.requireNonNull(HttpStatus.resolve(response.getStatus())))
                .body(response.readEntity(String.class));
    }

}
