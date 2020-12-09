package com.mrapp.docker.dockerhelloworld.immudb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrapp.docker.dockerhelloworld.model.Account;
import com.mrapp.docker.dockerhelloworld.repository.ImmudbRepository;
import io.codenotary.immudb4j.crypto.VerificationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceOld {

    private final ImmudbRepository immudbRepository;

    public AccountServiceOld(ImmudbRepository immudbRepository) {
        this.immudbRepository = immudbRepository;
    }

    public void auditAccount(Account account) throws VerificationException {
        JSONObject accountJson = new JSONObject();
        accountJson.put("account",account);

        immudbRepository.getClient().safeSet(String.valueOf(account.getId()),accountJson.toJSONString().getBytes());
    }

    public List<Account> getAudits(String key) throws VerificationException, ParseException, JsonProcessingException {
        byte[] bytes = immudbRepository.getClient().safeGet(key);
        String json = new String(bytes, StandardCharsets.UTF_8);

//        JSONParser jsonParser = new JSONParser();
//        Account account = (Account) jsonParser.parse(json);

        new ObjectMapper().readValue(json,Account.class);
        return  new ArrayList<>();
    }


}
