package com.onlinebank.account.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onlinebank.account.*;
import com.onlinebank.account.exceptions.AccountCreationFailedException;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import com.onlinebank.user.exceptions.UserNotFoundException;
import com.onlinebank.utils.ResponseBuilder;
import com.onlinebank.utils.Utils;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> listAllUserAccounts(@PathVariable("userId") Long userId) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        try {
            // retrieve user
            user = userService.find(userId);
        } catch (UserNotFoundException e) {
            responseBuilder.setResponseErrors(new String[]{"User not found"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase()).build(), HttpStatus.NOT_FOUND);
        }

        // retrieve account list
        List<Account> accountList = accountService.findAll(user);
        responseBuilder.setResponseResult(accountList);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> listAllUserAccountInfos(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;
        Account account = null;

        try {
            // retrieve user
            user = userService.find(userId);
        } catch (UserNotFoundException e) {
            responseBuilder.setResponseErrors(new String[]{"User not found"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase()).build(), HttpStatus.NOT_FOUND);
        }

        try {
            // retrieve account
            account = accountService.find(accountId, user);
            responseBuilder.setResponseResult(account);
        } catch (AccountNotFoundException e) {
            responseBuilder.setResponseErrors(new String[]{"Account not found"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase()).build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);
    }

    @RequestMapping(path = "/add/term",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> addTermAccount(@PathVariable("userId") Long userId, AccountTerm accountTerm) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        try {
            // retrieve user
            user = userService.find(userId);
        } catch (UserNotFoundException e) {
            responseBuilder.setResponseErrors(new String[]{"User not found"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase()).build(), HttpStatus.NOT_FOUND);
        }

        try {
            // create account
            accountTerm = accountService.add(accountTerm, user);
            responseBuilder.setResponseResult(accountTerm);
        } catch (BadRequestException e) {
            responseBuilder.setResponseErrors(Utils.getModelNullFields(accountTerm));
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.BAD_REQUEST.getReasonPhrase()).build(), HttpStatus.BAD_REQUEST);
        } catch (AccountCreationFailedException e) {
            responseBuilder.setResponseErrors(new String[]{"Account creation failed"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase()).build(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/add/current",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> addCurrentAccount(@PathVariable("userId") Long userId, AccountCurrent accountCurrent) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        try {
            // retrieve user
            user = userService.find(userId);
        } catch (UserNotFoundException e) {
            responseBuilder.setResponseErrors(new String[]{"User not found"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase()).build(), HttpStatus.NOT_FOUND);
        }

        try {
            // create account
            accountCurrent = accountService.add(accountCurrent, user);
            responseBuilder.setResponseResult(accountCurrent);
        } catch (BadRequestException e) {
            responseBuilder.setResponseErrors(Utils.getModelNullFields(accountCurrent));
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.BAD_REQUEST.getReasonPhrase()).build(), HttpStatus.BAD_REQUEST);
        } catch (AccountCreationFailedException e) {
            responseBuilder.setResponseErrors(new String[]{"Account creation failed"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase()).build(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/add/saving",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> addSavingAccount(@PathVariable("userId") Long userId, AccountSaving accountSaving) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        User user = null;

        try {
            // retrieve user
            user = userService.find(userId);
        } catch (UserNotFoundException e) {
            responseBuilder.setResponseErrors(new String[]{"User not found"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase()).build(), HttpStatus.NOT_FOUND);
        }

        try {
            // create account
            accountSaving = accountService.add(accountSaving, user);
            responseBuilder.setResponseResult(accountSaving);
        } catch (BadRequestException e) {
            responseBuilder.setResponseErrors(Utils.getModelNullFields(accountSaving));
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.BAD_REQUEST.getReasonPhrase()).build(), HttpStatus.BAD_REQUEST);
        } catch (AccountCreationFailedException e) {
            responseBuilder.setResponseErrors(new String[]{"Account creation failed"});
            return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase()).build(), HttpStatus.PRECONDITION_FAILED);
        }

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

}
