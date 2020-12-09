package com.mrapp.docker.dockerhelloworld;

import com.mrapp.docker.dockerhelloworld.immudb.ImmudbApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DockerHelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerHelloWorldApplication.class, args);
    }


    @Bean
    public ImmudbApp immudbAppRunner(){
        return new ImmudbApp();
    }



}
