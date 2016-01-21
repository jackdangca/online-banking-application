package com.onlinebank.account.web;

import com.onlinebank.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Controller
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        Assert.notNull(accountService);
        this.accountService = accountService;
    }

}
