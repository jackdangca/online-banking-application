package com.onlinebank.account;

import com.onlinebank.account.exceptions.AccountCreationFailedException;
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

    Account add(Account account, User user) throws BadRequestException, AccountCreationFailedException;

    AccountTerm add(AccountTerm accountTerm, User user) throws BadRequestException, AccountCreationFailedException;

    AccountTransaction add(AccountTransaction accountTransaction, User user) throws BadRequestException, AccountCreationFailedException;

    AccountSaving add(AccountSaving accountSaving, User user) throws BadRequestException, AccountCreationFailedException;

    AccountCurrent add(AccountCurrent accountCurrent, User user) throws BadRequestException, AccountCreationFailedException;

}
