package com.onlinebank.transaction;

import com.onlinebank.account.Account;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.transaction.exceptions.TransactionNotFoundException;
import com.onlinebank.utils.TransactionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p0wontnx on 2/17/16.
 */
@Service
class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionA2ARepository transactionA2ARepository;
    private TransactionCheckRepository transactionCheckRepository;
    private TransactionTicketDepositRepository transactionTicketDepositRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionA2ARepository transactionA2ARepository, TransactionCheckRepository transactionCheckRepository, TransactionTicketDepositRepository transactionTicketDepositRepository) {
        Assert.notNull(transactionRepository);
        this.transactionRepository = transactionRepository;

        Assert.notNull(transactionA2ARepository);
        this.transactionA2ARepository = transactionA2ARepository;

        Assert.notNull(transactionCheckRepository);
        this.transactionCheckRepository = transactionCheckRepository;

        Assert.notNull(transactionTicketDepositRepository);
        this.transactionTicketDepositRepository = transactionTicketDepositRepository;
    }

    @Override
    public List<Transaction> findAll(Account account) {

        List<Transaction> transactionList = new ArrayList<>();

        // retrieve A2A transactions
        List<TransactionA2A> a2aTransactions = transactionA2ARepository.findAllBySrcAccountIdOrDstAccountId(account.getAccountId(), account.getAccountId());
        if (a2aTransactions != null && !a2aTransactions.isEmpty()) {
            transactionList.addAll(a2aTransactions);
        }

        // retrieve check transactions
        List<TransactionCheck> checkTransactions = transactionCheckRepository.findAllBySrcAccountIdOrDstAccountId(account.getAccountId(), account.getAccountId());
        if (checkTransactions != null && !checkTransactions.isEmpty()) {
            transactionList.addAll(checkTransactions);
        }

        // retrieve ticket transactions
        List<TransactionTicketDeposit> ticketDepositsTransactions = transactionTicketDepositRepository.findAllByDstAccountId(account.getAccountId());
        if (ticketDepositsTransactions != null && !ticketDepositsTransactions.isEmpty()) {
            transactionList.addAll(ticketDepositsTransactions);
        }

        return transactionList;
    }

    @Override
    public Transaction find(Long transactionId, Account account) throws AccountNotFoundException, TransactionNotFoundException {

        // retrieve transaction
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);

        if (transaction == null) {
            throw new TransactionNotFoundException();
        }

        switch (transaction.getTransactionType()) {
            case TransactionTypes.A2A_TRANSACTION:
                transaction = findA2ATransaction(transactionId, account);
                break;
            case TransactionTypes.CHECK_TRANSACTION:
                transaction = findCheckTransaction(transactionId, account);
                break;
            case TransactionTypes.TICKET_TRANSACTION:
                transaction = findTicketDeposit(transactionId, account);
                break;
        }

        return transaction;
    }

    @Override
    public TransactionA2A findA2ATransaction(Long a2atransactionId, Account account) throws TransactionNotFoundException {

        // retrieve A2ATransaction
        TransactionA2A transactionA2A = transactionA2ARepository.findByA2atransactionIdAndAccountId(a2atransactionId, account.getAccountId());

        if (transactionA2A == null) {
            throw new TransactionNotFoundException();
        }

        return transactionA2A;
    }

    @Override
    public TransactionCheck findCheckTransaction(Long transactionId, Account account) throws TransactionNotFoundException {

        // retrieve TransactionCheck
        TransactionCheck transactionCheck = transactionCheckRepository.findByChecktransactionIdAndAccountId(transactionId, account.getAccountId());

        if (transactionCheck == null) {
            throw new TransactionNotFoundException();
        }

        return transactionCheck;
    }

    @Override
    public TransactionTicketDeposit findTicketDeposit(Long transactionId, Account account) throws TransactionNotFoundException {

        // retrieve TransactionTicketDeposit
        TransactionTicketDeposit transactionTicketDeposit = transactionTicketDepositRepository.findByTicketdepositIdAndAccountId(transactionId, account.getAccountId());

        if (transactionTicketDeposit == null) {
            throw new TransactionNotFoundException();
        }

        return transactionTicketDeposit;
    }
}
