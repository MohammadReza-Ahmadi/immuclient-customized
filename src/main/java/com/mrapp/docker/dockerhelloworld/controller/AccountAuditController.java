package com.mrapp.docker.dockerhelloworld.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mrapp.docker.dockerhelloworld.model.Account;
import com.mrapp.docker.dockerhelloworld.service.AccountImmudbAuditService;
import io.codenotary.immudb4j.crypto.VerificationException;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountAuditController {

    private final AccountImmudbAuditService accountImmudbAuditService;

    public AccountAuditController(AccountImmudbAuditService accountImmudbAuditService) {
        this.accountImmudbAuditService = accountImmudbAuditService;
    }

    @PostMapping(value = "/add")
    public void addAccountAudit(@RequestBody Account account) throws VerificationException, JsonProcessingException {
        accountImmudbAuditService.addAudit(account);
    }

    @GetMapping(value = "/{accountId}/last-audit")
    public Account getLastAccountAudit(@PathVariable Long accountId) throws VerificationException, JsonProcessingException {
        return accountImmudbAuditService.getLastAudit(accountId);
    }

    @GetMapping(value = "/{accountId}/audits")
    public List<Account> getAllAccountAudits(@PathVariable Long accountId) throws JsonProcessingException, UnsupportedEncodingException, InvalidProtocolBufferException {
        return accountImmudbAuditService.getAudits(accountId);
    }

    @GetMapping(value = "/{accountId}/audits-count")
    public Integer getAccountAuditsCount(@PathVariable Long accountId) throws JsonProcessingException, UnsupportedEncodingException, InvalidProtocolBufferException {
        return accountImmudbAuditService.getAuditsCount(accountId);
    }
}
