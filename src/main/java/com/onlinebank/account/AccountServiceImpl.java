package com.onlinebank.account;

import com.onlinebank.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Service
class AccountServiceImpl implements AccountService {

    private AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        Assert.notNull(repository);
        this.repository = repository;
    }

}
