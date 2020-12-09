package com.mrapp.docker.dockerhelloworld.immudb;
/*
Copyright 2019-2020 vChain, Inc.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
	http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


import com.mrapp.docker.dockerhelloworld.model.Account;
import com.mrapp.docker.dockerhelloworld.service.AccountImmudbAuditService;
import io.codenotary.immudb4j.FileRootHolder;
import io.codenotary.immudb4j.ImmuClient;
import io.codenotary.immudb4j.KV;
import io.codenotary.immudb4j.KVList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImmudbApp implements CommandLineRunner {

    @Autowired
    private AccountImmudbAuditService accountImmudbAuditService;
    @Autowired
    private AccountServiceOld accountServiceOld;


//    @Override
    public void run_3(String... args) throws Exception {
        Account account = createSampleAccount();
        accountServiceOld.auditAccount(account);
        accountServiceOld.getAudits("3");
    }

    @Override
    public void run(String... args) throws Exception {
//        Account account = createSampleAccount();

//        accountServiceGson.auditAccount(account);
//        accountServiceGson.getAudits("3");
    }

    private Account createSampleAccount() {
        Account account = new Account();
        account.setId(3L);
        account.setUserId(66L);
        account.setTitle("Account-3");
        account.setDescription("some desc:3");
        account.setBalance(BigDecimal.valueOf(2000));
        account.setWithdrawableBalance(BigDecimal.valueOf(2000));
        account.setBlockedBalance(BigDecimal.ZERO);
        account.setBookkeepingRequestId(200L);
        account.setTransactionAmount(BigDecimal.valueOf(2000));
        return account;
    }

    //    @Override
    public void run_1(String... args) throws Exception {
        ImmuClient client = null;

        try {
            FileRootHolder rootHolder = FileRootHolder.newBuilder().setRootsFolder("./helloworld_immudb_roots").build();
//            client = ImmuClient.newBuilder().setServerUrl("localhost").setServerPort(3322).setRootHolder(rootHolder).build();

            client = ImmuClient.newBuilder().setServerUrl("localhost").setServerPort(3322).build();
            client.login("immudb", "immudb");


            client.set("hello", "immutable world!".getBytes());

//            byte[] v = client.safeGet("hello");
            //todo: test
            byte[] v = client.get("hello");

            System.out.format("(%s, %s)", "hello \n", new String(v));


            // Multi-key operations

            KVList.KVListBuilder builder = KVList.newBuilder();

            builder.add("k123", new byte[]{1, 2, 3});
            builder.add("k321", new byte[]{3, 2, 1});

            KVList kvList = builder.build();

            client.setAll(kvList);


            List<String> keyList = new ArrayList<String>();

            keyList.add("k123");
            keyList.add("k321");
            keyList.add("k231");

            List<KV> result = client.getAll(keyList);

            for (KV kv : result) {
                byte[] key = kv.getKey();
                byte[] value = kv.getValue();

                System.out.format("(%s, %s) \n", new String(key), Arrays.toString(value));
            }


        } catch (IOException e) {
            e.printStackTrace();
//        } catch (VerificationException e) {
//            // TODO: tampering detected!
//            // This means the history of changes has been tampered
//            e.printStackTrace();
//            System.exit(1);
        } finally {
            if (client != null) {
                client.logout();
            }
        }

    }

}
