package com.onlinebank.promotion;

import com.onlinebank.promotion.exceptions.PromotionCreationFailed;
import com.onlinebank.promotion.exceptions.PromotionNotFoundException;
import com.onlinebank.utils.exceptions.BadRequestException;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
public interface PromotionService {

    List<Promotion> findAll();

    Promotion find(Long promotionId) throws PromotionNotFoundException;

    PromotionTax findTaxPromotion(Long taxpromotionId) throws PromotionNotFoundException;

    PromotionBonus findBonusPromotion(Long bonuspromotionId) throws PromotionNotFoundException;

    PromotionWithdrawalLimit findWithdrawalLimitPromotion(Long withdrawalLimitPromotionId) throws PromotionNotFoundException;

    PromotionBonus add(PromotionBonus promotionBonus) throws BadRequestException, PromotionCreationFailed;

    PromotionTax add(PromotionTax promotionTax) throws BadRequestException, PromotionCreationFailed;

    PromotionWithdrawalLimit add(PromotionWithdrawalLimit promotionWithdrawalLimit) throws BadRequestException, PromotionCreationFailed;

    PromotionBonus edit(Long promotionBonusId, PromotionBonus promotionBonus) throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException;

    PromotionTax edit(Long promotionTaxId, PromotionTax promotionTax) throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException;

    PromotionWithdrawalLimit edit(Long promotionWithdrawalLimitId, PromotionWithdrawalLimit promotionWithdrawalLimit) throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException;

    void remove(long promotionId) throws PromotionNotFoundException;

}
