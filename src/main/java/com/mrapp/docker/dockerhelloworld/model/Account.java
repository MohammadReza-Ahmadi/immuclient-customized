package com.mrapp.docker.dockerhelloworld.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account {

    private Long id;

   @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;

    private AccountType type;

    private Long bookkeepingRequestId;

    private String title;

    private AccountStatus status;

    @Enumerated(EnumType.ORDINAL)
    private AccountTransactionType transactionType;

    private BigDecimal balance;

    private BigDecimal withdrawableBalance;

    private BigDecimal blockedBalance;

    private BigDecimal transactionAmount;

    private Date createTimestamp;

    private String description;

}
