package com.onlinebank.account.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by p0wontnx on 2/19/16.
 */
@Controller
public class AccountWebController {

    @RequestMapping(path = "/user/{userId}/account")
    public String accountView(@PathVariable("userId") Long userId) {
        return "userView";
    }

    @RequestMapping(path = "/user/{userId}/account/{accountId}")
    public String accountEditView(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) {
        return "accountView";
    }

}
