package com.onlinebank.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 * Created by p0wontnx on 1/29/16.
 */
@Entity(name = "SavingAccount")
@PrimaryKeyJoinColumn(name = "savingaccountId", referencedColumnName = "transactionaccountId")
public class AccountSaving extends AccountTransaction {

    @Column(insertable = false, updatable = false)
    private Long savingaccountId;
    @NotNull
    private Long bonuspromotionId;
    @NotNull
    private Long withdrawallimitpromotionId;
    @NotNull
    private Long taxpromotionId;

    public Long getSavingaccountId() {
        return savingaccountId;
    }

    public void setSavingaccountId(Long savingaccountId) {
        this.savingaccountId = savingaccountId;
    }

    public Long getBonuspromotionId() {
        return bonuspromotionId;
    }

    public void setBonuspromotionId(Long bonuspromotionId) {
        this.bonuspromotionId = bonuspromotionId;
    }

    public Long getWithdrawallimitpromotionId() {
        return withdrawallimitpromotionId;
    }

    public void setWithdrawallimitpromotionId(Long withdrawallimitpromotionId) {
        this.withdrawallimitpromotionId = withdrawallimitpromotionId;
    }

    public Long getTaxpromotionId() {
        return taxpromotionId;
    }

    public void setTaxpromotionId(Long taxpromotionId) {
        this.taxpromotionId = taxpromotionId;
    }
}
