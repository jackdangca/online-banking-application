package com.onlinebank.account;

import com.onlinebank.OnlineBankApplicationTests;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import com.onlinebank.utils.Utils;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * Created by p0wontnx on 1/29/16.
 */
@Transactional
public class AccountServiceTests extends OnlineBankApplicationTests {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    public void testValidAccountCreation() throws Exception {

        Account account = new Account();
        // init account
        account.setLabel("Account1");

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        account = accountService.add(account, user);

        Assert.assertNotNull("failure - expect returned account not null", account);

    }

    @Test(expected = BadRequestException.class)
    public void testBadAccountCreationRequest() throws Exception {

        Account account = new Account();

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        account = accountService.add(account, user);

    }

    @Test
    public void testValidTermAccountCreation() throws Exception {

        AccountTerm accountTerm = new AccountTerm();
        // init AccountTerm
        accountTerm.setLabel("TermAccount1");
        accountTerm.setDuration(20);
        accountTerm.setBonuspromotionId(4l);

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        accountTerm = accountService.add(accountTerm, user);

        Assert.assertNotNull("failure - expect returned term account not null", accountTerm);

    }

    @Test(expected = BadRequestException.class)
    public void testBadTermAccountCreation() throws Exception {

        AccountTerm accountTerm = new AccountTerm();
        // init AccountTerm
        accountTerm.setLabel("TermAccount1");
        accountTerm.setDuration(20);
        // accountTerm.setBonuspromotionId(4l);

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        accountTerm = accountService.add(accountTerm, user);

    }

    @Test
    public void testValidTransactionAccountCreation() throws Exception {

        AccountTransaction accountTransaction = new AccountTransaction();
        // init AccountTransaction
        accountTransaction.setLabel("TransactionAccount1");

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        accountTransaction = accountService.add(accountTransaction, user);

        Assert.assertNotNull("failure - expect returned transaction account not null", accountTransaction);

    }

    @Test(expected = BadRequestException.class)
    public void testBadTransactionAccountCreation() throws Exception {

        AccountTransaction accountTransaction = new AccountTransaction();
        // init AccountTransaction
        // accountTransaction.setLabel("TransactionAccount1");

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        accountTransaction = accountService.add(accountTransaction, user);

    }

    @Test
    public void testValidCurrentAccountCreation() throws Exception {

        AccountCurrent accountCurrent = new AccountCurrent();
        // init AccountCurrent
        accountCurrent.setLabel("CurrentAccount1");
        accountCurrent.setTaxpromotionId(2l);

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        accountCurrent = accountService.add(accountCurrent, user);

        Assert.assertNotNull("failure - expect returned current account not null", accountCurrent);

    }

    @Test(expected = BadRequestException.class)
    public void testBadCurrentAccountCreation() throws Exception {

        AccountCurrent accountCurrent = new AccountCurrent();
        // init AccountCurrent
        accountCurrent.setLabel("CurrentAccount1");
        // accountCurrent.setTaxpromotionId(2l);

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        accountCurrent = accountService.add(accountCurrent, user);

    }

    @Test
    public void testValidSavingAccountCreation() throws Exception {

        AccountSaving accountSaving = new AccountSaving();
        // init AccountSaving
        accountSaving.setLabel("SavingAccount1");
        accountSaving.setTaxpromotionId(2l);
        accountSaving.setBonuspromotionId(4l);
        accountSaving.setWithdrawallimitpromotionId(1l);

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        accountSaving = accountService.add(accountSaving, user);

        Assert.assertNotNull("failure - expect returned current account not null", accountSaving);

    }

    @Test(expected = BadRequestException.class)
    public void testBadSavingAccountCreation() throws Exception {

        AccountSaving accountSaving = new AccountSaving();
        // init AccountSaving
        accountSaving.setLabel("SavingAccount1");
        accountSaving.setTaxpromotionId(2l);
        accountSaving.setBonuspromotionId(4l);
        // accountSaving.setWithdrawallimitpromotionId(1l);

        // register user to add account
        User user = new User();
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        accountSaving = accountService.add(accountSaving, user);

    }

}
