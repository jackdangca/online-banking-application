package com.onlinebank.promotion;

import com.onlinebank.OnlineBankApplicationTests;
import com.onlinebank.promotion.exceptions.PromotionCreationFailed;
import com.onlinebank.promotion.exceptions.PromotionNotFoundException;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by p0wontnx on 2/18/16.
 */
@Transactional
public class PromotionServiceTests extends OnlineBankApplicationTests {


    @Autowired
    private PromotionService promotionService;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testValidBonusPromotionCeation() throws BadRequestException, PromotionCreationFailed {

        PromotionBonus promotionBonus = new PromotionBonus();

        // init bonus promotion
        promotionBonus.setBonusPp(1.0);
        promotionBonus.setPeriod(366);

        // add promotion
        promotionBonus = promotionService.add(promotionBonus);

        Assert.assertNotNull("failure - expect returned bonus promotion not null", promotionBonus);

    }

    @Test(expected = BadRequestException.class)
    public void testInValidBonusPromotionCeation() throws BadRequestException, PromotionCreationFailed {

        PromotionBonus promotionBonus = new PromotionBonus();

        // init bonus promotion
        promotionBonus.setBonusPp(1.0);
        //promotionBonus.setPeriod(366);

        // add promotion
        promotionBonus = promotionService.add(promotionBonus);

    }

    @Test
    public void testValidTaxPromotionCeation() throws BadRequestException, PromotionCreationFailed {

        PromotionTax promotionTax = new PromotionTax();

        // init tax promotion
        promotionTax.setTaxPp(1.0);
        promotionTax.setPeriod(366);
        promotionTax.setTaxPt(1.0);

        // add promotion
        promotionTax = promotionService.add(promotionTax);
        Assert.assertNotNull("failure - expect returned promotion tax not null", promotionTax);

    }

    @Test(expected = BadRequestException.class)
    public void testInValidTaxPromotionCeation() throws BadRequestException, PromotionCreationFailed {

        PromotionTax promotionTax = new PromotionTax();

        // init tax promotion
        promotionTax.setTaxPp(1.0);
        promotionTax.setPeriod(366);
        //promotionTax.setTaxPt(1.0);

        // add promotion
        promotionTax = promotionService.add(promotionTax);

    }

    @Test
    public void testValidWithdrawalLimitPromotionCeation() throws BadRequestException, PromotionCreationFailed {

        PromotionWithdrawalLimit promotionWithdrawalLimit = new PromotionWithdrawalLimit();

        // init withdrawallimit promotion
        promotionWithdrawalLimit.setLimitPp(1.0);
        promotionWithdrawalLimit.setPeriod(366);

        // add promotion
        promotionWithdrawalLimit = promotionService.add(promotionWithdrawalLimit);

        Assert.assertNotNull("failure - expect returned promotion withdrawallimit not null", promotionWithdrawalLimit);

    }

    @Test(expected = BadRequestException.class)
    public void testInValidWithdrawalLimitPromotionCeation() throws BadRequestException, PromotionCreationFailed {

        PromotionWithdrawalLimit promotionWithdrawalLimit = new PromotionWithdrawalLimit();

        // init withdrawallimit promotion
        promotionWithdrawalLimit.setLimitPp(1.0);
        //promotionWithdrawalLimit.setPeriod(366);

        // add promotion
        promotionWithdrawalLimit = promotionService.add(promotionWithdrawalLimit);

    }

    @Test
    public void testValidBonusPromotionEdit() throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        PromotionBonus promotionBonus = new PromotionBonus();

        // init bonus promotion
        promotionBonus.setBonusPp(1.0);
        promotionBonus.setPeriod(366);

        // add promotion
        promotionBonus = promotionService.add(promotionBonus);

        Assert.assertNotNull("failure - expect returned bonus promotion not null", promotionBonus);

        promotionBonus.setPeriod(365);

        // edit promotion
        promotionBonus = promotionService.edit(promotionBonus.getBonuspromotionId(), promotionBonus);

        Assert.assertEquals("failure - expect promotion period equals 365", 365, (int) promotionBonus.getPeriod());

    }

    @Test
    public void testValidTaxPromotionEdit() throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        PromotionTax promotionTax = new PromotionTax();

        // init tax promotion
        promotionTax.setTaxPp(1.0);
        promotionTax.setPeriod(366);
        promotionTax.setTaxPt(1.0);

        // add promotion
        promotionTax = promotionService.add(promotionTax);
        Assert.assertNotNull("failure - expect returned promotion tax not null", promotionTax);

        promotionTax.setPeriod(365);

        // edit promotion
        promotionTax = promotionService.edit(promotionTax.getTaxpromotionId(), promotionTax);

        Assert.assertEquals("failure - expect promotion period equals 365", 365, (int) promotionTax.getPeriod());

    }

    @Test
    public void testValidWithdrawalLimitPromotionEdit() throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        PromotionWithdrawalLimit promotionWithdrawalLimit = new PromotionWithdrawalLimit();

        // init withdrawallimit promotion
        promotionWithdrawalLimit.setLimitPp(1.0);
        promotionWithdrawalLimit.setPeriod(366);

        // add promotion
        promotionWithdrawalLimit = promotionService.add(promotionWithdrawalLimit);

        Assert.assertNotNull("failure - expect returned promotion withdrawallimit not null", promotionWithdrawalLimit);

        promotionWithdrawalLimit.setPeriod(365);

        // edit promotion
        promotionWithdrawalLimit = promotionService.edit(promotionWithdrawalLimit.getWithdrawallimitpromotionId(), promotionWithdrawalLimit);

        Assert.assertEquals("failure - expect promotion period equals 365", 365, (int) promotionWithdrawalLimit.getPeriod());

    }

    @Test(expected = PromotionNotFoundException.class)
    public void testValidTaxPromotionRemove() throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        PromotionTax promotionTax = new PromotionTax();

        // init tax promotion
        promotionTax.setTaxPp(1.0);
        promotionTax.setPeriod(366);
        promotionTax.setTaxPt(1.0);

        // add promotion
        promotionTax = promotionService.add(promotionTax);
        Assert.assertNotNull("failure - expect returned promotion tax not null", promotionTax);

        // remove promotion
        promotionService.remove(promotionTax.getPromotionId());

        Assert.assertNull("failure - expect returned promotion null", promotionService.find(promotionTax.getPromotionId()));

    }

}
