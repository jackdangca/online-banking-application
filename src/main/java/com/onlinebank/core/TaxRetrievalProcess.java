package com.onlinebank.core;

import com.onlinebank.account.Account;
import com.onlinebank.account.AccountService;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by p0wontnx on 1/31/16.
 */
@Component
public class TaxRetrievalProcess {

    private AccountService accountService;
    private UserService userService;

    @Autowired
    public TaxRetrievalProcess(AccountService accountService, UserService userService) {
        Assert.notNull(accountService);
        this.accountService = accountService;

        Assert.notNull(userService);
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 * * *")
    public void retrieveTaxFromAccount() {

        List<User> userList = userService.findAll();

        for (User user : userList) {
            List<Account> accountList = accountService.findAll(user);

            for (Account account : accountList) {

            }

        }


    }

}
