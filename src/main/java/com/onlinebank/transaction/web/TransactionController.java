package com.onlinebank.transaction.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onlinebank.account.Account;
import com.onlinebank.account.AccountService;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.transaction.Transaction;
import com.onlinebank.transaction.TransactionA2A;
import com.onlinebank.transaction.TransactionService;
import com.onlinebank.transaction.exceptions.TransactionFailedException;
import com.onlinebank.transaction.exceptions.TransactionNotFoundException;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import com.onlinebank.user.exceptions.UserNotFoundException;
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
 * Created by p0wontnx on 2/17/16.
 */
@RestController
@RequestMapping(path = "/api/user/{userId}/account/{accountId}")
public class TransactionController {

    private TransactionService transactionService;
    private UserService userService;
    private AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService, AccountService accountService) {
        Assert.notNull(transactionService);
        this.transactionService = transactionService;

        Assert.notNull(userService);
        this.userService = userService;

        Assert.notNull(accountService);
        this.accountService = accountService;
    }

    @RequestMapping(path = {"/transaction", "/transaction/"}, method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> getAllAccountTransactions(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) throws UserNotFoundException, AccountNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // retrieve user
        User user = userService.find(userId);

        // retrieve account
        Account account = accountService.find(accountId, user);

        // retrieve all transactions
        List<Transaction> transactionList = transactionService.findAll(account);

        responseBuilder.setResponseResult(transactionList);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/transaction/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> getAccountTransactionInfos(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId, @PathVariable("transactionId") Long transactionId) throws UserNotFoundException, AccountNotFoundException, TransactionNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // retrieve user
        User user = userService.find(userId);

        // retrieve account
        Account account = accountService.find(accountId, user);

        // retrieve transaction
        Transaction transaction = transactionService.find(transactionId, account);

        responseBuilder.setResponseResult(transaction);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/transfer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> transferMoney(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId, TransactionA2A transactionA2A) throws UserNotFoundException, AccountNotFoundException, BadRequestException, TransactionFailedException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // retrieve user
        User user = userService.find(userId);

        // retrieve accounts
        Account srcAccount = accountService.find(accountId, user);
        Account dstAccount = new Account();

        if (transactionA2A.getDstAccountNum() != null) {
            dstAccount = accountService.findByNumber(transactionA2A.getDstAccountNum());
        }

        transactionService.addA2ATransaction(transactionA2A, srcAccount, dstAccount);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

}
