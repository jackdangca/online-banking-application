package com.onlinebank.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 * Created by p0wontnx on 2/17/16.
 */
@Entity(name = "A2ATransaction")
@PrimaryKeyJoinColumn(name = "a2atransactionId", referencedColumnName = "transactionId")
public class TransactionA2A extends Transaction {

    @Column(insertable = false, updatable = false)
    private Long a2atransactionId;

    private Long srcAccountId;

    private Long dstAccountId;

    @NotNull
    private Double balance;

    private Long srcAccountNum;

    @NotNull
    private Long dstAccountNum;

    public Double getBalance() {
        return balance;
    }

    public TransactionA2A setBalance(Double balance) {
        this.balance = balance;
        return this;
    }

    public Long getA2atransactionId() {
        return a2atransactionId;
    }

    public TransactionA2A setA2atransactionId(Long a2atransactionId) {
        this.a2atransactionId = a2atransactionId;
        return this;
    }

    public Long getDstAccountId() {
        return dstAccountId;
    }

    public TransactionA2A setDstAccountId(Long dstAccountId) {
        this.dstAccountId = dstAccountId;
        return this;
    }

    public Long getSrcAccountId() {
        return srcAccountId;
    }

    public TransactionA2A setSrcAccountId(Long srcAccountId) {
        this.srcAccountId = srcAccountId;
        return this;
    }

    public Long getDstAccountNum() {
        return dstAccountNum;
    }

    public Long getSrcAccountNum() {
        return srcAccountNum;
    }

    public TransactionA2A setDstAccountNum(Long dstAccountNum) {
        this.dstAccountNum = dstAccountNum;
        return this;
    }

    public TransactionA2A setSrcAccountNum(Long srcAccountNum) {
        this.srcAccountNum = srcAccountNum;
        return this;
    }
}
