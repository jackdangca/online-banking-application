package com.onlinebank.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Service
class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        Assert.notNull(repository);
        this.repository = repository;
    }

}
