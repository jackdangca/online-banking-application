package com.onlinebank.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Created by p0wontnx on 1/29/16.
 */
@Entity(name = "TransactionAccount")
@PrimaryKeyJoinColumn(name = "transactionaccountId", referencedColumnName = "accountId")
public class AccountTransaction extends Account {

    @Column(insertable = false, updatable = false)
    private Long transactionaccountId;

    public Long getTransactionaccountId() {
        return transactionaccountId;
    }

    public void setTransactionaccountId(Long transactionaccountId) {
        this.transactionaccountId = transactionaccountId;
    }
}
