package com.onlinebank.transaction;

import com.onlinebank.account.Account;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.transaction.exceptions.TransactionNotFoundException;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
public interface TransactionService {

    List<Transaction> findAll(Account account) throws AccountNotFoundException;

    Transaction find(Long transactionId, Account account) throws AccountNotFoundException, TransactionNotFoundException;

    TransactionA2A findA2ATransaction(Long a2atransactionId, Account account) throws AccountNotFoundException, TransactionNotFoundException;

    TransactionCheck findCheckTransaction(Long transactionId, Account account) throws AccountNotFoundException, TransactionNotFoundException;

    TransactionTicketDeposit findTicketDeposit(Long transactionId, Account account) throws AccountNotFoundException, TransactionNotFoundException;

}
