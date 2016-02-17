package com.onlinebank.account.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onlinebank.account.*;
import com.onlinebank.account.exceptions.AccountCreationFailedException;
import com.onlinebank.account.exceptions.AccountEditingException;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import com.onlinebank.user.exceptions.UserNotFoundException;
import com.onlinebank.utils.AccountTypes;
import com.onlinebank.utils.ResponseBuilder;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
@RestController
@RequestMapping(path = "/api/user/{userId}/account")
public class AccountController {

    private AccountService accountService;
    private UserService userService;

    @Autowired
    public AccountController(AccountService accountService, UserService userService) {
        Assert.notNull(accountService);
        this.accountService = accountService;

        Assert.notNull(userService);
        this.userService = userService;
    }

    //region Account infos
    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> getAllUserAccounts(@PathVariable("userId") Long userId) throws UserNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        // retrieve user
        user = userService.find(userId);

        // retrieve account list
        List<Account> accountList = accountService.findAll(user);
        responseBuilder.setResponseResult(accountList);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> getUserAccountInfos(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) throws UserNotFoundException, AccountNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;
        Account account = null;

        // retrieve user
        user = userService.find(userId);

        // retrieve account
        account = accountService.find(accountId, user);
        responseBuilder.setResponseResult(account);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }
    //endregion

    //region Account creation
    @RequestMapping(path = "/add/term",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> addTermAccount(@PathVariable("userId") Long userId, AccountTerm accountTerm) throws UserNotFoundException, BadRequestException, AccountCreationFailedException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        // retrieve user
        user = userService.find(userId);

        // create account
        accountTerm = accountService.add(accountTerm, user);
        responseBuilder.setResponseResult(accountTerm);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/add/current",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> addCurrentAccount(@PathVariable("userId") Long userId, AccountCurrent accountCurrent) throws UserNotFoundException, AccountCreationFailedException, BadRequestException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        // retrieve user
        user = userService.find(userId);

        // create account
        accountCurrent = accountService.add(accountCurrent, user);
        responseBuilder.setResponseResult(accountCurrent);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/add/saving",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> addSavingAccount(@PathVariable("userId") Long userId, AccountSaving accountSaving) throws UserNotFoundException, AccountCreationFailedException, BadRequestException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        // retrieve user
        user = userService.find(userId);

        // create account
        accountSaving = accountService.add(accountSaving, user);
        responseBuilder.setResponseResult(accountSaving);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }
    //endregion

    //region Account editing
    @RequestMapping(path = "/{accountId}/edit/term",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> editTermAccount(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId, AccountTerm accountTerm) throws UserNotFoundException, AccountEditingException, AccountNotFoundException, BadRequestException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        // retrieve user
        user = userService.find(userId);

        // edit account
        accountTerm = accountService.edit(accountId, accountTerm, user);
        responseBuilder.setResponseResult(accountTerm);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{accountId}/edit/current",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> editCurrentAccount(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId, AccountCurrent accountCurrent) throws UserNotFoundException, AccountEditingException, AccountNotFoundException, BadRequestException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        // retrieve user
        user = userService.find(userId);

        // edit account
        accountCurrent = accountService.edit(accountId, accountCurrent, user);
        responseBuilder.setResponseResult(accountCurrent);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{accountId}/edit/saving",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> editSavingAccount(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId, AccountSaving accountSaving) throws UserNotFoundException, AccountEditingException, AccountNotFoundException, BadRequestException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        // retrieve user
        user = userService.find(userId);

        // edit account
        accountSaving = accountService.edit(accountId, accountSaving, user);
        responseBuilder.setResponseResult(accountSaving);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }
    //endregion

    //region Accout delete
    @RequestMapping(path = "/{accountId}/remove",
            method = RequestMethod.GET)
    public ResponseEntity removeAccount(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) throws UserNotFoundException, AccountNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        // retrieve user
        user = userService.find(userId);

        // remove account
        accountService.remove(accountId, user);

        // if success
        return new ResponseEntity(HttpStatus.OK);
    }
    //endregion

}
