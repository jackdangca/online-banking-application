package com.onlinebank.core;

import com.onlinebank.account.*;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.promotion.PromotionBonus;
import com.onlinebank.promotion.PromotionService;
import com.onlinebank.promotion.PromotionTax;
import com.onlinebank.promotion.PromotionWithdrawalLimit;
import com.onlinebank.promotion.exceptions.PromotionNotFoundException;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import com.onlinebank.utils.AccountTypes;
import com.onlinebank.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by p0wontnx on 1/31/16.
 */
@Component
public class PromotionProcessor {

    private AccountService accountService;
    private UserService userService;
    private PromotionService promotionService;

    @Autowired
    public PromotionProcessor(AccountService accountService, UserService userService, PromotionService promotionService) {
        Assert.notNull(accountService);
        this.accountService = accountService;

        Assert.notNull(userService);
        this.userService = userService;

        Assert.notNull(promotionService);
        this.promotionService = promotionService;
    }

    @Scheduled(cron = "0 0 * * *")
    public void retrieveTaxFromAccount() throws AccountNotFoundException, PromotionNotFoundException {

        List<User> userList = userService.findAll();
        PromotionBonus promotionBonus = null;
        PromotionTax promotionTax = null;
        PromotionWithdrawalLimit promotionWithdrawalLimit = null;
        Date today = new Date();
        Date accountCreationDate = null;
        int daysFromAccountCreation = 0;

        for (User user : userList) {
            List<Account> accountList = accountService.findAll(user);

            for (Account account : accountList) {
                switch (account.getAccountType()) {
                    case AccountTypes.TERM_ACCOUNT:
                        // perform bonus promotion
                        AccountTerm accountTerm = accountService.findTermAccount(account.getAccountId(), user);
                        promotionBonus = promotionService.findBonusPromotion(accountTerm.getBonuspromotionId());
                        accountCreationDate = account.getCreationDate();
                        daysFromAccountCreation = Utils.daysBetween(today, accountCreationDate);
                        if (daysFromAccountCreation != 0 && daysFromAccountCreation % promotionBonus.getPeriod() == 0) {
                            account.setBalance(account.getBalance() * (1 + promotionBonus.getBonusPp()));
                        }
                        break;
                    case AccountTypes.CURRENT_ACCOUNT:
                        // perform tax promotion
                        AccountCurrent accountCurrent = accountService.findCurrentAccount(account.getAccountId(), user);
                        promotionTax = promotionService.findTaxPromotion(accountCurrent.getTaxpromotionId());
                        accountCreationDate = account.getCreationDate();
                        daysFromAccountCreation = Utils.daysBetween(today, accountCreationDate);
                        if (daysFromAccountCreation != 0 && daysFromAccountCreation % promotionTax.getPeriod() == 0) {
                            account.setBalance(account.getBalance() * (1 + promotionTax.getTaxPp()));
                        }
                        break;
                    case AccountTypes.SAVING_ACCOUNT:
                        // perform bonus promotion
                        AccountSaving accountSaving = accountService.findSavingAccount(account.getAccountId(), user);
                        promotionBonus = promotionService.findBonusPromotion(accountSaving.getBonuspromotionId());
                        accountCreationDate = account.getCreationDate();
                        daysFromAccountCreation = Utils.daysBetween(today, accountCreationDate);
                        if (daysFromAccountCreation != 0 && daysFromAccountCreation % promotionBonus.getPeriod() == 0) {
                            account.setBalance(account.getBalance() * (1 + promotionBonus.getBonusPp()));
                        }

                        // perform tax promotion
                        promotionTax = promotionService.findTaxPromotion(accountSaving.getTaxpromotionId());
                        accountCreationDate = account.getCreationDate();
                        daysFromAccountCreation = Utils.daysBetween(today, accountCreationDate);
                        if (daysFromAccountCreation != 0 && daysFromAccountCreation % promotionTax.getPeriod() == 0) {
                            account.setBalance(account.getBalance() * (1 + promotionTax.getTaxPp()));
                        }

                        break;
                }
            }

        }
    }

}
