package com.mrapp.docker.dockerhelloworld.model;

import static com.mrapp.docker.dockerhelloworld.constant.NumberConstants.*;

public enum AccountTransactionType {
    WITHDRAWAL(ZERO_INT),
    DEPOSIT(ONE_INT),
    BLOCK(TWO_INT);

    private int code;

    AccountTransactionType(int code) {
        this.code = code;
    }

    public static AccountTransactionType resolve(int code) {
        for (AccountTransactionType value : AccountTransactionType.values()) {
            if (value.getCode() == code)
                return value;
        }
        throw new IllegalArgumentException("AccountTransactionType by code:" + code + " is not defined!");
    }

    public int getCode() {
        return code;
    }

    public boolean isDeposit() {
        return this.equals(DEPOSIT);
    }

    public boolean isWithdrawal() {
        return this.equals(WITHDRAWAL);
    }
}
