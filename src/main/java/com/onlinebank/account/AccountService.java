package com.onlinebank.account;

import com.onlinebank.account.exceptions.AccountCreationFailedException;
import com.onlinebank.account.exceptions.AccountEditingException;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.user.User;
import com.onlinebank.utils.exceptions.BadRequestException;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
public interface AccountService {

    List<Account> findAll(User user);

    Account find(Long accountId, User user) throws AccountNotFoundException;

    AccountTransaction findTransactionAccount(Long transactionAccountId, User user) throws AccountNotFoundException;

    AccountTerm findTermAccount(Long termAccountId, User user) throws AccountNotFoundException;

    AccountCurrent findCurrentAccount(Long currentAccountId, User user) throws AccountNotFoundException;

    AccountSaving findSavingAccount(Long savingAccountId, User user) throws AccountNotFoundException;

    Account add(Account account, User user) throws BadRequestException, AccountCreationFailedException;

    Account edit(Long accountId, Account account, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException;

    AccountTerm add(AccountTerm accountTerm, User user) throws BadRequestException, AccountCreationFailedException;

    AccountTerm edit(Long accountId, AccountTerm accountTerm, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException;

    AccountTransaction add(AccountTransaction accountTransaction, User user) throws BadRequestException, AccountCreationFailedException;

    AccountTransaction edit(Long accountId, AccountTransaction accountTransaction, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException;

    AccountSaving add(AccountSaving accountSaving, User user) throws BadRequestException, AccountCreationFailedException;

    AccountSaving edit(Long accountId, AccountSaving accountSaving, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException;

    AccountCurrent add(AccountCurrent accountCurrent, User user) throws BadRequestException, AccountCreationFailedException;

    AccountCurrent edit(Long accoutnId, AccountCurrent accountCurrent, User user) throws BadRequestException, AccountEditingException, AccountNotFoundException;

    void remove(long accountId, User user) throws AccountNotFoundException;

}
