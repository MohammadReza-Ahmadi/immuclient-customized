package com.mrapp.docker.dockerhelloworld.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mrapp.docker.dockerhelloworld.model.Account;
import com.mrapp.docker.dockerhelloworld.repository.ImmudbRepository;
import io.codenotary.immudb.ImmudbProto;
import io.codenotary.immudb4j.crypto.VerificationException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountImmudbAuditService {

    private final ImmudbRepository immudbRepository;

    public AccountImmudbAuditService(ImmudbRepository immudbRepository) {
        this.immudbRepository = immudbRepository;
    }

    public void addAudit(Account account) throws VerificationException, JsonProcessingException {
        String json = new ObjectMapper().writer().writeValueAsString(account);
//        System.out.println(json);
        immudbRepository.getClient().safeSet(String.valueOf(account.getId()), json.getBytes());
    }

    public Account getLastAudit(Long accountId) throws VerificationException, JsonProcessingException {
        byte[] bytes;
        try {
            bytes = immudbRepository.getClient().safeGet(String.valueOf(accountId));
        } catch (StatusRuntimeException e) {
            immudbRepository.login();
            return getLastAudit(accountId);
        }
        String json = new String(bytes, StandardCharsets.UTF_8);
        return new ObjectMapper().readValue(json, Account.class);
    }


    public List<Account> getAudits(Long accountId) throws JsonProcessingException, UnsupportedEncodingException, InvalidProtocolBufferException {
        ImmudbProto.ItemList itemList = immudbRepository.getClient().getStub().history(ImmudbProto.Key.newBuilder().setKey(ByteString.copyFrom(String.valueOf(accountId), "UTF-8")).build());

        List<Account> accountAuditList = new ArrayList<>(itemList.getItemsCount());
        for (ImmudbProto.Item item : itemList.getItemsList()) {
            String json = item.getParserForType().parseFrom(item.getValue()).toBuilder().getValue().toStringUtf8();
//            System.out.println(json);
            accountAuditList.add(new ObjectMapper().readValue(json, Account.class));
        }

        return accountAuditList;
    }

    public int getAuditsCount(Long accountId) throws JsonProcessingException, UnsupportedEncodingException, InvalidProtocolBufferException {
        ImmudbProto.ItemList itemList = immudbRepository.getClient().getStub().history(ImmudbProto.Key.newBuilder().setKey(ByteString.copyFrom(String.valueOf(accountId), "UTF-8")).build());
        return itemList.getItemsCount();
    }


}
