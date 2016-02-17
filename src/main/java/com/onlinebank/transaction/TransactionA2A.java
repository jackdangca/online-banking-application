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

    @NotNull
    private Long srcAccountId;

    @NotNull
    private Long dstAccountId;

    private Double balance;

    public Double getBalance() {
        return balance;
    }

    public Long getA2atransactionId() {
        return a2atransactionId;
    }

    public Long getDstAccountId() {
        return dstAccountId;
    }

    public Long getSrcAccountId() {
        return srcAccountId;
    }

    public TransactionA2A setA2atransactionId(Long a2atransactionId) {
        this.a2atransactionId = a2atransactionId;
        return this;
    }

    public TransactionA2A setBalance(Double balance) {
        this.balance = balance;
        return this;
    }

    public TransactionA2A setDstAccountId(Long dstAccountId) {
        this.dstAccountId = dstAccountId;
        return this;
    }

    public TransactionA2A setSrcAccountId(Long srcAccountId) {
        this.srcAccountId = srcAccountId;
        return this;
    }

}
