package com.mrapp.docker.dockerhelloworld.repository;

import com.google.common.collect.ImmutableClassToInstanceMap;
import io.codenotary.immudb.ImmuServiceGrpc;
import io.codenotary.immudb.ImmudbProto;
import io.codenotary.immudb4j.ImmuClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class ImmudbRepository {
    private ImmuClient client;

    @Value("${immudb.server.url}")
    private String url;
    @Value("${immudb.server.port}")
    private int port;
    @Value("${immudb.user}")
    private String user;
    @Value("${immudb.pass}")
    private String pass;

    @PostConstruct
    public void login(){
        this.client = ImmuClient.newBuilder().setServerUrl(url).setServerPort(port).build();
        this.client.login(user, pass);
    }

    public ImmuClient getClient() {
        return client;
    }
}
