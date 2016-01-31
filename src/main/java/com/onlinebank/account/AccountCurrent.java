package com.onlinebank.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 * Created by p0wontnx on 1/29/16.
 */
@Entity(name = "CurrentAccount")
@PrimaryKeyJoinColumn(name = "currentaccountId", referencedColumnName = "transactionaccountId")
public class AccountCurrent extends AccountTransaction {

    @Column(insertable = false, updatable = false)
    private Long currentaccountId;
    @NotNull
    private Long taxpromotionId;

    public Long getCurrentaccountId() {
        return currentaccountId;
    }

    public void setCurrentaccountId(Long currentaccountId) {
        this.currentaccountId = currentaccountId;
    }

    public Long getTaxpromotionId() {
        return taxpromotionId;
    }

    public void setTaxpromotionId(Long taxpromotionId) {
        this.taxpromotionId = taxpromotionId;
    }
}
