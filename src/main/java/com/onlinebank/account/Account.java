package com.onlinebank.account;

import com.onlinebank.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long account_id;

    @NotNull
    private String label;

    private Integer number;

    private String password;

    private Double balance;

    private Date creation_date;
    @NotNull
    private Long user_id;

}
