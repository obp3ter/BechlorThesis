package com.obpeter.thesis.dbm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableReactiveElasticsearchRepositories
public class DbmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbmApplication.class, args);
    }
}
