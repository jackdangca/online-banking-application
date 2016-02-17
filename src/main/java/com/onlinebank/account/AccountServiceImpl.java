package com.onlinebank.account;

import com.onlinebank.account.exceptions.AccountCreationFailedException;
import com.onlinebank.account.exceptions.AccountEditingException;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.user.User;
import com.onlinebank.utils.AccountTypes;
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

    //region Account finding
    @Override
    public List<Account> findAll(User user) {
        // retrieve all user accounts
        List<Account> accountList = accountRepository.findAllByUserId(user.getUserId());
        return accountList;
    }

    @Override
    public Account find(Long accountId, User user) throws AccountNotFoundException {
        // retrieve a user account
        Account account = accountRepository.findOneByAccountIdAndUserId(accountId, user.getUserId());
        if (account == null) {
            throw new AccountNotFoundException();
        }

        switch (account.getAccountType()) {
            case AccountTypes.CURRENT_ACCOUNT:
                account = findCurrentAccount(accountId, user);
                break;
            case AccountTypes.SAVING_ACCOUNT:
                account = findSavingAccount(accountId, user);
                break;
            case AccountTypes.TERM_ACCOUNT:
                account = findTermAccount(accountId, user);
                break;
        }

        return account;
    }

    @Override
    public AccountTransaction findTransactionAccount(Long transactionAccountId, User user) throws AccountNotFoundException {
        // retrieve a user account
        Account account = accountRepository.findOneByAccountIdAndUserId(transactionAccountId, user.getUserId());
        AccountTransaction accountTransaction = accountTransactionRepository.findOne(account.getAccountId());
        if (accountTransaction == null) {
            throw new AccountNotFoundException();
        }
        return accountTransaction;
    }

    @Override
    public AccountTerm findTermAccount(Long termAccountId, User user) throws AccountNotFoundException {
        // retrieve a user account
        Account account = accountRepository.findOneByAccountIdAndUserId(termAccountId, user.getUserId());
        AccountTerm accountTerm = accountTermRepository.findOne(account.getAccountId());
        if (accountTerm == null) {
            throw new AccountNotFoundException();
        }
        return accountTerm;
    }

    @Override
    public AccountCurrent findCurrentAccount(Long currentAccountId, User user) throws AccountNotFoundException {
        // retrieve a user account
        Account account = accountRepository.findOneByAccountIdAndUserId(currentAccountId, user.getUserId());
        AccountCurrent accountCurrent = accountCurrentRepository.findOne(account.getAccountId());
        if (accountCurrent == null) {
            throw new AccountNotFoundException();
        }
        return accountCurrent;
    }

    @Override
    public AccountSaving findSavingAccount(Long savingAccountId, User user) throws AccountNotFoundException {
        // retrieve a user account
        Account account = accountRepository.findOneByAccountIdAndUserId(savingAccountId, user.getUserId());
        AccountSaving accountSaving = accountSavingRepository.findOne(account.getAccountId());
        if (accountSaving == null) {
            throw new AccountNotFoundException();
        }
        return accountSaving;
    }

    //endregion

    //region Account creation
    @Override
    public Account add(Account account, User user) throws BadRequestException, AccountCreationFailedException {

        // generate account number and password
        Long accountNumber = Math.abs(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        String accountPassword = Utils.generateString(new Random(), "0123456789", 8);

        while (accountRepository.findOneByNumber(accountNumber) != null) {
            accountNumber = Math.abs(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        }

        account.setNumber(accountNumber);
        account.setPassword(accountPassword);
        account.setBalance(0d);
        account.setCreationDate(new Date());
        account.setUserId(user.getUserId());

        // verify not null attributes
        if (Utils.isModelFieldNull(account)) {
            throw new BadRequestException(account);
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

        while (accountRepository.findOneByNumber(accountNumber) != null) {
            accountNumber = Math.abs(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        }

        accountTerm.setNumber(accountNumber);
        accountTerm.setPassword(accountPassword);
        accountTerm.setBalance(0d);
        accountTerm.setCreationDate(new Date());
        accountTerm.setUserId(user.getUserId());
        accountTerm.setAccountType(AccountTypes.TERM_ACCOUNT);

        // verify not null attributes
        if (Utils.isModelFieldNull(accountTerm)) {
            throw new BadRequestException(accountTerm);
        }

        try {
            // save term account
            accountTerm = accountTermRepository.save(accountTerm);
        } catch (DataIntegrityViolationException e) {
            throw new AccountCreationFailedException();
        }
        // verify if account was added successfully
        if (accountTerm.getAccountId() != null) {
            accountTerm.setTermaccountId(accountTerm.getAccountId());
            return accountTerm;
        }
        throw new AccountCreationFailedException();
    }

    @Override
    public AccountTransaction add(AccountTransaction accountTransaction, User user) throws BadRequestException, AccountCreationFailedException {

        // generate account number and password
        Long accountNumber = Math.abs(UUID.randomUUID().getMostSignificantBits());
        String accountPassword = Utils.generateString(new Random(), "0123456789", 8);

        while (accountRepository.findOneByNumber(accountNumber) != null) {
            accountNumber = Math.abs(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        }

        accountTransaction.setNumber(accountNumber);
        accountTransaction.setPassword(accountPassword);
        accountTransaction.setBalance(0d);
        accountTransaction.setCreationDate(new Date());
        accountTransaction.setUserId(user.getUserId());

        // verify not null attributes
        if (Utils.isModelFieldNull(accountTransaction)) {
            throw new BadRequestException(accountTransaction);
        }

        try {
            // save transaction account
            accountTransaction = accountTransactionRepository.save(accountTransaction);
        } catch (DataIntegrityViolationException e) {
            throw new AccountCreationFailedException();
        }
        // verify if account was added successfully
        if (accountTransaction.getAccountId() != null) {
            accountTransaction.setTransactionaccountId(accountTransaction.getAccountId());
            return accountTransaction;
        }
        throw new AccountCreationFailedException();
    }

    @Override
    public AccountSaving add(AccountSaving accountSaving, User user) throws BadRequestException, AccountCreationFailedException {

        // generate account number and password
        Long accountNumber = Math.abs(UUID.randomUUID().getMostSignificantBits());
        String accountPassword = Utils.generateString(new Random(), "0123456789", 8);

        while (accountRepository.findOneByNumber(accountNumber) != null) {
            accountNumber = Math.abs(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        }

        accountSaving.setNumber(accountNumber);
        accountSaving.setPassword(accountPassword);
        accountSaving.setBalance(0d);
        accountSaving.setCreationDate(new Date());
        accountSaving.setUserId(user.getUserId());
        accountSaving.setAccountType(AccountTypes.SAVING_ACCOUNT);

        // verify not null attributes
        if (Utils.isModelFieldNull(accountSaving)) {
            throw new BadRequestException(accountSaving);
        }

        try {
            // save saving account
            accountSaving = accountSavingRepository.save(accountSaving);
        } catch (DataIntegrityViolationException e) {
            throw new AccountCreationFailedException();
        }
        // verify if account was added successfully
        if (accountSaving.getAccountId() != null) {
            accountSaving.setSavingaccountId(accountSaving.getAccountId());
            accountSaving.setTransactionaccountId(accountSaving.getAccountId());
            return accountSaving;
        }
        throw new AccountCreationFailedException();
    }

    @Override
    public AccountCurrent add(AccountCurrent accountCurrent, User user) throws BadRequestException, AccountCreationFailedException {

        // generate account number and password
        Long accountNumber = Math.abs(UUID.randomUUID().getMostSignificantBits());
        String accountPassword = Utils.generateString(new Random(), "0123456789", 8);

        while (accountRepository.findOneByNumber(accountNumber) != null) {
            accountNumber = Math.abs(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        }

        accountCurrent.setNumber(accountNumber);
        accountCurrent.setPassword(accountPassword);
        accountCurrent.setBalance(0d);
        accountCurrent.setCreationDate(new Date());
        accountCurrent.setUserId(user.getUserId());
        accountCurrent.setAccountType(AccountTypes.CURRENT_ACCOUNT);

        // verify not null attributes
        if (Utils.isModelFieldNull(accountCurrent)) {
            throw new BadRequestException(accountCurrent);
        }

        try {
            // save current account
            accountCurrent = accountCurrentRepository.save(accountCurrent);
        } catch (DataIntegrityViolationException e) {
            throw new AccountCreationFailedException();
        }
        // verify if account was added successfully
        if (accountCurrent.getAccountId() != null) {
            accountCurrent.setCurrentaccountId(accountCurrent.getCurrentaccountId());
            accountCurrent.setTransactionaccountId(accountCurrent.getCurrentaccountId());
            return accountCurrent;
        }
        throw new AccountCreationFailedException();
    }
    //endregion

    //region Account editing
    @Override
    public Account edit(Long accountId, Account account, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException {

        // retrieve a user account
        Account oldAccount = accountRepository.findOneByAccountIdAndUserId(accountId, user.getUserId());

        if (oldAccount == null) {
            throw new AccountNotFoundException();
        }

        // update account label
        if (account.getLabel() != null) {
            oldAccount.setLabel(account.getLabel());
        }

        try {
            // update account
            oldAccount = accountRepository.save(oldAccount);
        } catch (DataIntegrityViolationException e) {
            throw new AccountEditingException();
        }
        // verify if account was updated successfully
        if (oldAccount.getAccountId() != null) {
            return oldAccount;
        }
        throw new AccountEditingException();
    }

    @Override
    public AccountTerm edit(Long accountId, AccountTerm accountTerm, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException {

        // retrieve a user account
        Account oldAccount = accountRepository.findOneByAccountIdAndUserId(accountId, user.getUserId());

        if (oldAccount == null) {
            throw new AccountNotFoundException();
        }

        AccountTerm oldTermAccount = accountTermRepository.findOne(accountId);

        if (oldTermAccount == null) {
            throw new AccountNotFoundException();
        }

        // update account label
        if (accountTerm.getLabel() != null) {
            oldTermAccount.setLabel(accountTerm.getLabel());
        }

        // update account duration
        if (accountTerm.getDuration() != null) {
            oldTermAccount.setDuration(accountTerm.getDuration());
        }

        // update account bonus promotion
        if (accountTerm.getBonuspromotionId() != null) {
            oldTermAccount.setBonuspromotionId(accountTerm.getBonuspromotionId());
        }

        try {
            // update account
            oldTermAccount = accountRepository.save(oldTermAccount);
        } catch (DataIntegrityViolationException e) {
            throw new AccountEditingException();
        }
        // verify if account was updated successfully
        if (oldTermAccount.getAccountId() != null) {
            return oldTermAccount;
        }
        throw new AccountEditingException();
    }

    @Override
    public AccountTransaction edit(Long accountId, AccountTransaction accountTransaction, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException {

        // retrieve a user account
        Account oldAccount = accountRepository.findOneByAccountIdAndUserId(accountId, user.getUserId());

        if (oldAccount == null) {
            throw new AccountNotFoundException();
        }

        AccountTransaction oldTransactionAccount = accountTransactionRepository.findOne(accountId);

        if (oldTransactionAccount == null) {
            throw new AccountNotFoundException();
        }

        // update account label
        if (accountTransaction.getLabel() != null) {
            oldTransactionAccount.setLabel(accountTransaction.getLabel());
        }

        try {
            // update account
            oldTransactionAccount = accountRepository.save(oldTransactionAccount);
        } catch (DataIntegrityViolationException e) {
            throw new AccountEditingException();
        }
        // verify if account was updated successfully
        if (oldTransactionAccount.getAccountId() != null) {
            return oldTransactionAccount;
        }
        throw new AccountEditingException();
    }

    @Override
    public AccountSaving edit(Long accountId, AccountSaving accountSaving, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException {

        // retrieve a user account
        Account oldAccount = accountRepository.findOneByAccountIdAndUserId(accountId, user.getUserId());

        if (oldAccount == null) {
            throw new AccountNotFoundException();
        }

        AccountSaving oldSavingAccount = accountSavingRepository.findOne(accountId);

        if (oldSavingAccount == null) {
            throw new AccountNotFoundException();
        }

        // update account label
        if (accountSaving.getLabel() != null) {
            oldSavingAccount.setLabel(accountSaving.getLabel());
        }

        // update account bonus promotion
        if (accountSaving.getBonuspromotionId() != null) {
            oldSavingAccount.setBonuspromotionId(accountSaving.getBonuspromotionId());
        }

        // update account tax promotion
        if (accountSaving.getTaxpromotionId() != null) {
            oldSavingAccount.setTaxpromotionId(accountSaving.getTaxpromotionId());
        }

        // update account withdrawallimit promotion
        if (accountSaving.getWithdrawallimitpromotionId() != null) {
            oldSavingAccount.setWithdrawallimitpromotionId(accountSaving.getWithdrawallimitpromotionId());
        }

        try {
            // update account
            oldSavingAccount = accountRepository.save(oldSavingAccount);
        } catch (DataIntegrityViolationException e) {
            throw new AccountEditingException();
        }
        // verify if account was updated successfully
        if (oldSavingAccount.getAccountId() != null) {
            return oldSavingAccount;
        }
        throw new AccountEditingException();
    }

    @Override
    public AccountCurrent edit(Long accountId, AccountCurrent accountCurrent, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException {

        // retrieve a user account
        Account oldAccount = accountRepository.findOneByAccountIdAndUserId(accountId, user.getUserId());

        if (oldAccount == null) {
            throw new AccountNotFoundException();
        }

        AccountCurrent oldCurrentAccount = accountCurrentRepository.findOne(accountId);

        if (oldCurrentAccount == null) {
            throw new AccountNotFoundException();
        }

        // update account label
        if (accountCurrent.getLabel() != null) {
            oldCurrentAccount.setLabel(accountCurrent.getLabel());
        }

        // update account tax promotion
        if (accountCurrent.getTaxpromotionId() != null) {
            oldCurrentAccount.setTaxpromotionId(accountCurrent.getTaxpromotionId());
        }

        try {
            // update account
            oldCurrentAccount = accountRepository.save(oldCurrentAccount);
        } catch (DataIntegrityViolationException e) {
            throw new AccountEditingException();
        }
        // verify if account was updated successfully
        if (oldCurrentAccount.getAccountId() != null) {
            return oldCurrentAccount;
        }
        throw new AccountEditingException();
    }
    //endregion

    //region Account delete
    @Override
    public void remove(long accountId, User user) throws AccountNotFoundException {

        // retrieve account
        Account account = accountRepository.findOneByAccountIdAndUserId(accountId, user.getUserId());
        if (user == null) {
            throw new AccountNotFoundException();
        }
        accountRepository.delete(account);
    }
    //endregion

}
