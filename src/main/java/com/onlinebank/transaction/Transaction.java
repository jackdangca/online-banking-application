package com.onlinebank.transaction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaction {

    @Id
    @GeneratedValue
    private Long transactionId;

    @NotNull
    private Date date;

    private String transactionType;

    public String getTransactionType() {
        return transactionType;
    }

    public Transaction setTransactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Transaction setDate(Date date) {
        this.date = date;
        return this;
    }

    public Transaction setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }
}
