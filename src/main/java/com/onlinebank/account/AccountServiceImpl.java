package com.onlinebank.account;

import com.onlinebank.account.exceptions.AccountCreationFailedException;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.user.User;
import com.onlinebank.utils.Utils;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Service
class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private AccountTermRepository accountTermRepository;
    private AccountTransactionRepository accountTransactionRepository;
    private AccountSavingRepository accountSavingRepository;
    private AccountCurrentRepository accountCurrentRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountTermRepository accountTermRepository, AccountTransactionRepository accountTransactionRepository, AccountSavingRepository accountSavingRepository, AccountCurrentRepository accountCurrentRepository) {
        Assert.notNull(accountRepository);
        this.accountRepository = accountRepository;
        Assert.notNull(accountTermRepository);
        this.accountTermRepository = accountTermRepository;
        Assert.notNull(accountTransactionRepository);
        this.accountTransactionRepository = accountTransactionRepository;
        Assert.notNull(accountSavingRepository);
        this.accountSavingRepository = accountSavingRepository;
        Assert.notNull(accountCurrentRepository);
        this.accountCurrentRepository = accountCurrentRepository;

    }

    @Override
    public List<Account> findAll(User user) {
        // retrieve all user accounts
        List<Account> accountList = accountRepository.findAllByUserId(user.getUserId());
        return accountList;
    }

    @Override
    public Account find(Long account_id, User user) throws AccountNotFoundException {
        // retrieve a user account
        Account account = accountRepository.findOneByAccountIdAndUserId(account_id, user.getUserId());
        if (account == null) {
            throw new AccountNotFoundException();
        }
        return account;
    }

    @Override
    public Account add(Account account, User user) throws BadRequestException, AccountCreationFailedException {

        // generate account number and password
        Long accountNumber = Math.abs(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        String accountPassword = Utils.generateString(new Random(), "0123456789", 8);

        account.setNumber(accountNumber);
        account.setPassword(accountPassword);
        account.setBalance(0d);
        account.setCreationDate(new Date());
        account.setUserId(user.getUserId());

        // verify not null attributes
        if (Utils.isModelFieldNull(account)) {
            throw new BadRequestException();
        }

        try {
            // save account
            account = accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new AccountCreationFailedException();
        }
        // verify if account was added successfully
        if (account.getAccountId() != null) {
            return account;
        }
        throw new AccountCreationFailedException();
    }

    @Override
    public AccountTerm add(AccountTerm accountTerm, User user) throws BadRequestException, AccountCreationFailedException {

        // generate account number and password
        Long accountNumber = Math.abs(UUID.randomUUID().getMostSignificantBits());
        String accountPassword = Utils.generateString(new Random(), "0123456789", 8);

        accountTerm.setNumber(accountNumber);
        accountTerm.setPassword(accountPassword);
        accountTerm.setBalance(0d);
        accountTerm.setCreationDate(new Date());
        accountTerm.setUserId(user.getUserId());

        // verify not null attributes
        if (Utils.isModelFieldNull(accountTerm)) {
            throw new BadRequestException();
        }

        try {
            // save term account
            accountTerm = accountTermRepository.save(accountTerm);
        } catch (DataIntegrityViolationException e) {
            throw new AccountCreationFailedException();
        }
        // verify if account was added successfully
        if (accountTerm.getAccountId() != null) {
            return accountTerm;
        }
        throw new AccountCreationFailedException();
    }

    @Override
    public AccountTransaction add(AccountTransaction accountTransaction, User user) throws BadRequestException, AccountCreationFailedException {

        // generate account number and password
        Long accountNumber = Math.abs(UUID.randomUUID().getMostSignificantBits());
        String accountPassword = Utils.generateString(new Random(), "0123456789", 8);

        accountTransaction.setNumber(accountNumber);
        accountTransaction.setPassword(accountPassword);
        accountTransaction.setBalance(0d);
        accountTransaction.setCreationDate(new Date());
        accountTransaction.setUserId(user.getUserId());

        // verify not null attributes
        if (Utils.isModelFieldNull(accountTransaction)) {
            throw new BadRequestException();
        }

        try {
            // save transaction account
            accountTransaction = accountTransactionRepository.save(accountTransaction);
        } catch (DataIntegrityViolationException e) {
            throw new AccountCreationFailedException();
        }
        // verify if account was added successfully
        if (accountTransaction.getAccountId() != null) {
            return accountTransaction;
        }
        throw new AccountCreationFailedException();
    }

    @Override
    public AccountSaving add(AccountSaving accountSaving, User user) throws BadRequestException, AccountCreationFailedException {

        // generate account number and password
        Long accountNumber = Math.abs(UUID.randomUUID().getMostSignificantBits());
        String accountPassword = Utils.generateString(new Random(), "0123456789", 8);

        accountSaving.setNumber(accountNumber);
        accountSaving.setPassword(accountPassword);
        accountSaving.setBalance(0d);
        accountSaving.setCreationDate(new Date());
        accountSaving.setUserId(user.getUserId());

        // verify not null attributes
        if (Utils.isModelFieldNull(accountSaving)) {
            throw new BadRequestException();
        }

        try {
            // save saving account
            accountSaving = accountSavingRepository.save(accountSaving);
        } catch (DataIntegrityViolationException e) {
            throw new AccountCreationFailedException();
        }
        // verify if account was added successfully
        if (accountSaving.getAccountId() != null) {
            return accountSaving;
        }
        throw new AccountCreationFailedException();
    }

    @Override
    public AccountCurrent add(AccountCurrent accountCurrent, User user) throws BadRequestException, AccountCreationFailedException {

        // generate account number and password
        Long accountNumber = Math.abs(UUID.randomUUID().getMostSignificantBits());
        String accountPassword = Utils.generateString(new Random(), "0123456789", 8);

        accountCurrent.setNumber(accountNumber);
        accountCurrent.setPassword(accountPassword);
        accountCurrent.setBalance(0d);
        accountCurrent.setCreationDate(new Date());
        accountCurrent.setUserId(user.getUserId());

        // verify not null attributes
        if (Utils.isModelFieldNull(accountCurrent)) {
            throw new BadRequestException();
        }

        try {
            // save current account
            accountCurrent = accountCurrentRepository.save(accountCurrent);
        } catch (DataIntegrityViolationException e) {
            throw new AccountCreationFailedException();
        }
        // verify if account was added successfully
        if (accountCurrent.getAccountId() != null) {
            return accountCurrent;
        }
        throw new AccountCreationFailedException();
    }
}
