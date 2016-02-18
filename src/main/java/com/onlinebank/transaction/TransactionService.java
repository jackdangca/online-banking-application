package com.onlinebank.transaction;

import com.onlinebank.account.Account;
import com.onlinebank.transaction.exceptions.TransactionFailedException;
import com.onlinebank.transaction.exceptions.TransactionNotFoundException;
import com.onlinebank.utils.exceptions.BadRequestException;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
public interface TransactionService {

    List<Transaction> findAll(Account account);

    Transaction find(Long transactionId, Account account) throws TransactionNotFoundException;

    TransactionA2A findA2ATransaction(Long a2atransactionId, Account account) throws TransactionNotFoundException;

    TransactionCheck findCheckTransaction(Long transactionId, Account account) throws TransactionNotFoundException;

    TransactionTicketDeposit findTicketDeposit(Long transactionId, Account account) throws TransactionNotFoundException;

    TransactionA2A addA2ATransaction(TransactionA2A transactionA2A, Account srcAccount, Account dstAccount) throws BadRequestException, TransactionFailedException;
}
