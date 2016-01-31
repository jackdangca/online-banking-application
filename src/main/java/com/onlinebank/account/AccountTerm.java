package com.onlinebank.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 * Created by p0wontnx on 1/29/16.
 */
@Entity(name = "TermAccount")
@PrimaryKeyJoinColumn(name = "termaccountId", referencedColumnName = "accountId")
public class AccountTerm extends Account {

    @Column(insertable = false, updatable = false)
    private Long termaccountId;
    @NotNull
    private Integer duration;
    @NotNull
    private Long bonuspromotionId;

    public Long getTermaccountId() {
        return termaccountId;
    }

    public void setTermaccountId(Long termaccountId) {
        this.termaccountId = termaccountId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getBonuspromotionId() {
        return bonuspromotionId;
    }

    public void setBonuspromotionId(Long bonuspromotionId) {
        this.bonuspromotionId = bonuspromotionId;
    }
}
