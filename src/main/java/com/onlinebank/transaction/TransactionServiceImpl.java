package com.onlinebank.transaction;

import com.onlinebank.account.Account;
import com.onlinebank.account.AccountService;
import com.onlinebank.transaction.exceptions.TransactionFailedException;
import com.onlinebank.transaction.exceptions.TransactionNotFoundException;
import com.onlinebank.transaction.utils.TransactionTypes;
import com.onlinebank.utils.Utils;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
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
    private AccountService accountService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionA2ARepository transactionA2ARepository, TransactionCheckRepository transactionCheckRepository, TransactionTicketDepositRepository transactionTicketDepositRepository, AccountService accountService) {
        Assert.notNull(transactionRepository);
        this.transactionRepository = transactionRepository;

        Assert.notNull(transactionA2ARepository);
        this.transactionA2ARepository = transactionA2ARepository;

        Assert.notNull(transactionCheckRepository);
        this.transactionCheckRepository = transactionCheckRepository;

        Assert.notNull(transactionTicketDepositRepository);
        this.transactionTicketDepositRepository = transactionTicketDepositRepository;

        Assert.notNull(accountService);
        this.accountService = accountService;
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
    public Transaction find(Long transactionId, Account account) throws TransactionNotFoundException {

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

    @Override
    public TransactionA2A addA2ATransaction(TransactionA2A transactionA2A, Account srcAccount, Account dstAccount) throws BadRequestException, TransactionFailedException {

        transactionA2A.setSrcAccountId(srcAccount.getAccountId());
        transactionA2A.setSrcAccountNum(srcAccount.getNumber());
        transactionA2A.setDstAccountId(dstAccount.getAccountId());
        transactionA2A.setDstAccountNum(dstAccount.getNumber());
        transactionA2A.setDate(new Date());

        // verify null fields
        if (Utils.isModelFieldNull(transactionA2A)) {
            throw new BadRequestException(transactionA2A);
        }

        // TODO : withdrawal verifier
        try {
            accountService.transfer(srcAccount, dstAccount, transactionA2A.getBalance());
            transactionA2A = transactionA2ARepository.save(transactionA2A);
        } catch (Exception e) {
            throw new TransactionFailedException("Transaction failed");
        }

        return transactionA2A;

    }
}
