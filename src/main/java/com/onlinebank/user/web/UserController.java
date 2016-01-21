package com.onlinebank.user.web;

import com.onlinebank.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        Assert.notNull(userService);
        this.userService = userService;
    }

}
