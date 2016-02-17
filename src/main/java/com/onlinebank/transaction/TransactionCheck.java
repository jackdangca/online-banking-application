package com.onlinebank.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 * Created by p0wontnx on 2/17/16.
 */
@Entity(name = "CheckTransaction")
@PrimaryKeyJoinColumn(name = "checktransactionId", referencedColumnName = "transactionId")
public class TransactionCheck extends Transaction {

    @Column(insertable = false, updatable = false)
    private Long checktransactionId;

    @NotNull
    private Long checkId;

    @NotNull
    private Long srcAccountId;

    @NotNull
    private Long dstAccountId;

    public Long getSrcAccountId() {
        return srcAccountId;
    }

    public Long getDstAccountId() {
        return dstAccountId;
    }

    public Long getCheckId() {
        return checkId;
    }

    public Long getChecktransactionId() {
        return checktransactionId;
    }

    public TransactionCheck setCheckId(Long checkId) {
        this.checkId = checkId;
        return this;
    }

    public TransactionCheck setChecktransactionId(Long checktransactionId) {
        this.checktransactionId = checktransactionId;
        return this;
    }

    public TransactionCheck setDstAccountId(Long dstAccountId) {
        this.dstAccountId = dstAccountId;
        return this;
    }

    public TransactionCheck setSrcAccountId(Long srcAccountId) {
        this.srcAccountId = srcAccountId;
        return this;
    }
}
