package com.obpeter.thesis.gateway.util;

import java.util.Arrays;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HttpClient {
    @Autowired
    DiscoveryClient eurekaClient;

    private String app = "database-manager";

    private Client client = ClientBuilder.newClient();

    public Response get(String ... path) {
        StringBuilder suffix = new StringBuilder();
        Arrays.asList(path).forEach(word->suffix.append('/').append(word));
        ServiceInstance instanceInfo = eurekaClient.getInstances(app).get(0);
        return client.target(instanceInfo.getUri())
                .path(suffix.toString())
                .request(MediaType.APPLICATION_JSON).get();
    }
    public Response post(Map<String, String> body, String... path) {
        StringBuilder suffix = new StringBuilder();
        Form form = new Form();
        body.forEach(form::param);
        Arrays.asList(path).forEach(word->suffix.append('/').append(word));
        ServiceInstance instanceInfo = eurekaClient.getInstances(app).get(0);
        return client.target(instanceInfo.getUri())
                .path(suffix.toString())
                .request(MediaType.APPLICATION_JSON).post(Entity.form(form));
    }


}
